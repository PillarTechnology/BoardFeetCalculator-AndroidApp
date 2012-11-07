package com.pillar.boardfeetcalculator.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.CircumferenceActionListener;
import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;
import com.pillar.boardfeetcalculator.R;

public class CircumferenceActionListenerTest extends TestCase {


	private DoyleScribnerCalculator bfCalculator = new DoyleScribnerCalculator();
	private CircumferenceToDiameterCalculator cdCalculator = new CircumferenceToDiameterCalculator();
	
	private void checkBehavior(String expected, String height, String circumference) {
		//REALLY don't like the level to which I've had to mock here. How can we make it better? I need a pair!
		Editable heightControl = mock(Editable.class);
		EditText heightView = mock(EditText.class);
		Calculator calculator = mock(Calculator.class);
		Editable circumferenceControl = mock(Editable.class);
		EditText circumferenceView = mock(EditText.class);
		EditText boardFeetView = mock(EditText.class);

		when(heightControl.toString()).thenReturn(height);
		when(heightView.getText()).thenReturn(heightControl);
		when(calculator.findViewById(R.id.editHeight)).thenReturn(heightView);
		when(circumferenceControl.toString()).thenReturn(circumference);
		when(circumferenceView.getText()).thenReturn(circumferenceControl);
		when(calculator.findViewById(R.id.editCircumference)).thenReturn(circumferenceView);
		when(calculator.findViewById(R.id.editBoardFeet)).thenReturn(boardFeetView);
			
		CircumferenceActionListener listener = new CircumferenceActionListener(calculator, bfCalculator, cdCalculator);

		listener.onEditorAction(circumferenceView, EditorInfo.IME_ACTION_DONE, null);

		verify(boardFeetView).setText(expected);
	}
	
	public void testOnEditorActionPopulatesTheBoardFootView() {
		checkBehavior("38.12736512446372", "8", "40");
	}
	
	public void testOnEditorActionDisplaysERRORWithBadHeightInput() {
		checkBehavior("ERROR", "-5", "40");
	}
	
	public void testOnEditorActionDisplaysERRORWithBadCircumferenceInput() {
		checkBehavior("ERROR", "5", "-40");
	}
	
	public void testOnEditorActionDisplaysERRORWithEmptyInput() {
		checkBehavior("ERROR", "", "");
	}
}
