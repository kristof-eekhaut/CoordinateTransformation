package com.eekhaut.kristof.coordinatetransform;


import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToFlatCoordinateTransformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class WGS84ToLambert2008TransformerTest {

    public static final double MAXIMUM_ALLOWED_DEVIATION = 0.02; // 2cm
    public static final double GEOGRAPHICAL_PRECISION = 0.000001;

    private WGS84ToLambert2008Transformer transformer = new WGS84ToLambert2008Transformer();

    private String description;
    private double latitude, longitude;
    private double x, y;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Zwinlaan - Knokke-Heist", 51.355953, 3.321320, 577028.18, 727859.22},
                {"Stuiverstraat - Oostende", 51.218933, 2.902275, 547541.61, 713108.14},
                {"Ollevierlaan - De Panne", 51.098078, 2.597596, 525940.49, 700128.00},
                {"Touquetstraat - Komen-Waasten", 50.708431, 2.892937, 545767.92, 656341.75},
                {"Rue du Marais - Honnelles", 50.334524, 3.690561, 601725.42, 613943.16},
                {"Rue Jéricho - Chimay", 50.043373, 4.313036, 646020.21, 581346.03},
                {"Route de Williers - Florenville", 49.681543, 5.315046, 718309.01, 541544.11},
                {"Rouvroy", 49.536000, 5.487362, 730989.89, 525532.11},
                {"Longeau", 49.578497, 5.833728, 755965.14, 530697.44},
                {"Bastogne", 50.011887, 5.755279, 749386.09, 578783.59},
                {"Krewinkel", 50.329840, 6.383937, 793469.37, 615173.21},
                {"Maasmechelen", 50.961542, 5.681144, 742192.79, 684301.81},
                {"Hamont-Achel", 51.255000, 5.499374, 728923.34, 716733.23},
                {"Essen", 51.448954, 4.463739, 656594.74, 737706.51},
                {"Stekene", 51.210840, 4.023814, 625890.29, 711262.06},
                {"Gent", 51.082423, 3.714606, 604159.41, 697118.72},
                {"Brussel", 50.843432, 4.368755, 649999.85, 670336.43},
                {"Leuven", 50.881308, 4.694456, 672919.92, 674603.00},
                {"Genk", 50.967213, 5.489466, 728719.05, 684710.26},
                {"Charleroi", 50.458473, 4.401731, 652346.88, 627516.48},
                {"Namur", 50.459346, 4.853616, 684432.91, 627729.60}
        });
    }

    public WGS84ToLambert2008TransformerTest(
            String description,
            double latitude,
            double longitude,
            double x,
            double y) {
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.x = x;
        this.y = y;
    }

    @Test
    public void test() {

        GeographicalToFlatCoordinateTransformation.Result result = transformer.transform(latitude, longitude);

        double actualX = result.getX();
        double actualY = result.getY();

        double deviation = Math.sqrt(Math.pow(x - actualX, 2) + Math.pow(y - actualY, 2));

        System.out.println("X: " + actualX + " (expected: " + x + ")");
        System.out.println("Y: " + actualY + " (expected: " + y + ")");
        System.out.println("Deviation: " + deviation);

        assertTrue(deviation < MAXIMUM_ALLOWED_DEVIATION);
    }


    @Test
    public void testInverse() {

        GeographicalToFlatCoordinateTransformation.Result result = transformer.transformInverse(x, y);

        double actualLatitude = result.getLatitude();
        double actualLongitude = result.getLongitude();

        System.out.println("Latitude: " + actualLatitude + " (expected: " + latitude + ")");
        System.out.println("Longitude: " + actualLongitude + " (expected: " + longitude + ")");

        assertEquals(latitude, actualLatitude, GEOGRAPHICAL_PRECISION);
        assertEquals(longitude, actualLongitude, GEOGRAPHICAL_PRECISION);
    }
}
