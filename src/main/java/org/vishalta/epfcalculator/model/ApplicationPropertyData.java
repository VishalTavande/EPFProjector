package org.vishalta.epfcalculator.model;

import org.vishalta.epfcalculator.format.Formatter;

public class ApplicationPropertyData {

    private double interest;
    private double baseSalary;
    private double currentBalance;
    private double annualRaise;
    private Formatter formatter;
    private int totalYears;
    private String htmlReportName;
    private int decimalPlaces;

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getAnnualRaise() {
        return annualRaise;
    }

    public void setAnnualRaise(double annualRaise) {
        this.annualRaise = annualRaise;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public int getTotalYears() {
        return totalYears;
    }

    public void setTotalYears(int totalYears) {
        this.totalYears = totalYears;
    }

    public String getHtmlReportName() {
        return htmlReportName;
    }

    public void setHtmlReportName(String htmlReportName) {
        this.htmlReportName = htmlReportName;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
}
