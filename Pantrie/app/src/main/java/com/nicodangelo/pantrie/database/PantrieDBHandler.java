//@author Jake Cox
package com.nicodangelo.pantrie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nicodangelo.pantrie.item.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PantrieDBHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "itemsDB.db";
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ITEMNAME = "itemname";
    public static final String COLUMN_ITEMAMOUNT = "itemamount";

    //We need to pass database information along to superclass
    public PantrieDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        String query = "CREATE TABLE " + TABLE_ITEMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEMNAME + " TEXT, " +
                COLUMN_ITEMAMOUNT + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        db.execSQL("DROP TABLE IF EXISTS " + "'" + COLUMN_ITEMNAME + "'");
        onCreate(db);
    }

    //Add a new row to the database
    public void addItem(Item item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEMNAME, item.getName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ITEMS, null, values);
        System.out.println("NEW ITEM: " + item.getName());
        db.close();
    }

    //Delete a product from the database
    public void deleteItem(String itemName)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = ("DELETE FROM "+ TABLE_ITEMS + " WHERE " + COLUMN_ITEMNAME + "='" + itemName + "';");
        db.execSQL(query);
        System.out.println(query);
        System.out.println("this is the product " + COLUMN_ITEMNAME + "  " + itemName + "  " + COLUMN_ITEMAMOUNT);
    }

    public void databaseToString(ArrayList<String> s)
    {
        s.clear();
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS;

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        if(c != null)
        {
            c.moveToFirst();

            //Position after the last row means the end of the results
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("itemname")) != null)
                {
                    dbString += c.getString(c.getColumnIndex("itemname"));
//                dbString += " - " + c.getString(c.getColumnIndex("itemamount"));
                    s.add(dbString);
                    dbString = "";
                }
                c.moveToNext();
            }
        }
        db.close();
    }

    public int getItemAmount(String itemName)
    {
        int amount = 0;
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " +  COLUMN_ITEMNAME + " FROM " + TABLE_ITEMS + " WHERE itemname='" + itemName + "';";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        amount = Integer.parseInt(c.getString(c.getColumnIndex("itemamount")));
        db.close();
        c.close();
        return amount;
    }
    public void setItemAmount(String itemName, int newAmount)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SET " + COLUMN_ITEMAMOUNT  + "=" + newAmount + "WHERE " + COLUMN_ITEMNAME + "='" + itemName +"';";
        db.execSQL(query);
        db.close();
    }

}
