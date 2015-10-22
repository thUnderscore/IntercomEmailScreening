package com.thunderscore.intercom.invitation

class CustomerHandlersChainTest extends GroovyTestCase {

    void testExecute() {

        CustomerStorage storage = new CustomerStorage(1);
        CustomerHandler handler1 = new CustomerHandler(null, storage, null){

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

        }

        CustomerHandler handler2 = new CustomerHandler(storage, null, handler1){

            boolean isCreated = false;
            int written = 0;


            @Override
            protected void write(Customer customer) throws InterruptedException {
                written++;
            }
        }

        assertEquals(handler2.written, 0);

        CustomerHandlersChain chain = new CustomerHandlersChain();
        chain.execute([handler1, handler2] as CustomerHandler[]);

        assertEquals(handler2.written, 1);
    }
}
