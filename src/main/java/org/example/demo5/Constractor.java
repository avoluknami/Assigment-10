package org.example.demo5;

public class Constractor extends Employee {
    private double hourlyRate;
    private int maxHours;

    public Constractor(String name, double hourlyRate, int maxHours) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.maxHours = maxHours;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * maxHours;
    }
}
