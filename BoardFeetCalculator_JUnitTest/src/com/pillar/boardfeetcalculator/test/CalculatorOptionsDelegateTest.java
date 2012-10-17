package com.pillar.boardfeetcalculator.test;

import android.view.MenuItem;
import android.widget.Toast;

import com.pillar.android.ToastFactory;
import com.pillar.boardfeetcalculator.Calculator;
import com.pillar.boardfeetcalculator.CalculatorOptionDelegate;
import com.pillar.boardfeetcalculator.R;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class CalculatorOptionsDelegateTest extends TestCase {
	
	private ToastFactory factory;
	private Calculator calculator;
	private MenuItem item;
	private CalculatorOptionDelegate delegate;
	
	@Override
	protected void setUp() throws Exception {
		factory = mock(ToastFactory.class);
		calculator = mock(Calculator.class);
 		item = mock(MenuItem.class);
 		
		delegate = new CalculatorOptionDelegate(factory, calculator);
	}
	
	public void testWillReturnFalseIfNoValidMenuItemSelected() throws Exception {
		when(item.getItemId()).thenReturn(0);
		
		boolean result = delegate.onOptionItemSelected(item);

		assertFalse(result);
	}
	
	public void testWillShowTextWhenMenuSettingsIsSelected() throws Exception {
		Toast toast = mock(Toast.class);
		when(factory.createToast(calculator, "Settings Menu Selected", Toast.LENGTH_SHORT)).thenReturn(toast);
		when(item.getItemId()).thenReturn(R.id.menu_settings);

		boolean result = delegate.onOptionItemSelected(item);

		assertTrue(result);
		verify(toast, times(1)).show();
	}
}
