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

import com.l2jolivia.Config;
import com.l2jolivia.gameserver.data.xml.impl.BuyListData;
import com.l2jolivia.gameserver.model.L2Object;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.actor.instance.L2MerchantInstance;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.model.buylist.L2BuyList;
import com.l2jolivia.gameserver.model.items.L2Item;
import com.l2jolivia.gameserver.model.items.instance.L2ItemInstance;
import com.l2jolivia.gameserver.network.SystemMessageId;
import com.l2jolivia.gameserver.network.serverpackets.ActionFailed;
import com.l2jolivia.gameserver.network.serverpackets.ExBuySellList;
import com.l2jolivia.gameserver.network.serverpackets.ExUserInfoInvenWeight;
import com.l2jolivia.gameserver.util.Util;

/**
 * RequestRefundItem client packet class.
 */
public final class RequestRefundItem extends L2GameClientPacket
{
	private static final String _C__D0_75_REQUESTREFUNDITEM = "[C] D0:75 RequestRefundItem";
	
	private static final int BATCH_LENGTH = 4; // length of the one item
	private static final int CUSTOM_CB_SELL_LIST = 423;
	
	private int _listId;
	private int[] _items = null;
	
	@Override
	protected void readImpl()
	{
		_listId = readD();
		final int count = readD();
		if ((count <= 0) || (count > Config.MAX_ITEM_IN_PACKET) || ((count * BATCH_LENGTH) != _buf.remaining()))
		{
			return;
		}
		
		_items = new int[count];
		for (int i = 0; i < count; i++)
		{
			_items[i] = readD();
		}
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		if (player == null)
		{
			return;
		}
		
		if (!getClient().getFloodProtectors().getTransaction().tryPerformAction("refund"))
		{
			player.sendMessage("You are using refund too fast.");
			return;
		}
		
		if (_items == null)
		{
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (!player.hasRefund())
		{
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final L2Object target = player.getTarget();
		L2Character merchant = null;
		if (!player.isGM() && (_listId != CUSTOM_CB_SELL_LIST))
		{
			if (!(target instanceof L2MerchantInstance) || (!player.isInsideRadius(target, INTERACTION_DISTANCE, true, false)) || (player.getInstanceId() != target.getInstanceId()))
			{
				sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			merchant = (L2Character) target;
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
		
		if ((merchant != null) && !buyList.isNpcAllowed(merchant.getId()))
		{
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		long weight = 0;
		long adena = 0;
		long slots = 0;
		
		final L2ItemInstance[] refund = player.getRefund().getItems();
		final int[] objectIds = new int[_items.length];
		
		for (int i = 0; i < _items.length; i++)
		{
			final int idx = _items[i];
			if ((idx < 0) || (idx >= refund.length))
			{
				Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent invalid refund index", Config.DEFAULT_PUNISH);
				return;
			}
			
			// check for duplicates - indexes
			for (int j = i + 1; j < _items.length; j++)
			{
				if (idx == _items[j])
				{
					Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " sent duplicate refund index", Config.DEFAULT_PUNISH);
					return;
				}
			}
			
			final L2ItemInstance item = refund[idx];
			final L2Item template = item.getItem();
			objectIds[i] = item.getObjectId();
			
			// second check for duplicates - object ids
			for (int j = 0; j < i; j++)
			{
				if (objectIds[i] == objectIds[j])
				{
					Util.handleIllegalPlayerAction(player, "Warning!! Character " + player.getName() + " of account " + player.getAccountName() + " has duplicate items in refund list", Config.DEFAULT_PUNISH);
					return;
				}
			}
			
			final long count = item.getCount();
			weight += count * template.getWeight();
			adena += (count * template.getReferencePrice()) / 2;
			if (!template.isStackable())
			{
				slots += count;
			}
			else if (player.getInventory().getItemByItemId(template.getId()) == null)
			{
				slots++;
			}
		}
		
		if ((weight > Integer.MAX_VALUE) || (weight < 0) || !player.getInventory().validateWeight((int) weight))
		{
			player.sendPacket(SystemMessageId.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if ((slots > Integer.MAX_VALUE) || (slots < 0) || !player.getInventory().validateCapacity((int) slots))
		{
			player.sendPacket(SystemMessageId.YOUR_INVENTORY_IS_FULL);
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if ((adena < 0) || !player.reduceAdena("Refund", adena, player.getLastFolkNPC(), false))
		{
			player.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		for (int i = 0; i < _items.length; i++)
		{
			final L2ItemInstance item = player.getRefund().transferItem("Refund", objectIds[i], Long.MAX_VALUE, player.getInventory(), player, player.getLastFolkNPC());
			if (item == null)
			{
				_log.warning("Error refunding object for char " + player.getName() + " (newitem == null)");
				continue;
			}
		}
		
		// Update current load status on player
		player.sendPacket(new ExUserInfoInvenWeight(player));
		player.sendPacket(new ExBuySellList(player, true));
	}
	
	@Override
	public String getType()
	{
		return _C__D0_75_REQUESTREFUNDITEM;
	}
}