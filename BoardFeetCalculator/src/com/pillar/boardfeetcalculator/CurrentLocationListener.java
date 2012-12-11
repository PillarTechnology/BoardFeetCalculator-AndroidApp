package com.pillar.boardfeetcalculator;

import com.pillar.boardfeetcalculator.Calculator;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;


public class CurrentLocationListener implements LocationListener {

	public static final String GPS_PROVIDER_NAME = LocationManager.GPS_PROVIDER;
	public static final String NET_PROVIDER_NAME = LocationManager.NETWORK_PROVIDER;
	public static final String UNKNOWN_STRING = "Unknown";
	public static final String DISABLED_STRING = "disabled";
	public static final String ENABLED_STRING = "enabled";
	public static final String OUT_OF_SERVICE_STRING = "out of service";
	public static final String UNAVAILABLE_STRING = "temporarily unavailable";
	public static final String AVAILABLE_STRING = "available";
	public static final String UNAVAILABLE_IN_DEVICE_STRING = "not available in device";
	public static final String NO_PERMISSION_STRING = "permission access not granted";
	public static final int TWO_MINUTES = 1000 * 60 * 2;
	private long minUpdateTime;
	private float minUpdateDistance;
	private Calculator calculator;
	private LocationManager locationManager = null;
	private Location lastKnownLocation = null;

	public CurrentLocationListener(Calculator calculator, LocationManager locationManager, long minUpdateTime, float minUpdateDistance) {
		this.calculator = calculator;
		this.locationManager = locationManager;
		this.minUpdateTime = minUpdateTime;
		this.minUpdateDistance = minUpdateDistance;

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
	
	public Location getLastKnownLocation() {
		return lastKnownLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
		if(isBetterLocation(location, lastKnownLocation)) {
			lastKnownLocation = location;
			calculator.setLocation(Location.convert(location.getLatitude(), Location.FORMAT_SECONDS),
					Location.convert(location.getLongitude(), Location.FORMAT_SECONDS));
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		calculator.showProviderFloatingMessage(provider, DISABLED_STRING);
	}

	@Override
	public void onProviderEnabled(String provider) {
		calculator.showProviderFloatingMessage(provider, ENABLED_STRING);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		switch(status)
		{
			case LocationProvider.OUT_OF_SERVICE:
			{
				calculator.showProviderFloatingMessage(provider, OUT_OF_SERVICE_STRING);
				break;
			}
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
			{
				calculator.showProviderFloatingMessage(provider, UNAVAILABLE_STRING);
				break;
			}
			case LocationProvider.AVAILABLE:
			{
				calculator.showProviderFloatingMessage(provider, AVAILABLE_STRING);
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
				calculator.showProviderFloatingMessage(provider, UNAVAILABLE_IN_DEVICE_STRING);
			} catch (SecurityException e) {			// if no suitable permission is present 
				calculator.showProviderFloatingMessage(provider, NO_PERMISSION_STRING);
			}
		}
	}
	
	public Location getLastKnownLocation(String provider, boolean displayResults) {
		Location lastLocation = null;
		try {
			lastLocation = locationManager.getLastKnownLocation(provider);
		} catch (IllegalArgumentException e) {	// if provider is null or doesn't exist on this device
			calculator.showProviderFloatingMessage(provider, UNAVAILABLE_IN_DEVICE_STRING);
		} catch (SecurityException e) {			// if no suitable permission is present 
			calculator.showProviderFloatingMessage(provider, NO_PERMISSION_STRING);
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
		calculator.setLocation(UNKNOWN_STRING, UNKNOWN_STRING);
	}
	
	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	public boolean isBetterLocation(Location location, Location currentBestLocation) {
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
