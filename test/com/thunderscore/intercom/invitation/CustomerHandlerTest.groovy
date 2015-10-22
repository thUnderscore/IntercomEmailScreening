package com.thunderscore.intercom.invitation

class CustomerHandlerTest extends GroovyTestCase {
    void testStart() {
        CustomerHandler handler = new CustomerHandler(null, null, null){

            boolean isCreated = false;
            int written = 0;

            @Override
            protected Customer read() throws InterruptedException {
                if (isCreated){
                    return null;
                }
                isCreated = true;
                def map = [(Customer.ID_FIELD_NAME):"1",
                           (Customer.NAME_FIELD_NAME):"some name",
                           (Customer.LONGITUDE_FIELD_NAME):"1",
                           (Customer.LATITUDE_FIELD_NAME):"2"]
                Customer customer = Customer.fromMap(map);
                return customer;
            }

            @Override
            boolean isInputEmpty() {
                return isCreated;
            }

            @Override
            protected void write(Customer customer) throws InterruptedException {
                written++;
            }
        }

        assertEquals(handler.written, 0);

        Thread thread = handler.start();

        thread.join();

        assertEquals(handler.written, 1);
    }
}
