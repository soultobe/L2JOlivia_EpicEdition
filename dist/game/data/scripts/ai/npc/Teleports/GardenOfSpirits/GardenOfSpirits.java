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
package ai.npc.Teleports.GardenOfSpirits;

import com.l2jolivia.gameserver.model.Location;
import com.l2jolivia.gameserver.model.actor.L2Character;
import com.l2jolivia.gameserver.model.zone.L2ZoneType;

import ai.npc.AbstractNpcAI;

/**
 * Garden of Spirits teleport zones.
 * @author Olivia
 */
final class GardenOfSpirits extends AbstractNpcAI
{
	// Zones
	private static final int ZONE_ID_1 = 200213;
	private static final int ZONE_ID_2 = 200214;
	private static final int ZONE_ID_3 = 200215;
	private static final int ZONE_ID_4 = 200216;
	private static final int ZONE_ID_5 = 200217;
	private static final int ZONE_ID_6 = 200218;
	private static final int ZONE_ID_7 = 200219;
	private static final int ZONE_ID_8 = 200220;
	// Teleport Locations
	private static final Location TELEPORT_LOC_1 = new Location(-55613, 84681, -4560);
	private static final Location TELEPORT_LOC_2 = new Location(-53634, 88711, -3944);
	private static final Location TELEPORT_LOC_3 = new Location(-42123, 79254, -4056);
	private static final Location TELEPORT_LOC_4 = new Location(-45887, 77906, -3656);
	private static final Location TELEPORT_LOC_5 = new Location(-34900, 83422, -3512);
	private static final Location TELEPORT_LOC_6 = new Location(-42123, 79254, -4056);
	private static final Location TELEPORT_LOC_7 = new Location(-42123, 91991, -3793);
	private static final Location TELEPORT_LOC_8 = new Location(-39725, 80900, -3931);
	
	private GardenOfSpirits()
	{
		super(GardenOfSpirits.class.getSimpleName(), "ai/npc/Teleports");
		addEnterZoneId(ZONE_ID_1, ZONE_ID_2, ZONE_ID_3, ZONE_ID_4, ZONE_ID_5, ZONE_ID_6, ZONE_ID_7, ZONE_ID_8);
	}
	
	@Override
	public String onEnterZone(L2Character character, L2ZoneType zone)
	{
		if (character.isPlayer())
		{
			switch (zone.getId())
			{
				case ZONE_ID_1:
				{
					character.teleToLocation(TELEPORT_LOC_1);
					break;
				}
				case ZONE_ID_2:
				{
					character.teleToLocation(TELEPORT_LOC_2);
					break;
				}
				case ZONE_ID_3:
				{
					character.teleToLocation(TELEPORT_LOC_3);
					break;
				}
				case ZONE_ID_4:
				{
					character.teleToLocation(TELEPORT_LOC_4);
					break;
				}
				case ZONE_ID_5:
				{
					character.teleToLocation(TELEPORT_LOC_5);
					break;
				}
				case ZONE_ID_6:
				{
					character.teleToLocation(TELEPORT_LOC_6);
					break;
				}
				case ZONE_ID_7:
				{
					character.teleToLocation(TELEPORT_LOC_7);
					break;
				}
				case ZONE_ID_8:
				{
					character.teleToLocation(TELEPORT_LOC_8);
					break;
				}
			}
		}
		return super.onEnterZone(character, zone);
	}
	
	public static void main(String[] args)
	{
		new GardenOfSpirits();
	}
}