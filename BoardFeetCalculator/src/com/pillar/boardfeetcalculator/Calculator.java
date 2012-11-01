package com.pillar.boardfeetcalculator;

import com.pillar.android.IntentFactory;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.pillar.doylescribner.CircumferenceToDiameterCalculator;

public class Calculator extends Activity {

	public Double diameter = Double.valueOf(0.0);
	private OnEditorActionListener circumferenceListener;
	private CalculatorOptionDelegate optionDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);        
    	circumferenceListener = new OnEditorActionListener() {
    	    @Override
    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    	        if (actionId == EditorInfo.IME_ACTION_DONE) {
    				Editable text = (Editable) v.getText();
    				double circumference = 0.0d;
    				try {
    					circumference = Double.valueOf(text.toString());
    				} catch (NumberFormatException nfe) {
    					//bury
    				}
    				CircumferenceToDiameterCalculator cal = new CircumferenceToDiameterCalculator();
    				diameter = cal.calculate(circumference);
    	        }
    	        return false;
    	    }
    	};
        EditText editCircumference = (EditText)findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
        if(editCircumference != null)
        {
	    	editCircumference.setOnEditorActionListener(circumferenceListener);
        }
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
