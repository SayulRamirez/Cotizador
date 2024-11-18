package edu.segundo.utils;

import javax.swing.*;
import java.awt.*;

public class Etiqueta extends JLabel {

    public Etiqueta(String contenido, int x, int y, int width) {
        super(contenido);
        setBounds(x, y, width, 15);
        setFont(new Font("SansSerif", Font.PLAIN, 16));
    }
}
