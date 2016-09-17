package com.eekhaut.kristof.coordinatetransform.util;

public class DegreeUtils {

    public static Double toDegrees(int degrees, int minutes, int seconds, int ms) {
        return degrees + (((double)(60 * 1000 * minutes + 1000 * seconds + ms)) / 3600000d);
    }

    private static double toDegrees(int degrees, int minutes, int seconds) {
        return degrees + (((double)(60 * minutes + seconds)) / 3600d);
    }

    private static double toDegrees(int degrees, int minutes, double seconds) {
        return degrees + ((60 * minutes + seconds) / 3600d);
    }

    private static double toDegrees(int degrees, double minutes) {
        return degrees + (minutes / 60d);
    }

    public static Double degreesToRadians(int degrees, int minutes, int seconds, int ms) {
        return Math.toRadians(toDegrees(degrees, minutes, seconds, ms));
    }

    public static Double degreesToRadians(int degrees, int minutes, int seconds) {
        return Math.toRadians(toDegrees(degrees, minutes, seconds));
    }

    public static Double degreesToRadians(int degrees, int minutes, double seconds) {
        return Math.toRadians(toDegrees(degrees, minutes, seconds));
    }

    public static Double degreesToRadians(int degrees, int minutes) {
        return Math.toRadians(toDegrees(degrees, minutes));
    }
}
