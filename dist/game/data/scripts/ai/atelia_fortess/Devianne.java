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
package ai.atelia_fortess;

import com.l2jolivia.gameserver.model.Location;
import com.l2jolivia.gameserver.model.actor.L2Npc;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

import ai.npc.AbstractNpcAI;

/**
 * Devianne AI.
 * @author hlwrave
 * @URL https://l2wiki.com/Atelia_Fortress
 */
final class Devianne extends AbstractNpcAI
{
	// NPCs
	private static final int BURNSTAIN = 23587;
	private static final int DEVIANNE = 34089;
	// Location
	private static final Location DEVIANNE_LOC = new Location(-50063, 49439, -1760, 40362);
	// Other
	private static final int DESPAWN = 3600000; // Time 1 Hour
	
	private Devianne()
	{
		super(Devianne.class.getSimpleName(), "ai/atelia_fortess");
		addKillId(BURNSTAIN);
		addSpawnId(DEVIANNE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("devianne_despawn"))
		{
			if (npc != null)
			{
				npc.deleteMe();
			}
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final L2Npc devianne = addSpawn(DEVIANNE, DEVIANNE_LOC);
		startQuestTimer("devianne_despawn", DESPAWN, devianne, null);
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new Devianne();
	}
}