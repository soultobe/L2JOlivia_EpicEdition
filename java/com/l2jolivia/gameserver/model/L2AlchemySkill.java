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
package com.l2jolivia.gameserver.model;

import java.util.List;

import com.l2jolivia.gameserver.model.holders.ItemHolder;

/**
 * Alchemy transmute skill DTO.
 * @author Olivia
 */
public class L2AlchemySkill
{
	private final List<ItemHolder> _ingredients;
	private final ItemHolder _product;
	
	public L2AlchemySkill(List<ItemHolder> ingredients, ItemHolder product)
	{
		_ingredients = ingredients;
		_product = product;
	}
	
	public List<ItemHolder> getIngridientItems()
	{
		return _ingredients;
	}
	
	public ItemHolder getTransmutedItem()
	{
		return _product;
	}
}