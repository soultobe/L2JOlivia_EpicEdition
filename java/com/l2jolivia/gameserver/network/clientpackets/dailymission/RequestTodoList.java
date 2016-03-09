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
package com.l2jolivia.gameserver.network.clientpackets.dailymission;

import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.network.clientpackets.L2GameClientPacket;
import com.l2jolivia.gameserver.network.serverpackets.dailymission.ExOneDayReceiveRewardList;
import com.l2jolivia.gameserver.network.serverpackets.dailymission.ExTodoListInzone;

/**
 * @author Olivia
 */
public class RequestTodoList extends L2GameClientPacket
{
	private int _tab;
	@SuppressWarnings("unused")
	private int _showAllLevels;
	
	@Override
	protected void readImpl()
	{
		_tab = readC(); // Daily Reward = 9, Event = 1, Instance Zone = 2
		_showAllLevels = readC(); // Disabled = 0, Enabled = 1
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		if (player == null)
		{
			return;
		}
		
		switch (_tab)
		{
			case 1:
			{
				player.sendPacket(new ExTodoListInzone());
				break;
			}
			case 2:
			{
				player.sendPacket(new ExTodoListInzone());
				break;
			}
			case 9:
			{
				player.sendPacket(new ExOneDayReceiveRewardList(player));
				break;
			}
		}
	}
	
	@Override
	public String getType()
	{
		return getClass().getSimpleName();
	}
}