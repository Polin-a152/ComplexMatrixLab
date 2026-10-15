public class ComplexMatrix {
    private Complex[][] data;
    private int rows;
    private int cols;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры должны быть больше нуля");
        }
        this.rows = rows;
        this.cols = cols;
        data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }
    public void set(int i, int j, Complex value) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            throw new IllegalArgumentException("Индекс за пределами матрицы");
        }
        data[i][j] = value;
    }
    public ComplexMatrix add(ComplexMatrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Для сложения размеры должны совпадать");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }
    public ComplexMatrix multiply(ComplexMatrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException(
                    "Число столбцов первой матрицы должно равняться числу строк второй");
        }
        ComplexMatrix result = new ComplexMatrix(rows, other.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex total = new Complex(0, 0);
                for (int k = 0; k < cols; k++) {
                    total = total.add(data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j] = total;
            }
        }
        return result;
    }
    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = data[i][j];
            }
        }
        return result;
    }
    private ComplexMatrix minor(int row, int col) {
        ComplexMatrix result = new ComplexMatrix(rows - 1, cols - 1);
        int minorRow = 0;
        for (int r = 0; r < rows; r++) {
            if (r == row) continue;
            int minorCol = 0;
            for (int c = 0; c < cols; c++) {
                if (c == col) continue;
                result.data[minorRow][minorCol] = data[r][c];
                minorCol++;
            }
            minorRow++;
        }
        return result;
    }
    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель есть только у квадратной матрицы");
        }
        if (rows == 1) {
            return data[0][0];
        }
        Complex detValue = new Complex(0, 0);
        for (int j = 0; j < cols; j++) {
            Complex term = data[0][j].multiply(minor(0, j).determinant());
            if (j % 2 == 0) {
                detValue = detValue.add(term);
            } else {
                detValue = detValue.add(new Complex(-1, 0).multiply(term));
            }
        }
        return detValue;
    }
    public ComplexMatrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }
        Complex detValue = determinant();
        if (detValue.isZero()) {
            throw new ArithmeticException("Определитель равен нулю, обратной матрицы нет");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        if (rows == 1) {
            result.data[0][0] = new Complex(1, 0).divide(data[0][0]);
            return result;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Complex cofactor = minor(i, j).determinant();
                if ((i + j) % 2 != 0) {
                    cofactor = new Complex(-1, 0).multiply(cofactor);
                }
                result.data[j][i] = cofactor.divide(detValue);
            }
        }
        return result;
    }
    public ComplexMatrix divide(ComplexMatrix other) {
        return multiply(other.inverse());
    }
    public void print() {
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                System.out.printf("%-16s", data[i][j].toString());
            }
            System.out.println("|");
        }
    }
}
