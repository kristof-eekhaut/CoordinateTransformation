package com.eekhaut.kristof.coordinatetransform;

import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToFlatCoordinateTransformation;
import com.eekhaut.kristof.coordinatetransform.parameters.projectionsystem.Lambert2008ProjectionParameters;

import static java.lang.Math.toRadians;

public class WGS84ToLambert2008Transformer {

    private static final int PHY_CALCULATION_PRECISION_DEPTH = 10;

    private Lambert2008ProjectionParameters lambert2008Parameters = new Lambert2008ProjectionParameters();

    private GeographicalToFlatCoordinateTransformation transformation = new GeographicalToFlatCoordinateTransformation(lambert2008Parameters, PHY_CALCULATION_PRECISION_DEPTH);

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
