package com.pillar.boardfeetcalculator;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;

public class InputActionListener implements OnEditorActionListener {

	private static final String EMPTY_STRING = "";
	public static final double MINIMUM_HEIGHT = 8d;
	public static final double MINIMUM_CIRCUMFERENCE = 6.5d;
	public static final String EMPTY_MESSAGE = EMPTY_STRING;
	public static final String ERROR_MESSAGE = "ERROR";
	
	private Calculator calculator;
	private DoyleScribnerCalculator boardFeetCalculator;
	private CircumferenceToDiameterCalculator diameterCalculator;

	public InputActionListener(Calculator calculator, DoyleScribnerCalculator bfCalculator,
			CircumferenceToDiameterCalculator cdCalculator) {
		this.calculator = calculator;
		boardFeetCalculator = bfCalculator;
		diameterCalculator = cdCalculator;
	}

	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
		String boardFeet = EMPTY_MESSAGE;
		if (isDoneOrNext(actionId) && haveBothInputs()) {
			if (inputsAreValid()) {
				try {
					boardFeet = calculateBoardFeet(view);
				} catch (IllegalArgumentException iae) {
					boardFeet = ERROR_MESSAGE;
				}
			} else {
				boardFeet = ERROR_MESSAGE;
			}
		}
		setBoardFeetTextValue(boardFeet);
		return false;
	}

	private boolean inputsAreValid() {
		boolean rval = false;
		try {
			rval = MINIMUM_CIRCUMFERENCE <= Double.valueOf(getCircumferenceTextField().getText().toString())
					&& MINIMUM_HEIGHT <= Double.valueOf(getHeightTextField().getText().toString());
		} catch (NumberFormatException nfe) {
			rval = false; // explicit
		}
		return rval;
	}

	private boolean isDoneOrNext(int actionId) {
		return actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT;
	}

	private boolean haveBothInputs() {
		return !(EMPTY_STRING.equals(getCircumferenceFieldValue()) || EMPTY_STRING.equals(getHeightFieldValue()));
	}

	private String calculateBoardFeet(TextView view) {
		return Double.toString(boardFeetCalculator.calculateBoardFeet(calculateDiameter(view), getHeight()));
	}

	private double getHeight() {
		return Double.valueOf(getHeightTextField().getText().toString());
	}

	private double calculateDiameter(TextView view) {
		return diameterCalculator.calculate(Double.valueOf(((Editable) view.getText()).toString()));
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