package com.thunderscore.intercom.arrays;

/**
 * Class represents single int value in nested array of int
 */
public class NestedLeaf extends BaseNestedArrayItem {

    /**
     * Int value of this object
     */
    private int value;

    public NestedLeaf(int value) {
        this.value = value;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    void saveToArray(int[] dest, int startIndex) {
        dest[startIndex] = value;
    }
}
