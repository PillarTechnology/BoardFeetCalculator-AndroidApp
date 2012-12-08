package com.pillar.boardfeetcalculator;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.pillar.android.IntentFactory;
import com.pillar.doylescribner.CircumferenceToDiameterCalculator;
import com.pillar.doylescribner.DoyleScribnerCalculator;
import com.pillar.boardfeetcalculator.CurrentLocationListener;

public class Calculator extends Activity {

	private static final long MIN_UPDATE_TIME = 1000;
	private static final float MIN_UPDATE_DISTANCE = 1.0f;
	private CalculatorOptionDelegate optionDelegate;
	private CurrentLocationListener locListener;

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
		locListener = new CurrentLocationListener(this,	(LocationManager) getSystemService(Context.LOCATION_SERVICE), 
				MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE);
		locListener.initializeListener();
		enableSaveButton(false);
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
	
	public void enableSaveButton(boolean enable) {
		Button saveButton = (Button)findViewById(R.id.buttonSave);
		saveButton.setEnabled(enable);	
		saveButton.setClickable(enable);
	}

}
