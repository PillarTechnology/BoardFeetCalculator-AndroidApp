package com.pillar.boardfeetcalculator.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.R;

public class CalculatorTest extends ActivityInstrumentationTestCase2<Calculator> {
	private Calculator MainActivity;

	public CalculatorTest() {
		super(Calculator.class);
	}

	protected void setUp() {
		MainActivity = getActivity();
		assertNotNull(MainActivity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testTitleIsBoardFeetCalculator() {
		String title = (String) MainActivity.getTitle();
		assertEquals("Board Feet Calculator", title);
	}

	public void testHeightInputAreaExists() {
		EditText editHeight = (EditText) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.editHeight);
		assertNotNull(editHeight);
	}

	public void testCircumferenceInputAreaExists() {
		EditText editCircumference = (EditText) MainActivity
				.findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
		assertNotNull(editCircumference);
	}

	public void testCircumferenceCanRecieveInput() throws Throwable {
		runTestOnUiThread(new Runnable() {
			public void run() {
				String expectedText = "New text";
				EditText editCircumference = (EditText) MainActivity
						.findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
				editCircumference.setText(expectedText);
				assertEquals("Cannot set editCircumference text", expectedText, editCircumference.getText().toString());
			}
		});
	}

	public void testResultAreaExists() {
		TextView textResult = (TextView) MainActivity.findViewById(R.id.editBoardFeet);
		assertNotNull(textResult);
	}

}
