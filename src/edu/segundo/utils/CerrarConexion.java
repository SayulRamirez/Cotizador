package edu.segundo.utils;

import edu.segundo.conexion.Conexion;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CerrarConexion extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        Conexion.cerrarConexion();
    }
}
