package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.calculate.Calculator;
import org.vishalta.epfcalculator.model.EPFBalance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class HTMLFormatter extends Formatter {

    @Override
    public void formatEPFProjection(Map<String, EPFBalance> epfProjectionForYear) {
        String fileName = Calculator.applicationPropertyData.getHtmlReportName() + ".html";
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            String htmlText = generateHtml(epfProjectionForYear);
            fos.write(htmlText.getBytes());
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find " + fileName);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateHtml(Map<String, EPFBalance> epfProjectionForYear) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head></head>");
        sb.append("<body>");

        epfProjectionForYear.entrySet().forEach(entry -> {
            String financialYear = entry.getKey();
            EPFBalance epfBalance = entry.getValue();
            sb.append("<h1>Current Balance: " + epfBalance.getPreviousYearBalance() + "\tYear: " + financialYear + "</h1><break/>");
            sb.append("<table>");
            sb.append("<tbody>");
            sb.append("<tr style=\"height: 21px;\">");
            encloseTD(sb, "Month");
            encloseTD(sb, "Employee Share");
            encloseTD(sb, "Employer Share");
            encloseTD(sb, "Interest Rate");
            encloseTD(sb, "Interest");
            encloseTD(sb, "Balance");
            sb.append("</tr>");

            epfBalance.getBalanceList().forEach(balance -> {
                sb.append("<tr style=\"height: 21px;\">");
                encloseTD(sb, balance.getDate() != null ? balance.getDate().getMonth().toString() : "" );
                encloseTD(sb, String.valueOf(balance.getEmployeeShare()));
                encloseTD(sb, String.valueOf(balance.getEmployerShare()));
                encloseTD(sb, String.valueOf(balance.getInterestRate()));
                encloseTD(sb, String.valueOf(balance.getInterest()));
                encloseTD(sb, String.valueOf(balance.getBalanceAmount()));
                sb.append("</tr>");
            });

            sb.append("</tbody>");
            sb.append("</table>");
        });

        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    private StringBuilder encloseTD(StringBuilder sb, String data) {
        return sb.append("<td style=\"width: 151.5px; height: 21px;\"><strong>&nbsp;" + data + "</strong></td>");
    }
}
