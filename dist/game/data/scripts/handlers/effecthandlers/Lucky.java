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

import com.l2jolivia.gameserver.model.StatsSet;
import com.l2jolivia.gameserver.model.conditions.Condition;
import com.l2jolivia.gameserver.model.effects.AbstractEffect;
import com.l2jolivia.gameserver.model.skills.BuffInfo;

/**
 * Lucky effect implementation.
 * @author Zoey76
 */
public final class Lucky extends AbstractEffect
{
	public Lucky(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean canStart(BuffInfo info)
	{
		return (info.getEffector() != null) && (info.getEffected() != null) && info.getEffected().isPlayer();
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		return info.getSkill().isPassive();
	}
}