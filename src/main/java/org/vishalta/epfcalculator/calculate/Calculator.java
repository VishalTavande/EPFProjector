package org.vishalta.epfcalculator.calculate;

import org.vishalta.epfcalculator.model.ApplicationPropertyData;
import org.vishalta.epfcalculator.model.Balance;
import org.vishalta.epfcalculator.model.EPFBalance;
import org.vishalta.epfcalculator.util.Utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Calculator {

    public static final ApplicationPropertyData applicationPropertyData = PropertyLoader.loadProperties();
    public static DecimalFormat decimalFormat = new DecimalFormat("#." + Utils.generatePattern(applicationPropertyData.getDecimalPlaces(), '#'));

    public Calculator() {

    }

    public void calculate() {
        int currentYear = LocalDate.now().getYear();
        int totalYears = applicationPropertyData.getTotalYears();
        Map<String, EPFBalance> epfProjectionPerYearMap = new LinkedHashMap<>(totalYears);
        double currentBalance = applicationPropertyData.getCurrentBalance();
        while(totalYears-- > 0) {
            List<Balance> epfProjectionForYear = getEPFProjectionForYear(currentYear);
            EPFBalance epfBalance = new EPFBalance(currentBalance, epfProjectionForYear);
            String financialYear = currentYear + " - " + (currentYear + 1);
            epfProjectionPerYearMap.put(financialYear, epfBalance);
            currentYear++;
            currentBalance = epfBalance.getBalanceList().stream().filter(x -> x.getDate() == null).findFirst().get().getBalanceAmount();
            applicationPropertyData.setBaseSalary(applicationPropertyData.getBaseSalary() + applicationPropertyData.getBaseSalary() * applicationPropertyData.getAnnualRaise() / 100);
        }

        applicationPropertyData.getFormatter().formatEPFProjection(epfProjectionPerYearMap);
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
        double totalBalanceForYear = balance + balanceList.stream().mapToDouble(Balance::getInterest).sum();
        balanceList.add(new Balance(null, 0, 0, 0, interest, interest+totalBalanceForYear));
        applicationPropertyData.setCurrentBalance(balance + balanceList.stream().mapToDouble(Balance::getInterest).sum());
        return balanceList;
    }

}
