package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.metadata.ColumnMetadata;
import org.vishalta.epfcalculator.metadata.Type;
import org.vishalta.epfcalculator.model.Balance;
import org.vishalta.epfcalculator.model.EPFBalance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.vishalta.epfcalculator.util.Utils.generatePattern;

public class TabularFormatter extends Formatter {

    public static final MathContext MATH_CONTEXT = new MathContext(10);
    private static final List<ColumnMetadata> columnMetadataList;

    private static final String MONTH = "Month";
    private static final String EMPLOYEE_SHARE = "Employee Share";
    private static final String EMPLOYER_SHARE = "Employer Share";
    private static final String INTEREST_RATE = "Interest Rate";
    private static final String INTEREST = "Interest";
    private static final String BALANCE = "Balance";

    static {
        columnMetadataList = Arrays.asList(
                new ColumnMetadata(MONTH, 9, Type.CHARACTER),
                new ColumnMetadata(EMPLOYEE_SHARE, 20, Type.FLOATING),
                new ColumnMetadata(EMPLOYER_SHARE, 15, Type.FLOATING),
                new ColumnMetadata(INTEREST_RATE, 13, Type.CHARACTER),
                new ColumnMetadata(INTEREST, 17, Type.CHARACTER),
                new ColumnMetadata(BALANCE, 17, Type.CHARACTER)
        );
    }

    @Override
    public void formatEPFProjection(Map<String, EPFBalance> epfBalanceMap) {
        epfBalanceMap.entrySet().forEach(this::accept);
    }

    private void accept(Map.Entry<String, EPFBalance> entry) {
        String financialYear = entry.getKey();
        EPFBalance epfBalance = entry.getValue();
        formatData(epfBalance.getBalanceList(), financialYear, epfBalance.getPreviousYearBalance());
    }

    private void formatData(List<Balance> epfProjectionForYear, String financialYear, double currentBalance) {
        NumberFormat numberFormatter = getNumberFormatter();
        System.out.printf("Current Balance: %15s%10sYear: %4s%n", numberFormatter.format(currentBalance), generatePattern(10, ' '), financialYear);
        generateDoubleDashedLine("|%n");

        columnMetadataList.forEach(column -> System.out.printf("| %"+column.getWidth()+"s ", column.getName()));
        System.out.printf("|%n");

        generateDoubleDashedLine("|%n");

        epfProjectionForYear.stream()
                .filter(e -> e.getDate() != null)
                .forEach(
                        entry -> System.out.printf(
                                "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(MONTH)).findFirst().get().getWidth()+"s " +
                                "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYEE_SHARE)).findFirst().get().getWidth()+"s " +
                                "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYER_SHARE)).findFirst().get().getWidth()+"s " +
                                "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST_RATE)).findFirst().get().getWidth()+"f " +
                                "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST)).findFirst().get().getWidth()+"s " +
                                "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(BALANCE)).findFirst().get().getWidth()+"s |%n",
                                entry.getDate().getMonth(), numberFormatter.format(entry.getEmployeeShare()), numberFormatter.format(entry.getEmployerShare()), entry.getInterestRate(), numberFormatter.format(entry.getInterest()), numberFormatter.format(entry.getBalanceAmount()))
                );
        Balance lastRow = epfProjectionForYear.stream()
                .filter(e -> e.getDate() == null)
                .findFirst()
                .get();
        System.out.printf(
                "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(MONTH)).findFirst().get().getWidth()+"s " +
                        "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYEE_SHARE)).findFirst().get().getWidth()+"s " +
                        "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYER_SHARE)).findFirst().get().getWidth()+"s " +
                        "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST_RATE)).findFirst().get().getWidth()+"s " +
                        "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST)).findFirst().get().getWidth()+"s " +
                        "| %"+columnMetadataList.stream().filter(x -> x.getName().equals(BALANCE)).findFirst().get().getWidth()+"s |%n",
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(MONTH)).findFirst().get().getWidth(), ' '),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYEE_SHARE)).findFirst().get().getWidth(), ' '),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYER_SHARE)).findFirst().get().getWidth(), ' '),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST_RATE)).findFirst().get().getWidth(), ' '),
                numberFormatter.format(lastRow.getInterest()),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(BALANCE)).findFirst().get().getWidth(), ' '));

        generateDoubleDashedLine("|%n");

        columnMetadataList.forEach(column -> {
            if(column.getName().equals(INTEREST)) {
                System.out.printf("| %"+column.getWidth()+"s ", numberFormatter.format(BigDecimal.valueOf(epfProjectionForYear.stream().mapToDouble(Balance::getInterest).sum()).round(MATH_CONTEXT)));
            } else if(column.getName().equals(BALANCE)) {
                System.out.printf("| %"+column.getWidth()+"s ",
                        numberFormatter.format(BigDecimal.valueOf(epfProjectionForYear.stream().mapToDouble(Balance::getInterest).sum() +
                                epfProjectionForYear.stream().filter(x -> x.getDate() != null).mapToDouble(Balance::getBalanceAmount).max().getAsDouble()).round(MATH_CONTEXT)));
            } else
                System.out.printf("| %"+column.getWidth()+"s ", generatePattern(column.getWidth(), ' '));
        });
        System.out.printf("|%n");
        generateDoubleDashedLine("|%n%n%n");
    }

    private static void generateDoubleDashedLine(String format) {
        columnMetadataList.forEach(column -> System.out.printf("| %" + column.getWidth() + "s ", generatePattern(column.getWidth())));
        System.out.printf(format);
    }

}
