package com.pillar.boardfeetcalculator;

import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;

public class CalculationWrapper {

	public static final double MINIMUM_HEIGHT = 8d;
	public static final double MINIMUM_CIRCUMFERENCE = 6.5d;
	public static final String ERROR_MESSAGE = "ERROR";

	private DoyleScribnerCalculator boardFeetCalculator;
	private CircumferenceToDiameterCalculator diameterCalculator;

	public CalculationWrapper(DoyleScribnerCalculator bfCalculator, CircumferenceToDiameterCalculator cdCalculator) {
		boardFeetCalculator = bfCalculator;
		diameterCalculator = cdCalculator;
	}

	public String runCalculation(String height, String circumference) {
		String boardFeetValue;
		if (validInputs(height, circumference)) {
			boardFeetValue = calculateBoardFeet(height, circumference);
		} else {
			boardFeetValue = ERROR_MESSAGE;
		}
		return boardFeetValue;
	}

	private boolean validInputs(String height, String circumference) {
		boolean rval = false;
		try {
			rval = MINIMUM_CIRCUMFERENCE <= Double.valueOf(circumference) && MINIMUM_HEIGHT <= Double.valueOf(height);
		} catch (NumberFormatException nfe) {
			rval = false; // explicit
		}
		return rval;
	}

	private String calculateBoardFeet(String height, String circumference) {
		return Double.toString(boardFeetCalculator.calculateBoardFeet(calculateDiameter(circumference),
				Double.valueOf(height)));
	}

	private double calculateDiameter(String circumference) {
		return diameterCalculator.calculate(Double.valueOf(circumference));
	}
}
