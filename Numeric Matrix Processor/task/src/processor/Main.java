package processor;

import java.util.Scanner;

class DimensionException extends Exception {

    private final static String ERROR_MESSAGE = "The operation cannot be performed.";

    public DimensionException() {
        super();
    }

    public DimensionException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

enum MenuOption {
    EXIT {
        @Override
        public void handle() {
            //Do Nothing
        }
    }, ADD {
        @Override
        public void handle() {
            Matrix a = UserInput.readFirstMatrix();
            Matrix b = UserInput.readSecondMatrix();

            try {
                a.add(b);
                UserOutput.displayResult(a);
            } catch (DimensionException e) {
                System.out.print(e.getMessage());
            }
        }
    }, SCALAR_MULT {
        @Override
        public void handle() {
            Matrix a = UserInput.readMatrix();
            int scalar = UserInput.readScalar();

            a.scalarMultiplication(scalar);

            UserOutput.displayResult(a);
        }
    }, VECTORIAL_MULT {
        @Override
        public void handle() {
            Matrix a = UserInput.readFirstMatrix();
            Matrix b = UserInput.readSecondMatrix();

            try {
                a.vectorialMultiplication(b);
                UserOutput.displayResult(a);
            } catch (DimensionException e) {
                System.out.println(e.getMessage());
            }
        }
    }, TRANSPOSE {
        @Override
        public void handle() {

        }
    },MAIN_TRANSPOSE {
        @Override
        public void handle() {
            Matrix matrix = UserInput.readMatrix();

            matrix.transposeMain();
            UserOutput.displayResult(matrix);
        }
    }, SIDE_TRANSPOSE {
        @Override
        public void handle() {
            Matrix matrix = UserInput.readMatrix();

            matrix.transposeSide();
            UserOutput.displayResult(matrix);
        }
    }, VERTICAL_TRANSPOSE {
        @Override
        public void handle() {
            Matrix matrix = UserInput.readMatrix();

            matrix.transposeVertical();
            UserOutput.displayResult(matrix);
        }
    }, HORIZONTAL_TRANSPOSE {
        @Override
        public void handle() {
            Matrix matrix = UserInput.readMatrix();
            matrix.transposeHorizontal();
            UserOutput.displayResult(matrix);
        }
    }, DETERMINANT {
        @Override
        public void handle() {
            Matrix matrix = UserInput.readMatrix();
            double det = matrix.determinant();
            UserOutput.displayDeterminant(det);
        }
    }, INVERSE {
        @Override
        public void handle() {
            Matrix matrix = UserInput.readMatrix();
            try {
                matrix.inverse();
                UserOutput.displayResult(matrix);
            } catch (DimensionException e) {
                System.out.println(e.getMessage());
            }

        }
    };

    public abstract void handle();
}

enum MenuState {
    EXIT {
        @Override
        public MenuState nextState() {
            return MenuState.EXIT;
        }

        @Override
        public MenuOption readUserOption() {
            return MenuOption.EXIT;
        }
    }, MENU {
        @Override
        public MenuState nextState() {
            MenuState nextState = MenuState.EXIT;
            UserOutput.displayMenu();
            MenuOption option = readUserOption();

            switch (option) {
                case EXIT:
                    nextState = MenuState.EXIT;
                    break;
                case ADD:
                case SCALAR_MULT:
                case VECTORIAL_MULT:
                case DETERMINANT:
                case INVERSE:
                    option.handle();
                    nextState = MenuState.MENU;
                    break;
                case TRANSPOSE:
                    option.handle();
                    nextState = MenuState.TRANSPOSE;
                    break;
            }

            return nextState;
        }

        @Override
        public MenuOption readUserOption() {
            int menuOption = UserInput.readMenuOption();
            MenuOption result = MenuOption.EXIT;

            switch (menuOption) {
                case 0:
                    result = MenuOption.EXIT;
                    break;
                case 1:
                    result = MenuOption.ADD;
                    break;
                case 2:
                    result = MenuOption.SCALAR_MULT;
                    break;
                case 3:
                    result = MenuOption.VECTORIAL_MULT;
                    break;
                case 4:
                    result = MenuOption.TRANSPOSE;
                    break;
                case 5:
                    result = MenuOption.DETERMINANT;
                    break;
                case 6:
                    result = MenuOption.INVERSE;
                    break;
            }
            return result;
        }
    }, TRANSPOSE {
        @Override
        public MenuState nextState() {
            UserOutput.displayTransposeMenu();
            MenuOption option = readUserOption();
            option.handle();
            return MenuState.MENU;
        }

        @Override
        public MenuOption readUserOption() {
            int menuOption = UserInput.readMenuOption();
            MenuOption result = MenuOption.EXIT;

                switch (menuOption) {
                    case 1:
                        result = MenuOption.MAIN_TRANSPOSE;
                        break;
                    case 2:
                        result = MenuOption.SIDE_TRANSPOSE;
                        break;
                    case 3:
                        result = MenuOption.VERTICAL_TRANSPOSE;
                        break;
                    case 4:
                        result = MenuOption.HORIZONTAL_TRANSPOSE;
                }

            return result;
        }
    };

