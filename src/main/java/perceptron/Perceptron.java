package perceptron;

import org.example.Einlesen;


import java.awt.*;
import java.util.ArrayList;

import static perceptron.Function.*;

public class Perceptron {
    public ArrayList<Point> red = new ArrayList<>();
    public ArrayList<Point> blue = new ArrayList<>();

    public int[] amountNeurons;
    public Matrix expectedValues;
    public Matrix inputValues;

    public Matrix[] weights;
    public Matrix[] networkInputs;
    public Matrix[] activationLevels;
    public Matrix[] deltas;
    double alpha = 0.1;
    double[][] pattern;

    public Perceptron(double[] inputValues, int amountNeurons[], double[] expectedValues, double [][] pattern) {
        this.inputValues = createMatrix(inputValues);
        this.amountNeurons = amountNeurons;
        this.expectedValues = createMatrix(expectedValues);
        this.pattern = pattern;
        weights = new Matrix[amountNeurons.length + 1];
        networkInputs = new Matrix[amountNeurons.length + 1];
        activationLevels = new Matrix[amountNeurons.length + 1];
        deltas = new Matrix[amountNeurons.length + 1];

        createWeights();
    }

    public void train(int iterations) {
        int[] randomPattern = chooseRandomOrder(pattern.length);
        for (int j = 0; j < iterations; j++) {
            for (int i = 0; i < randomPattern.length; i++) {
                double [] inputValue = {pattern[randomPattern[i]][0],pattern[randomPattern[i]][1]};
                double [] expectedValues = {Einlesen.expectedValues[randomPattern[i]]};
                setExpectedValues(expectedValues);
                setInputValues(inputValue);
                forwardPass();
                backwardPass();

            }

        }
        System.out.println("Delta: " + deltas[deltas.length - 1].matrix[0][0]);
        System.out.println("Calculated value: " + activationLevels[amountNeurons.length].matrix[0][0] +
                "\tExpected Value: " + expectedValues.matrix[0][0] + "\n");

    }

    private int[] chooseRandomOrder(int length) {
        int[] random = new int[length];
        for (int i = 0; i < length; i++) {
            random[i] = (int) (Math.random() * length);
        }
        return random;
    }

    void createWeights() {
        int columns = inputValues.rows + 1; // 1 is added for the extra weights of the Bias Neuron
        for (int i = 0; i < amountNeurons.length; i++) {
            weights[i] = new Matrix(amountNeurons[i], columns);
            columns = amountNeurons[i] + 1; // One column more for the Bias Neuron
        }
        weights[weights.length - 1] = new Matrix(expectedValues.rows, columns);
    }

    public void forwardPass() {
        Matrix inputVector = addBiasNeuron(inputValues);
        for (int i = 0; i < weights.length; i++) {
            Matrix networkInput = matrixMultiplication(weights[i], inputVector);
            networkInputs[i] = networkInput;
            Matrix activationLevel = sigmoid(networkInputs[i]);
            activationLevels[i] = activationLevel;
            inputVector = addBiasNeuron(activationLevel);
        }
    }


    public void backwardPass() {
        //Output delta is calculated
        Matrix subtract = subtractMatrix(expectedValues, activationLevels[deltas.length - 1]);
        Matrix derivative = derivativeSigmoid(networkInputs[deltas.length - 1]);
        deltas[deltas.length - 1] = times(derivative, subtract);
        //HiddenLayerDeltas are calculated
        for (int i = networkInputs.length - 2; i >= 0; i--) {
            Matrix derivativeNetworkInput = derivativeSigmoid(networkInputs[i]);
            Matrix withoutBias = withoutBias(weights[i + 1]);
            Matrix sumWeights = getDeltaNeurons(withoutBias, deltas[i + 1]); //Plus one because we need to sum up the weights entering the successice Layer of neurons
            deltas[i] = times(derivativeNetworkInput, sumWeights);
        }
        //Now we need to multiply the delta for each neuron  by the activation Level of the layer before and the alpha, after that we just subtract the received Capital Delta
        //to the weight of the Perceptron
        adjustWeights();
    }

