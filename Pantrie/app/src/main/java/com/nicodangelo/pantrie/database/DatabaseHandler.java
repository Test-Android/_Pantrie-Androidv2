//@author Jake Cox

package com.nicodangelo.pantrie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.lang.Long;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper
{
    //LogCat tag : I really don't know what this is
    private static final String LOG = "DatabaseHandler";

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "pantrie_database";

    //Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_USERS_ITEMS = "users_items";

    //Common  column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    //USERS Table - column names
    private static final String KEY_USERS = "user";
    private static final String KEY_STATUS = "status";

    //ITEMS Table - column names
    private static final String KEY_NAME = "item_name";
    private static final String KEY_AMOUNT = "item_amount";
    private static final String KEY_LOW_AMOUNT = "item_low_amount";

    //USERS_ITEMS - Table column names
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ITEM_ID = "item_id";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Table Create Statements
///////////////////////////////////////////////////////////////////////////////////////////////////

    //USERS table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
            + "( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USERS + " TEXT, "
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME"
            + ");";

    //ITEMS table create statement
    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS
            + "( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT,"
            + KEY_AMOUNT + " INTEGER,"
            + KEY_LOW_AMOUNT + ",INTEGER,"
            + KEY_CREATED_AT + " DATETIME"
            + ");";

    //USERS_ITEMS table create statement
    private static final String CREATE_TABLE_USERS_ITEMS = "CREATE TABLE " + TABLE_USERS_ITEMS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_ITEM_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_USERS_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //TODO : might not need this if statement "does not call it if version has not changed?"
        //onUpgrade drop older tables
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS_ITEMS);

            //create new tables
            onCreate(db);
        }
    }

    //Create a User in the user table, at the same time assign that user with a
    //tag name inside USER_ITEM
    public long createUser(User user, long[] item_ids)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,user.getUsername());
        values.put(KEY_STATUS,user.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        //insert row
        Long user_id = db.insert(TABLE_USERS,null,values);

        //assigning tags to user
        for(Long item_id : item_ids)
        {
            createUserItem(user_id,item_id);
        }
        return user_id;
    }

    //Fetch a User from the USERS table
    //SELECT * FROM users WHERE id = 1;
    public User getUser(long user_id)
    {
        SQLiteDatabase db = getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_ID + " = " + user_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);

        if(c != null)
            c.moveToFirst();

        User u = new User();
        u.setId(c.getColumnIndex(KEY_ID));
        u.setUsername(c.getString(c.getColumnIndex(KEY_USERS)));
        u.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return u;
    }

    //Fetching all the Users
    //SELECT * FROM users
    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        //loopinf through all the rows and adding them to the list
        if(c.moveToFirst())
        {
            do{
                User u = new User();
                u.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                u.setUsername(c.getString(c.getColumnIndex(KEY_USERS)));
                u.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
            }while(c.moveToNext());
        }
        return users;
    }

    //TODO : this concept might break everything and its dog...
    //Fetching all Users under an Item Name
    //TODO : get the SQL command example for this method
    public List<User> getAllUSersByItem(String item_name)
    {
        List<User> users = new ArrayList<User>();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " tu, "
            + TABLE_ITEMS + "  ti, " + TABLE_USERS_ITEMS + " ui WHERE tg. "
            + KEY_NAME + " = '" + item_name + "'" + "AND ti." + KEY_ID + " = "
            + "ui." + KEY_ITEM_ID + "AND tu." + KEY_ID + " = "
            + "ui." + KEY_USER_ID;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through all the rows and adding to the list
        if(c.moveToFirst())
        {
            do{
                User u = new User();
                u.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                u.setUsername(c.getString(c.getColumnIndex(KEY_USERS)));
                u.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                //add all of them to the array
                users.add(u);
            }while(c.moveToNext());
        }
        return users;
    }

    //This will update a user, and the information
    public int updateUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERS, user.getUsername());
        values.put(KEY_STATUS, user.getStatus());

        //update row
        return db.update(TABLE_USERS, values, KEY_ID + " =  ?", new String[] {String.valueOf(user.getId())});
    }

    //delete a user there are two options for this
    public void deleteUser(long user_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?", new String[] {String.valueOf(user_id)});
    }

    //this method will delete a User, it will also delete all the user data(items) but this is optional
    //by putting true in the last parameter it will delete all the User data along with the user.
    //TODO : Make sure that all the items are deleted when the user is deleted...
    public void deleteUser(User user, boolean delete_all_items)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        //before deleting the user
        //make sure if you want to delete there items.
        if(delete_all_items)
        {
            //TODO
            //get all the items for the user

            //TODO (use a for loop?)
            ////delete all the items

            //TODO
            //delete item
        }

        //delete the User
        db.delete(TABLE_USERS, KEY_ID + " = ?", new String[] {String.valueOf(user.getId())});
    }

    //TODO : finish this.... i think i am doing it wrong??
    //deletes all the Items with a specified user
    public void deleteAllItems(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery1 = "SELECT * FROM " + TABLE_ITEMS;
        String selectQuery2 = "SELECT * FROM " + TABLE_USERS_ITEMS;
        Cursor a = db.rawQuery(selectQuery1, null);
        Cursor b = db.rawQuery(selectQuery2, null);

        if (a.moveToFirst() && b.moveToFirst())
        {
            do
            {

            }
            while (a.moveToNext() && b.moveToNext());
        }
        a.close();
        b.close();
    }

    //delete a single item from a user
    public void deleteUserItem(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?", new String[] {String.valueOf(id)});
    }


    //Following will insert a row into the Item table
    public long createItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_AMOUNT, item.getAmount());
        values.put(KEY_LOW_AMOUNT, item.getAmount());
        values.put(KEY_CREATED_AT, getDateTime());

        //inset the row
        long item_id = db.insert(TABLE_ITEMS, null, values);
        return  item_id;
    }

    //Fetvhing all the Items
    public List<Item> getAllItems()
    {
        List<Item> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through the items and add them to the arrayList
        if(c.moveToFirst())
        {
            do{
               Item i = new Item();
                i.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                i.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                i.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
                i.setLow_amount(c.getInt(c.getColumnIndex(KEY_LOW_AMOUNT)));

                //adding to the list
                items.add(i);
            }while(c.moveToNext());
        }
        return items;
    }

    //updating items
    public int updateItem(Item item)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_AMOUNT, item.getAmount());
        values.put(KEY_LOW_AMOUNT, item.getLow_amount());

        //update the row
        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?", new String[] {String.valueOf(item.getId())});
    }

    //Asigning an Item to a User
    public long createUserItem(long user_id, long item_id)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user_id);
        values.put(KEY_ITEM_ID, item_id);
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_USERS_ITEMS, null, values);

        return id;
    }

    //removing an item  from a User
    //TODO : make a method that will remove a item from a user

    //updating an item of a User
    public int updateUserItem(long id, long item_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, item_id);

        //update row
        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?", new String[] {String.valueOf(id)});
    }

    //close the database connection
    public void close()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db != null && db.isOpen())
        {
            db.close();
        }
    }

    //get the datetime
    private String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
