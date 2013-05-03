package org.colossaldb.util;

import java.util.Collection;

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
 * Date: 5/2/13
 * Time: 9:56 AM
 */
public class CombinatoricUtil {
    private CombinatoricUtil() {
    }

    /**
     * Simple implementation for:  choosing a set of R-length sets out of a given collection containing N items.
     *
     * @param collectionIn the original collection of objects
     * @param r            the number of elements we want to select out of the original collection
     * @return Sets of all the possible combinations.
     */
    public static <T extends Comparable<T>> Collection<Collection<T>> combinations(Collection<T> collectionIn, int r) {
        return CombinatoricHelperUtil.chooseRoutOfN(CombinatoricHelperUtil.COMBINATIONS, collectionIn, r);
    }

    /**
     * Simple implementation for:  choosing a set of R-length sets out of a given collection containing N items.
     *
     * @param collectionIn the original collection of objects
     * @param r            the number of elements we want to select out of the original collection
     * @return Sets of all the possible combinations.
     */
    public static <T extends Comparable<T>> Collection<Collection<T>> permutations(Collection<T> collectionIn, int r) {
        return CombinatoricHelperUtil.chooseRoutOfN(CombinatoricHelperUtil.PERMUTATIONS, collectionIn, r);
    }

    public static int factorial(int n) {
        if (n < 0)
            throw new UnsupportedOperationException("Cannot calculate factorial for negative numbers. Input: " + n);


        long factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
            if (factorial > Integer.MAX_VALUE)
                throw new RuntimeException("The calculation of factorial exceeds Integer.MAX_VALUE");
        }

        return (int) factorial;
    }
}
