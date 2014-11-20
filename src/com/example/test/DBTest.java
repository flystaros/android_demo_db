package com.example.test;

import com.example.dao.DBHelper;

import android.test.AndroidTestCase;

public class DBTest extends AndroidTestCase 
{
	public void  creatTable()
	{
		DBHelper dbHelper = new DBHelper(getContext());
		dbHelper.getWritableDatabase();
	}
}
