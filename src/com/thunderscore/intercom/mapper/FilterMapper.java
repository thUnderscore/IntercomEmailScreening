package com.thunderscore.intercom.mapper;

/**
 * Mapper which just decides if object acceptable or not
 */
public abstract class FilterMapper<T> extends Mapper<T, T>{

    public FilterMapper() {
        super();
    }

    @Override
    protected T process(T obj) {
        return acceptable(obj) ? obj : null;
    }

    abstract protected boolean acceptable(T obj);
}
