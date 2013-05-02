package org.colossaldb.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
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
    public static <T extends Comparable<T>> Set<Collection<T>> combinations(Collection<T> collectionIn, int r) {
        if (r == 0) {
            return Collections.emptySet();
        }

        return CombinationUtil.chooseRoutOfN(collectionIn, r);
    }
}
