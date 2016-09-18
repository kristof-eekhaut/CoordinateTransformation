package com.eekhaut.kristof.coordinatetransform.parameters.projectionsystem;

import com.eekhaut.kristof.coordinatetransform.parameters.coordinatesystem.GeodeticCoordinateSystemParameters;

public interface ProjectionParameters extends GeodeticCoordinateSystemParameters {

    /**
     * Latitude of the 1st standard parallel
     */
    double getPhi1();

    /**
     * Latitude of the 2nd standard parallel
     */
    double getPhi2();

    /**
     * Latitude of the origin
     */
    double getPhi0();

    /**
     * Longitude of the origin
     */
    double getLambda0();

    /**
     * X coordinate of the origin
     */
    double getX0();

    /**
     * Y coordinate of the origin
     */
    double getY0();
}
