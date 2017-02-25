package com.eekhaut.kristof.coordinatetransform.parameters.projectionsystem;

import com.eekhaut.kristof.coordinatetransform.parameters.coordinatesystem.BD72Parameters;

import static com.eekhaut.kristof.coordinatetransform.util.DegreeUtils.degreesToRadians;
import static java.lang.Math.toRadians;

public class Lambert72ProjectionParameters extends BD72Parameters
        implements ProjectionParameters {

    private static final double phi1 = degreesToRadians(49, 50, 0.00204); // ϕ1
    private static final double phi2 = degreesToRadians(51, 10, 0.00204); // ϕ2

    private static final double phi0 = toRadians(90); // ϕ0
    private static final double lambda0 = degreesToRadians(4, 22, 2.952); // λ0
    private static final double x0 = 150000.013;
    private static final double y0 = 0;

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
