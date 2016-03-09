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
package ai.npc.Tunatun;

import com.l2jolivia.gameserver.model.actor.L2Npc;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.model.quest.QuestState;

import ai.npc.AbstractNpcAI;
import quests.Q00020_BringUpWithLove.Q00020_BringUpWithLove;

/**
 * Beast Herder Tunatun AI.
 * @author Adry_85
 */
final class Tunatun extends AbstractNpcAI
{
	// NPC
	private static final int TUNATUN = 31537;
	// Item
	private static final int BEAST_HANDLERS_WHIP = 15473;
	// Misc
	private static final int MIN_LEVEL = 82;
	
	private Tunatun()
	{
		super(Tunatun.class.getSimpleName(), "ai/npc");
		addStartNpc(TUNATUN);
		addFirstTalkId(TUNATUN);
		addTalkId(TUNATUN);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ("Whip".equals(event))
		{
			if (hasQuestItems(player, BEAST_HANDLERS_WHIP))
			{
				return "31537-01.html";
			}
			
			final QuestState qs = player.getQuestState(Q00020_BringUpWithLove.class.getSimpleName());
			if ((qs == null) && (player.getLevel() < MIN_LEVEL))
			{
				return "31537-02.html";
			}
			else if ((qs != null) || (player.getLevel() >= MIN_LEVEL))
			{
				giveItems(player, BEAST_HANDLERS_WHIP, 1);
				return "31537-03.html";
			}
		}
		return event;
	}
	
	public static void main(String[] args)
	{
		new Tunatun();
	}
}