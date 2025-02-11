package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class dbConnection extends SQLiteOpenHelper {

    private static String dbName = "SQLite";
    private static String dbTable= "Users";
    private static int dbVersion = 1 ;

    private static String fname = "fname";
    private static String lname = "lname";
    private static String userName = "userName";
    private static String email = "email";
    private static String password = "password";

    public dbConnection(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        String query = "CREATE TABLE Users('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'fname' " +
                "TEXT, 'lname' TEXT, 'userName' TEXT UNIQUE, " +
                "'email' TEXT UNIQUE, 'password' TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    public boolean addUser(@NonNull Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fname, user.getFname());
        values.put(lname, user.getLname());
        values.put(userName, user.getUserName());
        values.put(email, user.getEmail());
        values.put(password, user.getPassword());

        long result = db.insert(dbTable, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String email, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Users WHERE email = ? OR userName = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean validateUser(String email, String pass) {

        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM Users WHERE email = ? OR password = ?";
            Cursor cursor = db.rawQuery(query, new String[]{email, pass});

            boolean isValid = false;
            if (cursor.moveToFirst()) {
                isValid = true;
            }
            cursor.close();
            db.close();
            return isValid;
        } catch (Exception e) {
            Log.e("validateUser Error","Exception: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }


    }}
