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
package com.l2jolivia.gameserver.network.serverpackets.commission;

import com.l2jolivia.gameserver.model.commission.CommissionItem;
import com.l2jolivia.gameserver.network.serverpackets.AbstractItemPacket;

/**
 * @author NosBit
 */
public class ExResponseCommissionBuyInfo extends AbstractItemPacket
{
	public static final ExResponseCommissionBuyInfo FAILED = new ExResponseCommissionBuyInfo(null);
	
	private final CommissionItem _commissionItem;
	
	public ExResponseCommissionBuyInfo(CommissionItem commissionItem)
	{
		_commissionItem = commissionItem;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0xF8);
		writeD(_commissionItem != null ? 1 : 0);
		if (_commissionItem != null)
		{
			writeQ(_commissionItem.getPricePerUnit());
			writeQ(_commissionItem.getCommissionId());
			writeD(0); // CommissionItemType seems client does not really need it.
			writeCommissionItem(_commissionItem.getItemInfo());
		}
	}
}
