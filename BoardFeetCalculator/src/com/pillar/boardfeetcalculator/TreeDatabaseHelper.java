package com.pillar.boardfeetcalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TreeDatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Forest";
	private static final String TREE_TABLE_NAME = "Trees";
	
	private static final String KEY_ID = "id";
	
	public TreeDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TREES_TABLE = "CREATE TABLE " + TREE_TABLE_NAME + "(" +
									 KEY_ID + " INTEGER PRIMARY KEY" + ")";
		
		db.execSQL(CREATE_TREES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TREE_TABLE_NAME);
		onCreate(db);
	}

}
