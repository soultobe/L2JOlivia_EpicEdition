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
package handlers.effecthandlers;

import com.l2jolivia.gameserver.ai.CtrlIntention;
import com.l2jolivia.gameserver.model.StatsSet;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.conditions.Condition;
import com.l2jolivia.gameserver.model.effects.AbstractEffect;
import com.l2jolivia.gameserver.model.effects.L2EffectType;
import com.l2jolivia.gameserver.model.skills.BuffInfo;

public class RemoveTarget extends AbstractEffect
{
	public RemoveTarget(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.REMOVE_TARGET;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		final L2Character effected = info.getEffected();
		effected.setTarget(null);
		effected.abortAttack();
		effected.abortCast();
		effected.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, info.getEffector());
	}
}
