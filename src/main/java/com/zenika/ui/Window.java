package com.zenika.ui;

import javax.swing.JFrame;

public class Window extends JFrame {
    public Window() {
        setTitle("Casse-Brique");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new GamePanel());
        setVisible(true);
    }
}