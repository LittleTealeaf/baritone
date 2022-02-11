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

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.lang.reflect.Field;
import java.util.*;

public class TaskUtils {
    public static final Item[] ITEMS;
    public static final BlockItem[] BLOCK_ITEMS;
    public static final Block[] BLOCKS;
    public static final Map<Item,Block[]> ORE_MAP;

    static {
        ITEMS = collectItems();
        BLOCKS = collectBlocks();
        BLOCK_ITEMS = collectBlockItems();
        ORE_MAP = collectOreMap();
    }

    private static Map<Item,Block[]> collectOreMap() {
        return new OreMap() {{
            put(Items.DIAMOND,Blocks.DIAMOND_ORE,Blocks.DEEPSLATE_DIAMOND_ORE);
            put(Items.EMERALD,Blocks.EMERALD_ORE,Blocks.DEEPSLATE_EMERALD_ORE);
            put(Items.RAW_COPPER,Blocks.COPPER_ORE,Blocks.DEEPSLATE_COPPER_ORE);
            put(Items.RAW_IRON,Blocks.DEEPSLATE_IRON_ORE,Blocks.IRON_ORE);
            put(Items.RAW_GOLD,Blocks.DEEPSLATE_GOLD_ORE,Blocks.GOLD_ORE);
            put(Items.COAL,Blocks.COAL_ORE,Blocks.DEEPSLATE_COAL_ORE);
        }};
    }

    private static Item[] collectItems() {

        Field[] fields = Items.class.getFields();
        Item[] items = new Item[fields.length];
        for(int i = 0; i < fields.length; i++) {
            try {
                items[i] = (Item) fields[i].get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    private static Block[] collectBlocks() {
        Field[] fields = Blocks.class.getFields();
        Block[] blocks = new Block[fields.length];
        for(int i = 0; i < fields.length; i++) {
            try {
                blocks[i] = (Block) fields[i].get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return blocks;
    }

    private static BlockItem[] collectBlockItems() {
        List<BlockItem> blockItems = new ArrayList<>();
        for(Item item : ITEMS) {
            if(item instanceof BlockItem blockItem) {
                blockItems.add(blockItem);
            }
        }
        return blockItems.toArray(new BlockItem[0]);
    }

    static class OreMap extends HashMap<Item,Block[]> {

        @Override
        public Block[] put(Item item, Block... blocks) {
            return super.put(item,blocks);
        }
    }

    public static Iterable<Item> filterItemByString(String filter) {
        Set<Item> items = new HashSet<>();
        for(Item item : ITEMS) {
            if(item.toString().contains(filter)) {
                items.add(item);
            }
        }
        return items;
    }

    public static Item lookupItem(String name) {
        for(Item item : ITEMS) {
            if(item.toString().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
