package com.pillar.boardfeetcalculator;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class CalculatorSettings extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.layout.activity_calculatorsettings);
	}

}
