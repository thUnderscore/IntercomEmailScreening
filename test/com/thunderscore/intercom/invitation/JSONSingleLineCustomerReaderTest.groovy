package com.thunderscore.intercom.invitation

class JSONSingleLineCustomerReaderTest extends GroovyTestCase {

    void testRead() {
        CustomerHandler handler = new JSONSingleLineCustomerReader(
                new FileReader(CustomerReader.class.getResource("customers.json").getFile())
                , null){
            int written = 0;

            @Override
            protected void write(Customer customer) throws InterruptedException {
                written++;
            }
        }

        Thread thread = handler.start();
        thread.join();
        assertEquals(handler.written, 32);
    }
}
