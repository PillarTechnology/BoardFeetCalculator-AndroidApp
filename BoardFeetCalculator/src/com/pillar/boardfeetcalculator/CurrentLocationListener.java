package com.pillar.boardfeetcalculator;

import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.R;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class CurrentLocationListener implements LocationListener {

	public static final String GPS_PROVIDER_NAME = LocationManager.GPS_PROVIDER;
	public static final String NET_PROVIDER_NAME = LocationManager.NETWORK_PROVIDER;
	public static final String UNKNOWN_STRING = "Unknown";
	private long minUpdateTime = 1000;
	private float minUpdateDistance = 1.0f;
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private Calculator calculator;
	private TextView lat, lon;
	private LocationManager locationManager = null;
	private Location lastKnownLocation = null;

	public CurrentLocationListener(Calculator calculator, LocationManager locationManager, long minUpdateTime, float minUpdateDistance) {
		this.calculator = calculator;
		this.locationManager = locationManager;
		this.minUpdateTime = minUpdateTime;
		this.minUpdateDistance = minUpdateDistance;
		lat = (TextView) calculator.findViewById(R.id.displayLatitude);
		lon = (TextView) calculator.findViewById(R.id.displayLongitude);	

	}
	
	public void initializeListener() {
		if(locationManager != null) {
			requestLocationUpdates(GPS_PROVIDER_NAME);
			requestLocationUpdates(NET_PROVIDER_NAME);
			Location lastLocation = getLastKnownLocation(GPS_PROVIDER_NAME, true);
			if (lastLocation == null) {
				lastLocation = getLastKnownLocation(NET_PROVIDER_NAME, true);
			};
			onLocationChanged(lastLocation);
		};
		if(locationManager == null) {
			showUnknownLocation();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if(isBetterLocation(location, lastKnownLocation)) {
			lastKnownLocation = location;
			lat.setText(Location.convert(location.getLatitude(), Location.FORMAT_SECONDS));
			lon.setText(Location.convert(location.getLongitude(), Location.FORMAT_SECONDS));
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		showProviderFloatingMessage(provider, "disabled");
	}

	@Override
	public void onProviderEnabled(String provider) {
		showProviderFloatingMessage(provider, "enabled");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		switch(status)
		{
			case LocationProvider.OUT_OF_SERVICE:
			{
				showProviderFloatingMessage(provider, "out of service");
				break;
			}
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
			{
				showProviderFloatingMessage(provider, "temporarily unavailable");
				break;
			}
			case LocationProvider.AVAILABLE:
			{
				showProviderFloatingMessage(provider, "available");
				initializeListener();
				break;
			}
		}
	}
	
	public void requestLocationUpdates(String provider) {
		if(locationManager != null) {
			try {
				locationManager.requestLocationUpdates(provider, minUpdateTime, minUpdateDistance, this);
			} catch (IllegalArgumentException e) {	// if provider is null or doesn't exist on this device
				showProviderFloatingMessage(provider, "not available in device");
			} catch (SecurityException e) {			// if no suitable permission is present 
				showProviderFloatingMessage(provider, "permission access not granted");
			}
		}
	}
	
	public Location getLastKnownLocation(String provider, boolean displayResults) {
		Location lastLocation = null;
		try {
			lastLocation = locationManager.getLastKnownLocation(provider);
		} catch (IllegalArgumentException e) {	// if provider is null or doesn't exist on this device
			showProviderFloatingMessage(provider, "not available in device");
		} catch (SecurityException e) {			// if no suitable permission is present 
			showProviderFloatingMessage(provider, "permission access not granted");
		}
		if (displayResults) {
			if (lastLocation != null) {
				onLocationChanged(lastLocation);
			} else {
				showUnknownLocation();
			}
		}
		return lastLocation;
	}
	
	public void showUnknownLocation() {
		lat.setText(UNKNOWN_STRING);
		lon.setText(UNKNOWN_STRING);
	}
	
	public void showProviderFloatingMessage(String providerName, String message) {
		Toast.makeText(calculator, providerName.toUpperCase() + " provider\n" + message, Toast.LENGTH_SHORT).show();
	}

	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (location == null) {
	        // No location can never be better
	        return false;
	    }

	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
}
