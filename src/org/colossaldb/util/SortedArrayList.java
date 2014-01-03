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
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 4/29/13
 * Time: 9:15 PM
 *
 *
 */

/**
 * SortedArrayList is extension of array list with added benefits of keeping all the elements sorted. Null elements are not
 * permitted in the list.
 * This is NOT THE MOST EFFICIENT but works good enough for most data sets. This data structure is NOT thread safe.
 * <p/>
 * The sorted array list uses insertion sort as the internal algorithm for single inserts and uses merge sort
 * for bulk inserts. Efficiency wise, construction of this array with all the elements at the start will perform the best.
 * <p/>
 * Ideally restrict to a few insertion of elements.
 * <p/>
 * Other thoughts: We could use a tree representation internally.
 *
 * @param <E> - the parameter type.
 */
public class SortedArrayList<E> extends ArrayList<E> {
    final Comparator<E> comparator;

    public SortedArrayList() {
        this((Comparator<E>) null);
    }

    public SortedArrayList(Comparator<E> comparator) {
        super();
        this.comparator = comparator;
    }

    public SortedArrayList(Comparator<E> comparator, Collection<E> elements) {
        // Don't call the super with elements as argument as we have to sort them.
        super();
        this.comparator = comparator;
        addAll(elements);
    }

    public SortedArrayList(Collection<E> elements) {
        this(null, elements);
    }

    /**
     * Sort the list.
     *
     * @param comparator Comparator to use
     * @param list       list
     * @param <T>        parameter type of the list
     */
    @SuppressWarnings("unchecked")
    private static <T> void mySort(Comparator<T> comparator, List<? extends T> list) {
        if (comparator == null)
            // Hack: This will fail, if the underlying object of type <T> does not implement Comparable interface
            Collections.sort((List<? extends Comparable>) list);
        else
            Collections.sort(list, comparator);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E element) {
        if (super.isEmpty()) {
            return super.add(element);
        }

        // Binary search to find where this element needs to be added.
        int left = 0, right = super.size() - 1;
        int mid = 0, compareVal = 0;
        for (; left <= right; ) {
            mid = (left + right) / 2;
            compareVal = internalCompare(element, super.get(mid));
            if (compareVal == 0) {
                break; // We return true if the collection changed.
            }

            if (compareVal < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // Add the element in correct location.
        if (compareVal < 0)
            super.add(mid, element);
        else
            super.add(mid + 1, element);

        return true;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Insertion at a specified position is not permitted as this operation might break" +
                " the order of elements in the sorted list.");
    }

    /**
     * Sort the input collection and then merge it with the existing list (using simple merge sort algorithm).
     *
     * @param eCollection - the collection to be added.
     * @return - returns "true" if the collection is changed.
     */
    @Override
    public boolean addAll(Collection<? extends E> eCollection) {
        if (eCollection == null || eCollection.isEmpty())
            return false;

        List<? extends E> newElements = new ArrayList<E>(eCollection);
        mySort(comparator, newElements);
        List<E> localList = new ArrayList<E>(super.size() + eCollection.size());
        Iterator<E> first = super.iterator();
        Iterator<? extends E> second = newElements.iterator();
        for (E firstObj = getNext(first), secondObj = getNext(second); firstObj != null || secondObj != null; ) {
            if (firstObj == null) {
                localList.add(secondObj);
                secondObj = getNext(second);
                continue;
            }

            if (secondObj == null) {
                localList.add(firstObj);
                firstObj = getNext(first);
                continue;
            }

            int compareVal = internalCompare(firstObj, secondObj);
            if (compareVal < 0) {
                localList.add(firstObj);
                firstObj = getNext(first);
            } else {
                localList.add(secondObj);
                secondObj = getNext(second);
            }
        }

        super.clear();
        super.addAll(localList);
        return true;
    }

    private E getNext(Iterator<? extends E> iterator) {
        return iterator.hasNext() ? iterator.next() : null;
    }

    @SuppressWarnings("unchecked")
    private int internalCompare(E element1, E element2) {
        int compareVal;
        if (comparator == null) {
            if (element1 instanceof Comparable) {
                compareVal = ((Comparable) element1).compareTo(element2);
            } else {
                throw new RuntimeException("Cannot cast [" + element1 + "] element to Comparable. The comparator must be set in the constructor for SortedArrayList.");
            }
        } else {
            compareVal = comparator.compare(element1, element2);
        }
        return compareVal;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> eCollection) {
        throw new UnsupportedOperationException("Insertion at a specified position is not permitted as this operation might break" +
                " the order of elements in the sorted list.");
    }
}