package com.thunderscore.intercom.arrays

class NestedArrayUtilsTest extends GroovyTestCase {

    def intNullArray = null;
    def intNullArrayAsStringExpected = "";

    def intEmptyArray = new int[0];
    def intEmptyArrayAsStringExpected = "";

    def intOneItemArray = [1] as int[];
    def intOneItemArrayAsStringExpected = "1";

    def intMultiItemArray = [1, 2, 3] as int[];
    def intMultiItemArrayAsStringExpected = "1, 2, 3";

    def nullArray = null;
    def emptyArray = [] as BaseNestedArrayItem[];
    def nestedArray = [
            new NestedArray([
                    new NestedLeaf(1),
                    new NestedLeaf(2),
                    new NestedArray([
                            new NestedLeaf(3)
                    ] as BaseNestedArrayItem[])
            ] as BaseNestedArrayItem[]),
            new NestedLeaf(4)
    ] as BaseNestedArrayItem[];

    def nestedWithNullAndEmpty = [
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
    ] as BaseNestedArrayItem[];

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
        assertEquals(NestedArrayUtils.getSize(nullArray), 0);
    }

    void testSizeEmpty() {
        assertEquals(NestedArrayUtils.getSize(emptyArray), 0);
    }

    void testSizeNested() {
        assertEquals(NestedArrayUtils.getSize(nestedArray), 4);
    }

    void testSizeNestedWithNullAndEmpty() {
        assertEquals(NestedArrayUtils.getSize(nestedWithNullAndEmpty), 4);
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

    void testIntNullArrayAsString() {
        testIntArrayAsString(intNullArray, intNullArrayAsStringExpected);
    }

    void testIntEmptyArrayAsString() {
        testIntArrayAsString(intEmptyArray, intEmptyArrayAsStringExpected);
    }

    void testIntOneItemArrayAsString() {
        testIntArrayAsString(intOneItemArray, intOneItemArrayAsStringExpected);
    }

    void testIntMultiItemArrayAsString() {
        testIntArrayAsString(intMultiItemArray, intMultiItemArrayAsStringExpected);
    }

    //Utils
    void testSaveToArray(BaseNestedArrayItem[] nestedArray, int[] source, int start, int[] expected) {
        int[] array = Arrays.copyOf(source, source.length);
        NestedArrayUtils.saveToArray(nestedArray, NestedArrayUtils.getSize(nestedArray), array, start);
        assertArrayEquals( array as Object[], expected as Object[]);
    }

    void testToFlatArray(BaseNestedArrayItem[] nestedArray, int[] expected) {
        int[] flatArr = NestedArrayUtils.toFlatArray(nestedArray);
        assertArrayEquals( flatArr as Object[], expected as Object[]);
    }

    void testFlatAsString(BaseNestedArrayItem[] nestedArray, String expected) {
        assertEquals(NestedArrayUtils.flatAsString(nestedArray), expected);
    }

    void testIntArrayAsString(int[] array, String expected) {
        assertEquals(NestedArrayUtils.intArrayAsString(array), expected);
    }


}
