package com.mobilemerit.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobilemerit.javafiles.ItemsDetails;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 18;

	// Database Name
	private static final String DATABASE_NAME = "inventory";

	// Contacts table name
	private static final String TABLE_ITEM = "items";
	private static final String TABLE_CATEGORY = "category";
	
	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_CAT_ID = "cat_id";
	private static final String KEY_COMPANY = "company";
	private static final String KEY_PRICE = "price";
	private static final String KEY_QUANTITY = "quantity";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_DATE = "date";
	private static final String KEY_PRODUCT_ID = "productId";
	private static final String KEY_BRAND	 = "brand";
	private static final String KEY_NOTIFY_LIMIT = "min_limit";
	//
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ITEMS_TABLES = " CREATE TABLE " + TABLE_ITEM
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_DETAILS + " TEXT, "
				+ KEY_CAT_ID + " INTEGER, " + KEY_COMPANY + " TEXT, "
				+ KEY_PRICE + " INTEGER, " + KEY_QUANTITY + " INTEGER, "
				+ KEY_LOCATION + " TEXT, " +  KEY_IMAGE + " BLOB, "
				+ KEY_DATE + " TEXT, " + KEY_PRODUCT_ID+ " TEXT, "
				+ KEY_BRAND + " TEXT, "+ KEY_NOTIFY_LIMIT + " INTEGER "+ ")";
		
		String CREATE_CATEGORY_TABLES = " CREATE TABLE " + TABLE_CATEGORY
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT " + ")";

		db.execSQL(CREATE_ITEMS_TABLES);
		db.execSQL(CREATE_CATEGORY_TABLES);		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed	
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);	
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);	
		// Create tables again
		onCreate(db);
	}
	
	public String saveItemDetails(ItemsDetails details){
		try{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv=new ContentValues();
	//	cv.put(KEY_ID, post.getId());
	//	cv.put(KEY_ID, "null");
		cv.put(KEY_NAME, details.getName());
		cv.put(KEY_DETAILS, details.getDetails());
		cv.put(KEY_CAT_ID, details.getCatID());
		cv.put(KEY_COMPANY, details.getCompany());
		cv.put(KEY_PRICE, details.getPrice());
		cv.put(KEY_QUANTITY,details.getQuantity());
		cv.put(KEY_LOCATION,details.getLocation());
		cv.put(KEY_IMAGE, details.getImage());	
		cv.put(KEY_DATE, details.getDate());
		cv.put(KEY_PRODUCT_ID, details.getProductID());
		cv.put(KEY_BRAND, details.getBrand());
		cv.put(KEY_NOTIFY_LIMIT, details.getNotifyLimit());
		db.insert(TABLE_ITEM, null, cv);		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return getNewItemDetails();
	}

	public boolean updateItems(String id,ItemsDetails details){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_ITEM+
				" WHERE "+ KEY_ID+" = ?", new String[]{String.valueOf(id)});
		if(cursor.moveToNext()){			
						
					SQLiteDatabase dbW=this.getWritableDatabase();					
					ContentValues cv=new ContentValues();
					cv.put(KEY_NAME, details.getName());
					cv.put(KEY_DETAILS, details.getDetails());
					cv.put(KEY_CAT_ID, details.getCatID());
					cv.put(KEY_COMPANY, details.getCompany());
					cv.put(KEY_PRICE, details.getPrice());
					cv.put(KEY_QUANTITY,details.getQuantity());
					cv.put(KEY_LOCATION,details.getLocation());
					cv.put(KEY_IMAGE, details.getImage());	
					cv.put(KEY_DATE, details.getDate());
					cv.put(KEY_PRODUCT_ID, details.getProductID());
					cv.put(KEY_BRAND, details.getBrand());
					cv.put(KEY_NOTIFY_LIMIT, details.getNotifyLimit());
					dbW.update(TABLE_ITEM, cv, KEY_ID +"= ?",new String[]{String.valueOf(id)});
					dbW.close();
				}
		return true;
	} 
	
	public String getNewItemDetails(){
		String id = null;
		SQLiteDatabase db=this.getReadableDatabase();
		String query="SELECT * FROM "+TABLE_ITEM;
		try{
			Cursor cursor=db.rawQuery(query, null);
			if(cursor.moveToLast()){				
				
				id = ""+cursor.getInt(0);
				
			}
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		db.close();
		return id;
	}
	
	public boolean isItemsExists(){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + TABLE_ITEM;
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}
	
	public ItemsDetails getItemDetailsFromID(String id){
		ItemsDetails details=new ItemsDetails();
		SQLiteDatabase db=this.getReadableDatabase();
		String query="SELECT * FROM "+TABLE_ITEM+" WHERE "+KEY_ID+ " =?";
		try{
			Cursor cursor=db.rawQuery(query, new String[]{String.valueOf(id)});
			if(cursor.moveToFirst()){				
				
				details.setId(cursor.getInt(0));
				details.setName(cursor.getString(1));
				details.setDetails(cursor.getString(2));
				details.setCatID(cursor.getInt(3));
				details.setCompany(cursor.getString(4));
				details.setPrice(cursor.getInt(5));
				details.setQuantity(cursor.getInt(6));
				details.setLocation(cursor.getString(7));					
				details.setImage(cursor.getBlob(8));	
				details.setDate(cursor.getString(9));
				details.setProductID(cursor.getString(10));
				details.setBrand(cursor.getString(11));
				details.setNotifyLimit(cursor.getInt(12));
			}
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		db.close();
		return details;
	}
	
	public List<ItemsDetails>  getItemsDetails(){
		List<ItemsDetails> detailsList=new ArrayList<ItemsDetails>();
		String selectQuery = "SELECT  * FROM " + TABLE_ITEM;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.rawQuery(selectQuery, null);		
		if(cursor.moveToFirst()){
			do{
				try{
					ItemsDetails details=new ItemsDetails();
					details.setId(cursor.getInt(0));
					details.setName(cursor.getString(1));
					details.setDetails(cursor.getString(2));
					details.setCatID(cursor.getInt(3));
					details.setCompany(cursor.getString(4));
					details.setPrice(cursor.getInt(5));
					details.setQuantity(cursor.getInt(6));
					details.setLocation(cursor.getString(7));					
					details.setImage(cursor.getBlob(8));	
					details.setDate(cursor.getString(9));
					details.setProductID(cursor.getString(10));
					details.setBrand(cursor.getString(11));
					details.setNotifyLimit(cursor.getInt(12));
					detailsList.add(details);
				}catch(Exception e){
					e.printStackTrace();
				}
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return detailsList;
	}
	
	
	public List<ItemsDetails> getItemsDetailsFromCategory(String id){
		List<ItemsDetails> detailsList=new ArrayList<ItemsDetails>();
		SQLiteDatabase db=this.getReadableDatabase();
		String query="SELECT * FROM "+TABLE_ITEM+" WHERE "+KEY_CAT_ID+ " =?";
		try{
			Cursor cursor=db.rawQuery(query, new String[]{String.valueOf(id)});
			if(cursor.moveToFirst()){
				
				
				ItemsDetails details=new ItemsDetails();
				details.setId(cursor.getInt(0));
				details.setName(cursor.getString(1));
				details.setDetails(cursor.getString(2));
				details.setCatID(cursor.getInt(3));
				details.setCompany(cursor.getString(4));
				details.setPrice(cursor.getInt(5));
				details.setQuantity(cursor.getInt(6));
				details.setLocation(cursor.getString(7));					
				details.setImage(cursor.getBlob(8));
				details.setDate(cursor.getString(9));
				details.setProductID(cursor.getString(10));
				details.setBrand(cursor.getString(11));
				details.setNotifyLimit(cursor.getInt(12));
				detailsList.add(details);
			}
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		db.close();
		return detailsList;
	}
	
	public int deleteItemsDetails(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("DELETE FROM "+TABLE_ITEM,null);
		return cursor.getCount();
	}	
	
	public int deleteItemsDetailsByID(String id){
		SQLiteDatabase db=this.getWritableDatabase();
		try{
			db.delete(TABLE_ITEM, KEY_ID +" =?", new String[]{String.valueOf(id)});
		}catch(Exception e){
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	/*category function*/
	public void saveCategory(ItemsDetails details){
		try{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv=new ContentValues();
	//	cv.put(KEY_ID, post.getId());
	//	cv.put(KEY_ID, "null");
		cv.put(KEY_NAME, details.getName());				
		db.insert(TABLE_CATEGORY, null, cv);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ItemsDetails getCategoryFromID(String id){
		ItemsDetails details=new ItemsDetails();
		SQLiteDatabase db=this.getReadableDatabase();
		String query="SELECT * FROM "+TABLE_CATEGORY+" WHERE "+KEY_ID+ " =?";
		try{
			Cursor cursor=db.rawQuery(query, new String[]{String.valueOf(id)});
			if(cursor.moveToFirst()){						
				details.setId(cursor.getInt(0));
				details.setName(cursor.getString(1));				
			}
			cursor.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		db.close();
		return details;
	}
	public boolean isCotegoriesExists(){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + TABLE_CATEGORY;
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}
	
	public List<ItemsDetails>  getCategories(){
		List<ItemsDetails> detailsList=new ArrayList<ItemsDetails>();
		String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.rawQuery(selectQuery, null);		
		if(cursor.moveToFirst()){
			do{
				try{
					ItemsDetails details=new ItemsDetails();
					details.setId(cursor.getInt(0));
					details.setName(cursor.getString(1));
					
					detailsList.add(details);
				}catch(Exception e){
					e.printStackTrace();
				}
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return detailsList;
	}	
	
	public int deleteCategories(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("DELETE FROM "+TABLE_CATEGORY,null);
		return cursor.getCount();
	}	
	
	public int deleteCategoryByID(int id){
		SQLiteDatabase db=this.getWritableDatabase();
		try{
			db.delete(TABLE_CATEGORY, KEY_ID +" =?", new String[]{String.valueOf(id)});
		}catch(Exception e){
			e.printStackTrace();
			return 1;
		}
		return 0;
	}	
}