package com.example.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import base.DAOSupport;

import com.example.dao.DBHelper;
import com.example.dao.NewsDao;
import com.example.dao.domain.News;

public class NewsDaoImpl extends DAOSupport<News> implements NewsDao {

	
	public NewsDaoImpl(Context context) {
	        super(context);
        }


}
