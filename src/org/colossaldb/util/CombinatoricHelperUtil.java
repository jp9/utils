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
class CombinatoricHelperUtil {
    static final CombinatoricHelperUtil COMBINATIONS = new CombinatoricHelperUtil();

    static final CombinatoricHelperUtil PERMUTATIONS = new CombinatoricHelperUtil() {
        @Override
        protected <E> Collection<E> makeCollection() {
            return new ArrayList<E>();
        }

        @Override
        protected <E> Collection<E> makeCollectionStore() {
            return new ArrayList<E>();
        }

    };

    // Prevent instantiation of utility classes.
    private CombinatoricHelperUtil() {
    }

    protected <E> Collection<E> makeCollection() {
        return new SortedArrayList<E>();
    }

    protected <E> Collection<E> makeCollectionStore() {
        return new HashSet<E>();
    }

    /**
     * Simple implementation for:  choosing a set R items out of a given collection containing N items.
     *
     * @param util         CombinatoricHelperUtil instance to use.
     * @param collectionIn the original collection of objects
     * @param r            the number of elements we want to select out of the original collection
     * @return Sets of all the possible combinations.
     */
    static <T extends Comparable<T>> Collection<Collection<T>> chooseRoutOfN(CombinatoricHelperUtil util, Collection<T> collectionIn, int r) {
        if (r == 0 || r > collectionIn.size())
            return Collections.emptySet();

        // Duplicates objects in the input list will cause problem for collection comparisons
        // For example:
        //      List<Character> list = Arrays.asList('a', 'b', 'c', 'a');
        //      The exact same object can be the first and last object, when using sets we will not be allowed to create
        //      a set with two 'a' objects
        //
        // We can use sets and simplify logic if we don't have duplicates.
        // To work around the duplicates, we are simply going to create a
        // wrapper object for all objects.
        //
        Collection<? extends Comparable<T>> collection = addWrapper(collectionIn);

        Collection<Collection<Comparable<T>>> combinations = util.makeCollectionStore();
        for (Comparable<T> currT : collection) {
            Collection<Comparable<T>> currSet = util.makeCollectionStore();
            currSet.add(currT);
            combinations.add(currSet);
        }
        r--;

        // Iterate to add other elements now.
        while (r > 0) {
            Collection<Collection<Comparable<T>>> newCombinations = util.makeCollectionStore();
            for (Collection<Comparable<T>> currSet : combinations) {
                for (Comparable<T> curr : collection) {
                    // If we have already used this object, then we should not use it again.
                    if (currSet.contains(curr))
                        continue;
                    Collection<Comparable<T>> tempCurrSet = util.makeCollectionStore();
                    tempCurrSet.addAll(currSet);
                    tempCurrSet.add(curr);
                    newCombinations.add(tempCurrSet);
                }
            }

            // Now replace the old combinations with new one.
            combinations = newCombinations;
            r--;
        }

        return removeWrapper(util, combinations);
    }

    /**
     * Create an equivalent collection that contains the original object instead of the wrapper object.
     *
     * @param util         - CombinatoricHelperUtil object (either COMBINATION or PERMUTATION).
     * @param combinations the input collection that contains the wrapper object.
     * @return collection of objects instead of the wrapper object
     */
    private static <T extends Comparable<T>> Collection<Collection<T>> removeWrapper(CombinatoricHelperUtil util, Collection<Collection<Comparable<T>>> combinations) {
        Set<Collection<T>> result = new HashSet<Collection<T>>();
        for (Collection<Comparable<T>> combination : combinations) {
            Collection<T> list = util.makeCollection();
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
