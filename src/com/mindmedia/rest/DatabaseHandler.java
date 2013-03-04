package com.mindmedia.rest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SearchPlaces";

	// Contacts table name
	private static final String TABLE_CONTACTS = "SavePlaces";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PH_NO = "phone_number";
	private static final String KEY_ADD = "address";
	private static final String KEY_LAT= "latitude";
	private static final String KEY_LON="longitude";
	private static final String KEY_RAT="rating";
	private static final String KEY_REF="reference";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," + KEY_NAME + " TEXT,"
				+ KEY_PH_NO + " TEXT," + KEY_ADD + " TEXT," + KEY_LAT + " TEXT," + KEY_LON + " TEXT," + KEY_RAT + " TEXT," + KEY_REF + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	
	public DatabaseHandler open() throws SQLException
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return this;
	}
	
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */	
public long insertContact(String name,String phone,String address,String latitude,String longitude,String ratingValue,String reference){
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues iniValues = new ContentValues();
	iniValues.put(KEY_NAME, name);
	iniValues.put(KEY_PH_NO, phone);
	iniValues.put(KEY_ADD, address);
	iniValues.put(KEY_LAT, latitude);
	iniValues.put(KEY_LON, longitude);
	iniValues.put(KEY_RAT, ratingValue);
	iniValues.put(KEY_REF, reference);
	return db.insert(TABLE_CONTACTS,null,iniValues);
}

//---retrieves all the details---

public Cursor getAllContacts(){
	SQLiteDatabase db = this.getWritableDatabase();
	return db.query(TABLE_CONTACTS, new String[] {KEY_NAME, KEY_PH_NO, KEY_ADD, KEY_LAT, KEY_LON, KEY_RAT, KEY_REF},null,null,null,null,null);
}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	
}
