package com.example.madfinal.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "taskDB.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MonthPlans.MnthTask.TABLE_NAME + " (" +
                    MonthPlans.MnthTask._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MonthPlans.MnthTask.COLUMN_1 + " TEXT," +
                    MonthPlans.MnthTask.COLUMN_2 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MonthPlans.MnthTask.TABLE_NAME;


    public long addTask(String nickName, String month){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MonthPlans.MnthTask.COLUMN_1, nickName);
        values.put(MonthPlans.MnthTask.COLUMN_2, month);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MonthPlans.MnthTask.TABLE_NAME, null, values);

        return  newRowId;
    }

    public boolean updateTask(String id,String nickName, String month){
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        //String title = "MyNewTitle";
        ContentValues values = new ContentValues();

        values.put(MonthPlans.MnthTask.COLUMN_1 , nickName);
        values.put(MonthPlans.MnthTask.COLUMN_2 , month);


        // Which row to update, based on the title
        String selection = MonthPlans.MnthTask._ID+ " LIKE ?";
        String[] selectionArgs = { id };

        int count = db.update(MonthPlans.MnthTask.TABLE_NAME, values, selection, selectionArgs);

        if (count >= 1){
            return true;
        }
        else{
            return  false;
        }

    }

    public void deleteTask(String nickName){

        SQLiteDatabase db = getWritableDatabase();

        // Define 'where' part of query.
        String selection = MonthPlans.MnthTask.COLUMN_1 + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { nickName };
        // Issue SQL statement.
        int deletedRows = db.delete(MonthPlans.MnthTask.TABLE_NAME, selection, selectionArgs);

    }

    public  ArrayList readTask(){
        SQLiteDatabase db = getReadableDatabase();
        String nickName = "happy";

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                MonthPlans.MnthTask.COLUMN_1,
                MonthPlans.MnthTask.COLUMN_2,

        };

            // Filter results WHERE "title" = 'My Title'
            String selection = MonthPlans.MnthTask.COLUMN_1 + " = ?";
             String[] selectionArgs = { nickName };

           // How you want the results sorted in the resulting Cursor
        String sortOrder = MonthPlans.MnthTask.COLUMN_1 + " ASC";

        Cursor cursor = db.query(
                MonthPlans.MnthTask.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList taskInfo = new ArrayList<>();
        while(cursor.moveToNext()) {
            String nickN = cursor.getString(cursor.getColumnIndexOrThrow(MonthPlans.MnthTask.COLUMN_1));
            String month = cursor.getString(cursor.getColumnIndexOrThrow(MonthPlans.MnthTask.COLUMN_2));
//            String month = cursor.getString(cursor.getColumnIndexOrThrow(MonthPlans.MnthTask.COLUMN_3));
            taskInfo.add(nickN);//1 index
            taskInfo.add(month);//2 index
//            taskInfo.add(month);//3 index
        }
        cursor.close();
        return taskInfo;



    }
    public  List readTask(String nickName){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                MonthPlans.MnthTask.COLUMN_1,
                MonthPlans.MnthTask.COLUMN_2,

        };

//         Filter results WHERE "title" = 'My Title'
        String selection = MonthPlans.MnthTask.COLUMN_1 + " LIKE ?";
         String[] selectionArgs = { nickName };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MonthPlans.MnthTask.COLUMN_1 + " ASC";

        Cursor cursor = db.query(
                MonthPlans.MnthTask.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List taskInfo = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(MonthPlans.MnthTask._ID));
            String Name = cursor.getString(cursor.getColumnIndexOrThrow(MonthPlans.MnthTask.COLUMN_1));
            String month = cursor.getString(cursor.getColumnIndexOrThrow(MonthPlans.MnthTask.COLUMN_2));

            taskInfo.add(Name);//0 index
            taskInfo.add(month);//1 index
            taskInfo.add(id);

        }
        cursor.close();
        return taskInfo;



    }

}
