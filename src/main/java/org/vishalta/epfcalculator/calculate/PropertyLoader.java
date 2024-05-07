package org.vishalta.epfcalculator.calculate;

import org.vishalta.epfcalculator.format.Formatter;
import org.vishalta.epfcalculator.model.ApplicationPropertyData;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private static Properties properties;

    public static ApplicationPropertyData loadProperties() {
        ApplicationPropertyData applicationPropertyData = new ApplicationPropertyData();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();
            properties.load(is);
            applicationPropertyData.setInterest(Double.parseDouble(getProperty("interest", "6")));
            double baseSalary = Double.parseDouble(getProperty("annual_base_salary", "0"));
            if(baseSalary == Double.valueOf(0)) {
                baseSalary = Double.parseDouble(getProperty("annual_provident_fund", "0")) * 100 / 12;
            }
            if(baseSalary <= 0d) {
                throw new RuntimeException("annual_base_salary or annual_provident_fund should have some valid value");
            }
            applicationPropertyData.setBaseSalary(baseSalary);
            applicationPropertyData.setAnnualRaise(Double.parseDouble(getProperty("annual_base_salary_raise", "5")));
            applicationPropertyData.setCurrentBalance(Double.parseDouble(getProperty("current_balance", "0")));
            applicationPropertyData.setTotalYears(Integer.parseInt(getProperty("number_of_years", "5")));
            applicationPropertyData.setFormatter(Formatter.getInstance(getProperty("formatter", "TabularFormatter")));
            applicationPropertyData.setHtmlReportName(getProperty("html_report_name", "EPF_Projection"));
        } catch (IOException e) {
            System.err.println("Unable to read the application.properties file");
            throw new RuntimeException(e);
        }
        return applicationPropertyData;
    }

    private static String getProperty(String propertyName, String defaultValue) {
        String value = properties.getProperty(propertyName, defaultValue);
        return value.isEmpty() ? defaultValue : value;
    }
}
