package com.pillar.boardfeetcalculator.test;

import com.pillar.boardfeetcalculator.CalculatorSettings;

import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.TwoStatePreference;
import android.test.ActivityInstrumentationTestCase2;

public class CalculatorSettingsTest extends ActivityInstrumentationTestCase2<CalculatorSettings>{
	private CalculatorSettings MainActivity;
	
	public CalculatorSettingsTest(){
		super(CalculatorSettings.class);
	}
	
	protected void setUp(){
		MainActivity = getActivity();
		assertNotNull(MainActivity);
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
	}
	
	public void testPreferencesContainTwoItems(){
		int numOfPreferences = MainActivity.getPreferenceScreen().getPreferenceCount();
		assertEquals(2, numOfPreferences);
	}
	
	public void testFirstPreferenceIsListPreference(){
		ListPreference firstPref = (ListPreference) MainActivity.getPreferenceScreen().getPreference(0);
		assertNotNull(firstPref);
	}
	
	public void testSecondPreferenceIsCheckBoxPreference(){
		CheckBoxPreference secondPref = (CheckBoxPreference) MainActivity.getPreferenceScreen().getPreference(1);
		assertNotNull(secondPref);
	}
	
	public void testFirstPreferenceTitleIsCalculationType(){
		String firstItem = (String) MainActivity.getPreferenceScreen().getPreference(0).getTitle();
		assertEquals("Calculation Type", firstItem);
	}
	
	public void testSecondPreferenceTitleIsSaveWithPhoto(){
		String secondItem = (String) MainActivity.getPreferenceScreen().getPreference(1).getTitle();
		assertEquals("Save with Photo", secondItem);
	}
	
	public void testSecondPreferenceDefaultValueIsTrue(){
		CheckBoxPreference checkBoxPref = (CheckBoxPreference) MainActivity.getPreferenceScreen().getPreference(1);
		assertNotNull(checkBoxPref);
		boolean defaultValue = checkBoxPref.isChecked();
		assertTrue(defaultValue);
	}
}
