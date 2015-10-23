package com.thunderscore.intercom.mapper

class CollectionReaderMapperTest extends GroovyTestCase {
    void testInternalRead() {
        ArrayList<String> inList = [
            "1", "20", "300"
        ];

        ArrayList<String> outList = new ArrayList<>();


        ReaderMapper<String> mapper = new CollectionReaderMapper<String>(inList, true){


            @Override
            protected void write(String obj) throws InterruptedException {
                outList.add(obj + obj)
            }

        }

        assertEquals(0, outList.size());

        Thread thread = mapper.start();

        thread.join();

        assertEquals(inList.size(), outList.size());

        assertEquals(outList.get(0), "11");
    }
}
