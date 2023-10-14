package edu.hw2;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public sealed interface Expr {
    double evaluate();

    public record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }

        public String toString() {
            return Double.toString(evaluate());
        }
    }

    public record Negate(Expr expr) implements Expr {
        @Override
        public double evaluate() {
            return (-1.0) * expr.evaluate();
        }

        public String toString() {
            return "(-1) * " + Double.toString(expr.evaluate());
        }
    }

    public record Exponent(Expr base, Expr exponent) implements Expr {
        @Override
        public double evaluate() throws IllegalArgumentException {
            double baseValue = base.evaluate();
            if (baseValue <= 0) {
                throw new IllegalArgumentException("В вещественную степень можно возводить только положительное число");
            }
            double exponentValue = exponent.evaluate();
            return pow(baseValue, exponentValue);
        }

        public String toString() {
            return Double.toString(base.evaluate()) + " ** "
                + Double.toString(exponent.evaluate());
        }
    }

    public record Addition(Expr add1, Expr add2) implements Expr {
        @Override
        public double evaluate() {
            return add1.evaluate() + add2.evaluate();
        }
        public String toString() {
            return Double.toString(add1.evaluate()) + " + "
                + Double.toString(add2.evaluate());
        }
    }

    public record Multiplication(Expr mul1, Expr mul2) implements Expr {
        @Override
        public double evaluate() {
            return mul1.evaluate() * mul2.evaluate();
        }
        public String toString() {
            return Double.toString(mul1.evaluate()) + " * "
                + Double.toString(mul2.evaluate());
        }
    }
}
