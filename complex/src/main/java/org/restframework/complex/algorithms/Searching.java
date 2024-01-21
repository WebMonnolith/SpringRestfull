package org.restframework.complex.algorithms;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class Searching {
    public static <T extends Comparable<? super T>> int binSearch(@NotNull List<T> list, T key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            T midValue = list.get(mid);

            int cmp = midValue.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }

        return -1; // key not found
    }

    @Contract(pure = true)
    public static <T extends Comparable<? super T>> int binSearch(T @NotNull [] array, T key) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            T midValue = array[mid];

            int cmp = midValue.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }

        return -1; // key not found
    }
}
