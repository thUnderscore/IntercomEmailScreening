package com.thunderscore.intercom.invitation;

import com.thunderscore.intercom.mapper.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Instances of this class organize invitation using chain of Mappers.
 * Chain contains four mappers:
 * reader - reads customers from input
 * json transformer - convert text to Customer
 * filter - applies geo filter to read customers to define which are close enough
 * writer - writes filtered customers to output. "invite" them
 * Mappers work in parallel mode,  using MapperStorage to pass objects from mapper to mapper
 */
public class InvitationsSender {


    private final GeoUtils geoUtils;
    private List<Customer> invited = new ArrayList<>();
    private boolean lastFailed;
    private int lastErrorsCount;


    public InvitationsSender(double originLatitude, double originLongitude, double maxDistance){
        this.geoUtils = new GeoUtils(originLatitude, originLongitude, maxDistance);
    }

    /**
     * Returns true if last invitation failed
     * @return true if last invitation failed
     */
    public boolean isLastFailed() {
        return lastFailed;
    }

    /**
     * Number of errors in last invitation
     * @return number of errors in last invitation
     */
    public int getLastErrorsCount() {
        return lastErrorsCount;
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

        lastFailed = false;
        try {
            MappersChain chain = new MappersChain();
            chain.add(new SingleLineIOReaderMapper(inputReader, false));
            chain.add(new FromJSONMapper<Customer>(true) {
                @Override
                protected Customer parseObject(Map map) {
                    return Customer.fromMap(map);
                }
            });
            chain.add(new FilterMapper<Customer>() {
                @Override
                protected boolean acceptable(Customer obj) {
                    return geoUtils.isInMaxDistanceCircle(obj.getLatitude(), obj.getLongitude());
                }
            });
            chain.addLast(new WriterMapper<Customer>() {
                @Override
                protected void doWrite(Customer obj) {
                    invited.add(obj);
                }
            });


            chain.execute();

            if (chain.isInterrupted()){
                throw new RuntimeException("Interrupted");
            }

            lastErrorsCount = chain.getErrorsCount();
        } catch(Exception e){
            lastFailed = true;
            throw e;
        }

        invited.sort((o1, o2) -> o1.getId() - o2.getId());
        print();
    }

    private void print() {
        for (Customer customer : invited) {
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
            maxDistance = Double.parseDouble(args[argIndex]);
        } catch (Exception e) {
            printError(e.getMessage());
            return;
        }

        InvitationsSender sender = new InvitationsSender(originLatitude, originLongitude, maxDistance);
        sender.invite(new FileReader(file));
    }

}


