package com.thunderscore.intercom.mapper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Object storage. Is using for interaction between mappers in chain
 */
public class MapperStorage<T> {

    ArrayBlockingQueue<T> storage;

    public MapperStorage(int size){
        storage = new ArrayBlockingQueue<>(size);
    }

    /**
     * Get object from storage
     * @param millisecondsTimeout timeout interval
     * @return object from queue or null if timeout
     * @throws InterruptedException
     */
    public T poll(long millisecondsTimeout) throws InterruptedException {
        return storage.poll(millisecondsTimeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Put object in storage. Ignores null
     * @param obj oject to be putted
     * @throws InterruptedException
     */
    public void put(T obj) throws InterruptedException {
        if (obj == null){
            return;
        }
        storage.put(obj);
    }



}
