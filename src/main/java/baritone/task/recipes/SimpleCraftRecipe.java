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

public class SimpleCraftRecipe implements ITaskRecipe {

    private Item[][] recipe;
    private int expected;
    private Set<Item> items;

    public SimpleCraftRecipe(int expected, Item topLeft, Item topRight, Item bottomLeft, Item bottomRight) {
        recipe = new Item[][] {{topLeft, topRight}, {bottomLeft, bottomRight}};
        this.expected = expected;
        items = new HashSet<>();
        for(Item item : new Item[] {topLeft, topRight, bottomLeft, bottomRight}) {
            if(item != null) {
                items.add(item);
            }
        }
    }

    @Override
    public Collection<Item> getItems() {
        return items;
    }

    @Override
    public int getExpectedResult() {
        return expected;
    }
}
