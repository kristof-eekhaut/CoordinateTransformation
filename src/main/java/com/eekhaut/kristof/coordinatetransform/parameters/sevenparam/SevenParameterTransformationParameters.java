package com.eekhaut.kristof.coordinatetransform.parameters.sevenparam;

public interface SevenParameterTransformationParameters {

    /**
     * X-component of the translation vector
     */
    double getDx();

    /**
     * Y-component of the translation vector
     */
    double getDy();

    /**
     * Z-component of the translation vector
     */
    double getDz();

    /**
     * Scale factor
     */
    double getS();

    /**
     * Rotation about the X-axis
     */
    double getRx();

    /**
     * Rotation about the Y-axis
     */
    double getRy();

    /**
     * Rotation about the Z-axis
     */
    double getRz();
}
