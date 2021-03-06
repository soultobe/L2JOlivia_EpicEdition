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

import com.l2jolivia.gameserver.datatables.SkillData;
import com.l2jolivia.gameserver.model.StatsSet;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.model.conditions.Condition;
import com.l2jolivia.gameserver.model.effects.AbstractEffect;
import com.l2jolivia.gameserver.model.skills.BuffInfo;
import com.l2jolivia.gameserver.model.skills.Skill;
import com.l2jolivia.gameserver.network.SystemMessageId;
import com.l2jolivia.gameserver.network.serverpackets.SystemMessage;

/**
 * Shilen's Breath effect implementation.
 * @author St3eT
 */
public final class ShilensBreath extends AbstractEffect
{
	public ShilensBreath(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		if (info.getEffected().isPlayer() && !info.getEffected().isDead())
		{
			final L2PcInstance player = (L2PcInstance) info.getEffected();
			final int nextLv = info.getSkill().getLevel() - 1;
			
			if (nextLv > 0)
			{
				final Skill skill = SkillData.getInstance().getSkill(info.getSkill().getId(), nextLv);
				skill.applyEffects(player, player);
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOU_VE_BEEN_AFFLICTED_BY_SHILEN_S_BREATH_LEVEL_S1).addInt(nextLv));
			}
			else
			{
				player.sendPacket(SystemMessageId.SHILEN_S_BREATH_HAS_BEEN_PURIFIED);
			}
		}
	}
}