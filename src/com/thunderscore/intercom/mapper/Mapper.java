package com.thunderscore.intercom.mapper;

/**
 * Base class for mappers. Mapper reads all possible obects from input,
 * process them if possible and write to output. By default input for one mapper is output for another
 * thus we can implement chain of mappers. Suppose that mappers work in separate threads ans use BlockingQueue
 * for communication. Methods read write and process could be overridden to modify behaviour of mappers
 */
public class Mapper<I, O> implements Runnable{

    static final long POLL_TIMEOUT = 1000;

    private boolean started;
    private boolean finished;
    protected boolean interrupted;
    private int readCount;
    private int writtenCount;
    private int errorsCount;



    MapperStorage<I> inBuffer;
    MapperStorage<O> outBuffer;
    Mapper previousMapper;
    private long pollTimeout;



    /**
     * Create instance
     */
    public Mapper() {


        this.pollTimeout = POLL_TIMEOUT;
    }

    /**
     * Number of errors ccured
     * @return nmumber of errors ccured
     */
    public int getErrorsCount(){
        return errorsCount;
    }

    /**
     * Number of written object
     * @return number of written object
     */
    public int getWrittenCount(){
        return writtenCount;
    }

    /**
     * Number of read object
     * @return number of read object
     */
    public int getReadCount(){
        return readCount;
    }

    /**
     * Returns true if mapper have finished job
     * @return true if mapper have finished job
     */
    public boolean isFinished(){
        return finished;
    }

    /**
     * Returns true if mapper have finished job
     * @return true if mapper have finished job
     */
    public boolean isStarted(){
        return started;
    }

    /**
     * Returns true if InterruptedException occurred
     * @return true if InterruptedException occurred
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     * Returns true if all input was read. By default it means previousMapper have finished job
     * @return true if all input was read. By default it means previousMapper have finished job
     */
    public boolean isInputEmpty() {
        return previousMapper.isFinished();
    }

    /**
     * Calls from inheritors when error occurred but execution has not been stopped
     */
    protected void errorOccurred(){
        errorsCount++;
    }

    @Override
    public void run() {
        if (isFinished()){
            return;
        }
        beforeRun();
        try{
            I inObj;
            while((inObj = readObjectFromInput()) != null){
                readCount++;
                O outObj = process(inObj);
                if (outObj != null){
                    write(outObj);
                    writtenCount++;
                }
            }
        } catch (Exception e) {
            interrupted = true;
        } finally {
            afterRun();
        }
    }

    /**
     * Creates thread for mapper and start it
     * @return created thread
     */
    public Thread start() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    private I readObjectFromInput() throws InterruptedException {
        I obj;
        do{
            obj = read();
        } while((obj == null) && !isInputEmpty());
        return obj;
    }


    protected void beforeRun() {
        started = true;
    }

    protected void afterRun() {
        finished = true;
    }

    protected O process(I obj) {
        return (O)obj;
    }

    protected I read() throws InterruptedException {
        return inBuffer.poll(pollTimeout);
    }

    protected void write(O obj) throws InterruptedException {
        outBuffer.put(obj);
    }


}
