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
import com.l2jolivia.gameserver.model.actor.instance.L2TrapInstance;
import com.l2jolivia.gameserver.model.conditions.Condition;
import com.l2jolivia.gameserver.model.effects.AbstractEffect;
import com.l2jolivia.gameserver.model.skills.BuffInfo;

/**
 * Trap Detect effect implementation.
 * @author UnAfraid
 */
public final class TrapDetect extends AbstractEffect
{
	private final int _power;
	
	public TrapDetect(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		if (params.isEmpty())
		{
			throw new IllegalArgumentException(getClass().getSimpleName() + ": effect without power!");
		}
		
		_power = params.getInt("power");
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if (!info.getEffected().isTrap() || info.getEffected().isAlikeDead())
		{
			return;
		}
		
		final L2TrapInstance trap = (L2TrapInstance) info.getEffected();
		if (trap.getLevel() <= _power)
		{
			trap.setDetected(info.getEffector());
		}
	}
}
