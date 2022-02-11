/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.task.recipes;

import net.minecraft.world.item.Item;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CraftingRecipe implements ITaskRecipe {

    public static final int INVENTORY = 4, CRAFTING_TABLE = 9;

    private final Item[] recipe;
    private final int expected;

    public CraftingRecipe(int expected, Item slot1, Item slot2, Item slot3, Item slot4) {
        recipe = new Item[]{slot1, slot2, slot3, slot4};
        this.expected = expected;
    }

    public CraftingRecipe(int expected, Item slot1, Item slot2, Item slot3, Item slot4, Item slot5, Item slot6, Item slot7, Item slot8, Item slot9) {
        recipe = new Item[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        this.expected = expected;
    }

    public int getCraftingType() {
        return recipe.length;
    }

    @Override
    public Collection<Item> getItems() {
        Set<Item> items = new HashSet<>();
        for (Item item : recipe) {
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public int getExpectedResult() {
        return expected;
    }

    public Item[] getRecipe() {
        return recipe;
    }
}
