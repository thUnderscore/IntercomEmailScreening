package com.thunderscore.intercom.invitation;


import groovy.json.JsonSlurper;
import groovy.json.internal.LazyMap;

import java.io.IOException;
import java.io.Reader;

/**
 * Customer reader which processes each line as Customer in JSON format
 */
public class JSONSingleLineCustomerReader extends CustomerReader {

    private int lineNum;
    private JsonSlurper slurper;

    public JSONSingleLineCustomerReader(Reader reader, CustomerStorage outBuffer) {
        super(reader, outBuffer);
    }

    @Override
    protected void beforeRun() {
        super.beforeRun();
        slurper = new JsonSlurper();
    }

    @Override
    protected Customer readSingleCustomer() throws IOException {
        Customer customer = null;
        try{
            String  line = bufferedReader.readLine();
            isEOF = line == null;
            if (!isEOF && !"".equals(line)){
                customer = Customer.fromMap((LazyMap) slurper.parseText(line));
            }
        } catch (Throwable e){
            System.out.println(String.format("Error in line: %d", lineNum));
            throw e;
        } finally {
            lineNum++;
        }
        return customer;
    }
}
