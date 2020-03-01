package com.example.workplacedamagemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Damage.db";
    public static final String TABLE_NAME = "damage_table";
    public static final String col_1 = "ID";
    public static final String col_2 = "NAME"; //actually hours
    public static final String col_3 = "DESCRIPTION"; //actually time
    public static final String col_4 = "DATEM"; //date
    public static final String col_5 = "DATED"; // really the global id
    public static final String col_6 = "DATEY"; // status
    private static final String TAG = "Message:";
    public static final String col_8 = "USERNAME";

    public static final String col_9 = "ROAD";




    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME,null,7);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT, DATEM TEXT, DATED TEXT, DATEY TEXT, USERNAME TEXT, SUBMITTED BOOLEAN, ROAD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTs " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name, String description, String dateM, String dateD, String dateY, String username, String road)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, name);
        contentValues.put(col_3, description);
        contentValues.put(col_4, dateM);
        contentValues.put(col_5, dateD);
        contentValues.put(col_6, dateY);
        contentValues.put(col_8, username);
        contentValues.put(col_9, road);


        long result  = db.insert(TABLE_NAME,null, contentValues);
        return(result != -1);

    }


    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + col_2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName, String des, String dM,String dD,String dY, String road){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_1, id);
            contentValues.put(col_2, newName);
            contentValues.put(col_3, des);
            contentValues.put(col_4, dM);
            contentValues.put(col_5, dD);
            contentValues.put(col_6, dY);
            contentValues.put(col_9, road);

            db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{Integer.toString(id)} );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + col_1 + " = '" + id + "'" +
                " AND " + col_2 + " = '" + name + "'";
        db.execSQL(query);
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME , null);
        return res;
    }

}
