package com.thunderscore.intercom.mapper



class MapperStorageTest extends GroovyTestCase {

    void testStorage() {
        MapperStorage storage = new MapperStorage<String>(5);
        storage.put(null);
        String str = storage.poll(1000);
        assertNull(str)
        storage.put("test");
        str = storage.poll(1000);
        assertEquals(str, "test")
    }
}
