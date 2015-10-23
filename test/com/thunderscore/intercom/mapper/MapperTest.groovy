package com.thunderscore.intercom.mapper

import com.thunderscore.intercom.mapper.Mapper

class MapperTest extends GroovyTestCase {
    void testStart() {
        Mapper<String, String> mapper = new Mapper<String, String>(){

            boolean isCreated = false;

            @Override
            protected String read() throws InterruptedException {
                if (isCreated){
                    return null;
                }
                isCreated = true;
                return "test";
            }

            @Override
            protected void write(String obj) throws InterruptedException {
                //Do nothing
            }

            @Override
            boolean isInputEmpty() {
                return isCreated;
            }

        }

        assertEquals(mapper.getWrittenCount(), 0);
        assertEquals(mapper.getReadCount(), 0);
        assertFalse(mapper.isFinished());
        assertFalse(mapper.isStarted());
        assertEquals(mapper.getErrorsCount(), 0);
        assertFalse(mapper.isInterrupted());

        Thread thread = mapper.start();

        thread.join();

        assertEquals(mapper.getWrittenCount(), 1);
        assertEquals(mapper.getReadCount(), 1);
        assertTrue(mapper.isFinished());
        assertTrue(mapper.isStarted());
        assertEquals(mapper.getErrorsCount(), 0);
        assertFalse(mapper.isInterrupted());



        mapper = new Mapper<String, String>(){

            boolean isCreated = false;

            @Override
            protected String read() throws InterruptedException {
                isCreated = true;

                throw new Exception();
            }

            @Override
            boolean isInputEmpty() {
                return isCreated;
            }

        }

        thread = mapper.start();

        thread.join();

        assertTrue(mapper.isInterrupted());
    }
}
