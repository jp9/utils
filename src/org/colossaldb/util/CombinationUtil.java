package org.colossaldb.util;

import java.util.*;

/**
 * Copyright (C) 2013  Jayaprakash Pasala
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 4/29/13
 * Time: 9:15 PM
 *
 */

/**
 * Utility class for helping solve mathematical combination problems/puzzles.
 */
public class CombinationUtil {
    // Prevent instantiation of utility classes.
    private CombinationUtil() {
    }

    /**
     * Simple implementation for:  choosing a set R items out of a given collection containing N items.
     *
     * @param collectionIn the original collection of objects
     * @param r            the number of elements we want to select out of the original collection
     * @return Sets of all the possible combinations.
     */
    public static <T extends Comparable<T>> Set<Collection<T>> chooseRoutOfN(Collection<T> collectionIn, int r) {
        // Duplicates in the input list cause a lot of problems.
        // We can use sets and simplify logic if we don't have duplicates.
        // To work around the duplicates, we are simply going to create a
        // wrapper object for all objects.
        Collection<? extends Comparable<T>> collection = addWrapper(collectionIn);

        Set<Set<Comparable<T>>> combinations = new HashSet<Set<Comparable<T>>>();
        for (Comparable<T> currT : collection) {
            Set<Comparable<T>> currSet = new HashSet<Comparable<T>>();
            currSet.add(currT);
            combinations.add(currSet);
        }
        r--;

        // Iterate to add other elements now.
        while (r > 0) {
            Set<Set<Comparable<T>>> newCombinations = new HashSet<Set<Comparable<T>>>();
            for (Set<Comparable<T>> currSet : combinations) {
                for (Comparable<T> curr : collection) {
                    // If we have already used this object, then we should not use it again.
                    if (currSet.contains(curr))
                        continue;
                    Set<Comparable<T>> tempCurrSet = new HashSet<Comparable<T>>(currSet);
                    tempCurrSet.add(curr);
                    newCombinations.add(tempCurrSet);
                }
            }

            // Now replace the old combinations with new one.
            combinations = newCombinations;
            r--;
        }

        return removeWrapper(combinations);
    }

    /**
     * Create an equivalent collection that contains the original object instead of the wrapper object.
     *
     * @param combinations the input collection that contains the wrapper object.
     * @param <T>          object type
     * @return collection of objects instead of the wrapper object
     */
    private static <T extends Comparable<T>> Set<Collection<T>> removeWrapper(Set<Set<Comparable<T>>> combinations) {
        Set<Collection<T>> result = new HashSet<Collection<T>>();
        for (Set<Comparable<T>> combination : combinations) {
            List<T> list = new SortedArrayList<T>();
            for (Comparable<T> t : combination) {
                list.add(((ComparableWrapper<T>) t).holder);
            }
            result.add(list);
        }
        return result;
    }

    /**
     * Add the wrapper around all input collection elements.
     * This will make all objects in the collection unique.
     *
     * @param collection input collection
     * @param <T>        type of the collection
     * @return collection of wrapper objects. The wrapper objects will contain the original object.
     */
    private static <T extends Comparable<T>> Collection<? extends Comparable<T>> addWrapper(Collection<T> collection) {
        Collection<ComparableWrapper<T>> c = new ArrayList<ComparableWrapper<T>>();
        for (T t : collection) {
            c.add(new ComparableWrapper<T>(t));
        }
        return c;
    }

    /**
     * Wrapper object around an input object.
     *
     * @param <E> parameter type
     */
    private static class ComparableWrapper<E extends Comparable<E>> implements Comparable<E> {
        // The original object
        private final E holder;

        ComparableWrapper(E object) {
            this.holder = object;
        }

        @Override
        public int compareTo(E o) {
            return holder.compareTo(o);
        }
    }
}
