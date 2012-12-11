package com.example.itsfire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
 
public class NoteBD 
{

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "notesNotification.db";
 
	private static final String TABLE_NOTES = "table_notifications";
	
	private static final String COL_ID = "id";
	private static final int NUM_COL_ID = 0;
	private static final String COL_SUBJECT = "subject";
	private static final int NUM_COL_SUBJECT = 1;
	private static final String COL_KEYWORDS = "keywords";
	private static final int NUM_COL_KEYWORDS = 2;
	private static final String COL_DATE = "date";
	private static final int NUM_COL_DATE = 3;
	private static final String COL_READ = "read";
	private static final int NUM_COL_READ = 4;

	private SQLiteDatabase bdd;
 
	private MaBaseSQLite maBaseSQLite;
 
	public NoteBD(Context context)
	{
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open()
	{
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close()
	{
		bdd.close();
	}
 
	public SQLiteDatabase getBDD()
	{
		return bdd;
	}
 
	public long insertNote(note n)
	{
		ContentValues values = new ContentValues();
			values.put(COL_SUBJECT, n.getSubject());
			values.put(COL_KEYWORDS,n.getKeywords());
			values.put(COL_DATE,n.getDate());
			values.put(COL_READ,false);
		return bdd.insert(TABLE_NOTES, null, values);
	}
 
	public int removeNoteWithID(int id)
	{
		return bdd.delete(TABLE_NOTES, COL_ID+"="+id, null);
	}
 
	public note getNoteWithID(int id)
	{
		Cursor c = bdd.query(TABLE_NOTES, new String[] {COL_ID,COL_SUBJECT, COL_KEYWORDS, COL_DATE, COL_READ }, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		return cursorToNote(c);
	}
	
	public note[] getNotes()
	{
		Cursor c = bdd.query(TABLE_NOTES, new String[] {COL_ID, COL_SUBJECT, COL_KEYWORDS, COL_DATE, COL_READ },null, null, null, null, null);
		if (c.getCount() == 0)
			return null;
		note [] liste = new note[c.getCount()];
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++)
			{
			 liste[i]=cursorToNote(c);
			 c.moveToNext();
			}
		return liste;
	}
	
	private note cursorToNote(Cursor c)
	{
		note n = new note();
		n.setId(c.getInt(NUM_COL_ID));
		n.setSubject(c.getString(NUM_COL_SUBJECT));
		n.setKeywords(c.getString(NUM_COL_KEYWORDS));
		n.setDate(c.getString(NUM_COL_DATE));
		n.setRead(c.getInt(NUM_COL_READ)==1);
		return n;
	}
	
	public int updateRead(int id)
	{
		ContentValues values = new ContentValues();
		values.put(COL_READ, true);
		return bdd.update(TABLE_NOTES, values, COL_ID + " = " +id, null);
	}
}