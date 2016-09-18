package com.eekhaut.kristof.coordinatetransform.parameters.coordinatesystem;

public class ETRS89Parameters implements GeodeticCoordinateSystemParameters {

    private static final double a = 6378137D;
    private static final double f = 1.0 / 298.257222101;

    @Override
    public double getA() {
        return a;
    }

    @Override
    public double getF() {
        return f;
    }
}
