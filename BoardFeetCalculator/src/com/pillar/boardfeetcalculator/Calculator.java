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

public class Calculator extends Activity {
	public Double Diameter = Double.valueOf(0.0);

	private OnEditorActionListener CircumferenceListener;
	private CalculatorOptionDelegate optionDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);        
    	CircumferenceListener = new OnEditorActionListener() {
    	    @Override
    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    	        if (actionId == EditorInfo.IME_ACTION_DONE) {
    				Editable text = (Editable) v.getText();//	    				Double value = Double.valueOf(text.toString());
    				Diameter = Double.valueOf(text.toString());
    				Diameter /= Math.PI;
    	        }
    	        return false;
    	    }
    	};
        EditText editCircumference = (EditText)findViewById(com.pillar.boardfeetcalculator.R.id.editCircumference);
        if(editCircumference != null)
        {
	    	editCircumference.setOnEditorActionListener(CircumferenceListener);
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