    public abstract MenuOption readUserOption();
    public abstract MenuState nextState();
}

class UserOutput {

    private final static String ADD_MATRIX_MESSAGE = "1. Add matrices";
    private final static String SCALAR_MULTIPLICATION_MESSAGE = "2. Multiply matrix by a constant";
    private final static String VECTORIAL_MULTIPLICATION_MESSAGE = "3. Multiply matrices";
    private final static String TRANSPOSE_MESSAGE = "4. Transpose matrix";
    private final static String DETERMINANT_MESSAGE = "5. Calculate a determinant";
    private final static String INVERSE_MESSAGE = "6. Inverse matrix";
    private final static String EXIT_MESSAGE = "0. Exit";
    private final static String RESULT_MESSAGE = "The result is:";
    private final static String MAIN_DIAGONAL_TRANSPOSE_MESSAGE = "1. Main diagonal";
    private final static String SIDE_DIAGONAL_TRANSPOSE_MESSAGE = "2. Side diagonal";
    private final static String VERTICAL_TRANSPOSE_MESSAGE = "3. Vertical line";
    private final static String HORIZONTAL_MESSAGE = "4. Horizontal line";
    private final static String DETERMINANT_RESULT_MESSAGE = "The result is:%n%f%n";

    public static void displayMenu() {

        System.out.println(ADD_MATRIX_MESSAGE);
        System.out.println(SCALAR_MULTIPLICATION_MESSAGE);
        System.out.println(VECTORIAL_MULTIPLICATION_MESSAGE);
        System.out.println(TRANSPOSE_MESSAGE);
        System.out.println(DETERMINANT_MESSAGE);
        System.out.println(INVERSE_MESSAGE);
        System.out.println(EXIT_MESSAGE);
    }

    public static void displayTransposeMenu() {
        System.out.println(MAIN_DIAGONAL_TRANSPOSE_MESSAGE);
        System.out.println(SIDE_DIAGONAL_TRANSPOSE_MESSAGE);
        System.out.println(VERTICAL_TRANSPOSE_MESSAGE);
        System.out.println(HORIZONTAL_MESSAGE);
    }

    public static void displayResult(Matrix matrix) {
        System.out.println(RESULT_MESSAGE);

        printMatrix(matrix);
    }

    public static void printMatrix(Matrix matrix) {
        printFloatMatrix(matrix);
    }

    private static void printIntegerMatrix(Matrix matrix) {

        for (int i = 0; i < matrix.getRow(); i++) {
            for (int j = 0; j < matrix.getCol(); j++) {
                System.out.printf("%d ", (int) matrix.getMatrix()[i][j]);
            }
            System.out.println();
        }
    }

    private static void printFloatMatrix(Matrix matrix) {

        for (int i = 0; i < matrix.getRow(); i++) {
            for (int j = 0; j < matrix.getCol(); j++) {
                System.out.printf("%-6.2f ", matrix.getMatrix()[i][j]);
            }
            System.out.println();
        }
    }

    public static void displayDeterminant(double det) {
        System.out.printf(DETERMINANT_RESULT_MESSAGE,det);
    }
}

class UserInput {

    private final static String CHOICE_MESSAGE = "Your Choice:";
    private final static String MATRIX_SIZE_MESSAGE = "Enter size of matrix:";
    private final static String FIRST_MATRIX_SIZE_MESSAGE = "Enter size of first matrix:";
    private final static String SECOND_MATRIX_SIZE_MESSAGE = "Enter size of second matrix:";
    private final static String MATRIX_MESSAGE = "Enter matrix:";
    private final static String FIRST_MATRIX_MESSAGE = "Enter first matrix:";
    private final static String SECOND_MATRIX_MESSAGE = "Enter second matrix:";
    private final static String SCALAR_MESSAGE = "Enter constant:";

    private static boolean isInteger = true;

    public static boolean isIsInteger() {
        return isInteger;
    }

    private static Matrix readMatrix(int row, int col) {
        Scanner scanner = new Scanner(System.in);

        double[][] inputMatrix = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                inputMatrix[i][j] = scanner.nextFloat();
                //Checks if all the values are integer
                isInteger &= (inputMatrix[i][j] / (int) inputMatrix[i][j]) == 1f;
            }

        }

        Matrix matrix = new Matrix(row,col);

        matrix.loadMatrix(inputMatrix);

