package org.vishalta.epfcalculator;

import org.vishalta.epfcalculator.calculate.Calculator;

public class Launcher {
	public static void main(String[] args) {
		Calculator epfCalculator = new Calculator();
		epfCalculator.calculate();
	}

}