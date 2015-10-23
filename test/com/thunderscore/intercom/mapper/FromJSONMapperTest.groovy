package com.thunderscore.intercom.mapper

class FromJSONMapperTest extends GroovyTestCase {
    void testParseObject() {
        ArrayList<String> list = new ArrayList<>();

        String res1 = "error";
        String res2 = "{ s: \"\"}";

        Mapper<String, String> mapper = new FromJSONMapper<String>(false){

            int created = 2;

            @Override
            protected String read() throws InterruptedException {
                created--;
                if (created == 1){
                    return res1;
                } else if (created == 0){
                    return res2;
                } else {
                    return null;
                }
            }

            @Override
            boolean isInputEmpty() {
                return created < 0;
            }

            @Override
            protected void write(String obj) throws InterruptedException {
                list.add(obj);
            }

            @Override
            protected String parseObject(Map map) {
                return null
            }
        }

        assertEquals(list.size(), 0);

        Thread thread = mapper.start();

        thread.join();

        assertEquals(list.size(), 0);
        assertTrue(mapper.isInterrupted());
    }

    void testParseObjectIgnoreException() {
        ArrayList<String> list = new ArrayList<>();

        String res1 = "error";
        String res2 = "{ \"field\": \"value\"}";

        Mapper<String, String> mapper = new FromJSONMapper<String>(true){

            int created = 2;

            @Override
            protected String read() throws InterruptedException {
                created--;
                if (created == 1){
                    return res1;
                } else if (created == 0){
                    return res2;
                } else {
                    return null;
                }
            }

            @Override
            boolean isInputEmpty() {
                return created < 0;
            }

            @Override
            protected void write(String obj) throws InterruptedException {
                list.add(obj);
            }

            @Override
            protected String parseObject(Map map) {
                return "test"
            }
        }

        assertEquals(list.size(), 0);

        Thread thread = mapper.start();

        thread.join();

        assertFalse(mapper.isInterrupted());
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "test");

    }
}
