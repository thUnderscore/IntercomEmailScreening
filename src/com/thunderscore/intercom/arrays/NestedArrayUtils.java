package com.thunderscore.intercom.arrays;

/**
 * Utils class for nested arrays
 */
public class NestedArrayUtils {

    //Do not allow to create instance
    private NestedArrayUtils() {}

    /**
     * Converts all BaseNestedArrayItems to single int[].
     * @param array Value from this BaseNestedArrayItems will be saved to result
     * @return int[] which contains all int values from object and nested object. Never returns null
     */
    public static int[] toFlatArray(BaseNestedArrayItem[] array){
        if (array == null){
            return  new int[0];
        }
        int size = getSize(array);
        int[] result = new int[size];
        saveToArray(array, size, result, 0);
        return result;
    }


    /**
     * Returns result {@link #toFlatArray(BaseNestedArrayItem[]) toFlatArray} as string
     * @return result {@link #toFlatArray(BaseNestedArrayItem[]) toFlatArray} as string
     */
    public static String flatAsString(BaseNestedArrayItem[] array){
        return intArrayAsString(toFlatArray(array));
    }

    /**
     * Returns total number of int values in objects in {@array array}. It's number of int values in o
     * bject and all nested objects
     * @return number of int values in objects in {@array array}
     */
    static int getSize(BaseNestedArrayItem[] array) {
        int size = 0;
        if (array != null){
            for (int i = 0; i < array.length; i++) {
                BaseNestedArrayItem arrayItem = array[i];
                if (arrayItem != null){
                    size += arrayItem.size();
                }
            }
        };
        return size;
    }

    /**
     * Returns a string representation of int[]. Int values separated by coma and space
     * @param ints array of ints which need to be represented as string
     * @return string representation of int[].
     */
    static String intArrayAsString(int[] ints) {
        StringBuilder result = new StringBuilder();
        if (ints != null){
            for (int i = 0; i < ints.length; i++) {
                if (i > 0){
                    result.append(", ");
                }
                result.append(ints[i]);
            }
        }
        return result.toString();
    }


    /**
     * Internal method. Do not use it Outside package.
     * Saves int values from objects in {@source source} to int[]
     * @param source array of object which contain int values
     * @param size expected number of int values
     * @param dest int[] where int should be saved
     * @param startIndex index of first int value in {@dest dest}
     */
    static void saveToArray(BaseNestedArrayItem[] source, int size, int[] dest, int startIndex) {
        if (size == 0){
            return;
        }
        int index = startIndex;
        for (int i = 0; i < source.length; i++) {
            BaseNestedArrayItem arrayItem = source[i];
            if (arrayItem != null){
                arrayItem.saveToArray(dest, index);
                index += arrayItem.size();
            }
        }
    }
}
