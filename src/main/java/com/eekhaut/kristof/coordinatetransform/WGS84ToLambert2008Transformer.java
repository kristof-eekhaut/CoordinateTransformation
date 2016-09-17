package com.eekhaut.kristof.coordinatetransform;

import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToFlatCoordinateTransformation;

import static com.eekhaut.kristof.coordinatetransform.util.DegreeUtils.degreesToRadians;
import static java.lang.Math.toRadians;

public class WGS84ToLambert2008Transformer {

    private static final int PHY_CALCULATION_PRECISION_DEPTH = 10;

    private static final double a = 6378137D;
    private static final double f = 1.0 / 298.257222101;

    private static final double phi0 = degreesToRadians(50, 47, 52, 134); // ϕ0
    private static final double phi1 = degreesToRadians(49, 50, 0, 0); // ϕ1
    private static final double phi2 = degreesToRadians(51, 10, 0, 0); // ϕ2

    private static final double lambda0 = degreesToRadians(4, 21, 33, 177); // λ0
    private static final double x0 = 649328D;
    private static final double y0 = 665262D;

    private GeographicalToFlatCoordinateTransformation transformation = new GeographicalToFlatCoordinateTransformation(a, f, phi1, phi2, phi0, lambda0, x0, y0, PHY_CALCULATION_PRECISION_DEPTH);

    /**
     *
     * @param latitude Latitude in degrees of the WGS84 coordinate (e.g.: 50.835119)
     * @param longitude Longitude in degrees of the WGS84 coordinate (e.g.: 4.411966)
     * @return
     */
    public GeographicalToFlatCoordinateTransformation.Result transform(double latitude, double longitude) {
        double phi = toRadians(latitude);
        double lambda = toRadians(longitude);

        return transformation.directTransformation(phi, lambda);
    }

    public GeographicalToFlatCoordinateTransformation.Result transformInverse(double x, double y) {
        return transformation.inverseTransformation(x, y);
    }
}
