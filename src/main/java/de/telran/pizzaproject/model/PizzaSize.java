package de.telran.pizzaproject.model;

public enum PizzaSize {
    SMALL(12),
    MEDIUM(20),
    LARGE(28);

    int size;

    PizzaSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PizzaSize{" +
                "size=" + size +
                '}';
    }
}
