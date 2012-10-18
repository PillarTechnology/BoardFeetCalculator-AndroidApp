package com.pillar.boardfeetcalculator;

import com.pillar.android.IntentFactory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class Calculator extends Activity {	
	private CalculatorOptionDelegate optionDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        optionDelegate = new CalculatorOptionDelegate(new IntentFactory(), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculator, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      return optionDelegate.onOptionItemSelected(item) ? true : super.onOptionsItemSelected(item);
    }
}
