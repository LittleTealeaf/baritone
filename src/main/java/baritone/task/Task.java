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

package baritone.task;

import net.minecraft.world.item.Item;

import java.util.Set;

public abstract class Task {

    protected TaskBehavior behavior;
    protected Set<Task> prerequisites;

    public Task(TaskBehavior behavior) {
        this.behavior = behavior;
    }

    protected final Set<Task> getPrerequisites() {
        return prerequisites == null ? prerequisites = createPrerequisites() : prerequisites;
    }

    protected Set<Task> createPrerequisites() {
        return Set.of();
    }

    public abstract boolean isComplete();

    public void onTick() {
        if(tickPrerequisites()) {
            onTaskTick();
        }
    }

    public void onTaskTick() {

    }

    public boolean tickPrerequisites() {
        int count = 0;
        Task runTask = null;
        for(Task task : getPrerequisites()) {
            if(!task.isComplete() && (runTask == null || task.getSteps() < count)) {
                runTask = task;
                count = task.getSteps();
            }
        }
        if(runTask == null) {
            return true;
        } else {
            runTask.onTick();
            return false;
        }
    }

    public void addUsedItems(Set<Item> items) {
        for(Task task : getPrerequisites()) {
            task.addUsedItems(items);
        }
    }

    public int getSteps() {
        if(isComplete()) {
            return 0;
        } else {
            int count = 1;
            for(Task task : getPrerequisites()) {
                count += task.getSteps();
            }
            return count;
        }
    }
}
