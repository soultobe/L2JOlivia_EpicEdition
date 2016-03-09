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
package com.l2jolivia.gameserver.model.stats.functions;

import com.l2jolivia.gameserver.data.xml.impl.EnchantItemHPBonusData;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.conditions.Condition;
import com.l2jolivia.gameserver.model.items.instance.L2ItemInstance;
import com.l2jolivia.gameserver.model.skills.Skill;
import com.l2jolivia.gameserver.model.stats.Stats;

/**
 * @author Yamaneko
 */
public class FuncEnchantHp extends AbstractFunction
{
	public FuncEnchantHp(Stats stat, int order, Object owner, double value, Condition applayCond)
	{
		super(stat, order, owner, value, applayCond);
	}
	
	@Override
	public double calc(L2Character effector, L2Character effected, Skill skill, double initVal)
	{
		if ((getApplayCond() != null) && !getApplayCond().test(effector, effected, skill))
		{
			return initVal;
		}
		
		final L2ItemInstance item = (L2ItemInstance) getFuncOwner();
		if (item.getEnchantLevel() > 0)
		{
			return initVal + EnchantItemHPBonusData.getInstance().getHPBonus(item);
		}
		return initVal;
	}
}