        return matrix;
    }

    private static Matrix readInputMatrix(String sizeMessage, String matrixMessage) {
        System.out.print(sizeMessage);
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        System.out.print(matrixMessage);

        return readMatrix(row, col);
    }

    public static Matrix readMatrix() {
        return readInputMatrix(MATRIX_SIZE_MESSAGE, MATRIX_MESSAGE);
    }

    public static Matrix readFirstMatrix() {
        return readInputMatrix(FIRST_MATRIX_SIZE_MESSAGE, FIRST_MATRIX_MESSAGE);
    }

    public static Matrix readSecondMatrix() {
        return readInputMatrix(SECOND_MATRIX_SIZE_MESSAGE, SECOND_MATRIX_MESSAGE);
    }

    public static int readScalar() {
        System.out.print(SCALAR_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static int readMenuOption() {
        System.out.print(CHOICE_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}

class Matrix {

    private final static String NOT_INVERSE_MESSAGE = "This matrix doesn't have an inverse.";
    private double[][] matrix;

    protected Matrix(int row, int col) {
        this.matrix = new double[row][col];
    }



    public double[][] getMatrix() {
        return this.matrix;
    }

    public void loadMatrix(double[][] inputMatrix) {
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                this.matrix[i][j] = inputMatrix[i][j];
            }
        }
    }

    public void add(Matrix b) throws DimensionException{

        if (this.getRow() != b.getRow() || this.getCol() != b.getCol()) {
            throw new DimensionException();
        }

        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                this.matrix[i][j] += b.matrix[i][j];
            }
        }

    }

    public void scalarMultiplication(double scalar) {
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                this.matrix[i][j] *= scalar;
            }
        }
    }

    public void vectorialMultiplication(Matrix b) throws DimensionException {
        if (this.getCol() != b.getRow()) {
            throw new DimensionException();
        }

        double[][] result = new double[this.getRow()][b.getCol()];

        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < b.getCol(); j++) {
                for (int k = 0; k < getCol(); k++) {
                    result[i][j] += (this.matrix[i][k] * b.matrix[k][j]);
                }
            }
        }
        this.matrix = result;
    }

    public int getRow() {
        return matrix.length;
    }

    public int getCol() {
        return matrix.length > 0 ? matrix[0].length : 0;
    }

    public void transposeMain() {
        for (int i = 0; i < getRow(); i++) {
            for (int j = i + 1; j < getCol(); j++) {
                double swap = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = swap;
            }
        }
    }

    public void transposeSide() {
        for (int i = 0; i < getRow() - 1; i++) {
            for (int j = 0; j < getCol() - 1 - i; j++) {
                double swap = getMatrix()[i][j];


                getMatrix()[i][j] = getMatrix()[getCol() - 1 - j][getRow() - 1 - i];
                getMatrix()[getCol() - 1 - j][getRow() - 1 - i] = swap;
            }
        }
    }

    public void transposeHorizontal() {
        int rowLine = getRow() / 2;
        for (int i = 0; i < rowLine; i++) {
            for (int j = 0; j < getCol(); j++) {
                double swap = getMatrix()[i][j];

                int reflectionRow = getRow() - i - 1;
                getMatrix()[i][j] = getMatrix()[reflectionRow][j];
                getMatrix()[reflectionRow][j] = swap;
            }
        }
    }

    public void transposeVertical() {
        int colLine = getCol() / 2;
        for (int j = 0; j < colLine; j++) {
            for (int i = 0; i < getRow(); i++) {

                double swap = getMatrix()[i][j];

                int reflectionCol = getCol() - j - 1;
                getMatrix()[i][j] = getMatrix()[i][reflectionCol];
                getMatrix()[i][reflectionCol] = swap;
            }
        }
    }

    public double determinant() {
        return determinant(getMatrix());
    }

    private double determinant(double[][] mat) {
        int n = mat.length;

        if (n == 2) {
            return mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1];
        }
        if (n == 1) {
            return mat[0][0];
        }

        double det = 0;

        for (int i = 0; i < n ; i++) {
            double[][] subM = getSubM(mat, 0, i);
            double minimal = Math.pow(-1, 1 + i + 1);

            det += mat[0][i] * minimal * determinant(subM);
        }

        return det;
    }

    public void inverse() throws DimensionException {
        int n = getMatrix().length;
        double det = determinant();

        if (!isSquare() || det == 0.0d) {
            throw new DimensionException(NOT_INVERSE_MESSAGE);
        }

        double scalar = 1/det;

        this.loadMatrix(cofactors(getMatrix()));
        this.transposeMain();
        this.scalarMultiplication(scalar);

    }

    private double[][] cofactors(double[][] mat) {
        int n = mat.length;

        double[][] cofactors = new double[n][n];

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n; j++) {
                double[][] subM = getSubM(mat, i, j);
                double minimal = Math.pow(-1, i + 1 + j + 1);

                cofactors[i][j] = minimal * determinant(subM);
            }
        }

        return cofactors;
    }

    private double[][] getSubM(double[][] mat, int row, int col) {
        int n = mat.length - 1;
        double[][] subM = new double[n][n];
        int actualRow = 0;

        for (int i = 0; i < mat.length; i++) {
            if (row != i) {

                int actualCol = 0;
                for (int j = 0; j < mat.length; j++) {

                    if (j != col) {
                        subM[actualRow][actualCol] = mat[i][j];
                        actualCol++;
                    }
                }
                actualRow++;
            }
        }
        return subM;
    }

    private boolean isSquare() {
        return getRow() == getCol();
    }
}

public class Main {

    public static void main(String[] args) {
        MenuState state = MenuState.MENU;

        while (state != MenuState.EXIT) {
            state = state.nextState();
        }
    }
}