package com.thunderscore.intercom.mapper;

/**
 * Mapper -  writer. Suppose to be last mapper in mappers chain
 */
public abstract class WriterMapper<T> extends Mapper<T, T> {

    public WriterMapper() {
        super();
    }

    @Override
    protected void write(T obj) throws InterruptedException {
        doWrite(obj);
    }

    protected abstract  void doWrite(T obj);

}
