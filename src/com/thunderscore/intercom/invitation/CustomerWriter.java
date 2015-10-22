package com.thunderscore.intercom.invitation;

/**
 * Customer writer. Suppose to be last handler in handlers chain
 */
public class CustomerWriter extends CustomerHandler {

    public CustomerWriter(CustomerStorage inBuffer, CustomerHandler previousHandler) {
        super(inBuffer, null, previousHandler);
    }


}
