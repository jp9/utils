package org.colossaldb.util;

import java.util.*;

/**
 * SortedArrayList is extension of array list with added benefits of keeping all the elements sorted.
 * This is NOT EFFICIENT, I will fix it when time permits.
 *
 * @param <E> - the parameter type.
 */

public class SortedArrayList<E> extends ArrayList<E> {
        final Comparator<E> comparator;

        SortedArrayList() {
            this((Comparator<E>) null);
        }

        SortedArrayList(Comparator<E> comparator) {
            super();
            this.comparator = comparator;
        }

        SortedArrayList(Comparator<E> comparator, Collection<E> elements) {
            // Don't call the super with elements as argument as we have to sort them.
            super();
            this.comparator = comparator;
            addAll(elements);
        }

        SortedArrayList(Collection<E> elements) {
            this(null, elements);
        }

        @Override
        public boolean equals(Object otherObj) {
            if (null == otherObj)
                return true;
            if (!(otherObj instanceof List))
                return false;

            @SuppressWarnings("unchecked")
            List<E> others = (List<E>) otherObj;
            if (size() != others.size())
                return false;

            // It is NOT acceptable to change the internal order of elements in the other collection.
            // Hence, we are forced to make a copy
            others = new ArrayList<E>(others);
            mySort(comparator, others);

            return super.equals(others);
        }

    private static <T> void mySort(Comparator<T> comparator, List<T> list) {
        if (comparator == null)
            // Hack: If we don't have a comparator, let us see if the underlying object itself implements comparable
            Collections.sort((List<? extends Comparable>)list);
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