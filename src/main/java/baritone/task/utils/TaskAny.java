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

import baritone.Baritone;
import baritone.task.Task;
import baritone.task.TaskBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class TaskAny extends Task {

    private Task[] tasks;

    public TaskAny(TaskBehavior behavior, ItemStack... itemStacks) {
        super(behavior);
        tasks = new Task[itemStacks.length];
        for(int i = 0; i < tasks.length; i++) {
            tasks[i] = behavior.getTask(itemStacks[i]);
        }
    }

    public TaskAny(TaskBehavior behavior, Task... tasks) {
        super(behavior);
        this.tasks = tasks;
    }

    @Override
    public boolean isComplete() {
        for(Task task : tasks) {
            if(task.isComplete()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUsedItems(Set<Item> items) {
        for(Task task : tasks) {
            task.addUsedItems(items);
        }
    }
}
