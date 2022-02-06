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

import baritone.api.process.IMineProcess;
import baritone.process.MineProcess;
import baritone.task.Task;
import baritone.task.TaskBehavior;
import baritone.task.TaskUtils;
import baritone.task.utils.TaskAny;
import baritone.task.utils.TaskItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TaskMine extends TaskItem {

    protected final Task prerequisiteTask;
    protected final IMineProcess mineProcess;
    protected final Block[] blocks;
    private int resetTicks;

    public TaskMine(TaskBehavior behavior, ItemStack itemStack, Block... blocks) {
        super(behavior, itemStack);
        this.blocks = blocks;
        mineProcess = behavior.baritone.getMineProcess();
        Set<Item> tools = new HashSet<>();
        int count = 0;
        for(Item item : TaskUtils.ITEMS) {
            for(Block block : blocks) {
                if(item.isCorrectToolForDrops(block.defaultBlockState())) {
                    tools.add(item);
                    count++;
                    break;
                }
            }
        }
        Task[] toolTasks = new Task[count];
        for(Item item : tools) {
            toolTasks[count-=1] = behavior.getTask(new ItemStack(item,1));
        }

        prerequisiteTask = new TaskAny(behavior,toolTasks);
    }

    @Override
    public void onTick() {
        if(!prerequisiteTask.isComplete()) {
            prerequisiteTask.onTick();
            mineProcess.cancel();
        } else {
            if(!mineProcess.isActive() || resetTicks < 0) {
                mineProcess.mine(blocks);
                resetTicks = 600;
            } else {
                resetTicks--;
            }
        }
    }

    @Override
    public int getSteps() {
        if(!prerequisiteTask.isComplete()) {
            return prerequisiteTask.getSteps() + 1;
        } else {
            return 1;
        }
    }
}
