package com.eekhaut.kristof.coordinatetransform;

import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToFlatCoordinateTransformation;
import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToGeocentricCoordinateTransformation;
import com.eekhaut.kristof.coordinatetransform.algorithm.SevenParameterTransformation;
import com.eekhaut.kristof.coordinatetransform.util.DegreeUtils;

import static com.eekhaut.kristof.coordinatetransform.util.DegreeUtils.degreesToRadians;
import static java.lang.Math.toRadians;

public class WGS84ToLambert72Transformer {

    private static final int PHY_CALCULATION_PRECISION_DEPTH = 10;

    private static final long a = 6378388L;
    private static final double f = 1.0 / 297.0;

    private static final double phi0 = toRadians(90); // ϕ0
    private static final double phi1 = degreesToRadians(49, 50, 0.00204); // ϕ1
    private static final double phi2 = degreesToRadians(51, 10, 0.00204); // ϕ2

    private static final double lambda0 = degreesToRadians(4, 22, 2.952); // λ0
    private static final double x0 = 150000.013;
    private static final double y0 = 5400088.438;

    private static final double dx = 106.868628;
    private static final double dy = -52.297783;
    private static final double dz = 103.723893;

    private static final double s = 0.0000012747;

    private static final double rx = degreesToRadians(0, 0, 0.336570);
    private static final double ry = degreesToRadians(0, 0, -0.456955);
    private static final double rz = degreesToRadians(0, 0, 1.842183);

    private GeographicalToGeocentricCoordinateTransformation wgs84GeocentricTransformation = new GeographicalToGeocentricCoordinateTransformation(6378137D, 1.0 / 298.257222101);
    private SevenParameterTransformation sevenParameterTransformation = new SevenParameterTransformation(rx, ry, rz, s, dx, dy, dz);
    private GeographicalToGeocentricCoordinateTransformation lambert72GeocentricTransformation = new GeographicalToGeocentricCoordinateTransformation(a, f);
    private GeographicalToFlatCoordinateTransformation lambert72FlatTransformation = new GeographicalToFlatCoordinateTransformation(a, f, phi1, phi2, phi0, lambda0, x0, y0, PHY_CALCULATION_PRECISION_DEPTH);

    double testHeight = 60D;


    /**
     *
     * @param latitude Latitude in degrees of the WGS84 coordinate (e.g.: 50.835119)
     * @param longitude Longitude in degrees of the WGS84 coordinate (e.g.: 4.411966)
     * @return
     */
    public GeographicalToFlatCoordinateTransformation.Result transform(double latitude, double longitude) {
        double phi = toRadians(latitude);
        double lambda = toRadians(longitude);

        GeographicalToGeocentricCoordinateTransformation.Result wgsGeocentricResult = wgs84GeocentricTransformation.directTransformation(phi, lambda, testHeight);
        SevenParameterTransformation.Result lambert72GeocentricResult = sevenParameterTransformation.transformation(wgsGeocentricResult.getX(), wgsGeocentricResult.getY(), wgsGeocentricResult.getZ());
        GeographicalToGeocentricCoordinateTransformation.Result lambert72GeographicalResult = lambert72GeocentricTransformation.inverseTransformation(lambert72GeocentricResult.getX(), lambert72GeocentricResult.getY(), lambert72GeocentricResult.getZ());

        return lambert72FlatTransformation.directTransformation(lambert72GeographicalResult.getPhi(), lambert72GeographicalResult.getLambda());
    }

    public GeographicalToFlatCoordinateTransformation.Result transformInverse(double x, double y) {

        return null;
    }
}
