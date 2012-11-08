package com.pillar.boardfeetcalculator.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
	
	private EditText checkBehavior(String expected, String height, String circumference, int actionType) {
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

		listener.onEditorAction(circumferenceView, actionType, null);

		return boardFeetView;
	}
	
	private void checkBehaviorOccurs(String expected, String height, String circumference, int actionType) {
		EditText boardFeetView = checkBehavior(expected, height, circumference, actionType);
		verify(boardFeetView).setText(expected);
	}
	
	private void checkBehaviorDoesNotOccur(String expected, String height, String circumference, int actionType) {
		EditText boardFeetView = checkBehavior(expected, height, circumference, actionType);
		verify(boardFeetView, never()).setText(expected);
	}
	
	public void testOnEditorActionPopulatesTheBoardFootViewWithDone() {
		checkBehaviorOccurs("38.12736512446372", "8", "40", EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionDisplaysNothingWhenCircumferenceIsTooLowWithDone() {
		checkBehaviorDoesNotOccur("", "30", "6.49999", EditorInfo.IME_ACTION_DONE);
	}
	
	public void testOnEditorActionDisplaysNothingWhenHeightIsTooLowWithDone() {
		checkBehaviorDoesNotOccur("", "7.99999", "6.5", EditorInfo.IME_ACTION_DONE);	
	}

	public void testOnEditorActionDisplaysNothingWhenCircumferenceIsTooLowWithNext() {
		checkBehaviorDoesNotOccur("", "30", "6.49999", EditorInfo.IME_ACTION_NEXT);
	}
	
	public void testOnEditorActionDisplaysNothingWhenHeightIsTooLowWithNext() {
		checkBehaviorDoesNotOccur("", "7.99999", "6.5", EditorInfo.IME_ACTION_NEXT);	
	}
	
	public void testOnEditorActionDisplaysNothingWithEmptyInputWithDone() {
		checkBehaviorDoesNotOccur("", "", "", EditorInfo.IME_ACTION_DONE);
	}
	
	public void testOnEditorActionDisplaysNothingWithEmptyHeightWithDone() {
		checkBehaviorDoesNotOccur("", "", "30", EditorInfo.IME_ACTION_DONE);
	}
	
	public void testOnEditorActionDisplaysNothingWithEmptyCircumferenceWithDone() {
		checkBehaviorDoesNotOccur("", "30", "", EditorInfo.IME_ACTION_DONE);
	}
	
	public void testOnEditorActionPopulatesTheBoardFootViewWithNext() {
		checkBehaviorOccurs("38.12736512446372", "8", "40", EditorInfo.IME_ACTION_NEXT);
	}

	public void testOnEditorActionDisplaysNothingWithEmptyInputWithNext() {
		checkBehaviorDoesNotOccur("", "", "", EditorInfo.IME_ACTION_NEXT);
	}
	
	public void testOnEditorActionDisplaysNothingWithEmptyHeightInputWithNext() {
		checkBehaviorDoesNotOccur("", "", "30", EditorInfo.IME_ACTION_NEXT);
	}
	
	public void testOnEditorActionDisplaysNothingWithEmptyCircumferenceInputWithNext() {
		checkBehaviorDoesNotOccur("", "30", "", EditorInfo.IME_ACTION_NEXT);
	}
}
