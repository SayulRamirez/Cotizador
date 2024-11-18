package edu.segundo.modelos;

public record Automovil(
        int id,

        double precio,

        String color,

        String kilometraje,

        String numeroPlazas,

        String tipoTransmision,

        String tipoCombustible
) {

    public String obtenerCaracteristicas() {

        return "Color: " + color() + "\nKilometraje: " + kilometraje()
                + "\nNúmero de plazas: " + numeroPlazas()
                + "\nTipo de transmisión: " + tipoTransmision()
                + "\nTipo de combustible: " + tipoCombustible();
    }
}
