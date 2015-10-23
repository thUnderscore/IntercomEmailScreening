package com.thunderscore.intercom.mapper;

import java.util.ArrayList;

/**
 * Class implements executor of Customerhanfers set
 */
public class MappersChain {

    public static final int DEFAULT_BUFFER_SIZE = 50;

    private boolean interrupted;
    private int errorsCount;
    private ArrayList<Mapper> mappers = new ArrayList<>();
    private boolean isStarted;

    /**
     * Was one of the mappers interrrupted
     * @return true if at list one thread of mapper was interrupted
     */
    public boolean isInterrupted(){
        return interrupted;
    }

    /**
     * Returns total errors count for mappers
     * @return total errors count for mappers
     */
    public int getErrorsCount() {
        return errorsCount;
    }

    /**
     * Starts execution of mappers in parallel mode
     */
    public void execute() {
        checkStarted();
        isStarted = true;
        interrupted = false;
        if (mappers == null){
            return;
        }
        try {
            Thread[] threads = new Thread[mappers.size()];

            for (int i = 0; i < mappers.size(); i++) {
                threads[i] = mappers.get(i).start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            interrupted = true;
        }
        for (Mapper mapper : mappers) {
            interrupted = interrupted || mapper.isInterrupted();
            errorsCount += mapper.getErrorsCount();
        }
    }

    public <T> void add(Mapper mapper){
        internalAdd(mapper, DEFAULT_BUFFER_SIZE);
    }

    public <T> void add(Mapper mapper, int outBufferSize){
        internalAdd(mapper, outBufferSize);
    }

    public <T> void addLast(Mapper mapper){
        internalAdd(mapper, -1);
    }

    private <T> void internalAdd(Mapper mapper, int outBufferSize) {
        checkStarted();
        if (outBufferSize > -1){
            mapper.outBuffer = new MapperStorage<T>(outBufferSize);
        }
        if(mappers.size() > 0){
            Mapper prevMapper = mappers.get(mappers.size() - 1);
            mapper.inBuffer = prevMapper.outBuffer;
            mapper.previousMapper = prevMapper;
        }
        mappers.add(mapper);
    }

    protected void checkStarted() {
        if (isStarted){
            throw new RuntimeException("Already started");
        }
    }
}
