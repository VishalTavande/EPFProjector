package org.vishalta.epfcalculator.model;

import java.time.LocalDate;

public class Balance {

    private LocalDate date;
    private double employeeShare;
    private double employerShare;
    private float interestRate;
    private double interest;
    private double balanceAmount;

    public Balance(LocalDate date, double employeeShare, double employerShare, float interestRate, double interest, double balanceAmount) {
        this.date = date;
        this.employeeShare = employeeShare;
        this.employerShare = employerShare;
        this.interestRate = interestRate;
        this.interest = interest;
        this.balanceAmount = balanceAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getEmployeeShare() {
        return employeeShare;
    }

    public double getEmployerShare() {
        return employerShare;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public double getInterest() {
        return interest;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "date=" + date +
                ", employeeShare=" + employeeShare +
                ", employerShare=" + employerShare +
                ", interestRate=" + interestRate +
                ", interest=" + interest +
                ", balanceAmount=" + balanceAmount +
                '}';
    }
}
