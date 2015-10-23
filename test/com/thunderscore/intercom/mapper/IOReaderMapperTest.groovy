package com.thunderscore.intercom.mapper


class IOReaderMapperTest extends GroovyTestCase {
    void testInternalReadFromInput() {
        Mapper mapper = new IOReaderMapper<String>(new FileReader(ReaderMapper.class.getResource("input.json").getFile()),
            true){


            @Override
            protected String internalReadFromInput() throws IOException {
                String line = bufferedReader.readLine();
                isEOF = line == null;
                return line;
            }

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
