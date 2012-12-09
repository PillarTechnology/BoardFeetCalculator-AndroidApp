package com.pillar.boardfeetcalculator;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
	private TextView lat;
	private TextView lon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		lat = (TextView) findViewById(R.id.displayLatitude);
		lon = (TextView) findViewById(R.id.displayLongitude);	
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

	public void showProviderFloatingMessage(String providerName, String message) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(providerName.toUpperCase() + " provider\n" + message);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	
	public void setLocation(String latString, String lonString) {
		lat.setText(latString);
		lon.setText(lonString);
	}

}
