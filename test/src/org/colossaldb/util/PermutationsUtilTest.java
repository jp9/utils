package org.colossaldb.util;

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
 *
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 5/2/13
 * Time: 3:23 PM
 */

import junit.framework.Assert;
import org.junit.Test;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test class for testing permutations
 */
public class PermutationsUtilTest {
    private final Logger logger = Logger.getLogger(PermutationsUtilTest.class.getName());

    private final List<Integer> integerList = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

    private static <E extends Comparable<E>> Comparator<Collection<E>> makeComparator() {
        return new Comparator<Collection<E>>() {
            @Override
            public int compare(Collection<E> o1, Collection<E> o2) {
                return compareTo(o1, o2);
            }
        };
    }

    private static <E extends Comparable<E>> int compareTo(Collection<E> o1, Collection<E> o2) {
        if (o1.size() < o2.size())
            return -1;
        if (o1.size() > o2.size())
            return 1;
        Iterator<E> it1 = o1.iterator();
        Iterator<E> it2 = o2.iterator();
        for (; it1.hasNext() && it2.hasNext(); ) {
            E first = it1.next();
            E second = it2.next();
            if (first.equals(second))
                continue;
            return first.compareTo(second);
        }
        return 0;
    }


    @Test
    public void testSimplePermutation() {
        int size = integerList.size();
        int expectedValue = size;
        for (int i = 1; i <= 10; i++) {
            long startTime = System.nanoTime();
            Assert.assertEquals(expectedValue, CombinatoricUtil.permutations(integerList, i).size());
            long endTime = System.nanoTime();
            logger.log(Level.FINER, "Time taken for iteration [" + i + "] = " + (endTime - startTime) / 1000000L + " ms. Number of permutations = " + expectedValue);
            expectedValue *= (size - i);
        }
    }

    @Test
    public void testSimpleRedBlackPerm() {
        Collection<Character> input = Arrays.asList('r', 'r', 'b', 'b', 'b');
        // select one from input array
        Collection<Collection<Character>> expected = new TreeSet<Collection<Character>>(PermutationsUtilTest.<Character>makeComparator());
        Collection<Collection<Character>> actual = new TreeSet<Collection<Character>>(PermutationsUtilTest.<Character>makeComparator());

        expected.addAll(makeCollection(new Character[][]{{'r'}, {'b'}}));
        actual.addAll(CombinatoricUtil.permutations(input, 1));
        Assert.assertEquals(expected, actual);

        // Select any two from input array
        expected.clear();
        actual.clear();
        expected.addAll(makeCollection(new Character[][]{{'r', 'r'}, {'r', 'b'}, {'b', 'r'}, {'b', 'b'}}));
        actual.addAll(CombinatoricUtil.permutations(input, 2));
        Assert.assertEquals(expected, actual);

        // select three from input array
        expected.clear();
        actual.clear();
        expected.addAll(makeCollection(new Character[][]{{'r', 'r', 'b'}, {'r', 'b', 'r'}, {'r', 'b', 'b'},
                {'b', 'b', 'r'}, {'b', 'r', 'r'}, {'b', 'r', 'b'}, {'b', 'b', 'b'}}));
        actual.addAll(CombinatoricUtil.permutations(input, 3));
        Assert.assertEquals(expected, actual);
    }

    /**
     * Helper method to make a set of collections.
     *
     * @param elements - Input array
     * @param <E>      - element type
     * @return - set of collections.
     */
    private static <E> Collection<Collection<E>> makeCollection(E[][] elements) {
        Collection<Collection<E>> all = new ArrayList<Collection<E>>();
        for (E[] currArray : elements) {
            Collection<E> list = new ArrayList<E>(Arrays.asList(currArray));
            all.add(list);
        }
        return all;
    }
}
