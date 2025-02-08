package org.vishalta.epfcalculator.format;

import org.vishalta.epfcalculator.calculate.Calculator;
import org.vishalta.epfcalculator.model.EPFBalance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.vishalta.epfcalculator.calculate.Calculator.decimalFormat;

public class HTMLFormatter extends Formatter {

    @Override
    public void formatEPFProjection(Map<String, EPFBalance> epfProjectionForYear) {
        String fileName = "target/" + Calculator.applicationPropertyData.getHtmlReportName() + ".html";
        try (FileOutputStream fos = new FileOutputStream(fileName)){
            String htmlText = generateHtml(epfProjectionForYear);
            fos.write(htmlText.getBytes());
            System.out.println("The EPF Projection report is generated at path => " + new File(fileName).getAbsolutePath());
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
        sb.append("<body style=\"font-family:'Ubuntu', sans-serif;\">");

        epfProjectionForYear.forEach((financialYear, epfBalance) -> {
            sb.append("<br/>");
            sb.append("<table><tr>");
            sb.append("<td><h3>Current Balance: </h3></td>");
            sb.append("<td style=\"padding-bottom: 17px\">").append(decimalFormat.format(epfBalance.getPreviousYearBalance())).append("</td>");
            sb.append("<td/>");
            sb.append("<td><h3>Year: </h3></td>");
            sb.append("<td style=\"padding-bottom: 17px\">").append(financialYear).append("</td>");
            sb.append("</tr></table>");

            sb.append("<table>");
            sb.append("<tbody>");
            sb.append("<tr style=\"height: 21px; background-color: #D6D4D4\">");
            encloseTD(sb, "Month");
            encloseTD(sb, "Employee Share");
            encloseTD(sb, "Employer Share");
            encloseTD(sb, "Interest Rate");
            encloseTD(sb, "Interest");
            encloseTD(sb, "Balance");
            sb.append("</tr>");

            epfBalance.getBalanceList().forEach(balance -> {
                sb.append("<tr style=\"height: 21px;").append(balance.getDate() != null ? "font-weight: lighter;" : "").append("\">");
                encloseTD(sb, balance.getDate() != null ? balance.getDate().getMonth().toString() : "");
                encloseTD(sb, balance.getEmployeeShare() == 0 ? "" : decimalFormat.format(balance.getEmployeeShare()));
                encloseTD(sb, balance.getEmployerShare() == 0 ? "" : decimalFormat.format(balance.getEmployerShare()));
                encloseTD(sb, balance.getInterestRate() == 0 ? "" : decimalFormat.format(balance.getInterestRate()));
                encloseTD(sb, balance.getInterest() == 0 ? "" : decimalFormat.format(balance.getInterest()));
                encloseTD(sb, balance.getBalanceAmount() == 0 ? "" : decimalFormat.format(balance.getBalanceAmount()));
                sb.append("</tr>");
            });

            sb.append("</tbody>");
            sb.append("</table>");
        });

        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    private void encloseTD(StringBuilder sb, String data) {
        sb.append("<td style=\"width: 151.5px; height: 21px;\"><strong>&nbsp;").append(data).append("</strong></td>");
    }
}
