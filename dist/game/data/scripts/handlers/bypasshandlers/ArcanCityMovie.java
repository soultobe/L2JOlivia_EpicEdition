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
package handlers.bypasshandlers;

import com.l2jolivia.gameserver.handler.IBypassHandler;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Olivia
 */
public class ArcanCityMovie implements IBypassHandler
{
	// Npc
	private static final int MUMU = 32900;
	// Others
	private static final int ARCAN_ENTER_MOVIE = 104;
	private static final String[] COMMANDS =
	{
		"ArcanCityMovie"
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!target.isNpc() || (((L2NpcInstance) target).getId() != MUMU))
		{
			return false;
		}
		
		activeChar.showQuestMovie(ARCAN_ENTER_MOVIE);
		return true;
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}
