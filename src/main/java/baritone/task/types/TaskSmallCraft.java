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

import baritone.task.Task;
import baritone.task.TaskBehavior;
import baritone.task.utils.TaskItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class TaskSmallCraft extends TaskItem {

    private final Item[][] recipe;
    private final int craftConversion;

    public TaskSmallCraft(TaskBehavior behavior, ItemStack itemStack, int craftConversion, Item[][] recipe) {
        super(behavior, itemStack);
        this.recipe = recipe;
        this.craftConversion = craftConversion;
    }

    @Override
    protected Set<Task> createPrerequisites() {
        Set<Task> tasks = new HashSet<>();
        for(Item[] row : recipe) {
            for(Item item : row) {
                if(item != null) {
                    tasks.add(behavior.getTask(new ItemStack(item,roundUp(itemStack.getCount(),craftConversion))));
                }
            }
        }
        return tasks;
    }

    @Override
    public void onTick() {
        super.onTick();
    }


    private static int roundUp(int a, int b) {
        return (int) Math.ceil((double)a / b);
    }
}
