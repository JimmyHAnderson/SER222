package edu.ser222.m01_02;

/**
 * An implementation of the Matrix ADT. Provides four basic operations over an immutable type.
 * 
 * Last updated 7/31/2021.
 * 
 * @author (Jimmy Anderson)
 * @version (version 1)
 */

public class CompletedMatrix implements Matrix {
   // creating variables
    private final int[][] matrix;


    //Constructor method
    public CompletedMatrix(int[][] matrix) {
        //checks to see if a matrix is null will throw an exception if it is
        if (matrix == null) {
            throw new IllegalArgumentException("Can't have a null matrix lil bro");
        }
        //now we check if a matrix has alength of 0, we will then initialize it with rows and collumns
        if (matrix.length == 0) {
            this.matrix = new int[0][0];
            //if there is already a size for the matrix, we will adjust and make a copy
        } else {
            this.matrix = new int[matrix.length][];
            for (int i = 0; i < matrix.length; i++) {
                this.matrix[i] = matrix[i].clone();
            }
        }
    }
        //simply returns the values
    @Override
    public int getElement(int y, int x) {
        return matrix[y][x];

    }
        //will return the rows as an integer
    @Override
    public int getRows() {
        return matrix.length;
    }
        //will return the rows as an integer
    //we will also check to see if the length is greater than 0 since we cannot have negative columns
    @Override
    public int getColumns() {
        if (matrix.length > 0) {
            return matrix[0].length;
        } else {
            return 0;
        }
    }



    //method will take a matrix and scale it by a factor
    //we will create a new matrix which will be the newly scaled matrix and use two for loops to iterate through the new matrix

    @Override
    public Matrix scale(int scalar) {
        //creation of the new matrix
        int[][] scaledMatrix = new int[getRows()][getColumns()];
        //we iterate through rows and columns to multiple the values based on the scale
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                scaledMatrix[i][j] = matrix[i][j] * scalar;
            }
        }
        return new CompletedMatrix(scaledMatrix);
    }



    //method will take in a matrix and add it with Matrix other
    //we will first check to see if it is possible to add the two matrixes since a null matrix cannot be added
    @Override
    public Matrix plus(Matrix other) {
        //checks to see if matrix other is null
        if(other == null){
            throw new IllegalArgumentException("Can't add a null matrix lil bro lol");
        }
        //now we check to see if the the two matrixes have the same amount of rows, if they do then we can run the plus method
        //otherwise if one matrix has a different amount of rows., then we will throw another exception since we cannot add
        //two matrixes with didferent rows
        if(this.getRows() != other.getRows() || this.getColumns() != other.getColumns()) {
            throw new RuntimeException("Lil bro we need to have the same dimensions");
        }

        //once we eliminate these two exceptions we can now implement the addition method
        int[][] addedMatrix = new int[getRows()][getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                addedMatrix[i][j] = matrix[i][j] + other.getElement(i, j);
            }
        }
        return new CompletedMatrix(addedMatrix);
    }


    //method will take in a matrix and subtract it with Matrix other
    //similar to the plus method we will perform the same checks since subtraction requires the same constraints as addition
    @Override
    public Matrix minus(Matrix other) {
        //checks to see if matrix other is null
        if(other == null) {
            throw new IllegalArgumentException("Can't subtract a null matrix lil bro lol");
        }
        //now we check to see if the the two matrixes have the same amount of rows, if they do then we can run the subtract method
        //otherwise if one matrix has a different amount of rows., then we will throw another exception since we cannot add
        //two matrixes with didferent rows
        if(this.getRows() != other.getRows() || this.getColumns() != other.getColumns()) {
            throw new RuntimeException("Lil bro we need to have the same dimensions");
        }

        //once we eliminate these two exceptions we can now implement the subtraction method
        int[][] subtractedMatrix = new int[getRows()][getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                subtractedMatrix[i][j] = matrix[i][j] - other.getElement(i, j);
            }
        }
        return new CompletedMatrix(subtractedMatrix);
    }


    //method will take in a matrix and multiply it with Matrix other
    //we will also check for null matrixes as well as matching rows and columns
    @Override
    public Matrix multiply(Matrix other) {
        //checks to see if matrix other is null
        if(other == null) {
            throw new IllegalArgumentException("Can't multiply a null matrix lil bro lol");
        }
        //now we check to see if the the two matrixes have the same amount of rows, if they do then we can run the multiply method
        //otherwise if one matrix has a different amount of rows., then we will throw another exception since we cannot add
        //two matrixes with didferent rows
        if(this.getColumns() != other.getRows()) {
            throw new RuntimeException("Lil bro we need to have the same row dimensions");
        }

        //once we eliminate these two exceptions we can now implement the multiplcation method method
        //we will use three forloops in order to correctly get the right result

        int[][] multipliedMatrix = new int[this.getRows()][other.getColumns()];
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                for (int k = 0; k < this.getColumns(); k++) {
                    multipliedMatrix[i][j] += this.matrix[i][k] * other.getElement(k, j);
                }
            }
        }
        return new CompletedMatrix(multipliedMatrix);
    }
