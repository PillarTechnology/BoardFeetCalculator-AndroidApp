package com.pillar.boardfeetcalculator.test;

import com.pillar.boardfeetcalculator.Calculator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;

public class CalculatorTest extends ActivityInstrumentationTestCase2<Calculator> 
{
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
		EditText editCircumference = (EditText) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
		assertNotNull(editCircumference);
	}
	
	public void testCircumferenceCanRecieveInput() throws Throwable {
		runTestOnUiThread(new Runnable() {
		     public void run() {
		    	String expectedText = "New text";
		 		EditText editCircumference = (EditText) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);

		 		editCircumference.setText(expectedText);
				assertEquals("Cannot set editCircumference text", expectedText, editCircumference.getText().toString());
		    }
		});
	}
	
	public void testResultAreaExists() {
		TextView textResult = (TextView) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.textResult);
		assertNotNull(textResult);
	}

	public void testCircumferenceCanCalculateDiameter() throws Throwable {
		runTestOnUiThread(new Runnable() {
		    public void run() {
		    	EditText editCircumference = (EditText) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
				assertNotNull(editCircumference);
				Double value = Double.valueOf(Math.PI);
		    	editCircumference.setText(value.toString());
		    	editCircumference.onEditorAction(EditorInfo.IME_ACTION_DONE);
				assertEquals("Circumference EditText cannot calculate diameter", Double.valueOf(1.0), MainActivity.diameter);
		    }
		});
	}
	
	public void testCalculatesDiameterOfZeroWhenInputIsInvalid() throws Throwable {
		runTestOnUiThread(new Runnable() {
		    public void run() {
		    	EditText editCircumference = (EditText) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
				assertNotNull(editCircumference);
		    	editCircumference.setText("INVALID");
		    	editCircumference.onEditorAction(EditorInfo.IME_ACTION_DONE);
				assertEquals("Circumference EditText calculated diameter incorrectly for invalid input", Double.valueOf(0.0), MainActivity.diameter);
		    }
		});
	}
}
