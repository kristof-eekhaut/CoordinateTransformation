package com.eekhaut.kristof.coordinatetransform.parameters.coordinatesystem;

public class BD72Parameters implements GeodeticCoordinateSystemParameters {

    private static final double a = 6378388D;
    private static final double f = 1.0 / 297.0;

    @Override
    public double getA() {
        return a;
    }

    @Override
    public double getF() {
        return f;
    }
}
