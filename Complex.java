public class Complex {
    double real;
    double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }
    public Complex add(Complex other) {
        return new Complex(real + other.real, imaginary + other.imaginary);
    }
    public Complex multiply(Complex other) {
        double realPart = real * other.real - imaginary * other.imaginary;
        double imagPart = real * other.imaginary + imaginary * other.real;
        return new Complex(realPart, imagPart);
    }
    public Complex divide(Complex other) {
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double realPart = (real * other.real + imaginary * other.imaginary) / denominator;
        double imagPart = (imaginary * other.real - real * other.imaginary) / denominator;
        return new Complex(realPart, imagPart);
    }
    public boolean isZero() {
        return Math.abs(real) < 1e-10 && Math.abs(imaginary) < 1e-10;
    }
    public String toString() {
        double realPart = real;
        if (Math.abs(real) < 1e-10) {
            realPart = 0;
        }
        double imagPart = imaginary;
        if (Math.abs(imaginary) < 1e-10) {
            imagPart = 0;
        }
        if (imagPart == 0) {
            return String.format("%.2f", realPart);
        }
        if (realPart == 0) {
            return String.format("%.2fi", imagPart);
        }
        if (imagPart < 0) {
            return String.format("%.2f - %.2fi", realPart, -imagPart);
        }
        return String.format("%.2f + %.2fi", realPart, imagPart);
    }
}
