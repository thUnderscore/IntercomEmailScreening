package com.thunderscore.intercom.arrays

class NestedLeafTest extends GroovyTestCase {

    void testSize() {
        def obj = new NestedLeaf(45);
        //Size is always 1
        assertEquals(obj.size(), 1);
    }

    void testSaveToArraySimple() {
        def obj = new NestedLeaf(45);
        int[] array = [0];
        int[] expected = [45];
        obj.saveToArray(array, 0);
        assertArrayEquals( array as Object[], expected as Object[]);
    }

    void testSaveToArrayMulti() {
        int val = 45;
        def obj = new NestedLeaf(val);
        int[] array = [1, 2, 3];
        int[] expected = [1, 2, 3];

        obj.saveToArray(array, 0);
        expected[0] = val;
        assertArrayEquals( array as Object[], expected as Object[]);

        obj.saveToArray(array, 1);
        expected[1] = val;
        assertArrayEquals( array as Object[], expected as Object[]);

        obj.saveToArray(array, 2);
        expected[2] = val;
        assertArrayEquals( array as Object[], expected as Object[]);

        //Should get IndexOutOfBoundsException
        try{
            obj.saveToArray(array, 3);
            assertTrue(false);
        } catch (IndexOutOfBoundsException e){
            assertTrue(true);
        } catch (Throwable e){
            assertTrue(false);
        }

    }

    void testToFlatArray() {
        def obj = new NestedLeaf(45);
        assertArrayEquals( obj.toFlatArray() as Object[], [45] as Object[]);
    }

    void testFlatAsString() {
        def obj = new NestedLeaf(45);
        assertEquals(obj.flatAsString(), "45");
    }
}
