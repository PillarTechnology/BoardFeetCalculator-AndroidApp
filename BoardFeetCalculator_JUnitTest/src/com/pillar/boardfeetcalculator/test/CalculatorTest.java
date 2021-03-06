package com.pillar.boardfeetcalculator.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
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

	public void testSaveButtonExists() {
		Button saveButton = (Button) MainActivity
				.findViewById(com.pillar.boardfeetcalculator.R.id.buttonSave);
		assertNotNull(saveButton);
	}

	public void testActivityCanDisableSaveButton() throws Throwable {
		runTestOnUiThread(new Runnable() {
			public void run() {
				MainActivity.enableSaveButton(false);
			}
		});
		Button saveButton = (Button) MainActivity
				.findViewById(com.pillar.boardfeetcalculator.R.id.buttonSave);
		assertNotNull(saveButton);
		assertFalse("",saveButton.isClickable());
	}

	public void testActivityCanEnableSaveButton() throws Throwable {
		runTestOnUiThread(new Runnable() {
			public void run() {
				MainActivity.enableSaveButton(true);
			}
		});
		Button saveButton = (Button) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.buttonSave);
		assertNotNull(saveButton);
		assertTrue("",saveButton.isClickable());
	}

	public void testInitialSaveButtonStateIsDisabled() {
		Button saveButton = (Button) MainActivity
				.findViewById(com.pillar.boardfeetcalculator.R.id.buttonSave);
		assertNotNull(saveButton);
		assertFalse("",saveButton.isClickable());
	}
	
}
