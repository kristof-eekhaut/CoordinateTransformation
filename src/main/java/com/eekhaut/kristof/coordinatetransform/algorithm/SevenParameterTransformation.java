package com.eekhaut.kristof.coordinatetransform.algorithm;

import com.eekhaut.kristof.coordinatetransform.parameters.sevenparam.SevenParameterTransformationParameters;

public class SevenParameterTransformation {

    private final double rx;  // ω(x)
    private final double ry;  // ω(y)
    private final double rz;  // ω(z)

    private final double s;

    private final double dx;  // ∆(x)
    private final double dy;  // ∆(y)
    private final double dz;  // ∆(z)

    public SevenParameterTransformation(SevenParameterTransformationParameters parameters) {
        this.rx = parameters.getRx();
        this.ry = parameters.getRy();
        this.rz = parameters.getRz();

        this.s = parameters.getS();

        this.dx = parameters.getDx();
        this.dy = parameters.getDy();
        this.dz = parameters.getDz();
    }

    /**
     * @param x in meters
     * @param y in meters
     * @param z in meters
     * @return Result
     */
    public Result directTransformation(double x, double y, double z) {

        double tx = dx + (1 + s) * (  (1 * x) +  (rz * y) + (-ry * z));
        double ty = dy + (1 + s) * ((-rz * x) +   (1 * y) +  (rx * z));
        double tz = dz + (1 + s) * ( (ry * x) + (-rx * y) +   (1 * z));

        return new Result(tx, ty, tz);
    }

    /**
     * @param x in meters
     * @param y in meters
     * @param z in meters
     * @return Result
     */
    public Result inverseTransformation(double x, double y, double z) {

        double tx = -dx + (1 / (1 + s)) * (  (1 * x) + (-rz * y) +  (ry * z));
        double ty = -dy + (1 / (1 + s)) * ( (rz * x) +   (1 * y) + (-rx * z));
        double tz = -dz + (1 / (1 + s)) * ((-ry * x) +  (rx * y) +   (1 * z));

        return new Result(tx, ty, tz);
    }

    public static class Result {
        private double x, y, z;

        Result(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
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
    }
}
