package com.thunderscore.intercom.mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class IOReaderMapper<T>  extends ReaderMapper<T>{

    final protected Reader reader;
    protected BufferedReader bufferedReader;

    public IOReaderMapper(Reader reader, boolean ignoreReadError) {
        super(ignoreReadError);
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
    protected T internalRead() {
        T res = null;
        try{
            res = internalReadFromInput();
        }
        catch (IOException e) {
            isEOF = true;
            interrupted = true;
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Do read from input
     */
    protected abstract T internalReadFromInput() throws IOException;
}
