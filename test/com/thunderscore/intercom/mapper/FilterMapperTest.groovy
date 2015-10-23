package com.thunderscore.intercom.mapper

import com.thunderscore.intercom.mapper.FilterMapper
import com.thunderscore.intercom.mapper.Mapper


class FilterMapperTest extends GroovyTestCase {

    void testAcceptable() {

            ArrayList<String> list = new ArrayList<>();

            String res1 = "test1";
            String res2 = "test2";

            Mapper<String, String> mapper = new FilterMapper<String>(){

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
                protected boolean acceptable(String obj) {
                    return res1.equals(obj)
                }
            }

            assertEquals(list.size(), 0);

            Thread thread = mapper.start();

            thread.join();

            assertEquals(list.size(), 1);
            assertEquals(list.get(0), res1);

        }
}
