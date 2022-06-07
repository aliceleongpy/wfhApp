package com.example.wfhapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "wfhApp.db";
    public MyDatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String user = "CREATE TABLE user ("+
                "userID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userName TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "contact TEXT NOT NULL," +
                "loc TEXT NOT NULL " +
                ");";

        String book = "CREATE TABLE book ("+
                "bookID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userID INTEGER, " +
                "pickup TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "time TEXT, " +
                "hospital TEXT NOT NULL," +
                "ambulance TEXT," +
                "status TEXT NOT NULL,"+
                "type TEXT NOT NULL," +
                "FOREIGN KEY(userID) REFERENCES user(userID) " +
                ");";

        db.execSQL(user);
        db.execSQL(book);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS 'user'";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS 'book'";
        db.execSQL(query);

        onCreate(db);
    }

    public Cursor getUser(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT userName, password, contact, loc, userID FROM user WHERE userName = '" + name + "';", null);

        if(data.getCount() != 0){
            data.moveToFirst();
            return data;
        } else {
            return null;
        }

    }

    public Cursor getBook(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT * " +
                "FROM book INNER JOIN user ON book.userID = user.userID " +
                "WHERE user.userID =" + id + " AND book.status = 'On Going' ORDER BY bookID DESC LIMIT 1;", null);
        if(data.getCount() != 0){
            data.moveToFirst();
            return data;
        } else {
            return null;
        }
    }

    public Cursor getAllBook(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT * " +
                "FROM book INNER JOIN user ON book.userID = user.userID " +
                "WHERE user.userID =" + id + ";", null);
        if(data.getCount() != 0){
            data.moveToFirst();
            return data;
        } else {
            return null;
        }
    }

    public Cursor getID(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("SELECT userID " +
                "FROM user WHERE userName ='" + name + "';", null);
        data.moveToFirst();
        return data;
    }


    public void addUser(String name, String password, String contact, String loc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userName", name);
        cv.put("password", password);
        cv.put("loc", loc);
        cv.put("contact", contact);

        long result = db.insert("user", null, cv);

        if (result != -1) {
            Log.d("Click", "Success");
        } else {
            Log.d("Click", "Failed");
        }
    }

    public void addBooking(String id, String pickup, String size, String hospital, String date, String time, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userID", id);
        cv.put("pickup", pickup);
        cv.put("ambulance", size);
        cv.put("hospital", hospital);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("status", "On Going");
        cv.put("type", type);

        long result = db.insert("book", null, cv);

        if (result != -1) {
            Log.d("Click", "Success");
        } else {
            Log.d("Click", "Failed");
        }
    }

    public void updateUser(int id, String name, String contact, String loc){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE user SET userName = '" + name + "' WHERE userID = " + id + ";";
        db.execSQL(query);

        query = "UPDATE user SET  loc = '" + loc + "' WHERE userID = " + id + ";";
        db.execSQL(query);

        query = "UPDATE user SET contact = '" + contact + "' WHERE userID = " + id + ";";
        db.execSQL(query);
    }
}
