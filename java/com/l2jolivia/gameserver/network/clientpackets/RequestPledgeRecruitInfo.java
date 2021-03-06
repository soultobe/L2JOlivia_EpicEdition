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

import com.l2jolivia.gameserver.data.sql.impl.ClanTable;
import com.l2jolivia.gameserver.model.L2Clan;
import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;
import com.l2jolivia.gameserver.network.serverpackets.ExPledgeRecruitInfo;

/**
 * @author Sdw
 */
public class RequestPledgeRecruitInfo extends L2GameClientPacket
{
	private static final String _C__D0_D3_REQUESTPLEDGERECRUITINFO = "[C] D0;D3 RequestPledgeRecruitInfo";
	
	private int _clanId;
	
	@Override
	protected void readImpl()
	{
		_clanId = readD();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null)
		{
			return;
		}
		
		final L2Clan clan = ClanTable.getInstance().getClan(_clanId);
		
		if (clan == null)
		{
			return;
		}
		
		activeChar.sendPacket(new ExPledgeRecruitInfo(_clanId));
	}
	
	@Override
	public String getType()
	{
		return _C__D0_D3_REQUESTPLEDGERECRUITINFO;
	}
}
