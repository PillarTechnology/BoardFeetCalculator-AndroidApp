package com.pillar.boardfeetcalculator;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class CalculatorSettings extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
	}

	public static class PrefsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			PreferenceManager.setDefaultValues(getActivity(),
                    R.layout.activity_calculatorsettings, false);
			
			addPreferencesFromResource(R.layout.activity_calculatorsettings);
		}
	}
}
