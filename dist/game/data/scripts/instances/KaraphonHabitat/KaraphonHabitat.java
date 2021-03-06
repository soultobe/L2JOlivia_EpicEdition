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
package instances.KaraphonHabitat;

import com.l2jolivia.gameserver.instancemanager.InstanceManager;
import com.l2jolivia.gameserver.model.Location;
import com.l2jolivia.gameserver.model.actor.L2Npc;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.model.instancezone.InstanceWorld;
import com.l2jolivia.gameserver.model.quest.QuestState;

import instances.AbstractInstance;
import quests.Q10745_TheSecretIngredients.Q10745_TheSecretIngredients;

/**
 * @author Neanrakyr
 */
public class KaraphonHabitat extends AbstractInstance
{
	// NPCs
	private static final int DOLKIN = 33954;
	private static final int DOLKIN_INSTANCE = 34002;
	// Locations
	private static final Location START_LOC = new Location(-82250, 246406, -14152);
	private static final Location EXIT_LOC = new Location(-88240, 237450, -2880);
	// Instance
	private static final int TEMPLATE_ID = 253;
	
	class KHWorld extends InstanceWorld
	{
	}
	
	public KaraphonHabitat()
	{
		super(KaraphonHabitat.class.getSimpleName());
		addFirstTalkId(DOLKIN_INSTANCE);
		addStartNpc(DOLKIN, DOLKIN_INSTANCE);
		addTalkId(DOLKIN, DOLKIN_INSTANCE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = player.getQuestState(Q10745_TheSecretIngredients.class.getSimpleName());
		if (qs == null)
		{
			return null;
		}
		
		if (event.equals("enter_instance"))
		{
			enterInstance(player, new KHWorld(), "KaraphonHabitat.xml", TEMPLATE_ID);
		}
		else if (event.equals("exit_instance"))
		{
			final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
			if (!(world instanceof KHWorld))
			{
				return null;
			}
			world.removeAllowed(player.getObjectId());
			teleportPlayer(player, EXIT_LOC, 0);
		}
		
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return "34002.html";
	}
	
	@Override
	public void onEnterInstance(L2PcInstance player, InstanceWorld world, boolean firstEntrance)
	{
		if (firstEntrance)
		{
			world.addAllowed(player.getObjectId());
		}
		teleportPlayer(player, START_LOC, world.getInstanceId());
	}
}