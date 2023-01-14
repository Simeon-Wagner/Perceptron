package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    public final int WIDTH  = 500;
    public final int HEIGHT = 500;


    public static ArrayList<Point> red = new ArrayList<>();
    public static ArrayList<Point> blue = new ArrayList<>();
    public static ArrayList<Point> coordinates = new ArrayList<>();


    Panel () {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }



    public void paintComponent (Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(1));


        graphics2D.setPaint(Color.ORANGE);
        for (Point p: red) {
            graphics2D.drawLine(p.x,HEIGHT-p.y,p.x,HEIGHT-p.y);
        }
        graphics2D.setPaint(Color.darkGray);
        for (Point p: blue) {
            graphics2D.drawLine(p.x,HEIGHT-p.y,p.x,HEIGHT-p.y);
        }
    }




}
