package com.groupIrregular.wap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_CREATE = 
			"CREATE TABLE " + WAPDatabaseContract.TABLE_NAME + " (" +
			WAPDatabaseContract.Profile._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            WAPDatabaseContract.Profile.COLUMN_USERNAME + " VARCHAR(20) NOT NULL, " +
            WAPDatabaseContract.Profile.COLUMN_NAME + " VARCHAR(20) NOT NULL, " +
            WAPDatabaseContract.Profile.COLUMN_SURNAME + " VARCHAR(20) NOT NULL, " +
            WAPDatabaseContract.Profile.COLUMN_PHONE + " VARCHAR(10) , " +
            WAPDatabaseContract.Profile.COLUMN_EMAIL + " VARCHAR(20) );";
	
	public static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + WAPDatabaseContract.TABLE_NAME;
	
	public DatabaseHelper(Context context) {
		super(context, WAPDatabaseContract.DATABASE_NAME, null, WAPDatabaseContract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w("DatabaseHelper", "Veritabani " + oldVersion + "\'dan" + newVersion + "\'a guncelleniyor");
		
		db.execSQL(DATABASE_DROP);
		onCreate(db);
		
	}

}
