package org.example;

import gui.Frame;
import gui.Panel;
import perceptron.Perceptron;


import java.io.File;

public class Main {
    public static void main(String[] args) {
        Einlesen.einlesenVorlesungsbeispiele(new File("src/main/java/data/circularPattern.txt"));
        // the length of the array stands for the amount of Layers the Perceptron has the Value in each position for the
        // amount of Neurons the Layer has

        int [] amountNeurons = {10};
        double [] inputValues = new double[Einlesen.inputValues[0].length];
        double [] expectedValues = new double[1];
        Perceptron perceptron = new Perceptron(inputValues, amountNeurons, expectedValues, Einlesen.inputValues);
        perceptron.train(100_000);
        perceptron.evaluate();
        Panel.red = perceptron.red;
        Panel.blue = perceptron.blue;
        buildGui();

    }

    private static void buildGui() {
        Frame frame = new Frame();

    }
}