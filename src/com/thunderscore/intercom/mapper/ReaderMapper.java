package com.thunderscore.intercom.mapper;

/**
 * Mapper -  reader. Suppose to be first mapper in mappers chain
 */
public abstract class ReaderMapper<T> extends Mapper<T, T> {

    private final boolean ignoreReadError;
    /**
     * Set value true if you want stop reading
     */
    protected boolean isEOF = false;


    public ReaderMapper(boolean ignoreReadError) {
        super();
        this.ignoreReadError = ignoreReadError;
    }

    /**
     * Returns if reader should ignore read error and just increase errors count
     * @return if reader should ignore read error and just increase errors count
     */
    public boolean isIgnoreReadError() {
        return ignoreReadError;
    }

    @Override
    public boolean isInputEmpty() {
        return isEOF;
    }


    @Override
    protected T read() throws InterruptedException {
        T obj = null;
        try {
            obj = internalRead();
        } catch (Throwable e) {
            if (isIgnoreReadError()){
                errorOccurred();
            } else {
                isEOF = true;
                interrupted = true;
                throw e;
            }
        }

        return obj;
    }

    /**
     * Returns single object from input or return null
     * @return single object from input or return null
     */
    protected abstract T internalRead();

}
