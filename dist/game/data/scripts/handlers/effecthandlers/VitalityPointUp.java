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
import com.l2jolivia.gameserver.network.serverpackets.UserInfo;

/**
 * Vitality Point Up effect implementation.
 * @author Adry_85
 */
public final class VitalityPointUp extends AbstractEffect
{
	private final int _value;
	
	public VitalityPointUp(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_value = params.getInt("value", 0);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if ((info.getEffected() != null) && info.getEffected().isPlayer())
		{
			info.getEffected().getActingPlayer().updateVitalityPoints(_value, false, false);
			info.getEffected().getActingPlayer().sendPacket(new UserInfo(info.getEffected().getActingPlayer()));
		}
	}
}
