package com.thunderscore.intercom.invitation;


/**
 * Class provides geo calculations
 */
public class GeoUtils {


    /**
     * Radius of Earth
     */
    public static final double EARTH_RADIUS = (double) 6371;

    private final double cosOriginLatitude;
    private final double sinOriginLatitude;

    private double originLatitude;
    private double originLongitude;
    private double maxDistance;

    /**
     * Creates GeoUtils instance
     * @param originLatitude latitude of origin point for future evaluation isInMaxDistanceCircle
     * @param originLongitude longitude of origin point for future evaluation isInMaxDistanceCircle
     * @param maxDistance maximal acceptable distance of origin point for future evaluation isInMaxDistanceCircle
     */
    public GeoUtils(double originLatitude, double originLongitude, double maxDistance) {
        this.originLatitude = checkLatitude(originLatitude);
        this.originLongitude = checkLongitude(originLongitude);
        this.maxDistance = checkMaxDistance(maxDistance);

        double radOriginLatitude =  toRad(this.originLatitude);
        this.sinOriginLatitude= Math.sin(radOriginLatitude);
        this.cosOriginLatitude= Math.cos(radOriginLatitude);
    }

    /**
     * Converts degrees to radians
     * @param deg degrees value
     * @return radians value
     */
    public static double toRad(double deg) {
        return deg * Math.PI / 180;
    }

    /**
     * Checks if distance from point to origin less or equal than maxDistance
     * @param latitude latitude of point
     * @param  longitude longitude of point
     * @return true if distance less or equal
     */
    public boolean isInMaxDistanceCircle(double latitude, double longitude) {
        checkLatitude(latitude);
        checkLongitude(longitude);
        double radLatitude =  toRad(latitude);
        double sinLatitude= Math.sin(radLatitude);
        double cosLatitude= Math.cos(radLatitude);
        double rotateLength = Math.acos(sinLatitude * sinOriginLatitude +
                cosLatitude * cosOriginLatitude * Math.cos(toRad(longitude - originLongitude)));
        return EARTH_RADIUS * rotateLength <= maxDistance;
    }

    /**
     * Checks if distance is not negative
     * @param maxDistance value to check
     * @return same value
     */

    private static double checkMaxDistance(double maxDistance) {
        if (maxDistance < 0){
            throw new IllegalArgumentException(String.format("Wrong maxDistance: %f", maxDistance));
        }
        return maxDistance;
    }

    /**
     * Checks if longitude has value from [-180 ; 180]
     * @param longitude value to check
     * @return same value
     */
    public static double checkLongitude(double longitude) {
        if ((longitude < -180) || (longitude > 180)){
            throw new IllegalArgumentException(String.format("Wrong longitude: %f", longitude));
        }
        return longitude;
    }

    /**
     * Checks if latitude has value from [-90 ; 90]
     * @param latitude value to check
     * @return same value
     */

    public static double checkLatitude(double latitude) {
        if ((latitude < -90) || (latitude > 90)){
            throw new IllegalArgumentException(String.format("Wrong latitude: %f", latitude));
        }
        return latitude;
    }
}
