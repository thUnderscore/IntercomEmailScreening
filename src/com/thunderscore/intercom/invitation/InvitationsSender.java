package com.thunderscore.intercom.invitation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 * Instances of this class organize invitation using chain of CustomerHandlers.
 * Chain contains three handlers:
 * reader - reads customers from input
 * filter - applies geo filter to read customers to define which are close enough
 * writer - writes filtered customers to output. "invite" them
 * Handlers work in parallel mode,  using CustomerStorage to pass Customers from handler to handler
 */
public class InvitationsSender extends CustomerHandlersChain{


    private int readBufferSize;
    private int sendBufferSize;
    private final GeoUtils geoUtils;
    private List<Customer> invited = new ArrayList<>();


    public InvitationsSender(double originLatitude, double originLongitude, double maxDistance,
                             int readBufferSize, int sendBufferSize){
        this.readBufferSize = readBufferSize;
        this.sendBufferSize = sendBufferSize;
        this.geoUtils = new GeoUtils(originLatitude, originLongitude, maxDistance);
    }

    /**
     * Returns number of invited customers
     * @return number of invited customers
     */
    public int getInvitedCount(){
        return invited.size();
    }

    /**
     * Reads Cutomers from file and if customer is close enough invites him
     * @param customersFileName file wich contains Customers
     * @throws FileNotFoundException
     */
    public void invite(String customersFileName) throws FileNotFoundException {
        invite(new FileReader(customersFileName));
    }

    private void invite(Reader inputReader) {
        CustomerStorage readBuffer = new CustomerStorage(readBufferSize);
        CustomerStorage sendBuffer = new CustomerStorage(sendBufferSize);
        invited.clear();

        CustomerReader reader = new JSONSingleLineCustomerReader(inputReader, readBuffer);
        CustomerHandler filter = new CustomerHandler(readBuffer, sendBuffer, reader){
            @Override
            protected boolean process(Customer customer) {
                return geoUtils.isInMaxDistanceCircle(customer.getLatitude(), customer.getLongitude());
            }
        };
        CustomerWriter writer = new CustomerWriter(sendBuffer, filter){
            @Override
            protected void write(Customer customer) throws InterruptedException {
                invited.add(customer);
            }
        };

        execute(new CustomerHandler[]{reader, filter, writer});

        invited.sort((o1, o2) -> o1.getId() - o2.getId());

        print();

        }

    private void print() {
        for (int i = 0; i < invited.size(); i++) {
            Customer customer = invited.get(i);
            System.out.println(String.format("id: %5d name: %s", customer.getId(), customer.getName()));
        }
    }

    static void printError(String message){
        String mess = String.format(
                "Can't run app: \"%s\"%sRequired arguments: customerFile originLatitude, originLongitude, maxDistance",
                message, System.lineSeparator());
        System.out.println(mess);
    }
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 4){
            printError("wrong number af arguments");
            return;
        }
        int argIndex = 0;
        File file = new File(args[argIndex++]);
        if (!file.exists()){
            printError("customerFile was not found");
            return;
        }
        double originLatitude;
        double originLongitude;
        double maxDistance;
        try{
            originLatitude = Double.parseDouble(args[argIndex++]);
            originLongitude = Double.parseDouble(args[argIndex++]);
            maxDistance = Double.parseDouble(args[argIndex++]);
        } catch (Exception e) {
            printError(e.getMessage());
            return;
        }

        InvitationsSender sender = new InvitationsSender(originLatitude, originLongitude, maxDistance, 50, 50);
        sender.invite(new FileReader(file));
    }
}


