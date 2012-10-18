package com.pillar.boardfeetcalculator;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class CalculatorSettings extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {"Calculation Type"}));
	}

}
