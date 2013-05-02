package org.colossaldb.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
    [@Licence@]
*/

/**
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 12/14/12
 * Time: 2:26 PM
 *
*/
public class CombinationUtil {
    private static final CombinationUtil INTERNAL_INSTANCE = new CombinationUtil();

    /**
     * Simple implementation for:  choosing a set R items out of a given set containing N items.
     *
     * @param setN - The set of all items
     * @param r - Number of items in the subset.
     * @param <T> - Object type of the elements in the input set.
     * @return - Set of ALL possible subsets, containing EXACTLY "r" items in each subset.
     */
    public static <T> Set<Set<T>> chooseRoutOfN(Collection<T> setN, int r) {
        if(r == 0)
            return Collections.emptySet();

        // Suppressing the warnings for IDEs.
        @SuppressWarnings("unchecked")
        T[] nums = (T[]) setN.toArray();
        Set<Set<T>> all = INTERNAL_INSTANCE.makeCollection();
        for (T curr : nums) {
            if (r > 1) {
                setN.remove(curr);
                for (Set<T> currSubSet : chooseRoutOfN(setN, r - 1)) {
                    currSubSet.add(curr);
                    all.add(currSubSet);
                }
                setN.add(curr);
            } else {
                Set<T> simpleSet = INTERNAL_INSTANCE.makeCollection();
                simpleSet.add(curr);
                all.add(simpleSet);
            }
        }
        return all;
    }

    /**
     * Default collection creation.
     * @param <T> - Parametric type for the set.
     * @return - Create a new Set
     *
     * Override this method - if you want a different default collection type.
     * When Lambda's are introduced, we can simply get rid of this work around (or we can use reflection but not worth it).
     *
     */
    protected <T> Set<T> makeCollection() {
        return new HashSet<T>();
    }


}
