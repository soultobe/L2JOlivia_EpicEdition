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
package com.l2jolivia.gameserver.network.serverpackets.shuttle;

import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.network.serverpackets.L2GameServerPacket;

/**
 * @author UnAfraid
 */
public class ExMoveToLocationInShuttle extends L2GameServerPacket
{
	private final int _charObjId;
	private final int _airShipId;
	private final int _targetX, _targetY, _targetZ;
	private final int _fromX, _fromY, _fromZ;
	
	public ExMoveToLocationInShuttle(L2PcInstance player, int fromX, int fromY, int fromZ)
	{
		_charObjId = player.getObjectId();
		_airShipId = player.getShuttle().getObjectId();
		_targetX = player.getInVehiclePosition().getX();
		_targetY = player.getInVehiclePosition().getY();
		_targetZ = player.getInVehiclePosition().getZ();
		_fromX = fromX;
		_fromY = fromY;
		_fromZ = fromZ;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0xCF);
		writeD(_charObjId);
		writeD(_airShipId);
		writeD(_targetX);
		writeD(_targetY);
		writeD(_targetZ);
		writeD(_fromX);
		writeD(_fromY);
		writeD(_fromZ);
	}
}
