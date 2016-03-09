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

import java.util.ArrayList;
import java.util.List;

import com.l2jolivia.gameserver.ai.CtrlIntention;
import com.l2jolivia.gameserver.enums.ChatType;
import com.l2jolivia.gameserver.model.Location;
import com.l2jolivia.gameserver.model.actor.L2Npc;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.model.skills.Skill;
import com.l2jolivia.gameserver.network.NpcStringId;
import com.l2jolivia.gameserver.network.serverpackets.NpcSay;
import com.l2jolivia.util.Rnd;

import ai.npc.AbstractNpcAI;

/**
 * Leopard Dragon Hachling AI.
 * @author Olivia
 */
final class LeopardDragonHachling extends AbstractNpcAI
{
	// NPCs
	private static final int DRAGON_HACHLING = 23434;
	private static final int LEOPARD_DRAGON = 23435;
	// Locations
	private static final List<Location> TRANSFORM_LOCATIONS = new ArrayList<>();
	
	{
		TRANSFORM_LOCATIONS.add(new Location(84199, 120022, -2944));
		TRANSFORM_LOCATIONS.add(new Location(92138, 113735, -3076));
		TRANSFORM_LOCATIONS.add(new Location(103925, 122422, -3776));
		TRANSFORM_LOCATIONS.add(new Location(122040, 115493, -3648));
	}
	
	private LeopardDragonHachling()
	{
		super(LeopardDragonHachling.class.getSimpleName(), "ai/individual");
		addAttackId(DRAGON_HACHLING);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((npc != null) && event.equals("MOVE_TO_TRANSFORM"))
		{
			if (npc.calculateDistance(nearestLocation(npc), false, false) < 100)
			{
				final int random = Rnd.get(1, 4);
				for (int counter = 1; counter < random; counter++)
				{
					final L2Npc leopard = addSpawn(LEOPARD_DRAGON, npc.getLocation(), true, 300000); // 5 minute despawn time
					leopard.broadcastPacket(new NpcSay(leopard.getObjectId(), ChatType.NPC_GENERAL, LEOPARD_DRAGON, NpcStringId.I_M_GOING_TO_TRANSFORM_WITH_THE_POWER_OF_THE_VORTEX_YOU_JUST_WATCH));
					addAttackDesire(leopard, player);
				}
				cancelQuestTimer("MOVE_TO_TRANSFORM", npc, player);
				npc.deleteMe();
			}
			else
			{
				npc.abortAttack();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, nearestLocation(npc));
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon, Skill skill)
	{
		if (npc.getScriptValue() == 0)
		{
			npc.setScriptValue(1);
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), ChatType.NPC_GENERAL, DRAGON_HACHLING, NpcStringId.HEY_THAT_HURT_YOU_JUST_WAIT_HERE_AND_I_LL_BE_BACK_AS_A_STRONGER_DRAGON));
			startQuestTimer("MOVE_TO_TRANSFORM", 1000, npc, attacker, true);
		}
		npc.abortAttack();
		npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, nearestLocation(npc));
		return null;
	}
	
	private Location nearestLocation(L2Npc npc)
	{
		Location gotoLoc = TRANSFORM_LOCATIONS.get(0);
		for (Location loc : TRANSFORM_LOCATIONS)
		{
			if (npc.calculateDistance(loc, false, false) < npc.calculateDistance(gotoLoc, false, false))
			{
				gotoLoc = loc;
			}
		}
		return gotoLoc;
	}
	
	public static void main(String[] args)
	{
		new LeopardDragonHachling();
	}
}