package org.example.demo5;

public class FullTimeEmployee extends Employee {
    private double annualSalary;

    public FullTimeEmployee(String name, double annualSalary) {
        super(name);
        this.annualSalary = annualSalary;
    }

    @Override
    public double calculateSalary() {
        return annualSalary / 12; // Monthly salary
    }
}
