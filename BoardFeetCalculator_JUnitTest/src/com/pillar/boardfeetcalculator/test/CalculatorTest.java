package com.pillar.boardfeetcalculator.test;

import com.pillar.boardfeetcalculator.Calculator;
import android.test.ActivityInstrumentationTestCase2;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

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
		 		EditText editCircumference = (EditText) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
				assertNotNull(editCircumference);
		 		editCircumference.setText("New text");
				Editable text = editCircumference.getText();
				assertEquals("Cannot set editCircumference text", new String("New text"), text.toString());
		    }
		});
	}
	
	public void testResultAreaExists() {
		TextView textResult = (TextView) MainActivity.findViewById(com.pillar.boardfeetcalculator.R.id.textResult);
		assertNotNull(textResult);
	}
	
	public void testDatabaseExists() {
		String[] databaseList = MainActivity.databaseList();
		assertEquals(2, databaseList.length);
		assertEquals("Forest-journal", databaseList[0]);
		assertEquals("Forest", databaseList[1]);
	}
}
