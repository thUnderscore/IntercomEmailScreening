package com.thunderscore.intercom.mapper;

import java.util.Collection;
import java.util.Iterator;


/**
 * Allows read data from collections
 * @param <T> type of objects in collection
 */
public class CollectionReaderMapper<T> extends ReaderMapper<T>{

    private final Collection<T> collection;
    private Iterator<T> iterator;

    public CollectionReaderMapper(Collection<T> collection, boolean ignoreReadError) {
        super(ignoreReadError);
        this.collection = collection;
    }

    @Override
    protected void beforeRun() {
        super.beforeRun();
        iterator = collection.iterator();
    }

    @Override
    protected void afterRun() {
        iterator = null;
        super.afterRun();
    }
    @Override
    protected T internalRead() {
        T res;
        if (!iterator.hasNext()){
            isEOF = true;
            res = null;
        } else {
            res = iterator.next();
        }
        return res;
    }

}
