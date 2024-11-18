package edu.segundo.utils;

/**
 * Enum para representar diferentes plazos de financiamiento y sus intereses.
 */
public enum Plazos {

    /**
     * Plazo de doce meses con un interés del 15%
     */
    DOCE(15, 12),

    /**
     * Plazo de veinticuatro meses con un interés del 20%
     */
    VEINTICUATRO(20, 24),

    /**
     * Plazo de treinta y seis meses con un interés del 30%
     */
    TREINTA_Y_SEIS(30, 36);

    private final double interes;

    private final int plazos;

    Plazos(double interes, int plazos) {
        this.interes = interes;
        this.plazos = plazos;
    }

    public double getInteres() {
        return interes;
    }

    public int getPlazos() {
        return plazos;
    }
}
