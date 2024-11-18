package edu.segundo.conexion;

import edu.segundo.modelos.Cotizacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CotizacionRepositorio {

    private final Connection con;

    public CotizacionRepositorio() {
        con = Conexion.getConexion();
    }

    public int crear(Cotizacion cotizacion) {
        try {
            PreparedStatement query = con.prepareStatement(
                    "insert into cotizaciones (numero_cotizacion, fecha, precio_automovil, descuento, enganche, monto_financiar, numero_plazos, interes, nombre_cliente, direccion_cliente, ocupacion_cliente, empresa, telefono, automovil_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            query.setInt(1, cotizacion.numeroCotizacion());
            query.setString(2, cotizacion.fecha().toString());
            query.setDouble(3, cotizacion.precioAutomovil());
            query.setDouble(4, cotizacion.descuento());
            query.setDouble(5, cotizacion.enganche());
            query.setDouble(6, cotizacion.montoFinanciar());
            query.setDouble(7, cotizacion.numeroPlazos());
            query.setDouble(8, cotizacion.interes());
            query.setString(9, cotizacion.nombreCliente());
            query.setString(10, cotizacion.direccionCliente());
            query.setString(11, cotizacion.ocupacionCliente());
            query.setString(12, cotizacion.empresa());
            query.setString(13, cotizacion.telefono());
            query.setInt(14, cotizacion.automovilId());

            return query.executeUpdate();

        } catch (SQLException e) {
            return 0;
        }
    }

    public int obtenerNumeroCotizacion() {
        try {
            ResultSet resultado = con.prepareStatement(
                    "select max(id) as ultimo_id from cotizaciones").executeQuery();

            resultado.next();

            return resultado.getInt("ultimo_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
