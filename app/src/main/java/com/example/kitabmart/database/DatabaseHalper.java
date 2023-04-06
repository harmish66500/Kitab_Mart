package com.example.kitabmart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.kitabmart.Models.kitabmartModel;

public class DatabaseHalper extends SQLiteOpenHelper {

    public static  String DATABASE_NAME = "Cart.db";
    public static  int DATABASE_VERSION = 1;


    public static  String TABLE_NAME = "carttable";
    public static  String TABLE_NAME1 = "favtable";
    public static  String KEY_ID = "id";
    public static  String T_NAME = "tname";
    public static  String AUTHOR = "author";
    public static  String TYPE = "type";
    public static  String SINE = "sine";
    public static  String PHOTO = "Photo";
    public static  String PRICE = "price";
    public static  String PDF = "pdf";

    public DatabaseHalper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +" (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                T_NAME + " TEXT , " +
                AUTHOR + " TEXT , " +
                TYPE + " TEXT , " +
                SINE + " TEXT , " +
                PHOTO + " TEXT , " +
                PRICE + " TEXT , "+
                PDF + " TEXT )" ;



        String query1 = "CREATE TABLE " + TABLE_NAME1 +" (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                T_NAME + " TEXT , " +
                AUTHOR + " TEXT , " +
                TYPE + " TEXT , " +
                SINE + " TEXT , " +
                PHOTO + " TEXT , " +
                PRICE + " TEXT , "+
                PDF + " TEXT )" ;
        db.execSQL(query);
        db.execSQL(query1);
    }


    @Override
    public void onUpgrade (SQLiteDatabase db, int  oldVersion, int newVersion ){
    db.execSQL("DROP TABLE"+ TABLE_NAME);
    db.execSQL("DROP TABLE"+ TABLE_NAME1);

    onCreate(db);
    }
    public void insert(kitabmartModel kitabmartModel ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_NAME , kitabmartModel.getTitalName());
        contentValues.put(AUTHOR , kitabmartModel.getAuthor());
        contentValues.put(TYPE , kitabmartModel.getType());
        contentValues.put(SINE , kitabmartModel.getSine());
        contentValues.put(PHOTO , kitabmartModel.getPhoto());
        contentValues.put(PRICE , kitabmartModel.getPrice());
        contentValues.put(PDF , kitabmartModel.getPdf());
        db.insert(TABLE_NAME, null,contentValues);
    }
    public void insert1(kitabmartModel kitabmartModel ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_NAME , kitabmartModel.getTitalName());
        contentValues.put(AUTHOR , kitabmartModel.getAuthor());
        contentValues.put(TYPE , kitabmartModel.getType());
        contentValues.put(SINE , kitabmartModel.getSine());
        contentValues.put(PHOTO , kitabmartModel.getPhoto());
        contentValues.put(PRICE , kitabmartModel.getPrice());
        contentValues.put(PDF , kitabmartModel.getPdf());
        db.insert(TABLE_NAME1, null,contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME,null);
        return cursor;
    }
    public Cursor getData1() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME1,null);
        return cursor;
    }
    public void deleteItem(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+" = ?",new String[]{id});
    }
    public void deleteCart(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }


}
