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
import baritone.api.BaritoneAPI;
import baritone.api.event.events.TickEvent;
import baritone.api.behavior.ITaskBehavior;
import baritone.behavior.Behavior;
import baritone.task.types.TaskCheats;
import baritone.task.types.TaskMine;
import baritone.task.utils.TaskAny;
import net.fabricmc.loader.impl.util.log.Log;
import net.minecraft.world.item.*;

import java.util.HashSet;
import java.util.Set;

import static baritone.task.TaskMap.TASK_MAP;

public class TaskBehavior extends Behavior implements ITaskBehavior {

    private Task currentTask;

    public TaskBehavior(Baritone baritone) {
        super(baritone);
        TASK_MAP.containsKey(Items.ACACIA_BOAT);

    }

    @Override
    public void setCommand(int count, String... parameters) {
        Task[] tasks = new Task[parameters.length];
        for(int i = 0; i < tasks.length; i++) {
            tasks[i] = getTask(new ItemStack(toItem(parameters[i]),count));
            if(tasks[i] == null) {
                Task[] nTasks = new Task[tasks.length - 1];
                for(int j = 0; j < i; j++) {
                    nTasks[j] = tasks[j];
                }
                tasks = nTasks;
            }
        }
        currentTask = new TaskAny(this,tasks).simplifyTask();
    }

    private Item toItem(String string) {
        String str = string.replace("minecraft:","");
        for(Item item : TaskUtils.ITEMS) {
            if(item.toString().replace("minecraft:","").equals(str)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void onTick(TickEvent event) {
        super.onTick(event);

        Set<Item> usedItems = null;

        if(currentTask != null) {
            usedItems = new HashSet<>();
            currentTask.addUsedItems(usedItems);
            currentTask.onTick();
            if(currentTask.isComplete()) {
                currentTask = null;
                baritone.getMineProcess().cancel();
            }
        }

        Set<Item> useBlocks = new HashSet<>(Set.of(TaskUtils.BLOCK_ITEMS));
        if(usedItems != null) {
            for(Item item : usedItems) {
                useBlocks.remove(item);
            }
        }
        BaritoneAPI.getSettings().acceptableThrowawayItems.value = useBlocks.stream().toList();
    }

    public Task getTask(ItemStack itemstack) {
        if(itemstack == null) {
            return null;
        }

        Set<Task> tasks = new HashSet<>();

        if(TASK_MAP.containsKey(itemstack.getItem())) {
            return TASK_MAP.get(itemstack.getItem()).createTask(this,itemstack.getCount());
        }

        if(TaskUtils.ORE_MAP.containsKey(itemstack.getItem())) {
            tasks.add(new TaskMine(this,itemstack,TaskUtils.ORE_MAP.get(itemstack.getItem())));
        } else if(itemstack.getItem() instanceof BlockItem blockItem) {
            tasks.add(new TaskMine(this,itemstack,blockItem.getBlock()));
        }


        if(tasks.size() == 0) {
            return new TaskCheats(this,itemstack);
        } else {
            return new TaskAny(this,tasks.toArray(new Task[0]));
        }
    }


}
