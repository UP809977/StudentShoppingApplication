package com.example.studentshoppingapplication;

//imports
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
    public static final String C_Price= "Price";
    public static final String C_Barcode= "Barcode";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //used to create the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTable = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
                "("+C_ID +" Integer PRIMARY KEY autoincrement ," +
                ""+ C_Barcode +" text ," +
                ""+ C_Item_Name +" Text NOT NULL," +
                ""+ C_Price + " real NOT NULL )";
        database.execSQL(createTable);


    }

    //used to upgrade the database, not currently used in the system
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);

    }

    //used to take the data from the AddNewItem class and add it to the database
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

    //used to update the data using data sent from the EditItem class, ID field used as it is unique to each item
    public boolean upDateItem (String id,String barcode, String itemName, String price){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues edirItem = new ContentValues();
        edirItem.put(C_Barcode,barcode);
        edirItem.put(C_Item_Name, itemName);
        edirItem.put(C_Price, price);
        database.update(TABLE_NAME,edirItem," ID = ? ",new String[]{ id });
        return true;

    }


}
