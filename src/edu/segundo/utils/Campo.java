package edu.segundo.utils;

import javax.swing.*;
import java.awt.*;

public class Campo extends JTextField {

    public Campo (int x, int y, int width, boolean editable) {
        setBounds(x, y, width, 25);
        setFont(new Font("SansSerif", Font.PLAIN, 16));
        setEditable(editable);
    }
}
