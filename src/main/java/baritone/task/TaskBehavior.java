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

import baritone.Baritone;
import baritone.api.event.events.TickEvent;
import baritone.api.behavior.ITaskBehavior;
import baritone.behavior.Behavior;
import baritone.task.types.TaskCheats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TaskBehavior extends Behavior implements ITaskBehavior {

    private Task currentTask;

    public TaskBehavior(Baritone baritone) {
        super(baritone);
    }

    @Override
    public void setCommand(int count, String... parameters) {
        currentTask = createTask(new ItemStack(Items.DIAMOND_PICKAXE,1));
    }

    public void setCurrentTask(Task task) {

    }

    @Override
    public void onTick(TickEvent event) {
        super.onTick(event);

        if(currentTask != null) {
            if (currentTask.isComplete()) {
                currentTask = null;
            } else {
                currentTask.onTick();
            }
        }
    }

    private Task createTask(ItemStack itemstack) {


        return new TaskCheats(this, itemstack);
    }
}
