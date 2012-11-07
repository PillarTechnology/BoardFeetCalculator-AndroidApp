package com.pillar.boardfeetcalculator;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;

public class CircumferenceActionListener implements OnEditorActionListener {

	private Calculator calculator;
	private DoyleScribnerCalculator boardFeetCalculator;
	private CircumferenceToDiameterCalculator diameterCalculator;

	public CircumferenceActionListener(Calculator calculator, DoyleScribnerCalculator bfCalculator,
			CircumferenceToDiameterCalculator cdCalculator) {
		this.calculator = calculator;
		boardFeetCalculator = bfCalculator;
		diameterCalculator = cdCalculator;
	}

	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
		String boardFeet = "ERROR";
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			try {
				boardFeet = calculateBoardFeet(view);
			} catch (NumberFormatException nfe) {
				// no-op
			} catch (IllegalArgumentException iae) {
				// no-op
			}
			setBoardFeetTextValue(boardFeet);
		}
		return false;
	}

	private String calculateBoardFeet(TextView view) {
		return Double.toString(boardFeetCalculator.calculateBoardFeet(calculateDiameter(view), getHeight()));
	}

	private double getHeight() {
		EditText editHeight = getHeightTextField();
		return Double.valueOf(editHeight.getText().toString());
	}

	private double calculateDiameter(TextView view) {
		return diameterCalculator.calculate(Double.valueOf(((Editable) view.getText()).toString()));
	}

	private void setBoardFeetTextValue(String value) {
		getBoardFeetTextField().setText(value);
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