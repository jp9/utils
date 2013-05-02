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
 * SortedArrayList is extension of array list with added benefits of keeping all the elements sorted.
 * This is NOT EFFICIENT but works good enough for small data sets.
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
    private static <T> void mySort(Comparator<T> comparator, List<T> list) {
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

    @Override
    public boolean add(E element) {
        boolean result = super.add(element);
        mySort(comparator, this);
        return result;
    }

    @Override
    public void add(int index, E element) {
        super.add(index, element);
        mySort(comparator, this);
    }

    @Override
    public boolean addAll(Collection<? extends E> eCollection) {
        boolean result = super.addAll(eCollection);
        mySort(comparator, this);
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> eCollection) {
        boolean result = super.addAll(index, eCollection);
        mySort(comparator, this);
        return result;
    }
}