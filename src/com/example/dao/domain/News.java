package com.example.dao.domain;

import com.example.dao.DBHelper;
import com.example.dao.annotation.Column;
import com.example.dao.annotation.ID;
import com.example.dao.annotation.TableName;

/**
 * 新闻实体
 * @author flystar
 *
 */
@TableName(DBHelper.TABLE_NEWS_NAME)
public class News 
{
	@ID(autoincrement=true)
	@Column(DBHelper.TABLE_ID)
	private int id;
	@Column(DBHelper.TABLE_NEWS_TITLE)
	private String title;
	@Column(DBHelper.TABLE_NEWS_SUMMARY)
	private String summary;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	} 
	
	
}
