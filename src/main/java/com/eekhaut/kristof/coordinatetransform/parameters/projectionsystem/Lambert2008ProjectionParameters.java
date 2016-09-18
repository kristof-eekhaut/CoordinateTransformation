package com.eekhaut.kristof.coordinatetransform.parameters.projectionsystem;

import com.eekhaut.kristof.coordinatetransform.parameters.coordinatesystem.ETRS89Parameters;

import static com.eekhaut.kristof.coordinatetransform.util.DegreeUtils.degreesToRadians;

public class Lambert2008ProjectionParameters extends ETRS89Parameters
        implements ProjectionParameters {

    private static final double phi1 = degreesToRadians(49, 50, 0, 0); // ϕ1
    private static final double phi2 = degreesToRadians(51, 10, 0, 0); // ϕ2

    private static final double phi0 = degreesToRadians(50, 47, 52, 134); // ϕ0
    private static final double lambda0 = degreesToRadians(4, 21, 33, 177); // λ0
    private static final double x0 = 649328D;
    private static final double y0 = 665262D;

    @Override
    public double getPhi1() {
        return phi1;
    }

    @Override
    public double getPhi2() {
        return phi2;
    }

    @Override
    public double getPhi0() {
        return phi0;
    }

    @Override
    public double getLambda0() {
        return lambda0;
    }

    @Override
    public double getX0() {
        return x0;
    }

    @Override
    public double getY0() {
        return y0;
    }
}
