package com.example.mytremp;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mytremp.*;


public class Model {


	DatabaseHelper myDb;
	
	
	
	public static final String TABLE_TREMPS = "TREMPS";
	private static final String TABLE_TREMPS_ARCHIVE = "TREMPS_ARCHIVE";
	public static final String TREMP_ID = "_id";
	public static final String TREMP_NAME = "name";
	public static final String TREMP_ADDRESS = "address";
	public static final String TREMP_PHONE = "phone";
	public static final String TREMP_NUMBER_TO_PICK = "number_to_pick";
	public static final String TREMP_DESCRIPTION = "description";
	public static final String TREMP_GENDER = "gender";
	public static final String TREMP_DATE = "date";
	public static final String TREMP_TIME = "time";
	public static final String TREMP_HISTORY = "archive";
	

	

	// class member
	private static Model instance;
	
	// private constructor
	private Model(Context context){
		myDb = new DatabaseHelper(context);

	}
	//public accessor
	public static Model getInstance(Context context){
		if (instance == null) {
			instance = new Model(context);
		}
		return instance;
	}



	public boolean addTremp(Tremp tremp, String table_name){
		Log.d("EB","Model addTremp "+tremp);
		
		ContentValues values = new ContentValues();
		
		values.put("_id", tremp.get_id());
		values.put("name", tremp.getName());
		values.put("address", tremp.getAddress());
		values.put("phone", tremp.getPhone());
		values.put("number_to_pick", tremp.getNumberToPick());
		values.put("description", tremp.getDescription());
		values.put("gender", tremp.getGender());
		values.put(TREMP_DATE, tremp.getDate());
		values.put(TREMP_TIME, tremp.getTime());
		values.put(TREMP_HISTORY, "0");
		

		SQLiteDatabase db = myDb.getWritableDatabase();
		//Get confirmation
		long rowId = db.insert(table_name, "_id", values);
		if (rowId <= 0) return false;
		return true;
	}

	public ArrayList<Tremp> getAllTremps(String table, String[] args){
		SQLiteDatabase db = myDb.getReadableDatabase();   
		String[] colomns = {"_id","name","address","phone","number_to_pick","description",
				"gender",TREMP_DATE,TREMP_TIME};
		String selection = TREMP_HISTORY + " = ?";
		String[] selectionArgs = args;
		Cursor cur = db.query(table, colomns, selection, selectionArgs, null, null, null);
		ArrayList<Tremp> list = new ArrayList<Tremp>();
		if (cur.moveToFirst()) {
			int idIndex = cur.getColumnIndex("_id");
			int nameIndex = cur.getColumnIndex("name");
			int addressIndex = cur.getColumnIndex("address");
			int phoneIndex = cur.getColumnIndex("phone");
			int number_to_pickIndex = cur.getColumnIndex("number_to_pick");
			int descriptionIndex = cur.getColumnIndex("description");
			int vegIndex = cur.getColumnIndex("gender");
			int dateIndex = cur.getColumnIndex(TREMP_DATE);
			int timeIndex = cur.getColumnIndex(TREMP_TIME);
			do{
				Tremp tremp = new Tremp();
				tremp.set_id(cur.getInt(idIndex));
				tremp.setName(cur.getString(nameIndex));
				tremp.setAddress(cur.getString(addressIndex));
				tremp.setPhone(cur.getString(phoneIndex));
				tremp.setNumberToPick(cur.getString(number_to_pickIndex));
				tremp.setDescription(cur.getString(descriptionIndex));
				tremp.setGender(cur.getInt(vegIndex));
				tremp.setDate(cur.getString(dateIndex));
				tremp.setTime(cur.getString(timeIndex));

				list.add(tremp);
			} while (cur.moveToNext());
		}
		return list;
	}

	public Tremp getTrempById(int id){
		SQLiteDatabase db = myDb.getReadableDatabase();   
		String[] colomns = {"_id","name","address","phone","number_to_pick","description",
				"gender",TREMP_DATE,TREMP_TIME};
		String selection = "_id = ?";
		String[] args = {Integer.toString(id)};
		Cursor cur = db.query("tremps", colomns, selection, args, null, null, null);
		if (cur.moveToFirst()) {
			int idIndex = cur.getColumnIndex("_id");
			int nameIndex = cur.getColumnIndex("name");
			int addressIndex = cur.getColumnIndex("address");
			int phoneIndex = cur.getColumnIndex("phone");
			int number_to_pickIndex = cur.getColumnIndex("number_to_pick");
			int descriptionIndex = cur.getColumnIndex("description");
			int vegIndex = cur.getColumnIndex("gender");
			int dateIndex = cur.getColumnIndex(TREMP_DATE);
			int timeIndex = cur.getColumnIndex(TREMP_TIME);
			
			Tremp tremp = new Tremp();
			tremp.set_id(cur.getInt(idIndex));
			tremp.setName(cur.getString(nameIndex));
			tremp.setAddress(cur.getString(addressIndex));
			tremp.setPhone(cur.getString(phoneIndex));
			tremp.setNumberToPick(cur.getString(number_to_pickIndex));
			tremp.setDescription(cur.getString(descriptionIndex));
			tremp.setGender(cur.getInt(vegIndex));
			tremp.setDate(cur.getString(dateIndex));
			tremp.setTime(cur.getString(timeIndex));

			return tremp;
		}
		return null;
	}

