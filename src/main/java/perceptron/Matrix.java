package perceptron;

public class Matrix {
    public int rows;
    public int columns;

    public double [][] matrix;

    //Used when creating weights
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
        fillMatrix();
    }

    //Used for instatiating a Matrix when you already have the matrix
    public Matrix(double [][] matrix){
        this.matrix = matrix;
        rows = matrix.length;
        columns = matrix[0].length;
    }
    private void fillMatrix(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(Math.random()<0.5){
                    matrix[i][j]= -1 * Math.random();
                }
                else{
                    matrix[i][j]= Math.random();
                }
            }
        }
    }




}
