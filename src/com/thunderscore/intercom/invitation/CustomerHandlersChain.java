package com.thunderscore.intercom.invitation;


/**
 * Class implements executor of CustomerHandlers set
 */
public class CustomerHandlersChain {

    private boolean interrupted;
    private int errorsCount;

    /**
     * Was one of the handlers interrrupted
     * @return true if at list one thread of handler was interrupted
     */
    public boolean isInterrupted(){
        return interrupted;
    }

    /**
     * Returns total errors count for handlers
     * @return total errors count for handlers
     */
    public int getErrorsCount() {
        return errorsCount;
    }

    /**
     * Starts execution of handlers in parallel mode
     * @param customerHandlers set of handlers
     */
    protected void execute(CustomerHandler[] customerHandlers) {
        interrupted = false;
        if (customerHandlers == null){
            return;
        }
        try {
            Thread[] threads = new Thread[customerHandlers.length];

            for (int i = 0; i < customerHandlers.length; i++) {
                threads[i] = customerHandlers[i].start();
            }
            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            interrupted = true;
        }
        for (int i = 0; i < customerHandlers.length; i++) {
            CustomerHandler customerHandler = customerHandlers[i];
            interrupted = interrupted || customerHandler.isInterrupted();
            errorsCount += customerHandler.getErrorsCount();
        }
    }
}
