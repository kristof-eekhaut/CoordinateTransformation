package com.eekhaut.kristof.coordinatetransform.algorithm;

import com.eekhaut.kristof.coordinatetransform.parameters.coordinatesystem.GeodeticCoordinateSystemParameters;
import com.eekhaut.kristof.coordinatetransform.parameters.projectionsystem.Lambert72ProjectionParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.eekhaut.kristof.coordinatetransform.util.DegreeUtils.toDegrees;
import static java.lang.Math.toRadians;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GeographicalToGeocentricCoordinateTransformationTest {

    private GeodeticCoordinateSystemParameters lambert72Parameters = new Lambert72ProjectionParameters();
    private GeographicalToGeocentricCoordinateTransformation transformation = new GeographicalToGeocentricCoordinateTransformation(lambert72Parameters);

    private String description;
    private double latitude, longitude, h;
    private double x, y, z;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Example", toDegrees(50, 51, 48, 752), toDegrees(3, 49, 8, 255), 76.549, 4025100.583, 268684.735, 4924132.152},
        });
    }

    public GeographicalToGeocentricCoordinateTransformationTest(
            String description,
            double latitude,
            double longitude,
            double h,
            double x,
            double y,
            double z) {
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.h = h;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Test
    public void test() {

        double phi = toRadians(latitude);
        double lambda = toRadians(longitude);

        GeographicalToGeocentricCoordinateTransformation.Result result = transformation.directTransformation(phi, lambda, h);

        double actualX = result.getX();
        double actualY = result.getY();
        double actualZ = result.getZ();

        System.out.println("X: " + actualX + " (expected: " + x + ")");
        System.out.println("Y: " + actualY + " (expected: " + y + ")");
        System.out.println("Z: " + actualZ + " (expected: " + z + ")");

        assertEquals(x, actualX, 0.001);
        assertEquals(y, actualY, 0.001);
        assertEquals(z, actualZ, 0.001);
    }


    @Test
    public void testInverse() {

        GeographicalToGeocentricCoordinateTransformation.Result result = transformation.inverseTransformation(x, y, z);

        double actualLatitude = result.getLatitude();
        double actualLongitude = result.getLongitude();
        double actualH = result.getH();

        System.out.println("Latitude: " + actualLatitude + " (expected: " + latitude + ")");
        System.out.println("Longitude: " + actualLongitude + " (expected: " + longitude + ")");
        System.out.println("h: " + actualH + " (expected: " + h + ")");

        assertEquals(latitude, actualLatitude, 0.000001);
        assertEquals(longitude, actualLongitude, 0.000001);
        assertEquals(h, actualH, 0.001);
    }
}
