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
package com.l2jolivia.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.Map;

import com.l2jolivia.gameserver.instancemanager.CastleManorManager;
import com.l2jolivia.gameserver.model.CropProcure;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.model.items.instance.L2ItemInstance;

public class SellListProcure extends L2GameServerPacket
{
	private final long _money;
	private final Map<L2ItemInstance, Long> _sellList = new HashMap<>();
	
	public SellListProcure(L2PcInstance player, int castleId)
	{
		_money = player.getAdena();
		for (CropProcure c : CastleManorManager.getInstance().getCropProcure(castleId, false))
		{
			final L2ItemInstance item = player.getInventory().getItemByItemId(c.getId());
			if ((item != null) && (c.getAmount() > 0))
			{
				_sellList.put(item, c.getAmount());
			}
		}
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xEF);
		writeQ(_money); // money
		writeD(0x00); // lease ?
		writeH(_sellList.size()); // list size
		
		for (L2ItemInstance item : _sellList.keySet())
		{
			writeH(item.getItem().getType1());
			writeD(item.getObjectId());
			writeD(item.getDisplayId());
			writeQ(_sellList.get(item)); // count
			writeH(item.getItem().getType2());
			writeH(0); // unknown
			writeQ(0); // price, u shouldnt get any adena for crops, only raw materials
		}
	}
}
