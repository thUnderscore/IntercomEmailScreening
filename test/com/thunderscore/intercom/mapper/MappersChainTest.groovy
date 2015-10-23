package com.thunderscore.intercom.mapper

class MappersChainTest extends GroovyTestCase {

    void testExecute() {

        Mapper mapper1 = new Mapper<String, String>(){

            boolean isCreated = false;
            int written = 0;

            @Override
            protected String read() throws InterruptedException {
                if (isCreated){
                    return null;
                }
                isCreated = true;
                return "test";
            }

            @Override
            boolean isInputEmpty() {
                return isCreated;
            }

        }

        Mapper mapper2 = new Mapper<String, String>(){




            @Override
            protected void write(String obj) throws InterruptedException {

            }
        }


        MappersChain chain = new MappersChain();
        chain.add(mapper1);
        chain.add(mapper2);

        assertEquals(mapper2.getWrittenCount(), 0);
        chain.execute();

        assertEquals(mapper2.getWrittenCount(), 1);

        try{
            chain.add(new Mapper());
            assertTrue(false);
        } catch (RuntimeException e){
            assertTrue(true);
        } catch (Throwable ex){
            assertTrue(false);
        }

        try{
            chain.execute();
            assertTrue(false);
        } catch (RuntimeException ex){
            assertTrue(true);
        } catch (Throwable e){
            assertTrue(false);
        }



    }
}
