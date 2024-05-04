package org.vishalta.epfcalculator.calculate;

import org.vishalta.epfcalculator.model.ApplicationPropertyData;
import org.vishalta.epfcalculator.model.Balance;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

    private final ApplicationPropertyData applicationPropertyData;

    public Calculator() {
        applicationPropertyData = PropertyLoader.loadProperties();
    }

    public void calculate() {
        int currentYear = LocalDate.now().getYear();
        int totalYears = applicationPropertyData.getTotalYears();
        while(totalYears-- > 0) {
            double currentBalance = applicationPropertyData.getCurrentBalance();
            List<Balance> epfProjectionForYear = getEPFProjectionForYear(currentYear);
            applicationPropertyData.getFormatter().printEPFProjection(epfProjectionForYear, currentYear, currentBalance);
            currentYear++;
            applicationPropertyData.setBaseSalary(applicationPropertyData.getBaseSalary() + applicationPropertyData.getBaseSalary() * applicationPropertyData.getAnnualRaise() / 100);
        }
    }

    private List<Balance> getEPFProjectionForYear(int year) {
        List<Balance> balanceList = new ArrayList<>(12);
        double balance = 0;
        double interest;
        List<Month> months = Arrays.asList(Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY, Month.MARCH);
        for (Month month : months) {
            int currYear = Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH).contains(month) ? year + 1 : year;
            LocalDate date = LocalDate.of(currYear, month, 15);
            double employeeShare = applicationPropertyData.getBaseSalary() * 0.12 / 12;
            double employerShare = employeeShare - 1250;
            interest = month.equals(Month.APRIL) ? 0 : applicationPropertyData.getInterest() / 12 * balance / 100;
            balance = month.equals(Month.APRIL) ? applicationPropertyData.getCurrentBalance() + employeeShare + employerShare : balance + employeeShare + employerShare;

            balanceList.add(new Balance(date, employeeShare, employerShare, (float) applicationPropertyData.getInterest(), interest, balance));
        }
        interest = applicationPropertyData.getInterest() / 12 * balance / 100;
        balanceList.add(new Balance(null, 0, 0, 0, interest, 0));
        applicationPropertyData.setCurrentBalance(balance + balanceList.stream().mapToDouble(Balance::getInterest).sum());
        return balanceList;
    }

}
