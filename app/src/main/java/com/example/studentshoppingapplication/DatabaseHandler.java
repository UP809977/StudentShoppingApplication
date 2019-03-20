package com.example.studentshoppingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    //database version
    private static final int DATABASE_VERSION = 1;

    //database name
    private static final String DATABASE_NAME = "ItemsTesting7.db";

    //table name
    public static final String TABLE_NAME = "Items";

    //Table fields
    public static final String C_ID = "ID";
    public static final String C_Item_Name = "Item_Name";
   //private static final String C_Supermarket= "Supermarket";
    public static final String C_Price= "Price";
    private static final String C_Barcode= "Barcode";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTable = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
                "("+C_ID +" Integer PRIMARY KEY autoincrement ," +
                ""+ C_Barcode +" text ," +
                ""+ C_Item_Name +" Text NOT NULL," +
                ""+ C_Price + " real NOT NULL )";
        database.execSQL(createTable);
        addTestItems();


    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);

    }

    public boolean insertNewItem(String barcode, String itemName, String price) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues newItem = new ContentValues();
        newItem.put(C_Barcode, barcode);
        newItem.put(C_Item_Name, itemName);
        newItem.put(C_Price, price);
        long output = database.insert(TABLE_NAME, null, newItem);
        if (output == -1) {return false;
        }
        else {
            return true;
        }

    }

    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor results = database.rawQuery("select * from "+TABLE_NAME,null);
        return results;
    }

    public void addTestItems(){
        ContentValues testItems = new ContentValues();
        testItems.put(C_Barcode, 1);
        testItems.put(C_Item_Name, "test1");
        testItems.put(C_Price, 1);
        testItems.put(C_Barcode, 2);
        testItems.put(C_Item_Name, "test2");
        testItems.put(C_Price, 2);
        testItems.put(C_Barcode, 3);
        testItems.put(C_Item_Name, "test3");
        testItems.put(C_Price, 3);

    }


}
