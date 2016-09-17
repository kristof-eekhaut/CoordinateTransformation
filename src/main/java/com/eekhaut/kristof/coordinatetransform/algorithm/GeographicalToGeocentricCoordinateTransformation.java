package com.eekhaut.kristof.coordinatetransform.algorithm;

public class GeographicalToGeocentricCoordinateTransformation {

    private final double a;
    private final double f;

    private double eSquared;

    public GeographicalToGeocentricCoordinateTransformation(double a, double f) {
        this.a = a;
        this.f = f;

        initCommonValues();
    }

    private void initCommonValues() {
        eSquared = 2.0 * f - Math.pow(f, 2);
    }

    /**
     * @param phi in radians
     * @param lambda in radians
     * @param h in meters
     * @return Result
     */
    public Result directTransformation(double phi, double lambda, double h) {
        double v = a / Math.sqrt(1 - eSquared * Math.pow(Math.sin(phi), 2));

        double x = (v + h) * Math.cos(phi) * Math.cos(lambda);
        double y = (v + h) * Math.cos(phi) * Math.sin(lambda);
        double z = ((1 - eSquared) * v + h) * Math.sin(phi);

        return new Result(x, y, z, phi, lambda, h);
    }

    /**
     * @param x in meters
     * @param y in meters
     * @param z in meters
     * @return Result
     */
    public Result inverseTransformation(double x, double y, double z) {
        double pSquared = Math.pow(x, 2) + Math.pow(y, 2);
        double p = Math.sqrt(pSquared);

        double r = Math.sqrt(pSquared + Math.pow(z, 2));
        double u = Math.atan((z / p) * ((1 - f) + (eSquared * a / r)));

        double lambda = Math.atan(y / x);
        double phi = Math.atan( (z * (1 - f) + eSquared * a * Math.pow(Math.sin(u), 3)) / ((1 - f) * (p - eSquared * a * Math.pow(Math.cos(u), 3))) );
        double h = p * Math.cos(phi) + z * Math.sin(phi) - a * Math.sqrt(1 - eSquared * Math.pow(Math.sin(phi), 2));

        return new Result(x, y, z, phi, lambda, h);
    }

    public static class Result {
        private double x, y, z;
        private double phi, lambda, h;

        Result(double x, double y, double z, double phi, double lambda, double h) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.phi = phi;
            this.lambda = lambda;
            this.h = h;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
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

        public double getH() {
            return h;
        }
    }
}
