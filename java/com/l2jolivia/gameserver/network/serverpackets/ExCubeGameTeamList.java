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

import java.util.List;

import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author mrTJO
 */
public class ExCubeGameTeamList extends L2GameServerPacket
{
	// Players Lists
	List<L2PcInstance> _bluePlayers;
	List<L2PcInstance> _redPlayers;
	
	// Common Values
	int _roomNumber;
	
	/**
	 * Show Minigame Waiting List to Player
	 * @param redPlayers Red Players List
	 * @param bluePlayers Blue Players List
	 * @param roomNumber Arena/Room ID
	 */
	public ExCubeGameTeamList(List<L2PcInstance> redPlayers, List<L2PcInstance> bluePlayers, int roomNumber)
	{
		_redPlayers = redPlayers;
		_bluePlayers = bluePlayers;
		_roomNumber = roomNumber - 1;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0x98);
		writeD(0x00);
		
		writeD(_roomNumber);
		writeD(0xffffffff);
		
		writeD(_bluePlayers.size());
		for (L2PcInstance player : _bluePlayers)
		{
			writeD(player.getObjectId());
			writeS(player.getName());
		}
		writeD(_redPlayers.size());
		for (L2PcInstance player : _redPlayers)
		{
			writeD(player.getObjectId());
			writeS(player.getName());
		}
	}
}