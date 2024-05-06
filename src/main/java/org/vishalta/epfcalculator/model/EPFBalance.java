package org.vishalta.epfcalculator.model;

import java.util.List;

public class EPFBalance {

    private double previousYearBalance;
    private List<Balance> balanceList;

    public EPFBalance(double previousYearBalance, List<Balance> balanceList) {
        this.previousYearBalance = previousYearBalance;
        this.balanceList = balanceList;
    }

    public double getPreviousYearBalance() {
        return previousYearBalance;
    }

    public List<Balance> getBalanceList() {
        return balanceList;
    }
}
