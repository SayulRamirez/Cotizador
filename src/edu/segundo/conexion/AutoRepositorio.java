package edu.segundo.conexion;

import edu.segundo.modelos.Automovil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutoRepositorio {

    private final Connection con;

    public AutoRepositorio() {
        con = Conexion.getConexion();
    }

    public List<String> obtenerMarcas() {
        List<String> marcas = new ArrayList<>();

        try {
            ResultSet respuesta = con.prepareStatement("select marca from marcas").executeQuery();

            while (respuesta.next()) marcas.add(respuesta.getString("marca"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return marcas;
    }

    public List<String> obtenerModelos(String marca) {
        List<String> modelos = new ArrayList<>();

        try {
            PreparedStatement query = con.prepareStatement(
                    "select modelo from modelos where marca_id in (select id from marcas where marca = ?)");

            query.setString(1, marca);

            ResultSet respuesta = query.executeQuery();

            while (respuesta.next()) modelos.add(respuesta.getString("modelo"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return modelos;
    }

    public List<String> obtenerVersiones(String modelo) {

        List<String> versiones = new ArrayList<>();

        try {
            PreparedStatement query = con.prepareStatement(
                    "select version from versiones where modelo_id in (select id from modelos where modelo = ?)");

            query.setString(1, modelo);

            ResultSet resultado = query.executeQuery();

            while (resultado.next()) versiones.add(resultado.getString("version"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return versiones;
    }

    public Automovil obtenerAutomovil(String version) {

        try {
            PreparedStatement query = con.prepareStatement("select * from automoviles where version_id in (select id from versiones where version = ?)");

            query.setString(1, version);
            ResultSet resultado = query.executeQuery();

            resultado.next();

            return new Automovil(
                        resultado.getInt("id"),
                        resultado.getDouble("precio"),
                        resultado.getString("color"),
                        resultado.getString("kilometraje"),
                        resultado.getString("numero_plazas"),
                        resultado.getString("tipo_transmision"),
                        resultado.getString("tipo_combustible")
            );

        } catch (SQLException e) {
            return null;
        }

    }
}
