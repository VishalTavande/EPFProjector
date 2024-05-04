package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.metadata.ColumnMetadata;
import org.vishalta.epfcalculator.metadata.Type;
import org.vishalta.epfcalculator.model.Balance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.generate;

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
    public void printEPFProjection(List<Balance> epfProjectionForYear, int currentYear, double currentBalance) {
        System.out.printf("Current Balance: %15f%10sYear: %4d - %4d\n", currentBalance, generatePattern(10, ' '), currentYear, (currentYear+1));
        columnMetadataList.forEach(column -> System.out.printf("%"+column.getWidth()+"s ", generatePattern(column.getWidth())));
        System.out.println();

        columnMetadataList.forEach(column -> System.out.printf("%"+column.getWidth()+"s ", column.getName()));
        System.out.println();

        columnMetadataList.forEach(column -> System.out.printf("%"+column.getWidth()+"s ", generatePattern(column.getWidth())));
        System.out.println();

        epfProjectionForYear.stream()
                .filter(e -> e.getDate() != null)
                .forEach(
                        entry -> System.out.printf(
                                "%"+columnMetadataList.stream().filter(x -> x.getName().equals(MONTH)).findFirst().get().getWidth()+"s " +
                                "%"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYEE_SHARE)).findFirst().get().getWidth()+"f " +
                                "%"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYER_SHARE)).findFirst().get().getWidth()+"f " +
                                "%"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST_RATE)).findFirst().get().getWidth()+"f " +
                                "%"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST)).findFirst().get().getWidth()+"f " +
                                "%"+columnMetadataList.stream().filter(x -> x.getName().equals(BALANCE)).findFirst().get().getWidth()+"f\n",
                                entry.getDate().getMonth(), entry.getEmployeeShare(), entry.getEmployerShare(), entry.getInterestRate(), entry.getInterest(), entry.getBalanceAmount())
                );
        Balance lastRow = epfProjectionForYear.stream()
                .filter(e -> e.getDate() == null)
                .findFirst()
                .get();
        System.out.printf(
                "%"+columnMetadataList.stream().filter(x -> x.getName().equals(MONTH)).findFirst().get().getWidth()+"s " +
                        "%"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYEE_SHARE)).findFirst().get().getWidth()+"s " +
                        "%"+columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYER_SHARE)).findFirst().get().getWidth()+"s " +
                        "%"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST_RATE)).findFirst().get().getWidth()+"s " +
                        "%"+columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST)).findFirst().get().getWidth()+"f " +
                        "%"+columnMetadataList.stream().filter(x -> x.getName().equals(BALANCE)).findFirst().get().getWidth()+"s\n",
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(MONTH)).findFirst().get().getWidth(), ' '),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYEE_SHARE)).findFirst().get().getWidth(), ' '),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(EMPLOYER_SHARE)).findFirst().get().getWidth(), ' '),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(INTEREST_RATE)).findFirst().get().getWidth(), ' '),
                lastRow.getInterest(),
                generatePattern(columnMetadataList.stream().filter(x -> x.getName().equals(BALANCE)).findFirst().get().getWidth(), ' '));

        columnMetadataList.forEach(column -> System.out.printf("%"+column.getWidth()+"s ", generatePattern(column.getWidth())));
        System.out.println();

        columnMetadataList.forEach(column -> {
            if(column.getName().equals(INTEREST)) {
                System.out.printf("%"+column.getWidth()+"s ", BigDecimal.valueOf(epfProjectionForYear.stream().mapToDouble(Balance::getInterest).sum()).round(MATH_CONTEXT));
            } else if(column.getName().equals(BALANCE)) {
                System.out.printf("%"+column.getWidth()+"s ",
                        BigDecimal.valueOf(epfProjectionForYear.stream().mapToDouble(Balance::getInterest).sum() +
                                epfProjectionForYear.stream().filter(x -> x.getDate() != null).mapToDouble(Balance::getBalanceAmount).max().getAsDouble()).round(MATH_CONTEXT));
            } else
                System.out.printf("%"+column.getWidth()+"s ", generatePattern(column.getWidth(), ' '));
        });
        System.out.println();
        System.out.println();
    }

    private static String generatePattern(int size) {
        return generate(() -> "=")
                .limit(size)
                .collect(joining());
    }

    private static String generatePattern(int size, char ch) {
        return generate(() -> String.valueOf(ch))
                .limit(size)
                .collect(joining());
    }
}
