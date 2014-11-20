package com.example.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TABLE_ID = "_id";
	public static final String TABLE_NEWS_NAME = "news";
	public static final String TABLE_NEWS_TITLE = "title";
	public static final String TABLE_NEWS_SUMMARY = "summary";
	
	private static String NAME = "Fly.db";
	private static int VERSION = 1;

	//新闻表　：　主键　＋　标题　+ 摘要 
	public DBHelper(Context context) {
	        super(context, NAME, null, VERSION);
        }

	@Override
	public void onCreate(SQLiteDatabase db) {
		//建表
		db.execSQL("CREATE TABLE" + TABLE_NEWS_NAME + "(" + 
						TABLE_ID + "integer primary key autoincrement," + 
						TABLE_NEWS_TITLE + " varchar(50),"+
						TABLE_NEWS_SUMMARY + " VARCHAR(200))" 
						);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
