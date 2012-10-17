package com.pillar.boardfeetcalculator;

import android.view.MenuItem;
import android.widget.Toast;

import com.pillar.android.ToastFactory;

public class CalculatorOptionDelegate {

	private static final String TOAST_TEXT = "Settings Menu Selected";

	private final ToastFactory factory;
	private final Calculator calculator;

	public CalculatorOptionDelegate(final ToastFactory factory, final Calculator calculator) {
		this.factory = factory;
		this.calculator = calculator;
	}

	public boolean onOptionItemSelected(final MenuItem item) {
		
		if(item.getItemId() == R.id.menu_settings) {
			factory.createToast(calculator, TOAST_TEXT, Toast.LENGTH_SHORT).show();
			return true;
		}
		
		return false;
	}

}
