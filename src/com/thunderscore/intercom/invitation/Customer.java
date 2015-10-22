package com.thunderscore.intercom.invitation;

import java.util.Map;

/**
 * Class represents customer
 */
public class Customer {

    public static final String ID_FIELD_NAME = "user_id";
    public static final String NAME_FIELD_NAME = "name";
    public static final String LATITUDE_FIELD_NAME = "latitude";
    public static final String LONGITUDE_FIELD_NAME = "longitude";


    private int id;

    private String name;

    private double latitude;

    private double longitude;

    protected Customer(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private static String checkName(Object name) {
        String result = null;
        if (name != null){
            result = name.toString();
        }
        if (result == null || result.trim() == ""){
            throw new IllegalArgumentException(String.format("Wrong longitude id: %s", result));
        }
        return result;
    }

    private static int checkId(Object id) throws IllegalArgumentException {
        int result = -1;
        if (id != null){
            result = Integer.parseInt(id.toString());

        }
        if (result < 1){
            throw new IllegalArgumentException(String.format("Wrong user id: %s", id));
        }
        return result;
    }

    private static double checkLongitude(Object angle) throws IllegalArgumentException {
        double result = Double.POSITIVE_INFINITY;
        if (angle != null){
            result = GeoUtils.checkLongitude(Double.parseDouble(angle.toString()));
        }
        if (Double.isInfinite(result)){
            throw new IllegalArgumentException(String.format("Wrong user longitude: %s", angle));
        }
        return result;
    }

    private static double checkLatitude(Object angle) throws IllegalArgumentException {
        double result = Double.POSITIVE_INFINITY;
        if (angle != null){
            result = GeoUtils.checkLatitude(Double.parseDouble(angle.toString()));
        }
        if (Double.isInfinite(result)){
            throw new IllegalArgumentException(String.format("Wrong user latitude: %s", angle));
        }
        return result;
    }


    @Override
    public String toString() {
        return name;
    }

    /**
     * Creates customer by map object
     * @param map map which contains customer field values
     * @return Created Customer
     * @throws IllegalArgumentException
     */
    public static Customer fromMap(Map map) throws IllegalArgumentException {
        if (map == null){
            throw new IllegalArgumentException(String.format("map is null"));
        }
        return new Customer(
                checkId(map.get(ID_FIELD_NAME)),
                checkName(map.get(NAME_FIELD_NAME)),
                checkLatitude(map.get(LATITUDE_FIELD_NAME)),
                checkLongitude(map.get(LONGITUDE_FIELD_NAME))
        );
    }
}
