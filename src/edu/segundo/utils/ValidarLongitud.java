package edu.segundo.utils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ValidarLongitud extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() instanceof JTextField) {

            if (((JTextField) e.getSource()).getText().length() >= 100) e.consume();
        }
    }
}
