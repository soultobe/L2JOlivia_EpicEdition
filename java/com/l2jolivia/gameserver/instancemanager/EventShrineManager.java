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
package com.l2jolivia.gameserver.instancemanager;

/**
 * @author Olivia
 */
public final class EventShrineManager
{
	private static boolean ENABLE_SHRINES = false;
	
	public boolean areShrinesEnabled()
	{
		return ENABLE_SHRINES;
	}
	
	public void setEnabled(boolean enabled)
	{
		ENABLE_SHRINES = enabled;
	}
	
	public static final EventShrineManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final EventShrineManager _instance = new EventShrineManager();
	}
}
