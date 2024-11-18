package edu.segundo.utils;

import javax.swing.*;
import java.awt.*;

public class Boton extends JButton {

    public static final int ROJO = 1;

    public static final int AZUL = 2;

    public Boton (String message, int x, int y, int width, int color) {
        super(message);
        setBounds(x, y, width, 25);
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.WHITE);
        setBorderPainted(false);

        setBackground((color == Boton.AZUL) ? new Color(30, 160, 237) : new Color(204, 2, 2));
    }
}

