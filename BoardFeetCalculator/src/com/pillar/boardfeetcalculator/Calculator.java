package com.pillar.boardfeetcalculator;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class Calculator extends Activity implements LocationListener {

	private CalculatorOptionDelegate optionDelegate;
	private TextView lat, lon;
	private LocationManager locationManager;
	private String provider;

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
		initializeLocationListener();
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
	
	private void initializeLocationListener() {
		lat = (TextView) findViewById(R.id.displayLatitude);
		lon = (TextView) findViewById(R.id.displayLongitude);	

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			onLocationChanged(location);
		} else {
			lat.setText("Location not available");
			lon.setText("Location not available");
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		lat.setText(Location.convert(location.getLatitude(), Location.FORMAT_SECONDS));
		lon.setText(Location.convert(location.getLongitude(), Location.FORMAT_SECONDS));
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String arg0) {
		Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// no-op
	}

	public void enableSaveButton(boolean enable) {
		Button saveButton = (Button)findViewById(R.id.buttonSave);
		saveButton.setEnabled(enable);	
		saveButton.setClickable(enable);
	}

}
