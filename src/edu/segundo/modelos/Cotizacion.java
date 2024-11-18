package edu.segundo.modelos;

import java.time.LocalDate;

public record Cotizacion(
        int numeroCotizacion,

        LocalDate fecha,

        double precioAutomovil,

        double descuento,

        double enganche,

        double montoFinanciar,

        int numeroPlazos,

        double interes,

        String nombreCliente,

        String direccionCliente,

        String ocupacionCliente,

        String empresa,

        String telefono,

        int automovilId) {
}
