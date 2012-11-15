package com.pillar.boardfeetcalculator;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class InputActionListener implements OnEditorActionListener {

	private static final String EMPTY_STRING = "";
	public static final String EMPTY_MESSAGE = EMPTY_STRING;

	private Calculator calculator;
	private CalculationWrapper calculationWrapper;

	public InputActionListener(Calculator calculator, CalculationWrapper calculationWrapper) {
		this.calculator = calculator;
		this.calculationWrapper = calculationWrapper;
	}

	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
		String boardFeet = EMPTY_MESSAGE;
		if (isDoneOrNext(actionId) && haveBothInputs()) {
			boardFeet = calculationWrapper.runCalculation(getHeightFieldValue(), getCircumferenceFieldValue());
		}
		setBoardFeetTextValue(boardFeet);
		return false;
	}

	private boolean isDoneOrNext(int actionId) {
		return actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT;
	}

	private boolean haveBothInputs() {
		return !(EMPTY_STRING.equals(getCircumferenceFieldValue()) || EMPTY_STRING.equals(getHeightFieldValue()));
	}

	private void setBoardFeetTextValue(String value) {
		getBoardFeetTextField().setText(value);
	}

	private String getCircumferenceFieldValue() {
		return getCircumferenceTextField().getText().toString();
	}

	private String getHeightFieldValue() {
		return getHeightTextField().getText().toString();
	}

	private EditText getCircumferenceTextField() {
		return getEditTextField(R.id.editCircumference);
	}

	private EditText getHeightTextField() {
		return getEditTextField(R.id.editHeight);
	}

	private EditText getBoardFeetTextField() {
		return getEditTextField(R.id.editBoardFeet);
	}

	private EditText getEditTextField(int editTextId) {
		return (EditText) calculator.findViewById(editTextId);
	}
}