package perceptron;

import java.util.InputMismatchException;

public class Function {

    public static Matrix matrixMultiplication(Matrix firstMatrix, Matrix secondMatrix) {
        int firstMatrixRows = firstMatrix.matrix.length;
        int firstMatrixColumns = firstMatrix.matrix[0].length;
        int secondMatrixColumns = secondMatrix.matrix[0].length;

        double[][] product = new double[firstMatrixRows][secondMatrixColumns];

        for (int i = 0; i < firstMatrixRows; i++) {
            for (int j = 0; j < secondMatrixColumns; j++) {
                for (int k = 0; k < firstMatrixColumns; k++) {
                    product[i][j] += firstMatrix.matrix[i][k] * secondMatrix.matrix[k][j];
                }
            }
        }

        return new Matrix(product);
    }

    public static Matrix sigmoid (Matrix matrix){
        double[][] values = new double[matrix.rows][matrix.columns];
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.columns; j++) {
                values[i][j] = sigmoid(matrix.matrix[i][j]);
            }
        }
        return new Matrix(values);
    }
    public static double sigmoid(double x){
        return 1/(1+ Math.exp((-1*x)));
    }

    public static Matrix derivativeSigmoid (Matrix matrix){
        double[][] values = new double[matrix.rows][matrix.columns];
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.columns; j++) {
                values[i][j] = sigmoid(matrix.matrix[i][j])*(1-sigmoid(matrix.matrix[i][j]));;
            }
        }
        return new Matrix(values);
    }

    //This Method does not multiply Matrices in the classical way, but performs multiplication only on Matrices with
    //the same size.
    public static Matrix times(Matrix a, Matrix b){

        double [][] values = new double[a.rows][a.columns];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j]= a.matrix[i][j]*b.matrix[i][j];
            }
        }
        return new Matrix(values);
    }

    public static Matrix addMatrix(Matrix a, Matrix b){
        double [][] values = new double[a.rows][a.columns];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j]= a.matrix[i][j]+b.matrix[i][j];
            }
        }
        return new Matrix(values);
    }

    public static Matrix subtractMatrix(Matrix a, Matrix b){
        double [][] values = new double[a.rows][a.columns];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j]= a.matrix[i][j]-b.matrix[i][j];
            }
        }
        return new Matrix(values);
    }

    public static Matrix timesRealNumber(Matrix a, double number){
        double [][] values = new double[a.rows][a.columns];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j]= a.matrix[i][j]*number;
            }
        }
        return new Matrix(values);
    }

    //Just to debug
    public static void printMatrix(Matrix m){
        System.out.println("\n");
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.columns; j++) {
                System.out.print(m.matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }
}
