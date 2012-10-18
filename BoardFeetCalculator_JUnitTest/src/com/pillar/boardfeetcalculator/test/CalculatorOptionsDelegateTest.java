package com.pillar.boardfeetcalculator.test;

import android.content.Intent;
import android.view.MenuItem;

import com.pillar.android.IntentFactory;
import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.CalculatorOptionDelegate;
import com.pillar.boardfeetcalculator.CalculatorSettings;
import com.pillar.boardfeetcalculator.R;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class CalculatorOptionsDelegateTest extends TestCase {
	
	private IntentFactory factory;
	private Calculator calculator;
	private MenuItem item;
	private CalculatorOptionDelegate delegate;
	
	@Override
	protected void setUp() throws Exception {
		factory = mock(IntentFactory.class);
		calculator = mock(Calculator.class);
 		item = mock(MenuItem.class);
 		
		delegate = new CalculatorOptionDelegate(factory, calculator);
	}
	
	public void testWillReturnFalseIfNoValidMenuItemSelected() throws Exception {
		when(item.getItemId()).thenReturn(0);
		
		boolean result = delegate.onOptionItemSelected(item);

		assertFalse(result);
	}
	
	public void testWillLaunchCalculatorSettingsWhenMenuSettingsIsSelected() throws Exception {
		Intent intent = mock(Intent.class);
		when(factory.createIntent(calculator, CalculatorSettings.class)).thenReturn(intent);
		when(item.getItemId()).thenReturn(R.id.menu_settings);

		boolean result = delegate.onOptionItemSelected(item);

		assertTrue(result);
		verify(calculator, times(1)).startActivity(intent);
	}

}
