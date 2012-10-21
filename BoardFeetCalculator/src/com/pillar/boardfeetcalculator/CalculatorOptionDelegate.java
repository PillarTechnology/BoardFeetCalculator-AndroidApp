package com.pillar.boardfeetcalculator;

import android.view.MenuItem;

import com.pillar.android.IntentFactory;

public class CalculatorOptionDelegate {

	private final IntentFactory factory;
	private final Calculator calculator;

	public CalculatorOptionDelegate(final IntentFactory factory, final Calculator calculator) {
		this.factory = factory;
		this.calculator = calculator;
	}

	public boolean onOptionItemSelected(final MenuItem item) {
		
		if(item.getItemId() == R.id.menu_settings) {
			calculator.startActivity(factory.createIntent(calculator, CalculatorSettings.class));
			return true;
		}
		
		return false;
	}

}
