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

package baritone.task.utils;

import baritone.task.Task;
import baritone.task.TaskBehavior;
import baritone.task.recipes.CraftingRecipe;
import baritone.task.recipes.ITaskRecipe;
import baritone.task.types.TaskSimpleCraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class TaskRecipe extends TaskItem  {

    protected ITaskRecipe recipe;

    public TaskRecipe(TaskBehavior behavior, ItemStack itemStack, ITaskRecipe recipe) {
        super(behavior, itemStack);
        this.recipe = recipe;
    }

    @Override
    protected Set<Task> createPrerequisites() {
        Set<Task> tasks = new HashSet<>();
        for(Item item : recipe.getItems()) {
            tasks.add(behavior.getTask(new ItemStack(item, roundUp(itemStack.getCount(), recipe.getExpectedResult()))));
        }
        return tasks;
    }

    private static int roundUp(int a, int b) {
        return a%b == 0 ? a/b : a/b+1;
    }

    public static TaskRecipe getCraftRecipe(TaskBehavior behavior, ItemStack itemStack, CraftingRecipe recipe) {
        if(recipe.getCraftingType() == CraftingRecipe.INVENTORY) {
            return new TaskSimpleCraft(behavior,itemStack,recipe);
        } else {
            return null;
        }
    }
}
