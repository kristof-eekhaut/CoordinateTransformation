package com.eekhaut.kristof.coordinatetransform.algorithm;

import com.eekhaut.kristof.coordinatetransform.parameters.projectionsystem.ProjectionParameters;

public class GeographicalToFlatCoordinateTransformation {

    private final double a;
    private final double f;

    private final double phi1; // ϕ1
    private final double phi2; // ϕ2

    private final double phi0; // ϕ0
    private final double lambda0; // λ0

    private final double x0;
    private final double y0;

    private final int phyCalculationPrecisionDepth;

    private double eSquared;
    private double e;

    private double n;
    private double g;
    private double r0;

    public GeographicalToFlatCoordinateTransformation(ProjectionParameters parameters, int phyCalculationPrecisionDepth) {
        this.a = parameters.getA();
        this.f = parameters.getF();
        this.phi1 = parameters.getPhi1();
        this.phi2 = parameters.getPhi2();
        this.phi0 = parameters.getPhi0();
        this.lambda0 = parameters.getLambda0();
        this.x0 = parameters.getX0();
        this.y0 = parameters.getY0();

        this.phyCalculationPrecisionDepth = phyCalculationPrecisionDepth;

        initCommonValues();
    }

    private void initCommonValues() {
        eSquared = 2.0 * f - Math.pow(f, 2);
        e = Math.sqrt(eSquared);

        double m1 = m(phi1);
        double m2 = m(phi2);
        double t1 = t(phi1);
        double t2 = t(phi2);
        double t0 = t(phi0);

        n = (Math.log(m1) - Math.log(m2)) / (Math.log(t1) - Math.log(t2));
        g = m1 / (n * Math.pow(t1, n));
        r0 = r(t0);
    }

    /**
     * @param phi in radians
     * @param lambda in radians
     * @return Result
     */
    public Result directTransformation(double phi, double lambda) {
        double t = t(phi);
        double r = r(t);
        double theta = n * (lambda - lambda0); // θ

        double x = x0 + r * Math.sin(theta);
        double y = y0 + r0 - r * Math.cos(theta);

        return new Result(x, y, phi, lambda);
    }

    /**
     * @param x in meters
     * @param y in meters
     * @return Result
     */
    public Result inverseTransformation(double x, double y) {
        double r = Math.sqrt(Math.pow(x - x0, 2) + Math.pow(r0 - (y - y0), 2));
        double t = Math.pow(r / (a * g), 1 / n);
        double theta = Math.atan((x - x0) / (r0 - (y - y0)));

        double phiApproximation = (Math.PI / 2.0) - (2.0 * Math.atan(t));

        double lambda = (theta / n) + lambda0;
        double phi = improvePhiApproximation(phiApproximation, t, phyCalculationPrecisionDepth);

        return new Result(x, y, phi, lambda);
    }

    private double improvePhiApproximation(double phiApproximation, double t, int timesToRepeat) {
        double improvedApproximation = (Math.PI / 2.0) - (2.0 * Math.atan( t * Math.pow((1 - e * Math.sin(phiApproximation)) / (1 + e * Math.sin(phiApproximation)), e / 2.0) ));
        if(timesToRepeat > 1) {
            return improvePhiApproximation(improvedApproximation, t, timesToRepeat - 1);
        }
        return improvedApproximation;
    }

    private double r(double t) {
        return a * g * Math.pow(t, n);
    }

    private double m(double phi) {
        return Math.cos(phi) / Math.sqrt(1 - eSquared * Math.pow(Math.sin(phi), 2));
    }

    private double t(double phi) {
        return Math.tan((Math.PI / 4.0) - (phi / 2.0)) / Math.pow((1 - e * Math.sin(phi)) / (1 + e * Math.sin(phi)), e / 2.0);
    }

    public static class Result {
        private double x, y;
        private double phi, lambda;

        Result(double x, double y, double phi, double lambda) {
            this.x = x;
            this.y = y;
            this.phi = phi;
            this.lambda = lambda;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getPhi() {
            return phi;
        }

        public double getLatitude() {
            return Math.toDegrees(phi);
        }

        public double getLambda() {
            return lambda;
        }

        public double getLongitude() {
            return Math.toDegrees(lambda);
        }
    }
}
