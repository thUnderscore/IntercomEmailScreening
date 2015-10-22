package com.thunderscore.intercom.arrays;

import java.util.Arrays;

/**
 * Class represents nested array of int
 */
public class NestedArray extends BaseNestedArrayItem{

    private static final int DEF_SIZE = -1;
    /**
     * Cached total size of all nested objects
     */
    private int size;

    /**
     * Array of nested objects
     */
    private BaseNestedArrayItem[] array;

    public NestedArray(BaseNestedArrayItem[] array) {
        this.size = DEF_SIZE;
        this.array = array == null ? null : Arrays.copyOf(array, array.length);
    }

    @Override
    public int size() {
        if (size == DEF_SIZE){
            size = NestedArrayUtils.getSize(array);
        }
        return size;
    }

    @Override
    void saveToArray(int[] dest, int startIndex) {
        NestedArrayUtils.saveToArray(array, size(), dest, startIndex);
    }
}
