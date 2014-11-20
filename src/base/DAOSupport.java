package base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dao.DBHelper;
import com.example.dao.annotation.Column;
import com.example.dao.annotation.ID;
import com.example.dao.annotation.TableName;

public abstract class DAOSupport<M> implements DAO<M>
{
	protected Context context;
	protected DBHelper helper;
	protected SQLiteDatabase db;
	public DAOSupport(Context context) {
	        super();
	        this.context = context;
	        helper = new DBHelper(context);
	        db = helper.getWritableDatabase();
        }
	@Override
        public long insert(M m) {
		ContentValues values = new ContentValues();
		
		fillColumn(m,values);
		
	        return db.insert(getTableName(), null, values);
        }
	
	@Override
        public int delete(Serializable id) {
	        return db.delete(getTableName(), DBHelper.TABLE_ID + " = ?", new String[]{id.toString()});
        }
	@Override
        public int update(M m) {
		ContentValues values = new ContentValues();
		fillColumn(m,values);
	        return db.update(getTableName(), values, DBHelper.TABLE_ID + " = ?", new String[]{getId(m)});
        }
	
	@Override
        public List<M> findAll() {
		List<M> result = null;
		Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);
		
		if(cursor != null)
		{
			result = new ArrayList<M>();
			while(cursor.moveToNext())
			{
				M m = getInstance();
				
				fillField(cursor,m);
				
				result.add(m);
			}
			cursor.close();
		}
		
	        return result;
        }
	
	
	private String getTableName()
	{
		//每个表对应一个实体
		//方案一:如果能够获取到实体,获取实体的简单名字,首字母小写
		//方案二:利用注解,实体和数据库表的名称脱离关系
		
		//获取到实体
		M m  =  getInstance();
		
		//获取实体上的注解, 依据value里设置的值确定操作的数据表 
		TableName tableName = m.getClass().getAnnotation(TableName.class);  // annotationType:注解的类型
		if(tableName != null)
		{
			return tableName.value();
		}
		
		return "";
	}
	
	/**
	 * 实体对象的创建 
	 * @return
	 */
	private M getInstance()
	{ 
		//1,那个孩子调用的方法
		//返回此Object的运行时类
		Class clazz = getClass();
		//2,获取孩子的父类("支持泛型的父类")
		Type supperclass = clazz.getGenericSuperclass();
		//泛型实现接口(参数化的类型), 规定了泛型的通用操作
		if(supperclass != null && supperclass instanceof ParameterizedType)
		{
			Type[] arguments = ((ParameterizedType)supperclass).getActualTypeArguments();
			try {
	                      return   (M) ((Class)arguments[0]).newInstance();
                        } catch (Exception e) {
	                        e.printStackTrace();
                        }
		}
		return null;
	}
	
	/**
	 * 将实体中的数据,按照对应的关系导入到数据库中
	 * @param m
	 * @param values
	 */
	private void fillColumn(M m, ContentValues values) {
	       Field[] fields = m.getClass().getDeclaredFields();
	       
	       for(Field item : fields)
	       {
		       item.setAccessible(true);
		       Column column = item.getAnnotation(Column.class);
		       if(column != null)
		       {
			       String key = column.value();
			       try {
	                        String value = item.get(m).toString();
	                        //如果该field是主键,并且是自增的,不能添加到集合中
	                        ID id = item.getAnnotation(ID.class);
	                        if(id != null && id.autoincrement())
	                        {
	                        }
	                        else
	                        {
	                        	values.put(key, value);
	                        }
                        } catch (IllegalAccessException | IllegalArgumentException e) {
	                        e.printStackTrace();
                        }
		       }
	       }
        }
	
	/**
	 * 明确实体中的主键,获取到主键中封装的值 
	 * @param m
	 * @return
	 */
	private String getId(M m) {
		Field[] fields = m.getClass().getDeclaredFields();
		for(Field item : fields)
		{
			item.setAccessible(true);
			ID id = item.getAnnotation(ID.class);
			if(id != null)
			{
				try {
	                                return item.get(m).toString();
                                } catch (IllegalAccessException | IllegalArgumentException e) {
	                                e.printStackTrace();
                                }
			}
		}
	        return null;
        }
	
	
	/**
	 * 将数据表中列的数据,按照对应关系导入到实体中
	 * @param cursor
	 * @param m
	 */
	private void fillField(Cursor cursor, M m) {
	        Field[] fields = m.getClass().getDeclaredFields();
	        for(Field item : fields)
	        {
	        	item.setAccessible(true);
	        	Column column = item.getAnnotation(Column.class);
	        	if(column != null)
	        	{
	        		int columnIndex = cursor.getColumnIndex(column.value());
	        		String value = cursor.getString(columnIndex);
	        		try {
	        			if(item.getType() == int.class)
	        			{
	        				item.set(m, Integer.parseInt(value));
	        			}
	        			else
	        			{
	        				item.set(m, value);
	        			}
	        			
                                } catch (IllegalAccessException | IllegalArgumentException e) {
	                                e.printStackTrace();
                                }
	        	}
	        }
        }
	
	
	
	
	
	
	
	
	
	
}

