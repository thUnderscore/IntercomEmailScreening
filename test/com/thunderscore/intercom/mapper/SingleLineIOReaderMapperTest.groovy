package com.thunderscore.intercom.mapper

class SingleLineIOReaderMapperTest extends GroovyTestCase {
    void testInternalReadFromInput() {

        Mapper mapper = new SingleLineIOReaderMapper(
                new FileReader(ReaderMapper.class.getResource("input.json").getFile()), true){


            @Override
            protected void write(String obj) throws InterruptedException {
                //Do nothing
            }
        }

        Thread thread = mapper.start();
        thread.join();
        assertEquals(mapper.getWrittenCount(), 32);
        assertEquals(mapper.getReadCount(), 32);
    }
}
