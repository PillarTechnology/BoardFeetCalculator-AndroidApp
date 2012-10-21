package com.pillar.doylescribner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

	/*
	 * Doyle-Scribner Rule according to
	 * http://www.sizes.com/units/doyles_rule.htm
	 * 
	 * board_feet = (((D - 4) / 4) ^ 2 ) * L
	 * 
	 * D = diameter in inches L = length in feet
	 */
	private Calculator calculator;

	@Before
	public void setup() {
		calculator = new Calculator();
	}

	private void calculate(double diameter, double length, double expected) {
		assertThat(calculator.calculateBoardFeet(diameter, length),
				is(expected));
	}

	@Test
	public void shouldReturnZeroForZeroLength() {
		calculate(0.0d, 0.0d, 0.0d);
	}

	@Test
	public void shouldReturnLengthForZeroDiameter() {
		calculate(0.0d, 1.0d, 1.0d);
	}

	@Test
	public void shouldThrowExceptionIfDiameterIsLessThanZero() {
		try {
			calculate(-0.1d, 1.0d, 0.0d);
			fail("None shall pass!");
		} catch (IllegalArgumentException iae) {
			assertThat(iae.getMessage(), is(Calculator.DIAMETER_EX_MSG));
		}
	}

	@Test
	public void shouldThrowExceptionIfLengthIsLessThanZero() {
		try {
			calculate(1.0d, -1.0d, 0.0d);
			fail("None shall pass!");
		} catch (IllegalArgumentException iae) {
			assertThat(iae.getMessage(), is(Calculator.LENGTH_EX_MSG));
		}
	}

	@Test
	public void shouldReturnZeroIfDiameterIsFour() {
		calculate(4.0d, 8.0d, 0.0d);
	}

	@Test
	public void shouldReturnOneIfDiameterIsEightAndLengthIsOne() {
		calculate(8.0d, 1.0d, 1.0d);
	}

	@Test
	public void shouldReturnFourIfDiameterIsTwelveAndLengthIsOne() {
		calculate(12.0d, 1.0d, 4.0d);
	}

	@Test
	public void shouldReturnOneQuarterIfDiameterIsTwoAndLengthIsOne() {
		calculate(2.0d, 1.0d, 0.25d);
	}
}
