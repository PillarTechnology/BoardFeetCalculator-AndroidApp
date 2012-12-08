package com.pillar.boardfeetcalculator.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeastOnce;

import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import junit.framework.TestCase;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.CurrentLocationListener;
import com.pillar.boardfeetcalculator.R;

public class CurrentLocationListenerTest extends TestCase {

	private static final long MIN_UPDATE_TIME = 1000;
	private static final float MIN_UPDATE_DISTANCE = 1.0f;
	private Calculator calculator;
	private TextView lat;
	private TextView lon;
	private CurrentLocationListener listener;
	
	class IsEqualString extends ArgumentMatcher<String> {
		public boolean matches(Object value) {
			return ((String) value).equals(this);
		}
	}

	private void setupTest(LocationManager locationManager) {
		calculator = mock(Calculator.class);
		lat = mock(TextView.class);
		lon = mock(TextView.class);

		when(calculator.findViewById(R.id.displayLatitude)).thenReturn(lat);
		when(calculator.findViewById(R.id.displayLongitude)).thenReturn(lon);
		when(calculator.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager);
		listener = new CurrentLocationListener(calculator, locationManager, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE);
	}
	
	private void tearDownTest() {
		listener = null;
		calculator = null;
		lat = null;
		lon = null;
	}
	
	public void testGetLastKnownLocationDisplaysValuesWhenRequested() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenReturn(null);
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, true);
		verify(lat, times(1)).setText(CurrentLocationListener.UNKNOWN_STRING);
		verify(lon, times(1)).setText(CurrentLocationListener.UNKNOWN_STRING);
		tearDownTest();
	}
	
	public void testGetLastKnownLocationDoesNotDisplayValuesWhenRequested() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenReturn(null);
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, false);
		verify(lat, never()).setText(CurrentLocationListener.UNKNOWN_STRING);
		verify(lon, never()).setText(CurrentLocationListener.UNKNOWN_STRING);
		tearDownTest();
	}
	
	public void testGetLastKnownLocationDisplaysCorrectValuesWhenLastKnownLocationIsUnknown() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenReturn(null);
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, true);
		verify(lat, times(1)).setText(CurrentLocationListener.UNKNOWN_STRING);
		verify(lon, times(1)).setText(CurrentLocationListener.UNKNOWN_STRING);
		tearDownTest();
	}
	
	public void testGetLastKnownLocationDisplaysCorrectValuesWhenLastKnownLocationIsKnown() {
		LocationManager locMgr = mock(LocationManager.class);
		Location location = new Location(CurrentLocationListener.GPS_PROVIDER_NAME);
		location.setLatitude(10.0);
		location.setLongitude(10.0);	
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenReturn(location);
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, true);
		verify(lat, times(1)).setText("10:0:0");
		verify(lon, times(1)).setText("10:0:0");
		tearDownTest();
	}
	
	public void testListenerInitialization() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenReturn(null);
		setupTest(locMgr);
		listener.initializeListener();
		verify(locMgr, times(1)).requestLocationUpdates(CurrentLocationListener.GPS_PROVIDER_NAME, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, listener);
		verify(locMgr, times(1)).requestLocationUpdates(CurrentLocationListener.NET_PROVIDER_NAME, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, listener);
		verify(locMgr, times(1)).getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME);
		verify(lat, atLeastOnce()).setText(CurrentLocationListener.UNKNOWN_STRING);
		verify(lon, atLeastOnce()).setText(CurrentLocationListener.UNKNOWN_STRING);
		tearDownTest();
	}
	
}
