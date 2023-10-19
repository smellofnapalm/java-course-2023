package edu.hw3.task6;

public class Stock {
    private double value = 0.0;
    final String companyName;

    Stock(double value, String companyName) {
        if (value < 0 || companyName == null) {
            throw new IllegalArgumentException("Стоимость акции не может "
                + "быть отрицательна, название компании должно быть валидным");
        }
        this.companyName = companyName;
        this.value = value;
    }

    public void setValue(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Стоимость акции не может быть отрицательной!");
        }
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
