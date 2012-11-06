package com.pillar.doylescribner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DoyleScribnerCalculatorTest {

	/*
	 * Doyle-Scribner Rule according to
	 * http://www.sizes.com/units/doyles_rule.htm
	 * 
	 * board_feet = (((D - 4) / 4) ^ 2 ) * L
	 * 
	 * D = diameter in inches L = length in feet
	 */
	private DoyleScribnerCalculator doyleScribnerCalculator;

	@Before
	public void setup() {
		doyleScribnerCalculator = new DoyleScribnerCalculator();
	}

	private void calculate(double diameter, double length, double expected) {
		assertThat(
				doyleScribnerCalculator.calculateBoardFeet(diameter, length),
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

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldThrowExceptionIfDiameterIsLessThanZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(DoyleScribnerCalculator.DIAMETER_EX_MSG);
		calculate(-0.1d, 1.0d, 0.0d);
	}

	@Test
	public void shouldThrowExceptionIfLengthIsLessThanZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(DoyleScribnerCalculator.LENGTH_EX_MSG);
		calculate(1.0d, -1.0d, 0.0d);
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