//we now need to tostring method in otherwise we will get an address instead of the values
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                sb.append(this.getElement(i, j));
                if (j < this.getColumns() - 1) {
                    sb.append(" ");
                }
            }
                if (i < this.getRows()-1){
                    sb.append("\n");
                }
            }
        return sb.toString();
    }
    //similar to the tostring method we need a new equals method to compare the representations rather than the actual values

    @Override
    public boolean equals(Object other) {
         if (other ==this) {
            return true;
        }
        if(other == null) {
            return false;
        }
        if(other.getClass() != this.getClass()) {
            return false;
        }

        if (this.getRows() != ((CompletedMatrix) other).getRows() || this.getColumns() != ((CompletedMatrix) other).getColumns()) {
            return false;
        }

        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                if (this.getElement(i, j) != ((CompletedMatrix) other).getElement(i,j)) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Entry point for matrix testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //These tests show sample usage of the matrix, and some basic ideas for testing. They are not comprehensive.

        int[][] data1 = new int[0][0];
        int[][] data2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] data3 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data4 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data5 = {{1, 4, 7}, {2, 5, 8}};

        Matrix m1 = new CompletedMatrix(data1);
        Matrix m2 = new CompletedMatrix(data2);
        Matrix m3 = new CompletedMatrix(data3);
        Matrix m4 = new CompletedMatrix(data4);
        Matrix m5 = new CompletedMatrix(data5);

        System.out.println("m1 --> Rows: " + m1.getRows() + " Columns: " + m1.getColumns());
        System.out.println("m2 --> Rows: " + m2.getRows() + " Columns: " + m2.getColumns());
        System.out.println("m3 --> Rows: " + m3.getRows() + " Columns: " + m3.getColumns());

        //check for reference issues
        System.out.println("m2 -->\n" + m2);
        data2[1][1] = 101;
        System.out.println("m2 -->\n" + m2);

        //test equals
        System.out.println("m2==null: " + m2.equals(null));             //false
        System.out.println("m3==\"MATRIX\": " + m2.equals("MATRIX"));   //false
        System.out.println("m2==m1: " + m2.equals(m1));                 //false
        System.out.println("m2==m2: " + m2.equals(m2));                 //true
        System.out.println("m2==m3: " + m2.equals(m3));                 //false
        System.out.println("m3==m4: " + m3.equals(m4));                 //true

        //test operations (valid)
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("2 * m2:\n" + m2.scale(2));
        System.out.println("m2 + m3:\n" + m2.plus(m3));
        System.out.println("m2 - m3:\n" + m2.minus(m3));
        System.out.println("3 * m5:\n" + m5.scale(3));

        //not tested... multiply(). you know what to do.

        //test operations (invalid)
        //System.out.println("m1 + m2" + m1.plus(m2));
        //System.out.println("m1 + m5" + m1.plus(m5));
        //System.out.println("m1 - m2" + m1.minus(m2));
    }
}