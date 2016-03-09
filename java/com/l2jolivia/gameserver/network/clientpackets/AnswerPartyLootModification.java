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
package com.l2jolivia.gameserver.network.clientpackets;

import com.l2jolivia.gameserver.model.L2Party;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author JIV
 */
public class AnswerPartyLootModification extends L2GameClientPacket
{
	private static final String _C__D0_79_ANSWERPARTYLOOTMODIFICATION = "[C] D0:79 AnswerPartyLootModification";
	
	public int _answer;
	
	@Override
	protected void readImpl()
	{
		_answer = readD();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}
		final L2Party party = activeChar.getParty();
		if (party != null)
		{
			party.answerLootChangeRequest(activeChar, _answer == 1);
		}
	}
	
	@Override
	public String getType()
	{
		return _C__D0_79_ANSWERPARTYLOOTMODIFICATION;
	}
}
