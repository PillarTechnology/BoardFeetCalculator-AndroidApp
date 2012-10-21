package com.pillar.doylescribner;

public class Calculator {

	public static final String DIAMETER_EX_MSG = "Diameter must be a positive number of inches";
	public static final String LENGTH_EX_MSG = "Length must be a positive number of feet";

	public double calculateBoardFeet(double diameter, double length) {
		if ( diameter < 0.0d ) {
			throw new IllegalArgumentException(DIAMETER_EX_MSG);
		}
		if ( length < 0.0d ) {
			throw new IllegalArgumentException(LENGTH_EX_MSG);
		}
		
		return Math.pow((diameter - 4.0d) / 4.0, 2.0d) * length;
	}

}
