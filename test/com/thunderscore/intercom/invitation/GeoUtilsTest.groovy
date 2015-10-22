package com.thunderscore.intercom.invitation

/**
 * Created by tosh on 22.10.2015.
 */
class GeoUtilsTest extends GroovyTestCase {
    void testToRad() {
        assertEquals(GeoUtils.toRad(0), 0);
        assertEquals(GeoUtils.toRad(90), Math.PI / 2);
        assertEquals(GeoUtils.toRad(180), Math.PI);
        assertEquals(GeoUtils.toRad(270), 3 * Math.PI / 2);
        assertEquals(GeoUtils.toRad(360), 2 * Math.PI);
    }

    void testIsInMaxDistanceCircle() {
        GeoUtils geoUtils = new GeoUtils(0, 0, GeoUtils.EARTH_RADIUS * GeoUtils.toRad(1));
        assertTrue(geoUtils.isInMaxDistanceCircle(0, 1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 + 0.01, 1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 - 0.01, 1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0, 1 + 0.01));
        assertTrue(geoUtils.isInMaxDistanceCircle(0, 1 - 0.01));

        assertTrue(geoUtils.isInMaxDistanceCircle(0, -1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 - 0.01, -1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 + 0.01, -1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0, -1 - 0.01));
        assertTrue(geoUtils.isInMaxDistanceCircle(0, -1 + 0.01));

        assertTrue(geoUtils.isInMaxDistanceCircle(1, 0));
        assertFalse(geoUtils.isInMaxDistanceCircle(1, 0 + 0.01));
        assertFalse(geoUtils.isInMaxDistanceCircle(1, 0 - 0.01));
        assertFalse(geoUtils.isInMaxDistanceCircle(1 + 0.01, 0));
        assertTrue(geoUtils.isInMaxDistanceCircle(1 - 0.01, 0));

        assertTrue(geoUtils.isInMaxDistanceCircle(-1, 0));
        assertFalse(geoUtils.isInMaxDistanceCircle(-1, 0 - 0.01));
        assertFalse(geoUtils.isInMaxDistanceCircle(-1, 0 + 0.01));
        assertFalse(geoUtils.isInMaxDistanceCircle(-1 - 0.01, 0));
        assertTrue(geoUtils.isInMaxDistanceCircle(-1 + 0.01, 0));

        geoUtils = new GeoUtils(0, 180, GeoUtils.EARTH_RADIUS * GeoUtils.toRad(1));
        assertTrue(geoUtils.isInMaxDistanceCircle(0, -179));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 + 0.01, -179));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 - 0.01, -179));
        assertFalse(geoUtils.isInMaxDistanceCircle(0, -179 + 0.01));
        assertTrue(geoUtils.isInMaxDistanceCircle(0, -179 - 0.01));

        assertTrue(geoUtils.isInMaxDistanceCircle(0, 180 - 1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 - 0.01, 180 - 1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0 + 0.01, 180 - 1));
        assertFalse(geoUtils.isInMaxDistanceCircle(0, 180 - 1 - 0.01));
        assertTrue(geoUtils.isInMaxDistanceCircle(0, 180 -1 + 0.01));

        assertTrue(geoUtils.isInMaxDistanceCircle(1, 180 ));
        assertFalse(geoUtils.isInMaxDistanceCircle(1, -179.99));
        assertFalse(geoUtils.isInMaxDistanceCircle(1, 180  - 0.01));
        assertFalse(geoUtils.isInMaxDistanceCircle(1 + 0.01, 180));
        assertTrue(geoUtils.isInMaxDistanceCircle(1 - 0.01, 180));

        assertTrue(geoUtils.isInMaxDistanceCircle(-1, 180));
        assertFalse(geoUtils.isInMaxDistanceCircle(-1, 180 - 0.01));
        assertFalse(geoUtils.isInMaxDistanceCircle(-1, -179.99));
        assertFalse(geoUtils.isInMaxDistanceCircle(-1 - 0.01, 180));
        assertTrue(geoUtils.isInMaxDistanceCircle(-1 + 0.01, 180));


        geoUtils = new GeoUtils(90, 180, GeoUtils.EARTH_RADIUS * GeoUtils.toRad(1));
        assertTrue(geoUtils.isInMaxDistanceCircle(89, 0));
        assertTrue(geoUtils.isInMaxDistanceCircle(89, 90));
        assertTrue(geoUtils.isInMaxDistanceCircle(89, 180));
        assertTrue(geoUtils.isInMaxDistanceCircle(89, -90));
        assertTrue(geoUtils.isInMaxDistanceCircle(89, -180));
        assertFalse(geoUtils.isInMaxDistanceCircle(88.99, 0));
        assertFalse(geoUtils.isInMaxDistanceCircle(88.99, 90));
        assertFalse(geoUtils.isInMaxDistanceCircle(88.99, 180));
        assertFalse(geoUtils.isInMaxDistanceCircle(88.99, -90));
        assertFalse(geoUtils.isInMaxDistanceCircle(88.99, -180));

        geoUtils = new GeoUtils(-90, 180, GeoUtils.EARTH_RADIUS * GeoUtils.toRad(1));
        assertTrue(geoUtils.isInMaxDistanceCircle(-89, 0));
        assertTrue(geoUtils.isInMaxDistanceCircle(-89, 90));
        assertTrue(geoUtils.isInMaxDistanceCircle(-89, 180));
        assertTrue(geoUtils.isInMaxDistanceCircle(-89, -90));
        assertTrue(geoUtils.isInMaxDistanceCircle(-89, -180));
        assertFalse(geoUtils.isInMaxDistanceCircle(-88.99, 0));
        assertFalse(geoUtils.isInMaxDistanceCircle(-88.99, 90));
        assertFalse(geoUtils.isInMaxDistanceCircle(-88.99, 180));
        assertFalse(geoUtils.isInMaxDistanceCircle(-88.99, -90));
        assertFalse(geoUtils.isInMaxDistanceCircle(-88.99, -180));


        TestUtils.assertIllegalArgumentException( {-> geoUtils.isInMaxDistanceCircle( 91, 0)});
        TestUtils.assertIllegalArgumentException( {-> geoUtils.isInMaxDistanceCircle( -91, 0)});
        TestUtils.assertIllegalArgumentException( {-> geoUtils.isInMaxDistanceCircle( 0, 181)});
        TestUtils.assertIllegalArgumentException( {-> geoUtils.isInMaxDistanceCircle( 0, -181)});

    }

    void testCheckLongitude() {
        TestUtils.assertIllegalArgumentException( {-> GeoUtils.checkLongitude(-181)});
        TestUtils.assertIllegalArgumentException( {-> GeoUtils.checkLongitude(181)});
        GeoUtils.checkLongitude(-91);
        GeoUtils.checkLongitude(91);
        GeoUtils.checkLongitude(0);
    }

    void testCheckLatitude() {
        TestUtils.assertIllegalArgumentException( {-> GeoUtils.checkLatitude(-91)});
        TestUtils.assertIllegalArgumentException( {-> GeoUtils.checkLatitude(91)});
        GeoUtils.checkLongitude(0);
    }
}
