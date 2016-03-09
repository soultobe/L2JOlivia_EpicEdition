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

import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Olivia
 */
public class ExFishingEnd extends L2GameServerPacket
{
	private final int _win;
	private final L2Character _activeChar;
	
	public ExFishingEnd(int win, L2PcInstance character)
	{
		_win = win;
		_activeChar = character;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0x1F);
		writeD(_activeChar.getObjectId());
		writeC(_win); // lose 0 - win 1 - quit 2
	}
}