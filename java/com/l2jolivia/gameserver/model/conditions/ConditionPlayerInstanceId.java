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
package com.l2jolivia.gameserver.model.conditions;

import java.util.ArrayList;

import com.l2jolivia.gameserver.instancemanager.InstanceManager;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.instancezone.InstanceWorld;
import com.l2jolivia.gameserver.model.items.L2Item;
import com.l2jolivia.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerInstanceId.
 */
public class ConditionPlayerInstanceId extends Condition
{
	private final ArrayList<Integer> _instanceIds;
	
	/**
	 * Instantiates a new condition player instance id.
	 * @param instanceIds the instance ids
	 */
	public ConditionPlayerInstanceId(ArrayList<Integer> instanceIds)
	{
		_instanceIds = instanceIds;
	}
	
	@Override
	public boolean testImpl(L2Character effector, L2Character effected, Skill skill, L2Item item)
	{
		if (effector.getActingPlayer() == null)
		{
			return false;
		}
		
		final int instanceId = effector.getInstanceId();
		if (instanceId <= 0)
		{
			return false; // player not in instance
		}
		
		final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(effector.getActingPlayer());
		if ((world == null) || (world.getInstanceId() != instanceId))
		{
			return false; // player in the different instance
		}
		return _instanceIds.contains(world.getTemplateId());
	}
}
