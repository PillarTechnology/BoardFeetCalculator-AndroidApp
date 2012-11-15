package com.pillar.boardfeetcalculator.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.pillar.boardfeetcalculator.CalculationWrapper;
import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.InputActionListener;
import com.pillar.boardfeetcalculator.R;

public class InputActionListenerTest extends TestCase {

	private static final String VALID_INPUT_VALUE = "INPUT";
	private static final String EMPTY_STRING = "";
	private static final String CALCULATION_RESULT = "RESULT";

	private EditText setupAndExecute(String expected, String height, String circumference, int actionType,
			CalculationWrapper wrapper) {
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

		InputActionListener listener = new InputActionListener(calculator, wrapper);

		listener.onEditorAction(circumferenceView, actionType, null);

		return boardFeetView;
	}
	
	private CalculationWrapper setupAndExecuteForWrapper(String height, String circumference, int actionType) {
		CalculationWrapper wrapper = mock(CalculationWrapper.class);
		setupAndExecute("", height, circumference, actionType, wrapper);
		return wrapper;
	}

	private void checkCalculationWrapperBehaviorShouldOccur(String height, String circumference, int actionType) {
		CalculationWrapper wrapper = setupAndExecuteForWrapper(height, circumference, actionType);
		verify(wrapper).runCalculation(height, circumference);
	}

	private void checkCalculationWrapperBehaviorShouldNotOccur(String height, String circumference, int actionType) {
		CalculationWrapper wrapper = setupAndExecuteForWrapper(height, circumference, actionType);
		verify(wrapper, never()).runCalculation(height, circumference);
	}

	private void checkDisplayValueShouldOccur(String expected, String height, String circumference, int actionType) {
		CalculationWrapper wrapper = mock(CalculationWrapper.class);
		when(wrapper.runCalculation(anyString(), anyString())).thenReturn(CALCULATION_RESULT);
		EditText boardFeetView = setupAndExecute(expected, height, circumference, actionType, wrapper);
		verify(boardFeetView).setText(expected);
	}

	public void testUsesCalculationWrapperWhenBothInputsPresentWithDone() {
		checkCalculationWrapperBehaviorShouldOccur(VALID_INPUT_VALUE, VALID_INPUT_VALUE, EditorInfo.IME_ACTION_DONE);
	}

	public void testDoesntUseCalculationWrapperHeightMissingWithDone() {
		checkCalculationWrapperBehaviorShouldNotOccur(EMPTY_STRING, VALID_INPUT_VALUE, EditorInfo.IME_ACTION_DONE);
	}

	public void testDoesntUseCalculationWrapperCircumferenceMissingWithDone() {
		checkCalculationWrapperBehaviorShouldNotOccur(VALID_INPUT_VALUE, EMPTY_STRING, EditorInfo.IME_ACTION_DONE);
	}

	public void testDoesntUseCalculationWrapperInputsMissingWithDone() {
		checkCalculationWrapperBehaviorShouldNotOccur(EMPTY_STRING, EMPTY_STRING, EditorInfo.IME_ACTION_DONE);
	}

	public void testUsesCalculationWrapperWhenBothInputsPresentWithNext() {
		checkCalculationWrapperBehaviorShouldOccur(VALID_INPUT_VALUE, VALID_INPUT_VALUE, EditorInfo.IME_ACTION_NEXT);
	}

	public void testDoesntUseCalculationWrapperHeightMissingWithNext() {
		checkCalculationWrapperBehaviorShouldNotOccur(EMPTY_STRING, VALID_INPUT_VALUE, EditorInfo.IME_ACTION_NEXT);
	}

	public void testDoesntUseCalculationWrapperCircumferenceMissingWithNext() {
		checkCalculationWrapperBehaviorShouldNotOccur(VALID_INPUT_VALUE, EMPTY_STRING, EditorInfo.IME_ACTION_NEXT);
	}

	public void testDoesntUseCalculationWrapperInputsMissingWithNext() {
		checkCalculationWrapperBehaviorShouldNotOccur(EMPTY_STRING, EMPTY_STRING, EditorInfo.IME_ACTION_NEXT);
	}

	public void testOnEditorActionDisplaysNothingWhenCalculationWrapperNotCalled() {
		checkDisplayValueShouldOccur(InputActionListener.EMPTY_MESSAGE, EMPTY_STRING, EMPTY_STRING,
				EditorInfo.IME_ACTION_DONE);
	}

	public void testOnEditorActionDisplaysResultWhenCalculationWrapperCalled() {
		checkDisplayValueShouldOccur(CALCULATION_RESULT, VALID_INPUT_VALUE, VALID_INPUT_VALUE, EditorInfo.IME_ACTION_DONE);
	}
}