	void deleteTremp(int id){
		Tremp tremp = getTrempById(id);
		SQLiteDatabase db = myDb.getWritableDatabase();
		String selection = "_id = ?";
		String[] args = {Integer.toString(tremp.get_id())};
		ContentValues values = new ContentValues();
		values.put("_id", tremp.get_id());
		values.put("name", tremp.getName());
		values.put("address", tremp.getAddress());
		values.put("phone", tremp.getPhone());
		values.put("number_to_pick", tremp.getNumberToPick());
		values.put("description", tremp.getDescription());
		values.put("gender", tremp.getGender());
		values.put(TREMP_DATE, tremp.getDate());
		values.put(TREMP_TIME, tremp.getTime());
		values.put(TREMP_HISTORY, "1");

		//Get confirmation 
		long rowId = db.update("tremps",values,selection, args);
		/*
		if (rowId <= 0) return false;
		return true;
		
		//Add to archive table
		Tremp tremp = getTrempById(id);
		addTremp(tremp, TABLE_TREMPS_ARCHIVE);
		
		//Delete from Tremps table
		SQLiteDatabase db = myDb.getWritableDatabase();
		db.execSQL("delete from TREMPS where _id = '" + id + "';" );
		*/
	}

	public void deleteTrempFromHistory(int id){
		//Delete from Tremps table
		SQLiteDatabase db = myDb.getWritableDatabase();
		db.execSQL("delete from TREMPS where _id = '" + id + "';" );

	}
	
	public boolean editTremp(Tremp tremp){
		SQLiteDatabase db = myDb.getWritableDatabase();
		String selection = "_id = ?";
		String[] args = {Integer.toString(tremp.get_id())};
		ContentValues values = new ContentValues();
		values.put("_id", tremp.get_id());
		values.put("name", tremp.getName());
		values.put("address", tremp.getAddress());
		values.put("phone", tremp.getPhone());
		values.put("number_to_pick", tremp.getNumberToPick());
		values.put("description", tremp.getDescription());
		values.put("gender", tremp.getGender());
		values.put(TREMP_DATE, tremp.getDate());
		values.put(TREMP_TIME, tremp.getTime());
		values.put(TREMP_HISTORY, "0");

		//Get confirmation 
		long rowId = db.update("tremps",values,selection, args);
		if (rowId <= 0) return false;
		return true;
	}

	public int getLastID(){
		SQLiteDatabase db = myDb.getReadableDatabase();   
		String query = "SELECT _id from TREMPS order by _id DESC limit 1";
		Cursor c = db.rawQuery(query, null);
		long lastId = (long) 0.0;
		if (c != null && c.moveToFirst()) {
		    lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
		}
		return (int)lastId;
	}
	
	final int version = 1;

	class DatabaseHelper extends SQLiteOpenHelper {



		public DatabaseHelper(Context context){
			super(context, "datamodel.db", null , version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_TREMPS+" ;");
			db.execSQL("create table "+TABLE_TREMPS+" ("+TREMP_ID+" INTEGER PRIMARY KEY,"+TREMP_NAME+" TEXT, "+
					TREMP_ADDRESS+" TEXT, " +TREMP_PHONE+" TEXT, "+TREMP_NUMBER_TO_PICK+" TEXT,"+
					TREMP_DESCRIPTION+" TEXT, "+TREMP_GENDER+" INTEGER, "+TREMP_DATE+" TEXT, "+
					TREMP_TIME+" TEXT, "+TREMP_HISTORY+" TEXT);");
			/*
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_TREMPS_ARCHIVE+" ;");
			db.execSQL("create table "+TABLE_TREMPS+" ("+TREMP_ID+" INTEGER PRIMARY KEY,"+TREMP_NAME+" TEXT, "+
					TREMP_ADDRESS+" TEXT, " +TREMP_PHONE+" TEXT, "+TREMP_NUMBER_TO_PICK+" TEXT,"+
					TREMP_DESCRIPTION+" TEXT, "+TREMP_GENDER+" INTEGER, "+TREMP_DATE+" TEXT, "+
					TREMP_TIME+" TEXT);");
			
					
					*/
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {		
			onCreate(db);
		}
	}
}
