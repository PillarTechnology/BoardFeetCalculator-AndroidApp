package com.pillar.boardfeetcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;

import com.pillar.android.IntentFactory;
import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;

public class Calculator extends Activity {

	private CalculatorOptionDelegate optionDelegate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		CalculationWrapper calculationWrapper = new CalculationWrapper(new DoyleScribnerCalculator(),
				new CircumferenceToDiameterCalculator());
		OnEditorActionListener listener = new InputActionListener(this, calculationWrapper);
		((EditText) findViewById(R.id.editCircumference)).setOnEditorActionListener(listener);
		((EditText) findViewById(R.id.editHeight)).setOnEditorActionListener(listener);
		optionDelegate = new CalculatorOptionDelegate(new IntentFactory(), this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return optionDelegate.onOptionItemSelected(item) ? true : super.onOptionsItemSelected(item);
	}
}
