package com.pillar.boardfeetcalculator.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.InputActionListener;
import com.pillar.boardfeetcalculator.R;
import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;

public class InputActionListenerTest extends TestCase {

	private DoyleScribnerCalculator bfCalculator = new DoyleScribnerCalculator();
	private CircumferenceToDiameterCalculator cdCalculator = new CircumferenceToDiameterCalculator();

	private EditText checkBehavior(String expected, String height, String circumference, int actionType) {
		Calculator calculator = mock(Calculator.class);
		EditText heightView = mock(EditText.class);
		EditText circumferenceView = mock(EditText.class);
		EditText boardFeetView = mock(EditText.class);
		Editable circumferenceControl = mock(Editable.class);
		Editable heightControl = mock(Editable.class);

		when(calculator.findViewById(R.id.editHeight)).thenReturn(heightView);
		when(calculator.findViewById(R.id.editCircumference)).thenReturn(circumferenceView);
		when(calculator.findViewById(R.id.editBoardFeet)).thenReturn(boardFeetView);
		when(heightView.getText()).thenReturn(heightControl);
		when(circumferenceView.getText()).thenReturn(circumferenceControl);
		when(heightControl.toString()).thenReturn(height);
		when(circumferenceControl.toString()).thenReturn(circumference);

		InputActionListener listener = new InputActionListener(calculator, bfCalculator, cdCalculator);

		listener.onEditorAction(circumferenceView, actionType, null);

		return boardFeetView;
	}

	private void checkBehaviorShouldOccur(String expected, String height, String circumference, int actionType) {
		EditText boardFeetView = checkBehavior(expected, height, circumference, actionType);
		verify(boardFeetView).setText(expected);
	}

	public void testOnEditorActionPopulatesTheBoardFootViewWithDone() {
		checkBehaviorShouldOccur("38.12736512446372", "8", "40", EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionDisplaysNothingWhenCircumferenceIsTooLowWithDone() {
		checkBehaviorShouldOccur("ERROR", "30", "6.49999", EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionDisplaysNothingWhenHeightIsTooLowWithDone() {
		checkBehaviorShouldOccur("ERROR", "7.99999", "6.5", EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionDisplaysNothingWhenCircumferenceIsTooLowWithNext() {
		checkBehaviorShouldOccur("ERROR", "30", "6.49999", EditorInfo.IME_ACTION_NEXT);
	}

	public void testOnEditorActionDisplaysNothingWhenHeightIsTooLowWithNext() {
		checkBehaviorShouldOccur("ERROR", "7.99999", "6.5", EditorInfo.IME_ACTION_NEXT);
	}

	public void testOnEditorActionDisplaysNothingWithEmptyInputWithDone() {
		checkBehaviorShouldOccur("", "", "", EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionDisplaysNothingWithEmptyHeightWithDone() {
		checkBehaviorShouldOccur("", "", "30", EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionDisplaysNothingWithEmptyCircumferenceWithDone() {
		checkBehaviorShouldOccur("", "30", "", EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionPopulatesTheBoardFootViewWithNext() {
		checkBehaviorShouldOccur("38.12736512446372", "8", "40", EditorInfo.IME_ACTION_NEXT);
	}

	public void testOnEditorActionDisplaysNothingWithEmptyInputWithNext() {
		checkBehaviorShouldOccur("", "", "", EditorInfo.IME_ACTION_NEXT);
	}

	public void testOnEditorActionDisplaysNothingWithEmptyHeightInputWithNext() {
		checkBehaviorShouldOccur("", "", "30", EditorInfo.IME_ACTION_NEXT);
	}

	public void testOnEditorActionDisplaysNothingWithEmptyCircumferenceInputWithNext() {
		checkBehaviorShouldOccur("", "30", "", EditorInfo.IME_ACTION_NEXT);
	}
}
