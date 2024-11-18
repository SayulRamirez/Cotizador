package edu.segundo.utils;

import javax.swing.*;
import java.awt.*;

public class Selector extends JComboBox<String> {

    public Selector(int x, int y, int width) {
        super();
        setBounds(x, y, width, 25);
        setFont(new Font("SansSerif", Font.PLAIN, 16));
    }
}
