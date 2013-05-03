package org.colossaldb.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Copyright (C) 2013  Jayaprakash Pasala
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 4/29/13
 * Time: 9:15 PM
 */

/**
 * Test class for testing combinations
 */
public class CombinationsUtilTest {
    @Test
    public void testChooseRoutOfNForSize() {
        List<Integer> inputList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int size = inputList.size();
        int factorialOf9 = CombinatoricUtil.factorial(size);
        for (int i = 1; i <= size; i++) {
            int expectedValue = factorialOf9 / (CombinatoricUtil.factorial(size - i) * CombinatoricUtil.factorial(i));
            Assert.assertEquals(expectedValue, CombinatoricUtil.combinations(inputList, i).size());
        }
    }

    @Test
    public void testChooseRedAndBlack() {
        Collection<Character> input = Arrays.asList('r', 'r', 'b', 'b', 'b');
        // select one from input array
        Set<Collection<Character>> expected = makeSet(new Character[][]{{'r'}, {'b'}});
        Assert.assertEquals(expected, CombinatoricUtil.combinations(input, 1));

        // Select any two from input array
        Assert.assertEquals(makeSet(new Character[][]{{'r', 'r'}, {'r', 'b'}, {'b', 'b'}}),
                CombinatoricUtil.combinations(input, 2));

        // select three from input array
        Assert.assertEquals(makeSet(new Character[][]{{'r', 'r', 'b'}, {'r', 'b', 'b'}, {'b', 'b', 'b'}}),
                CombinatoricUtil.combinations(input, 3));
    }

    /**
     * Helper method to make a set of collections.
     *
     * @param elements - Input array
     * @param <E>      - element type
     * @return - set of collections.
     */
    private static <E> Set<Collection<E>> makeSet(E[][] elements) {
        Set<Collection<E>> all = new HashSet<Collection<E>>();
        for (E[] currArray : elements) {
            Collection<E> list = new SortedArrayList<E>(Arrays.asList(currArray));
            all.add(list);
        }
        return all;
    }

    @Test
    public void testChooseRoutOfN() {
        Collection<Integer> input = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        for (int i = 1; i <= 10; i++) {
            Assert.assertEquals(makeExpectedOutput(input, i), CombinatoricUtil.combinations(input, i));
        }
    }

    @Test
    public void testSpeed() {
        List<Set<Collection<Integer>>> testResults = new ArrayList<Set<Collection<Integer>>>();
        List<Collection<Collection<Integer>>> prodRes = new ArrayList<Collection<Collection<Integer>>>();

        Collection<Integer> input = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        long startTime = System.nanoTime();
        for (int i = 1; i <= 10; i++) {
            testResults.add(makeExpectedOutput(input, i));
        }
        long endTime = System.nanoTime();
        System.out.println("Time taken using test method: [" + (endTime - startTime) / 1000000L + "ms]");
        startTime = System.nanoTime();
        for (int i = 1; i <= 10; i++) {
            prodRes.add(CombinatoricUtil.combinations(input, i));
        }
        endTime = System.nanoTime();
        System.out.println("Time taken using production method: [" + (endTime - startTime) / 1000000L + "ms]");

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(testResults.get(i), prodRes.get(i));
        }
    }

    /**
     * Simple implementation for:  choosing a set R items out of a given collection containing N items.
     * <p/>
     * WARNINGS:
     * - Will not work when we have duplicates in the input set.
     * - Very slow.
     *
     * @param collectionN - The input collection containing "n" elements
     * @param r           - Number of items in the subset.
     * @param <T>         - Object type of the elements in the input set.
     * @return - Set of ALL possible subsets, containing EXACTLY "r" items in each subset.
     */
    public static <T> Set<Collection<T>> makeExpectedOutput(Collection<T> collectionN, int r) {
        Collection<T> setN = new HashSet<T>();
        setN.addAll(collectionN);

        if (setN.size() != collectionN.size())
            throw new UnsupportedOperationException("No duplicates are permitted in the input collection.");

        if (r == 0)
            return Collections.emptySet();

        // Suppressing the warnings for IDEs.
        @SuppressWarnings("unchecked")
        T[] nums = (T[]) setN.toArray();
        Set<Collection<T>> all = new HashSet<Collection<T>>();
        for (T curr : nums) {
            if (r > 1) {
                setN.remove(curr);
                for (Collection<T> currSubSet : makeExpectedOutput(setN, r - 1)) {
                    currSubSet.add(curr);
                    all.add(currSubSet);
                }
                setN.add(curr);
            } else {
                // Note: This can be a set but will break the comparison to the
                // output.
                Collection<T> simpleList = new SortedArrayList<T>();
                simpleList.add(curr);
                all.add(simpleList);
            }
        }
        return all;
    }

}
