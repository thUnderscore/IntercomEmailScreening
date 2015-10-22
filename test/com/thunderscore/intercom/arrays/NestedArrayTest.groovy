package com.thunderscore.intercom.arrays


class NestedArrayTest extends GroovyTestCase {

    def nullArray = new NestedArray(null);
    def emptyArray = new NestedArray([] as BaseNestedArrayItem[]);
    def nestedArray = new NestedArray([
            new NestedArray([
                    new NestedLeaf(1),
                    new NestedLeaf(2),
                    new NestedArray([
                            new NestedLeaf(3)
                    ] as BaseNestedArrayItem[])
            ] as BaseNestedArrayItem[]),
            new NestedLeaf(4)
    ] as BaseNestedArrayItem[]);

    def nestedWithNullAndEmpty = new NestedArray([
            null,
            new NestedArray(null),
            new NestedArray([] as BaseNestedArrayItem[]),
            new NestedArray([
                    null,
                    new NestedArray(null),
                    new NestedArray([] as BaseNestedArrayItem[]),
                    new NestedLeaf(1),
                    null,
                    new NestedArray(null),
                    new NestedArray([] as BaseNestedArrayItem[]),
                    new NestedLeaf(2),
                    null,
                    new NestedArray(null),
                    new NestedArray([] as BaseNestedArrayItem[]),
                    new NestedArray([
                            null,
                            new NestedArray([] as BaseNestedArrayItem[]),
                            new NestedLeaf(3),
                            null,
                            new NestedArray([] as BaseNestedArrayItem[]),
                    ] as BaseNestedArrayItem[]),
                    null,
                    new NestedArray([] as BaseNestedArrayItem[]),
                    new NestedArray(null)
            ] as BaseNestedArrayItem[]),
            null,
            new NestedLeaf(4),
            new NestedArray([] as BaseNestedArrayItem[]),
            new NestedArray(null)
    ] as BaseNestedArrayItem[]);

    int[] sourceNullSaveToArray = [100];
    int[] expectedNullSaveToArray = [100];

    int[] sourceEmptySaveToArray = [200];
    int[] expectedEmptySaveToArray = [200];

    int saveNestedStartSaveToArray = 1;
    int[] sourceNestedSaveToArray = [100, 0, 0, 0, 0, 100];
    int[] expectedNestedSaveToArray = [100, 1, 2, 3, 4, 100];

    int saveNestedWithNullAndEmptyStartSaveToArray = 1;
    int[] sourceNestedWithNullAndEmptySaveToArray = [200, 0, 0, 0, 0, 200];
    int[] expectedNestedWithNullAndEmptySaveToArray = [200, 1, 2, 3, 4, 200];

    int[] expectedNullToFlat = [];
    int[] expectedEmptyToFlat = [];
    int[] expectedNestedToFlat = [1, 2, 3, 4];
    int[] expectedNestedWithNullAndEmptyToFlat = [1, 2, 3, 4];

    String expectedNullFlatToString = "";
    String expectedEmptyFlatToString = "";
    String expectedNestedFlatToString = "1, 2, 3, 4";
    String expectedNestedWithNullAndEmptyFlatToString = "1, 2, 3, 4";




    void testSizeNull() {
        assertEquals(nullArray.size(), 0);
    }

    void testSizeEmpty() {
        assertEquals(emptyArray.size(), 0);
    }

    void testSizeNested() {
        assertEquals(nestedArray.size(), 4);
    }

    void testSizeNestedWithNullAndEmpty() {
        assertEquals(nestedWithNullAndEmpty.size(), 4);
    }

    void testSaveToArrayNull() {
        testSaveToArray(nullArray, sourceNullSaveToArray, 0, expectedNullSaveToArray);
    }

    void testSaveToArrayEmpty() {
        testSaveToArray(emptyArray, sourceEmptySaveToArray, 0, expectedEmptySaveToArray);
    }

    void testSaveToArrayNested() {
        testSaveToArray(nestedArray, sourceNestedSaveToArray, saveNestedStartSaveToArray, expectedNestedSaveToArray);
    }

    void testSaveToArrayNestedWithNullAndEmpty() {
        testSaveToArray(nestedWithNullAndEmpty, sourceNestedWithNullAndEmptySaveToArray, saveNestedWithNullAndEmptyStartSaveToArray, expectedNestedWithNullAndEmptySaveToArray
        );
    }

    void testSaveToArrayException() {
        try{
            testSaveToArray(nestedArray, sourceNestedSaveToArray, 1000, expectedNestedSaveToArray);
            assertTrue(false);
        } catch (IndexOutOfBoundsException e){
            assertTrue(true);
        } catch (Throwable e){
            assertTrue(false);
        }

    }

    void testToFlatNull() {
        testToFlatArray(nullArray, expectedNullToFlat);
    }

    void testToFlatEmpty() {
        testToFlatArray(emptyArray, expectedEmptyToFlat);
    }

    void testToFlatNested() {
        testToFlatArray(nestedArray, expectedNestedToFlat);
    }

    void testToFlatNestedWithNullAndEmpty() {
        testToFlatArray(nestedWithNullAndEmpty, expectedNestedWithNullAndEmptyToFlat);
    }

    void testFlatAsStringNull() {
        testFlatAsString(nullArray, expectedNullFlatToString);
    }

    void testFlatAsStringEmpty() {
        testFlatAsString(emptyArray, expectedEmptyFlatToString);
    }

    void testFlatAsStringNested() {
        testFlatAsString(nestedArray, expectedNestedFlatToString);
    }

    void testFlatAsStringNestedWithNullAndEmpty() {
        testFlatAsString(nestedWithNullAndEmpty, expectedNestedWithNullAndEmptyFlatToString);
    }

    //Utils
    void testSaveToArray(NestedArray nestedArray, int[] source, int start, int[] expected) {
        int[] array = Arrays.copyOf(source, source.length);
        nestedArray.saveToArray(array, start);
        assertArrayEquals( array as Object[], expected as Object[]);
    }

    void testToFlatArray(NestedArray nestedArray, int[] expected) {
        int[] flatArr = nestedArray.toFlatArray();
        assertArrayEquals( flatArr as Object[], expected as Object[]);
    }

    void testFlatAsString(NestedArray nestedArray, String expected) {
        assertEquals(nestedArray.flatAsString(), expected);
    }




}
