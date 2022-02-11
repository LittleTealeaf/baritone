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

import baritone.task.recipes.SimpleCraftRecipe;
import baritone.task.types.TaskSimpleCraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.Map;

public class TaskMap {
    public static final Map<Item, TaskFactory> TASK_MAP;

    static {
        TASK_MAP = new HashMap<>() {{
            for(Item log : new FilterNames<Item>().contains("_log").doesNotContain("stripped").filter(TaskUtils.ITEMS)) {
                String type = log.toString().substring(0,log.toString().length() - 4);
                Item planks = TaskUtils.lookupItem(type + "_planks");
                if(planks != null) {
                    put(planks,createSimpleRecipe(planks,4,log,null,null,null));
                }
            }
        }};
    }

    private static TaskFactory createSimpleRecipe(Item item, int conversion, Item topLeft, Item topRight, Item bottomLeft, Item bottomRight) {
        SimpleCraftRecipe recipe = new SimpleCraftRecipe(conversion,topLeft, topRight,bottomLeft, bottomRight);
        return (behavior, stack_size) -> new TaskSimpleCraft(behavior,new ItemStack(item,stack_size),recipe);
    }

    interface TaskFactory {
        Task createTask(TaskBehavior behavior, int stack_size);
    }
}
