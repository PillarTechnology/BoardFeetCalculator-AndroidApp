package com.pillar.doylescribner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

public class CircumferenceToDiameterCalculatorTest {

	private CircumferenceToDiameterCalculator calculator;
	
	@Before
	public void setup() {
		calculator = new CircumferenceToDiameterCalculator();
	}
	
	@Test
	public void shouldReturnZeroIfGivenZero() {
		assertThat(calculator.calculate(0.0d), is(0.0d));
	}
	
	@Test
	public void shouldReturnOneGivenPI() {
		assertThat(calculator.calculate(Math.PI), is(1.0));
	}

	@Test
	public void shouldThrowIllegalArgumentExceptionIfGivenNegativeNumber() {
		try {
			calculator.calculate(-1.0d);
			fail("None shall pass");
		} catch (IllegalArgumentException iae) {
			assertThat(iae.getMessage(), is(CircumferenceToDiameterCalculator.ILLEGAL_ARG_EX_MSG));
		}
	}
}
