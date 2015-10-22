package com.thunderscore.intercom.invitation


class TestUtils  extends GroovyTestCase {

    static void assertIllegalArgumentException(def exec){
        try{
            exec();
            assertTrue(false);
        } catch (IllegalArgumentException e){
            assertTrue(true);
        } catch (Throwable e){
            assertTrue(false);
        }
    }

    static void assertFileNotFoundException(def exec){
        try{
            exec();
            assertTrue(false);
        } catch (FileNotFoundException e){
            assertTrue(true);
        } catch (Throwable e){
            assertTrue(false);
        }
    }

    void testFake(){

    }
}
