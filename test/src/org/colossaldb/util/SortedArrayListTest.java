package org.colossaldb.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 12/27/13
 * Time: 11:04 AM
 */
public class SortedArrayListTest {

    @Test
    public void testAddAll() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        initial.addAll(Arrays.asList(-44, 8, 8, 3, 6));
        Assert.assertArrayEquals(new Integer[]{-44, -10, 3, 3, 3, 4, 5, 6, 7, 8, 8},
                initial.toArray(new Integer[initial.size()]));
    }

    @Test
    public void testAddNullToAddAll() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        initial.addAll(null);
        Assert.assertArrayEquals(new Integer[]{-10, 3, 3, 4, 5, 7},
                initial.toArray(new Integer[initial.size()]));
    }

    @Test
    public void testAddNullToAddMethod() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        try {
            initial.add(null);
        } catch (RuntimeException e) {
            // Null elements not permitted.
            return;
        }

        throw new RuntimeException("Adding null to the array did not throw an exception");
    }

    @Test
    public void testEmptyCollectionToAddAll() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        initial.addAll(new ArrayList<Integer>());
        Assert.assertArrayEquals(new Integer[]{-10, 3, 3, 4, 5, 7},
                initial.toArray(new Integer[initial.size()]));
    }

    @Test
    public void testConstructor() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        Assert.assertArrayEquals(new Integer[]{-10, 3, 3, 4, 5, 7},
                initial.toArray(new Integer[initial.size()]));
    }

    @Test
    public void testAddSingleElementV1() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        initial.add(-22);
        Assert.assertArrayEquals(new Integer[]{-22, -10, 3, 3, 4, 5, 7},
                initial.toArray(new Integer[initial.size()]));
    }

    @Test
    public void testAddSingleElementV2() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        initial.add(22);
        Assert.assertArrayEquals(new Integer[]{-10, 3, 3, 4, 5, 7, 22},
                initial.toArray(new Integer[initial.size()]));
    }

    @Test
    public void testAddSingleElementV3() {
        SortedArrayList<Integer> initial = new SortedArrayList<Integer>(Arrays.asList(-10, 5, 4, 3, 7, 3));
        initial.add(6);
        Assert.assertArrayEquals(new Integer[]{-10, 3, 3, 4, 5, 6, 7},
                initial.toArray(new Integer[initial.size()]));
    }

    @Test
    public void testRandom() {
        Random r = new Random(System.currentTimeMillis());
        final int seedSize = 50;
        List<Integer> inputList = new SortedArrayList<Integer>();
        for (int i = 0; i < seedSize; i++)
            inputList.add(r.nextInt());

        String errMsg = inputList.toString();
        // Verify that the elements are in order.
        for (int i = 1; i < seedSize; i++) {
            int prev = inputList.get(i - 1);
            int curr = inputList.get(i);
            Assert.assertTrue("Array is not sorted: " + errMsg, prev <= curr);
        }

    }
}

