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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FilterNames<T> {

    private final Collection<String> contains;
    private final Collection<String> doesNotContain;

    public FilterNames() {
        contains = new HashSet<>();
        doesNotContain = new HashSet<>();
    }

    public FilterNames<T> contains(String filter) {
        contains.add(filter);
        return this;
    }

    public FilterNames<T> doesNotContain(String filter) {
        doesNotContain.add(filter);
        return this;
    }

    public Collection<T> filter(Collection<T> items) {
        Set<T> set = new HashSet<>();
        for (T item : items) {
            if (filterItem(item)) {
                set.add(item);
            }
        }
        return set;
    }

    private boolean filterItem(T item) {
        for (String filter : contains) {
            if (!item.toString().contains(filter)) {
                return false;
            }
        }
        for (String filter : doesNotContain) {
            if (item.toString().contains(filter)) {
                return false;
            }
        }
        return true;
    }

    public Collection<T> filter(T... items) {
        Set<T> set = new HashSet<>();
        for (T item : items) {
            if (filterItem(item)) {
                set.add(item);
            }
        }
        return set;
    }
}
