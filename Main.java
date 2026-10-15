import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US); 
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n1 - Сложение");
            System.out.println("2 - Умножение");
            System.out.println("3 - Деление A на B");
            System.out.println("4 - Транспонирование");
            System.out.println("5 - Определитель");
            System.out.println("0 - Выход");
            System.out.print("Выберите действие: ");

            if (!input.hasNextInt()) {
                System.out.println("Введите номер операции.");
                input.next();
                continue;
            }

            int action = input.nextInt();
            if (action == 0) {
                break;
            }

            try {
                if (action == 1 || action == 2 || action == 3) {
                    System.out.println("Первая матрица:");
                    ComplexMatrix first = readMatrix(input);
                    System.out.println("Вторая матрица:");
                    ComplexMatrix second = readMatrix(input);

                    if (action == 1) {
                        first.add(second).print();
                    } else if (action == 2) {
                        first.multiply(second).print();
                    } else {
                        first.divide(second).print();
                    }
                } else if (action == 4) {
                    ComplexMatrix first = readMatrix(input);
                    first.transpose().print();
                } else if (action == 5) {
                    ComplexMatrix first = readMatrix(input);
                    System.out.println("Определитель: " + first.determinant());
                } else {
                    System.out.println("Нет такой операции.");
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        input.close();
    }

    private static int readInt(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.print("Введите целое число: ");
            input.next();
        }
        return input.nextInt();
    }

    private static double readDouble(Scanner input) {
        while (!input.hasNextDouble()) {
            System.out.print("Введите число (дробную часть отделяйте точкой): ");
            input.next();
        }
        return input.nextDouble();
    }

    private static ComplexMatrix readMatrix(Scanner input) {
        System.out.print("Количество строк: ");
        int rowCount = readInt(input);
        System.out.print("Количество столбцов: ");
        int colCount = readInt(input);

        ComplexMatrix matrix = new ComplexMatrix(rowCount, colCount);

        System.out.println("Для каждого элемента введите действительную и мнимую части.");
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                System.out.print("Элемент [" + (i + 1) + "][" + (j + 1) + "]: ");
                double real = readDouble(input);
                double imagPart = readDouble(input);
                matrix.set(i, j, new Complex(real, imagPart));
            }
        }
        return matrix;
    }
}