    public Matrix getDeltaNeurons(Matrix weightsWithoutBias, Matrix delta) {
        weightsWithoutBias = multiplyDelta(weightsWithoutBias, delta);
        double[][] values = new double[1][weightsWithoutBias.columns];

        for (int i = 0; i < weightsWithoutBias.columns; i++) {
            double sum = 0;
            for (int j = 0; j < weightsWithoutBias.rows; j++) {
                sum += weightsWithoutBias.matrix[j][i];
            }
            values[0][i] = sum;
        }
        return transformMatrixVertical(new Matrix(values));
    }

    public Matrix multiplyDelta(Matrix weightsWithoutBias, Matrix delta) {
        double[][] values = new double[weightsWithoutBias.rows][weightsWithoutBias.columns];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j] = weightsWithoutBias.matrix[i][j] * delta.matrix[i][0];
            }
        }
        return new Matrix(values);
    }


    private Matrix withoutBias(Matrix m) {
        double[][] values = new double[m.rows][m.columns - 1]; //The column with the Bias weights are subtracted

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j] = m.matrix[i][j + 1];
            }
        }
        return new Matrix(values);
    }

    private void adjustWeights() {
        int length = deltas.length - 1;
        for (int i = length; i > 0; i--) {
            Matrix smallDelta = timesRealNumber(matrixMultiplication(deltas[length], transformMatrix(addBiasNeuron(activationLevels[length - 1]))), alpha);
            weights[length] = addMatrix(weights[length], smallDelta);
        }
        Matrix smallDelta = timesRealNumber(matrixMultiplication(deltas[0], transformMatrix(addBiasNeuron(inputValues))), alpha);
        weights[0] = addMatrix(weights[0], smallDelta);
    }

    private Matrix addBiasNeuron(Matrix matrix) {
        double[][] values = new double[matrix.rows + 1][matrix.columns];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                if (i == 0) {
                    values[i][j] = 1;
                } else {
                    values[i][j] = matrix.matrix[i - 1][j];
                }
            }
        }
        return new Matrix(values);

    }


    //The Inputvalues need to be placed vertically in Matrix because of the scalar multiplication
    private Matrix createMatrix(double[] values) {
        double[][] matrix = new double[values.length][1];
        for (int i = 0; i < values.length; i++) {
            matrix[i][0] = values[i];
        }
        return new Matrix(matrix);
    }

    //Transforms the Matrix, this is because the deltas and the activationLevels are both vertical vectors,
    //to scalarly multiply them we have to transform the Matrix
    public Matrix transformMatrix(Matrix m) {
        double[][] values = new double[1][m.rows];
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.columns; j++) {
                values[0][i] = m.matrix[i][j];
            }
        }
        return new Matrix(values);
    }

    public Matrix transformMatrixVertical(Matrix m) {
        double[][] values = new double[m.columns][1];
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.columns; j++) {
                values[j][0] = m.matrix[i][j];
            }
        }
        return new Matrix(values);
    }

    public void setExpectedValues(double[] expectedValues) {
        this.expectedValues = createMatrix(expectedValues);
    }

    public void setInputValues(double[] inputValues) {
        this.inputValues = createMatrix(inputValues);
    }

    public void evaluate() {
        double[] input = new double[2];
        for (int z = 500; z >= 0; z--) {
            for (int s = 0; s <= 500; s++) {

                input[0] = (double) (s / 500.);
                input[1] = (double) (z / 500.);
                inputValues = createMatrix(input);
                forwardPass();

                //if(z==90 && s==20)
                double x = activationLevels[activationLevels.length - 1].matrix[0][0];
                if (x > 0.5) {
                    red.add(new Point(z,s));
                }else{
                    blue.add(new Point(z,s));
                }
            }

        }
    }
}
