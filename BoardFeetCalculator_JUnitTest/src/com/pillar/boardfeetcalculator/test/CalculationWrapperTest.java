package com.pillar.boardfeetcalculator.test;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.pillar.boardfeetcalculator.CalculationWrapper;
import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;

import junit.framework.TestCase;

public class CalculationWrapperTest extends TestCase {

	private static final double CORRECT_RESPONSE = 1.0d;

	private DoyleScribnerCalculator bfCalculator = mock(DoyleScribnerCalculator.class);
	private CircumferenceToDiameterCalculator cdCalculator = mock(CircumferenceToDiameterCalculator.class);

	private String doCalculation(String givenHeight, String givenCircumference) {
		return new CalculationWrapper(bfCalculator, cdCalculator).runCalculation(givenHeight, givenCircumference);
	}

	private void checkCalculation(String expectedBoardFeet, String givenHeight, String givenCircumference) {
		when(bfCalculator.calculateBoardFeet(anyDouble(), anyDouble())).thenReturn(CORRECT_RESPONSE);

		assertEquals(expectedBoardFeet, doCalculation(givenHeight, givenCircumference));
	}

	public void testWrapperReturnsCalculationResultWhenInputsAreValid() {
		checkCalculation(Double.toString(CORRECT_RESPONSE), "8", "40");
	}

	public void testWrapperReturnsErrorWhenCircumferenceIsTooLow() {
		checkCalculation(CalculationWrapper.ERROR_MESSAGE, "30", "6.49999");
	}

	public void testWrapperReturnsErrorWhenHeightIsTooLow() {
		checkCalculation(CalculationWrapper.ERROR_MESSAGE, "7.99999", "6.5");
	}

	public void testWrapperReturnsErrorWhenBothInputsAreTooLow() {
		checkCalculation(CalculationWrapper.ERROR_MESSAGE, "7.99999", "6.49999");
	}

	public void testWrapperReturnsErrorWhenHeightIsNotNumeric() {
		checkCalculation(CalculationWrapper.ERROR_MESSAGE, "HEIGHT", "40");
	}

	public void testWrapperReturnsErrorWhenCircumferenceIsNotNumeric() {
		checkCalculation(CalculationWrapper.ERROR_MESSAGE, "8", "CIRCUMFERENCE");
	}

	private void checkNoCalculationOccurs(String givenHeight, String givenCircumference) {
		doCalculation(givenHeight, givenCircumference);

		verify(cdCalculator, never()).calculate(anyDouble());
		verify(bfCalculator, never()).calculateBoardFeet(anyDouble(), anyDouble());
	}

	public void testWrapperDoesNoCalculationWhenCircumferenceIsTooLow() {
		checkNoCalculationOccurs("30", "6.49999");
	}

	public void testWrapperDoesNoCalculationWhenHeightIsTooLow() {
		checkNoCalculationOccurs("7.99999", "6.5");
	}

	public void testWrapperDoesNoCalculationWhenBothInputsAreTooLow() {
		checkNoCalculationOccurs("7.99999", "6.49999");
	}

	public void testWrapperDoesNoCalculationWhenHeightIsNotNumeric() {
		checkNoCalculationOccurs("HEIGHT", "40");
	}

	public void testWrapperDoesNoCalculationWhenCircumferenceIsNotNumeric() {
		checkNoCalculationOccurs("8", "CIRCUMFERENCE");
	}

}
