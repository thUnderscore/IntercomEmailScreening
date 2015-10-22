package com.thunderscore.intercom.invitation;


import java.io.*;

/**
 * Customer reader. Suppose to be first handler in handlers chain
 */
public abstract class CustomerReader extends CustomerHandler{

    private Reader reader;
    protected BufferedReader bufferedReader;
    protected boolean isEOF = false;


    public CustomerReader(Reader reader, CustomerStorage outBuffer) {
        super(null, outBuffer, null);
        this.reader = reader;
    }

    @Override
    protected void beforeRun() {
        super.beforeRun();
        bufferedReader = new BufferedReader(reader);
    }

    @Override
    protected void afterRun() {
        try {
            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.afterRun();
    }

    @Override
    protected Customer read() throws InterruptedException {
        Customer customer = null;
        try {
            customer = readSingleCustomer();
        } catch (IOException e) {
            isEOF = true;
            e.printStackTrace();
        } catch (Throwable e) {
            errorsCount++;
        }

        return customer;
    }

    @Override
    public boolean isInputEmpty() {
        return isEOF;
    }

    protected abstract Customer readSingleCustomer() throws IOException;

}
