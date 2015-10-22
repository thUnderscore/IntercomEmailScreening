package com.thunderscore.intercom.invitation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Customer storege. Is using for interaction between handlers in chain
 */
public class CustomerStorage {

    ArrayBlockingQueue storage;

    public CustomerStorage(int size){
        storage = new ArrayBlockingQueue(size);
    }

    /**
     * Get customer from storage
     * @param millisecondsTimeout timeout interval
     * @return Cutomer or null if timeout
     * @throws InterruptedException
     */
    public Customer poll(long millisecondsTimeout) throws InterruptedException {
        return (Customer) storage.poll(millisecondsTimeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Put customer in storage
     * @param customer
     * @throws InterruptedException
     */
    public void put(Customer customer) throws InterruptedException {
        if (customer == null){
            return;
        }
        storage.put(customer);
    }



}
