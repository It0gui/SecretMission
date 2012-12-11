package com.example.itsfire;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class MaBaseSQLite extends SQLiteOpenHelper 
{
 
	private static final String TABLE_NOTIFICATION = "table_notifications";
	private static final String COL_ID = "id";
	private static final String COL_SUBJECT = "subject";
	private static final String COL_KEYWORDS = "keywords";
	private static final String COL_DATE = "date";
	private static final String COL_READ = "read";
	
	private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NOTIFICATION + " ("
	+ COL_ID + " integer primary key autoincrement , " + COL_SUBJECT + " TEXT NOT NULL , "+ COL_KEYWORDS + " TEXT NOT NULL , "
	+ COL_DATE + " TEXT NOT NULL , "+COL_READ+" BOOLEAN default false );";
 
	public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) 
	{
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{
		// TODO Auto-generated method stub
		
	}
 
}
