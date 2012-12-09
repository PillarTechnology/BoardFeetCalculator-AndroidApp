package com.pillar.boardfeetcalculator.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import org.mockito.ArgumentMatcher;

import junit.framework.TestCase;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.CurrentLocationListener;

public class CurrentLocationListenerTest extends TestCase {
	private static final long MIN_UPDATE_TIME = 1;
	private static final float MIN_UPDATE_DISTANCE = 1.0f;
	private Calculator calculator;
	private CurrentLocationListener listener;
	
	class IsEqualString extends ArgumentMatcher<String> {
		public boolean matches(Object value) {
			return ((String) value).equals(this);
		}
	}

	private void setupTest(LocationManager locationManager) {
		calculator = mock(Calculator.class);
		when(calculator.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManager);
		listener = new CurrentLocationListener(calculator, locationManager, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE);
	}
	
	private void tearDownTest() {
		listener = null;
		calculator = null;
	}
	
	@SuppressWarnings("unused")
	private int getInternalResourceID(String resName, String resType) {
		Resources res = Resources.getSystem();
		return res.getIdentifier(resName, resType, "android");
	}
	
	public void testGetLastKnownLocationDisplaysValuesWhenRequested() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenReturn(null);
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, true);
		verify(calculator, times(1)).setLocation(CurrentLocationListener.UNKNOWN_STRING, CurrentLocationListener.UNKNOWN_STRING);
		tearDownTest();
	}
	
	public void testGetLastKnownLocationDoesNotDisplayValuesWhenRequested() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenReturn(null);
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, false);
		verify(calculator, never()).setLocation(CurrentLocationListener.UNKNOWN_STRING, CurrentLocationListener.UNKNOWN_STRING);
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
		verify(calculator, times(1)).setLocation("10:0:0", "10:0:0");
		tearDownTest();
	}
	
	public void testGetLastKnownLocationShowsToastMessageWhenProviderIsNotAvailableInDevice() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenThrow(new IllegalArgumentException());
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, true);
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.UNAVAILABLE_IN_DEVICE_STRING);
		tearDownTest();
	}
	
	public void testGetLastKnownLocationShowsToastMessageWhenThereIsNoSuitablePermission() {
		LocationManager locMgr = mock(LocationManager.class);
		when(locMgr.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME)).thenThrow(new SecurityException());
		setupTest(locMgr);
		listener.getLastKnownLocation(CurrentLocationListener.GPS_PROVIDER_NAME, true);
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.NO_PERMISSION_STRING);
		tearDownTest();
	}
	
	public void testRequestLocationUpdatesShowsToastMessageWhenProviderIsNotAvailableInDevice() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		doThrow(new IllegalArgumentException()).when(locMgr).requestLocationUpdates(CurrentLocationListener.GPS_PROVIDER_NAME,
				MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, listener);
		listener.requestLocationUpdates(CurrentLocationListener.GPS_PROVIDER_NAME);
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.UNAVAILABLE_IN_DEVICE_STRING);
		tearDownTest();
	}
	
	public void testRequestLocationUpdatesShowsToastMessageWhenThereIsNoSuitablePermission() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		doThrow(new SecurityException()).when(locMgr).requestLocationUpdates(CurrentLocationListener.GPS_PROVIDER_NAME,
				MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, listener);
		listener.requestLocationUpdates(CurrentLocationListener.GPS_PROVIDER_NAME);
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.NO_PERMISSION_STRING);
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
		verify(locMgr, times(1)).getLastKnownLocation(CurrentLocationListener.NET_PROVIDER_NAME);
		verify(calculator, times(2)).setLocation(CurrentLocationListener.UNKNOWN_STRING, CurrentLocationListener.UNKNOWN_STRING);
		tearDownTest();
	}
	
	public void testOnProviderDisabledShowsToastMessage() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		listener.onProviderDisabled(CurrentLocationListener.GPS_PROVIDER_NAME);
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.DISABLED_STRING);
		tearDownTest();
	}
	
	public void testOnProviderEnabledShowsToastMessage() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		listener.onProviderEnabled(CurrentLocationListener.GPS_PROVIDER_NAME);
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.ENABLED_STRING);
		tearDownTest();
	}
	
	public void testOnStatusChangedShowsToastMessageWhenProviderIsOutOfService() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		listener.onStatusChanged(CurrentLocationListener.GPS_PROVIDER_NAME, LocationProvider.OUT_OF_SERVICE, new Bundle());
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.OUT_OF_SERVICE_STRING);
		tearDownTest();
	}
	
	public void testOnStatusChangedShowsToastMessageWhenProviderIsUnavailable() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		listener.onStatusChanged(CurrentLocationListener.GPS_PROVIDER_NAME, LocationProvider.TEMPORARILY_UNAVAILABLE, new Bundle());
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.UNAVAILABLE_STRING);
		tearDownTest();
	}
	
	public void testOnStatusChangedShowsToastMessageWhenProviderIsAvailable() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		listener.onStatusChanged(CurrentLocationListener.GPS_PROVIDER_NAME, LocationProvider.AVAILABLE, new Bundle());
		verify(calculator, times(1)).showProviderFloatingMessage(CurrentLocationListener.GPS_PROVIDER_NAME,
				CurrentLocationListener.AVAILABLE_STRING);
		tearDownTest();
	}
	
	public void testShowUnknownLocationCallsSetLocationWithCorrectArguments() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		listener.showUnknownLocation();
		verify(calculator, times(1)).setLocation(CurrentLocationListener.UNKNOWN_STRING, CurrentLocationListener.UNKNOWN_STRING);
		tearDownTest();
	}
	
	public void testOnLocationChangedSavesNewLocationWhenProvidedWithBetterValue() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		Location location = new Location(CurrentLocationListener.GPS_PROVIDER_NAME);
		location.setLatitude(-100.0);
		location.setLongitude(-100.0);	
		listener.onLocationChanged(location);
		assertEquals(listener.getLastKnownLocation(),location);
		verify(calculator, times(1)).setLocation("-100:0:0", "-100:0:0");
		tearDownTest();
	}
	
	public void testOnLocationChangedDoesntSaveNewLocationWhenProvidedWithWorseValue() {
		LocationManager locMgr = mock(LocationManager.class);
		setupTest(locMgr);
		Location location1 = new Location(CurrentLocationListener.GPS_PROVIDER_NAME);
		location1.setLatitude(100.0);
		location1.setLongitude(100.0);	
		listener.onLocationChanged(location1);
		Location location2 = new Location(CurrentLocationListener.NET_PROVIDER_NAME);
		location2.setLatitude(-200.0);
		location2.setLongitude(90.0);	
		listener.onLocationChanged(location2);
		assertEquals(listener.getLastKnownLocation(),location1);
		verify(calculator, times(0)).setLocation("-200:0:0", "90:0:0");
		tearDownTest();
	}
	
}
