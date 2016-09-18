package com.eekhaut.kristof.coordinatetransform.parameters.sevenparam;

import static com.eekhaut.kristof.coordinatetransform.util.DegreeUtils.degreesToRadians;

public class BD72ToETRS89TransformationParameters implements SevenParameterTransformationParameters {

    private static final double dx = 106.868628;
    private static final double dy = -52.297783;
    private static final double dz = 103.723893;

    private static final double s = 0.0000012747;

    private static final double rx = degreesToRadians(0, 0, 0.336570);
    private static final double ry = degreesToRadians(0, 0, -0.456955);
    private static final double rz = degreesToRadians(0, 0, 1.842183);

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public double getDz() {
        return dz;
    }

    @Override
    public double getS() {
        return s;
    }

    @Override
    public double getRx() {
        return rx;
    }

    @Override
    public double getRy() {
        return ry;
    }

    @Override
    public double getRz() {
        return rz;
    }
}
