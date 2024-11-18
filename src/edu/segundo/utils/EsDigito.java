package edu.segundo.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EsDigito extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar())) e.consume();
    }
}
