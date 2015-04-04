//@author Jake Cox
package com.nicodangelo.pantrie.database;

public class PantrieDBHandler
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "itemsDB.db";
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ITEMNAME = "itemname";
    public static sinal String COLUMN_ITEMAMOUNT = "itemamount";

    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        String query = "CREATE TABLE " + COLUMN_ITEMNAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEMNAME + " TEXT, " +
                COLUMN_ITEMAMOUNT + " TEXT "
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        db.execSQL("DROP TABLE IF EXISTS " + COLUMN_ITEMNAME);
        onCreate(db);
    }

    //Add a new row to the database
    public void addItem(Item item){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEMNAME, item.getName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteItem(String itemName)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = ("DELETE FROM "+ TABLE_ITEMS + " WHERE " + COLUMN_ITEMNAME + "='" + itemName + "' + ";");
        db.execSQL(query);
        System.out.println(query);
        System.out.println("this is the product " + COLUMN_ITEMNAME + "  " + itemName + "  " + COLUMN_ITEMAMOUNT);
    }

    public String databaseToString()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS + " WHERE 1;";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("itemname")) != null) 
            {
                dbString += c.getString(c.getColumnIndex("itemname"));
                dbString += " - " + c.getString(c.getColumnIndex("itemamount"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
