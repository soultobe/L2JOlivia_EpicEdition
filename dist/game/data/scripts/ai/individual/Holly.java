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
package ai.individual;

import com.l2jolivia.gameserver.enums.ChatType;
import com.l2jolivia.gameserver.model.actor.L2Npc;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.network.NpcStringId;

import ai.npc.AbstractNpcAI;

/**
 * Holly AI.
 * @author Gladicek
 */
final class Holly extends AbstractNpcAI
{
	// NPCs
	private static final int HOLLY = 33219;
	
	private Holly()
	{
		super(Holly.class.getSimpleName(), "ai/individual");
		addSpawnId(HOLLY);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("SPAM_TEXT") && (npc != null))
		{
			broadcastNpcSay(npc, ChatType.NPC_GENERAL, NpcStringId.GIRAN_SHUTTLE_DOES_NOT_COME_ANYMORE_IT_S_ALL_IN_THE_PAST, 1000);
		}
		else if (event.equals("SOCIAL_ACTION") && (npc != null))
		{
			npc.broadcastSocialAction(6);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("SPAM_TEXT", 10000, npc, null, true);
		startQuestTimer("SOCIAL_ACTION", 2000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Holly();
	}
}