package com.thunderscore.intercom.arrays;

/**
 * Base class for objects which could be part of nested array
 */
public abstract class BaseNestedArrayItem {


    /**
     * Saves int values from object and all nested object to array. No checks will be done.
     * @param dest array where int values should be saved
     * @param startIndex index of first int value
     */
    abstract void saveToArray(int[] dest, int startIndex);

    /**
     * Returns number of int values in this object. It's number of int values in object and all nested objects
     * @return number of int values in this object
     */
    public abstract int size();

    /**
     * Converts object to int[].
     * @return int[] which contains all int values from object and nested object. Never returns null
     */
    public int[] toFlatArray(){
        int[] result = new int[size()];
        saveToArray(result, 0);
        return result;
    }

    /**
     * Returns result {@link #toFlatArray() toFlatArray} as string
     * @return result {@link #toFlatArray() toFlatArray} as string
     */
    public String flatAsString(){
        return  NestedArrayUtils.intArrayAsString(toFlatArray());
    }
}
