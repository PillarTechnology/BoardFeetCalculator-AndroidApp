package com.pillar.boardfeetcalculator.test;

import com.pillar.boardfeetcalculator.CalculatorSettings;
import com.pillar.boardfeetcalculator.CalculatorSettings.PrefsFragment;

import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.test.ActivityInstrumentationTestCase2;

public class CalculatorSettingsTest extends ActivityInstrumentationTestCase2<CalculatorSettings>{
	private CalculatorSettings MainActivity;
	private PrefsFragment pf;
	
	public CalculatorSettingsTest(){
		super(CalculatorSettings.class);
	}
	
	protected void setUp(){
		MainActivity = getActivity();
		assertNotNull(MainActivity);
		
		pf = (PrefsFragment) MainActivity.getFragmentManager().findFragmentById(android.R.id.content);
		assertNotNull(pf);
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
	}
	
	public void testPreferencesContainOnePreferenceCategory(){
		assertEquals (1, pf.getPreferenceScreen().getPreferenceCount());
	}
	
	public void testPreferenceCategoryContainTwoItems(){
		PreferenceCategory pc = (PreferenceCategory) pf.getPreferenceScreen().getPreference(0);
		assertEquals (2, pc.getPreferenceCount());
	}
	
	public void testFirstPreferenceIsListPreference(){
		PreferenceCategory pc = (PreferenceCategory) pf.getPreferenceScreen().getPreference(0);
		ListPreference firstPref = (ListPreference) pc.getPreference(0);
		assertNotNull(firstPref);
	}
	
	public void testSecondPreferenceIsCheckBoxPreference(){
		PreferenceCategory pc = (PreferenceCategory) pf.getPreferenceScreen().getPreference(0);
		CheckBoxPreference secondPref = (CheckBoxPreference) pc.getPreference(1);
		assertNotNull(secondPref);
	}
	
	public void testFirstPreferenceTitleIsCalculationType(){
		PreferenceCategory pc = (PreferenceCategory) pf.getPreferenceScreen().getPreference(0);
		String firstItem = (String) pc.getPreference(0).getTitle();
		assertEquals("Calculation Type", firstItem);
	}
	
	public void testSecondPreferenceTitleIsSaveWithPhoto(){
		PreferenceCategory pc = (PreferenceCategory) pf.getPreferenceScreen().getPreference(0);
		String secondItem = (String) pc.getPreference(1).getTitle();
		assertEquals("Save with Photo", secondItem);
	}
}
