package com.thunderscore.intercom.invitation


class CustomerTest extends GroovyTestCase {




    void testFromMapAndGetters() {
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(null)});


        def map = [(Customer.ID_FIELD_NAME):"1",
                   (Customer.NAME_FIELD_NAME):"some name",
                   (Customer.LONGITUDE_FIELD_NAME):"1",
                   (Customer.LATITUDE_FIELD_NAME):"2"]
        Customer customer = Customer.fromMap(map);
        assertEquals(customer.getId(), 1);
        assertEquals(customer.getName(), "some name");
        assertEquals(customer.getLongitude(), 1);
        assertEquals(customer.getLatitude(), 2);



        map = [(Customer.ID_FIELD_NAME):"0",
                   (Customer.NAME_FIELD_NAME):"some name",
                   (Customer.LONGITUDE_FIELD_NAME):"1",
                   (Customer.LATITUDE_FIELD_NAME):"2"]
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(map)});

        map = [(Customer.ID_FIELD_NAME):"1",
               (Customer.NAME_FIELD_NAME):null,
               (Customer.LONGITUDE_FIELD_NAME):"1",
               (Customer.LATITUDE_FIELD_NAME):"2"]
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(map)});

        map = [(Customer.ID_FIELD_NAME):"1",
               (Customer.NAME_FIELD_NAME):"",
               (Customer.LONGITUDE_FIELD_NAME):"1",
               (Customer.LATITUDE_FIELD_NAME):"2"]
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(map)});

        map = [(Customer.ID_FIELD_NAME):"1",
               (Customer.NAME_FIELD_NAME):"some name",
               (Customer.LONGITUDE_FIELD_NAME):"181",
               (Customer.LATITUDE_FIELD_NAME):"2"]
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(map)});


        map = [(Customer.ID_FIELD_NAME):"1",
               (Customer.NAME_FIELD_NAME):"some name",
               (Customer.LONGITUDE_FIELD_NAME):"-181",
               (Customer.LATITUDE_FIELD_NAME):"2"]
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(map)});

        map = [(Customer.ID_FIELD_NAME):"1",
               (Customer.NAME_FIELD_NAME):"some name",
               (Customer.LONGITUDE_FIELD_NAME):"1",
               (Customer.LATITUDE_FIELD_NAME):"91"]
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(map)});

        map = [(Customer.ID_FIELD_NAME):"1",
               (Customer.NAME_FIELD_NAME):"some name",
               (Customer.LONGITUDE_FIELD_NAME):"1",
               (Customer.LATITUDE_FIELD_NAME):"-91"]
        TestUtils.assertIllegalArgumentException( {-> Customer.fromMap(map)});



    }
}
