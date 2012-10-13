package com.pillar.boardfeetcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Calculator extends Activity {
	private static final String TAG = "Calculator";
	
	private TreeDatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        databaseHelper = new TreeDatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        databaseHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculator, menu);
        return true;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.v(TAG, "onPause()" );
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Log.v(TAG, "onResume()" );
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.menu_settings:
        Toast.makeText(this, "Settings Menu Selected", Toast.LENGTH_SHORT)
            .show();
        break;

      default:
        return super.onOptionsItemSelected(item);
      }

      return true;
    }
}
