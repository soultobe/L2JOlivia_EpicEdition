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
package handlers.targethandlers;

import com.l2jolivia.gameserver.handler.ITargetTypeHandler;
import com.l2jolivia.gameserver.model.L2Object;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.actor.instance.L2ArtefactInstance;
import com.l2jolivia.gameserver.model.skills.Skill;
import com.l2jolivia.gameserver.model.skills.targets.L2TargetType;

/**
 * @author UnAfraid
 */
public class Holy implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		if (!(target instanceof L2ArtefactInstance))
		{
			return EMPTY_TARGET_LIST;
		}
		
		return new L2Object[]
		{
			target
		};
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.HOLY;
	}
}
