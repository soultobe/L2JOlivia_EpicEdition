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

/**
 * @author Olivia
 */
public class ExAutoSoulShot extends L2GameServerPacket
{
	private final int _itemId;
	private final int _enabled;
	private final int _type; // 0 SS, 1 SPS, 2 Beast SS, 3 Beast SPS
	
	/**
	 * @param itemId
	 * @param enabled
	 * @param type
	 */
	public ExAutoSoulShot(int itemId, int enabled, int type)
	{
		_itemId = itemId;
		_enabled = enabled;
		_type = type;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xFE);
		writeH(0x0C);
		writeD(_itemId);
		writeD(_enabled);
		writeD(_type);
	}
}