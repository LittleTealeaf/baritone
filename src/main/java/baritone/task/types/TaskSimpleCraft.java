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

package baritone.task.types;

import baritone.task.TaskBehavior;
import baritone.task.recipes.ITaskRecipe;
import baritone.task.recipes.SimpleCraftRecipe;
import baritone.task.utils.TaskRecipe;
import net.minecraft.world.item.ItemStack;

public class TaskSimpleCraft extends TaskRecipe {

    public TaskSimpleCraft(TaskBehavior behavior, ItemStack itemStack, SimpleCraftRecipe recipe) {
        super(behavior, itemStack, recipe);
    }
}
