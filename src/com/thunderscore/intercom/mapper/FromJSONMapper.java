package com.thunderscore.intercom.mapper;


import groovy.json.JsonSlurper;
import groovy.json.internal.LazyMap;

import java.util.Map;

public abstract class FromJSONMapper<T> extends Mapper<String, T>{

    private final boolean ignoreException;
    private JsonSlurper slurper;

    @Override
    protected void beforeRun() {
        super.beforeRun();
        slurper = new JsonSlurper();
    }

    public FromJSONMapper(boolean ignoreException) {
        super();
        this.ignoreException = ignoreException;
    }

    @Override
    protected T process(String obj) {
        T res = null;
        if (!"".equals(obj)){
            try{
                res = parseObject((LazyMap) slurper.parseText(obj));
            } catch (Throwable e){
                errorOccurred();
                if (!ignoreException){
                    throw e;
                }
            }
        }
        return res;
    }

    protected abstract T parseObject(Map map);
}
