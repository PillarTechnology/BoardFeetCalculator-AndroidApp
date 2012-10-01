package com.pillar.boardfeetcalculator.test;

import com.pillar.boardfeetcalculator.Calculator;
import android.test.ActivityInstrumentationTestCase2;

public class CalculatorTest extends ActivityInstrumentationTestCase2<Calculator> 
{
	public CalculatorTest() {
		super(Calculator.class);
	}

	protected void setUp() {
	}
	
	protected void tearDown() {
	}
	
	public void testExample() {
		assertTrue(true);
	}
	
	public void testTitleIsBoardFeetCalculator() {
		String title = (String) getActivity().getTitle();
		assertEquals("Board Feet Calculator", title);
	}
}
