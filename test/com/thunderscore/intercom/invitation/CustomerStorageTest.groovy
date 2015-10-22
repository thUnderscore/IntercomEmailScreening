package com.thunderscore.intercom.invitation


class CustomerStorageTest extends GroovyTestCase {

    void testStorage() {
        CustomerStorage storage = new CustomerStorage(5);
        storage.put(null);
        Customer customer = storage.poll(1000);
        assertNull(customer)
        def map = [(Customer.ID_FIELD_NAME):"1",
                   (Customer.NAME_FIELD_NAME):"some name",
                   (Customer.LONGITUDE_FIELD_NAME):"1",
                   (Customer.LATITUDE_FIELD_NAME):"2"]
        storage.put(Customer.fromMap(map));
        customer = storage.poll(1000);
        assertNotNull(customer)
    }
}
