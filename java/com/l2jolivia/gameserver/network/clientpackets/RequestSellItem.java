/*
 * This file is part of the L2J Olivia project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jolivia.gameserver.network.clientpackets;

import static com.l2jolivia.gameserver.model.actor.L2Npc.INTERACTION_DISTANCE;
import static com.l2jolivia.gameserver.model.itemcontainer.Inventory.MAX_ADENA;

import java.util.ArrayList;
import java.util.List;

import com.l2jolivia.Config;
import com.l2jolivia.gameserver.data.xml.impl.BuyListData;
import com.l2jolivia.gameserver.model.L2Object;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.actor.instance.L2MerchantInstance;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.model.buylist.L2BuyList;
import com.l2jolivia.gameserver.model.holders.UniqueItemHolder;
import com.l2jolivia.gameserver.model.items.instance.L2ItemInstance;
import com.l2jolivia.gameserver.network.serverpackets.ActionFailed;
import com.l2jolivia.gameserver.network.serverpackets.ExBuySellList;
import com.l2jolivia.gameserver.network.serverpackets.ExUserInfoInvenWeight;
import com.l2jolivia.gameserver.util.Util;

/**
 * RequestSellItem client packet class.
 */
public final class RequestSellItem extends L2GameClientPacket
{
	private static final String _C__37_REQUESTSELLITEM = "[C] 37 RequestSellItem";
	
	private static final int BATCH_LENGTH = 16;
	private static final int CUSTOM_CB_SELL_LIST = 423;
	
	private int _listId;
	private List<UniqueItemHolder> _items = null;
	
	@Override
	protected void readImpl()
	{
		_listId = readD();
		final int size = readD();
		if ((size <= 0) || (size > Config.MAX_ITEM_IN_PACKET) || ((size * BATCH_LENGTH) != _buf.remaining()))
		{
			return;
		}
		
		_items = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
		{
			final int objectId = readD();
			final int itemId = readD();
			final long count = readQ();
			if ((objectId < 1) || (itemId < 1) || (count < 1))
			{
				_items = null;
				return;
			}
			_items.add(new UniqueItemHolder(itemId, objectId, count));
		}
	}
	
	@Override
	protected void runImpl()
	{
		processSell();
	}
	
	protected void processSell()
	{
		final L2PcInstance player = getClient().getActiveChar();
		
		if (player == null)
		{
			return;
		}
		
		if (!getClient().getFloodProtectors().getTransaction().tryPerformAction("buy"))
		{
			player.sendMessage("You are buying too fast.");
			return;
		}
		
		if (_items == null)
		{
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Alt game - Karma punishment
		if (!Config.ALT_GAME_KARMA_PLAYER_CAN_SHOP && (player.getReputation() < 0))
		{
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final L2Object target = player.getTarget();
		L2Character merchant = null;
		if (!player.isGM() && (_listId != CUSTOM_CB_SELL_LIST))
		{
			if ((target == null) || (!player.isInsideRadius(target, INTERACTION_DISTANCE, true, false)) || (player.getInstanceId() != target.getInstanceId()))
			{
				sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			if (target instanceof L2MerchantInstance)
			{
				merchant = (L2Character) target;
			}
			else
			{
				sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
		}
		
		if ((merchant == null) && !player.isGM() && (_listId != CUSTOM_CB_SELL_LIST))
		{
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final L2BuyList buyList = BuyListData.getInstance().getBuyList(_listId);
		if (buyList == null)
		{
			Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent a false BuyList list_id " + _listId, Config.DEFAULT_PUNISH);
			return;
		}
		
		if (merchant != null)
		{
			if (!buyList.isNpcAllowed(merchant.getId()))
			{
				sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
		}
		
		long totalPrice = 0;
		// Proceed the sell
		for (UniqueItemHolder i : _items)
		{
			L2ItemInstance item = player.checkItemManipulation(i.getObjectId(), i.getCount(), "sell");
			if ((item == null) || (!item.isSellable()))
			{
				continue;
			}
			
			final long price = item.getReferencePrice() / 2;
			totalPrice += price * i.getCount();
			if (((MAX_ADENA / i.getCount()) < price) || (totalPrice > MAX_ADENA))
			{
				Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " tried to purchase over " + MAX_ADENA + " adena worth of goods.", Config.DEFAULT_PUNISH);
				return;
			}
			
			if (Config.ALLOW_REFUND)
			{
				item = player.getInventory().transferItem("Sell", i.getObjectId(), i.getCount(), player.getRefund(), player, merchant);
			}
			else
			{
				item = player.getInventory().destroyItem("Sell", i.getObjectId(), i.getCount(), player, merchant);
			}
		}
		player.addAdena("Sell", totalPrice, merchant, false);
		
		// Update current load as well
		player.sendPacket(new ExUserInfoInvenWeight(player));
		player.sendPacket(new ExBuySellList(player, true));
	}
	
	@Override
	public String getType()
	{
		return _C__37_REQUESTSELLITEM;
	}
}
