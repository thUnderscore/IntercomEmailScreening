package com.thunderscore.intercom.mapper

import com.thunderscore.intercom.mapper.Mapper


class WriterMapperTest extends GroovyTestCase {

    void testWrite() {

        ArrayList<String> list = new ArrayList<>();

        String res = "test";

        Mapper<String, String> mapper = new Mapper<String, String>(){

            boolean isCreated = false;

            @Override
            protected String read() throws InterruptedException {
                if (isCreated){
                    return null;
                }
                isCreated = true;
                return res;
            }

            @Override
            boolean isInputEmpty() {
                return isCreated;
            }

            @Override
            protected void write(String obj) throws InterruptedException {
                list.add(obj);
            }

        }

        assertEquals(list.size(), 0);

        Thread thread = mapper.start();

        thread.join();

        assertEquals(list.size(), 1);
        assertEquals(list.get(0), res);

    }
}
