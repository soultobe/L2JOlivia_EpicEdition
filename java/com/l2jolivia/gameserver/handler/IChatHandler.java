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
package com.l2jolivia.gameserver.handler;

import com.l2jolivia.gameserver.enums.ChatType;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

/**
 * Interface for chat handlers
 * @author durgus
 */
public interface IChatHandler
{
	/**
	 * Handles a specific type of chat messages
	 * @param type
	 * @param activeChar
	 * @param target
	 * @param text
	 */
	public void handleChat(ChatType type, L2PcInstance activeChar, String target, String text);
	
	/**
	 * Returns a list of all chat types registered to this handler
	 * @return
	 */
	public ChatType[] getChatTypeList();
}