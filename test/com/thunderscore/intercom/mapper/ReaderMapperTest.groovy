package com.thunderscore.intercom.mapper

import com.thunderscore.intercom.mapper.ReaderMapper

class ReaderMapperTest extends GroovyTestCase {
    void testReadFromInput() {
        ArrayList<String> list = new ArrayList<>();

        String res1 = "test1";
        String res2 = "test2";

        ReaderMapper<String> mapper = new ReaderMapper<String>(false){

            int created = 2;

            @Override
            protected void write(String obj) throws InterruptedException {
                list.add(obj);
            }

            @Override
            protected String internalRead() {
                created--;
                if (created == 1){
                    return res1;
                } else if (created == 0){
                    return res2;
                } else {
                    isEOF = true;
                    return null;
                }
            }


        }

        assertEquals(list.size(), 0);

        Thread thread = mapper.start();

        thread.join();

        assertEquals(list.size(), 2);
        assertEquals(list.get(0), res1);
        assertEquals(list.get(1), res2);
        assertFalse(mapper.isInterrupted());
    }

    void testReadFromInputError() {
        ArrayList<String> list = new ArrayList<>();

        ReaderMapper<String> mapper = new ReaderMapper<String>(false){

            int created = 2;

            @Override
            protected void write(String obj) throws InterruptedException {
                list.add(obj);
            }

            @Override
            protected String internalRead() {
                created--;
                throw new Exception();
            }


        }

        assertEquals(list.size(), 0);

        Thread thread = mapper.start();

        thread.join();

        assertEquals(list.size(), 0);
        assertTrue(mapper.isInterrupted());

    }

    void testReadFromInputIgnoredError() {
        ArrayList<String> list = new ArrayList<>();

        String res2 = "test2";

        ReaderMapper<String> mapper = new ReaderMapper<String>(true){

            int created = 2;

            @Override
            protected void write(String obj) throws InterruptedException {
                list.add(obj);
            }

            @Override
            protected String internalRead() {
                created--;
                if (created == 1){
                    throw new Exception();
                } else if (created == 0){
                    return res2;
                } else {
                    isEOF = true;
                    return null;
                }
            }


        }

        assertEquals(list.size(), 0);

        Thread thread = mapper.start();

        thread.join();

        assertEquals(list.size(), 1);
        assertEquals(list.get(0), res2);
        assertFalse(mapper.isInterrupted());
    }
}
