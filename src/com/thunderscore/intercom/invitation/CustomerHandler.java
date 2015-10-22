package com.thunderscore.intercom.invitation;


import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class for customer handlers. Handler reads all possible customers,
 * process them if possible and write to output. By default input for one handler is output for another
 * thus we can implement chain of handlers. Suppose that handlers work in separate threads ans use BlockingQueue
 * for communication. Methods read write and process could be overridden to modify behaviour of handlers
 */
public class CustomerHandler implements Runnable{

    static final long POLL_TIMEOUT = 1000;
    AtomicBoolean isFinished = new AtomicBoolean();
    private CustomerStorage inBuffer;
    private CustomerStorage outBuffer;
    private CustomerHandler previousHandler;
    private long pollTimeout;
    private boolean interrupted;
    protected int errorsCount;

    /**
     * Create instance
     * @param inBuffer input buffer
     * @param outBuffer output buffer
     * @param previousHandler previous handler, it should use inBuffer for output
     */
    public CustomerHandler(CustomerStorage inBuffer, CustomerStorage outBuffer, CustomerHandler previousHandler) {

        this.inBuffer = inBuffer;
        this.outBuffer = outBuffer;
        this.previousHandler = previousHandler;

        this.pollTimeout = POLL_TIMEOUT;
        errorsCount = 0;
    }

    /**
     * Number of errors ccured
     * @return nmumber of errors ccured
     */
    public int getErrorsCount(){
        return errorsCount;
    }

    @Override
    public void run() {
        if (isFinished.get()){
            return;
        }
        beforeRun();
        try{
            Customer customer;
            while((customer = readCustomerFromInput()) != null){
                if (process(customer)){
                    write(customer);
                }
            }
        } catch (InterruptedException e) {
            interrupted = true;
        } finally {
            afterRun();
        }
    }

    /**
     * Returns true if handler have finished job
     * @return true if handler have finished job
     */
    public boolean iFinished(){
        return isFinished.get();
    }

    /**
     * Returns true if InterruptedException occurred
     * @return true if InterruptedException occurred
     */
    public boolean isInterrupted() {
        return interrupted;
    }


    /**
     * Returns true if all input was read. By default it means previousHandler have finished job
     * @return
     */
    public boolean isInputEmpty() {
        return previousHandler.iFinished();
    }

    /**
     * Creates thread for handler and start it
     * @return created thread
     */
    public Thread start() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    private Customer readCustomerFromInput() throws InterruptedException {
        Customer customer;
        do{
            customer = read();
        } while((customer == null) && !isInputEmpty());
        return customer;
    }


    protected void beforeRun() {

    }

    protected void afterRun() {
        isFinished.set(true);
    }

    protected boolean process(Customer customer) {
        return true;
    }

    protected Customer read() throws InterruptedException {
        return inBuffer.poll(pollTimeout);
    }

    protected void write(Customer customer) throws InterruptedException {
        outBuffer.put(customer);
    }


}
