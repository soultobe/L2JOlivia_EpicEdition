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
package com.l2jolivia.gameserver.model.actor.request;

import java.util.Objects;

import com.l2jolivia.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author UnAfraid
 */
public class PartyRequest extends AbstractRequest
{
	private final L2PcInstance _targetPlayer;
	
	public PartyRequest(L2PcInstance activeChar, L2PcInstance targetPlayer)
	{
		super(activeChar);
		Objects.requireNonNull(targetPlayer);
		_targetPlayer = targetPlayer;
	}
	
	public L2PcInstance getTargetPlayer()
	{
		return _targetPlayer;
	}
	
	@Override
	public boolean isUsing(int objectId)
	{
		return false;
	}
	
	@Override
	public void onTimeout()
	{
		super.onTimeout();
		getActiveChar().removeRequest(getClass());
		_targetPlayer.removeRequest(getClass());
	}
}
