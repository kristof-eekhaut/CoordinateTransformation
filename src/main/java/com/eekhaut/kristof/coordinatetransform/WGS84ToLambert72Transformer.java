package com.eekhaut.kristof.coordinatetransform;

import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToFlatCoordinateTransformation;
import com.eekhaut.kristof.coordinatetransform.algorithm.GeographicalToGeocentricCoordinateTransformation;
import com.eekhaut.kristof.coordinatetransform.algorithm.SevenParameterTransformation;
import com.eekhaut.kristof.coordinatetransform.parameters.coordinatesystem.ETRS89Parameters;
import com.eekhaut.kristof.coordinatetransform.parameters.projectionsystem.Lambert72ProjectionParameters;
import com.eekhaut.kristof.coordinatetransform.parameters.sevenparam.BD72ToETRS89TransformationParameters;

import static java.lang.Math.toRadians;

public class WGS84ToLambert72Transformer {

    private static final int PHY_CALCULATION_PRECISION_DEPTH = 10;
    private static final double HEIGHT = 60D;  // Just providing an estimated avg height

    private Lambert72ProjectionParameters lambert72Parameters = new Lambert72ProjectionParameters();
    private ETRS89Parameters etrs89Parameters = new ETRS89Parameters();
    private BD72ToETRS89TransformationParameters sevenParameters = new BD72ToETRS89TransformationParameters();

    private GeographicalToGeocentricCoordinateTransformation wgs84GeocentricTransformation = new GeographicalToGeocentricCoordinateTransformation(etrs89Parameters);
    private SevenParameterTransformation sevenParameterTransformation = new SevenParameterTransformation(sevenParameters);
    private GeographicalToGeocentricCoordinateTransformation lambert72GeocentricTransformation = new GeographicalToGeocentricCoordinateTransformation(lambert72Parameters);
    private GeographicalToFlatCoordinateTransformation lambert72FlatTransformation = new GeographicalToFlatCoordinateTransformation(lambert72Parameters, PHY_CALCULATION_PRECISION_DEPTH);

    /**
     *
     * @param latitude Latitude in degrees of the WGS84 coordinate (e.g.: 50.835119)
     * @param longitude Longitude in degrees of the WGS84 coordinate (e.g.: 4.411966)
     * @return
     */
    public GeographicalToFlatCoordinateTransformation.Result transform(double latitude, double longitude) {
        double phi = toRadians(latitude);
        double lambda = toRadians(longitude);

        GeographicalToGeocentricCoordinateTransformation.Result wgsGeocentricResult = wgs84GeocentricTransformation.directTransformation(phi, lambda, HEIGHT);
        SevenParameterTransformation.Result lambert72GeocentricResult = sevenParameterTransformation.directTransformation(wgsGeocentricResult.getX(), wgsGeocentricResult.getY(), wgsGeocentricResult.getZ());
        GeographicalToGeocentricCoordinateTransformation.Result lambert72GeographicalResult = lambert72GeocentricTransformation.inverseTransformation(lambert72GeocentricResult.getX(), lambert72GeocentricResult.getY(), lambert72GeocentricResult.getZ());

        return lambert72FlatTransformation.directTransformation(lambert72GeographicalResult.getPhi(), lambert72GeographicalResult.getLambda());
    }

    public GeographicalToGeocentricCoordinateTransformation.Result transformInverse(double x, double y) {

        GeographicalToFlatCoordinateTransformation.Result lambert72GeographicalResult = lambert72FlatTransformation.inverseTransformation(x, y);
        GeographicalToGeocentricCoordinateTransformation.Result lambert72GeocentricResult = lambert72GeocentricTransformation.directTransformation(lambert72GeographicalResult.getPhi(), lambert72GeographicalResult.getLambda(), HEIGHT);
        SevenParameterTransformation.Result wgsGeocentricResult = sevenParameterTransformation.inverseTransformation(lambert72GeocentricResult.getX(), lambert72GeocentricResult.getY(), lambert72GeocentricResult.getZ());

        return wgs84GeocentricTransformation.inverseTransformation(wgsGeocentricResult.getX(), wgsGeocentricResult.getY(), wgsGeocentricResult.getZ());
    }
}
