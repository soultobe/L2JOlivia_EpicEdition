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
package com.l2jolivia.gameserver.network.clientpackets;

import com.l2jolivia.Config;
import com.l2jolivia.gameserver.data.sql.impl.ClanTable;
import com.l2jolivia.gameserver.model.L2Clan;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.network.SystemMessageId;

public final class AllyDismiss extends L2GameClientPacket
{
	private static final String _C__8F_ALLYDISMISS = "[C] 8F AllyDismiss";
	
	private String _clanName;
	
	@Override
	protected void readImpl()
	{
		_clanName = readS();
	}
	
	@Override
	protected void runImpl()
	{
		if (_clanName == null)
		{
			return;
		}
		final L2PcInstance player = getClient().getActiveChar();
		if (player == null)
		{
			return;
		}
		if (player.getClan() == null)
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_A_CLAN_MEMBER_AND_CANNOT_PERFORM_THIS_ACTION);
			return;
		}
		final L2Clan leaderClan = player.getClan();
		if (leaderClan.getAllyId() == 0)
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_CURRENTLY_ALLIED_WITH_ANY_CLANS);
			return;
		}
		if (!player.isClanLeader() || (leaderClan.getId() != leaderClan.getAllyId()))
		{
			player.sendPacket(SystemMessageId.THIS_FEATURE_IS_ONLY_AVAILABLE_TO_ALLIANCE_LEADERS);
			return;
		}
		final L2Clan clan = ClanTable.getInstance().getClanByName(_clanName);
		if (clan == null)
		{
			player.sendPacket(SystemMessageId.THAT_CLAN_DOES_NOT_EXIST);
			return;
		}
		if (clan.getId() == leaderClan.getId())
		{
			player.sendPacket(SystemMessageId.ALLIANCE_LEADERS_CANNOT_WITHDRAW);
			return;
		}
		if (clan.getAllyId() != leaderClan.getAllyId())
		{
			player.sendPacket(SystemMessageId.DIFFERENT_ALLIANCE);
			return;
		}
		
		final long currentTime = System.currentTimeMillis();
		leaderClan.setAllyPenaltyExpiryTime(currentTime + (Config.ALT_ACCEPT_CLAN_DAYS_WHEN_DISMISSED * 86400000L), L2Clan.PENALTY_TYPE_DISMISS_CLAN); // 24*60*60*1000 = 86400000
		leaderClan.updateClanInDB();
		
		clan.setAllyId(0);
		clan.setAllyName(null);
		clan.changeAllyCrest(0, true);
		clan.setAllyPenaltyExpiryTime(currentTime + (Config.ALT_ALLY_JOIN_DAYS_WHEN_DISMISSED * 86400000L), L2Clan.PENALTY_TYPE_CLAN_DISMISSED); // 24*60*60*1000 = 86400000
		clan.updateClanInDB();
		
		player.sendPacket(SystemMessageId.YOU_HAVE_SUCCEEDED_IN_EXPELLING_THE_CLAN);
	}
	
	@Override
	public String getType()
	{
		return _C__8F_ALLYDISMISS;
	}
}