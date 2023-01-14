package gui;

import javax.swing.*;

import java.awt.*;


public class Frame extends JFrame {

    public Panel panel;

    public Frame(){
        this.panel = new Panel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}