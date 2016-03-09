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

import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Sdw
 */
public class ExResponseResetList extends L2GameServerPacket
{
	private final L2PcInstance _activeChar;
	
	public ExResponseResetList(L2PcInstance activeChar)
	{
		_activeChar = activeChar;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0x137);
		
		writeQ(_activeChar.getAdena());
		writeQ(_activeChar.getBeautyTickets());
		
		writeD(_activeChar.getAppearance().getHairStyle());
		writeD(_activeChar.getAppearance().getHairColor());
		writeD(_activeChar.getAppearance().getFace());
	}
}