package edu.segundo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Connection con;

    private Conexion() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cotizador",
                    "avanzada", "1234"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    public static Connection getConexion() {
        if (con == null) {
            new Conexion();
        }

        return con;
    }

    public static void cerrarConexion() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
