package com.pillar.doylescribner;


public class CircumferenceToDiameterCalculator {

	public static final String ILLEGAL_ARG_EX_MSG = "A circumference must be positive";

	public double calculate(double circumference) {
		if ( circumference < 0.0d ) {
			throw new IllegalArgumentException(ILLEGAL_ARG_EX_MSG);
		}
		return (circumference / Math.PI);
	}

}
