package com.example.sanket.test;

 import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;

public class Helper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "books.db";
    public static final String TABLE_NAME = "book";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BY = "by";
    public static final String COLUMN_PUBLISHER = "pub";

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_TITLE + " TEXT, " + COLUMN_BY + " TEXT, " + COLUMN_PUBLISHER + " INTEGER" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }
    public void firstRun()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME); //delete all rows in a table
        db.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String fieldValue) {
        SQLiteDatabase db= getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + COLUMN_TITLE +"=\""+fieldValue+"\";" ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void addBook(String a, String b, int c) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, a);
        values.put(COLUMN_BY, b);
        values.put(COLUMN_PUBLISHER, String.valueOf(c));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Book> getTable() {
        ArrayList<Book> res = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1" ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        try {
            while (c.moveToNext())
            {
                Book book = new Book();
                book.setTitle(c.getString(0));
                book.setAuthor(c.getString(1));
                book.setPublisher(c.getInt(2));
                res.add(book);
            }
        } finally
        {
            c.close();
        }
        return res;

    }
    public void qty(String a)
    {
        String query= "UPDATE "+TABLE_NAME+" SET "+COLUMN_PUBLISHER+" = "+COLUMN_PUBLISHER+"+1"+" WHERE "+COLUMN_TITLE+"=\""+a+"\";";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(query);
    }
    public void price(String a)
    {
        String query= "UPDATE "+TABLE_NAME+" SET "+COLUMN_BY+" = "+COLUMN_BY+"+"+COLUMN_BY+"/"+COLUMN_PUBLISHER+" WHERE "+COLUMN_TITLE+"=\""+a+"\";";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(query);
    }
    public String sum()
    {
        String query= "SELECT SUM("+COLUMN_BY+") FROM "+TABLE_NAME+";";
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        String contact = cursor.getString(0);
        // return contact
        return contact;
    }
    public String quantity()
    {
        String query= "SELECT SUM("+COLUMN_PUBLISHER+") FROM "+TABLE_NAME+";";
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        String contact = cursor.getString(0);
        // return contact
        return contact;
    }

    String getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SElECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE +"=\"" + "abc" + "\";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        String contact = cursor.getString(0);
        // return contact
        return contact;
    }
}

