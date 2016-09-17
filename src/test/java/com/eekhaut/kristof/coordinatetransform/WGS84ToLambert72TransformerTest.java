package com.eekhaut.kristof.coordinatetransform;

import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToFlatCoordinateTransformation;
import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToGeocentricCoordinateTransformation;
import com.eekhaut.kristof.coordinatetransform.util.DegreeUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class WGS84ToLambert72TransformerTest {


    public static final double MAXIMUM_ALLOWED_DEVIATION = 0.01; // 1cm
    public static final double GEOGRAPHICAL_PRECISION = 0.000001;

    private WGS84ToLambert72Transformer transformer = new WGS84ToLambert72Transformer();

    private String description;
    private double latitude, longitude;
    private double x, y;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Zwinlaan - Knokke-Heist", 51.355953, 3.321320, 77034.80, 227867.96},
                {"Stuiverstraat - Oostende", 51.218933, 2.902275, 47546.24, 213120.28},
                {"Ollevierlaan - De Panne", 51.098078, 2.597596, 25943.40, 200142.60},
                {"Touquetstraat - Komen-Waasten", 50.708431, 2.892937, 45765.78, 156353.64},
                {"Rue du Marais - Honnelles", 50.334524, 3.690561, 101718.65, 113948.07},
                {"Rue Jéricho - Chimay", 50.043373, 4.313036, 146009.91, 81345.37},
                {"Route de Williers - Florenville", 49.681543, 5.315046, 218294.56, 41534.50},
                {"Rouvroy", 49.536000, 5.487362, 230973.63, 25520.87},
                {"Longeau", 49.578497, 5.833728, 255949.71, 30683.26},
                {"Bastogne", 50.011887, 5.755279, 249376.35, 78770.60},
                {"Krewinkel", 50.329840, 6.383937, 293464.33, 115155.25},
                {"Maasmechelen", 50.961542, 5.681144, 242195.56, 184290.54},
                {"Hamont-Achel", 51.255000, 5.499374, 228929.86, 216723.80},
                {"Essen", 51.448954, 4.463739, 156603.18, 237705.87},
                {"Stekene", 51.210840, 4.023814, 125895.33, 211264.85},
                {"Gent", 51.082423, 3.714606, 104162.57, 197123.98},
                {"Brussel", 50.843432, 4.368755, 150000.20, 170336.03},
                {"Leuven", 50.881308, 4.694456, 172920.96, 174599.88},
                {"Genk", 50.967213, 5.489466, 228721.74, 184700.59},
                {"Charleroi", 50.458473, 4.401731, 152342.14, 127515.44},
                {"Namur", 50.459346, 4.853616, 184428.45, 127724.73}
        });
    }

    public WGS84ToLambert72TransformerTest(
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

        GeographicalToGeocentricCoordinateTransformation.Result result = transformer.transformInverse(x, y);

        double actualLatitude = result.getLatitude();
        double actualLongitude = result.getLongitude();

        System.out.println("Latitude: " + actualLatitude + " (expected: " + latitude + ")");
        System.out.println("Longitude: " + actualLongitude + " (expected: " + longitude + ")");

        assertEquals(latitude, actualLatitude, GEOGRAPHICAL_PRECISION);
        assertEquals(longitude, actualLongitude, GEOGRAPHICAL_PRECISION);
    }
}
