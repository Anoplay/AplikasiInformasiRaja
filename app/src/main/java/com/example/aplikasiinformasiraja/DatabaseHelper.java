package com.example.aplikasiinformasiraja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Raja.db";
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "ID";
    public static final String COL_USER_USERNAME = "USERNAME";
    public static final String COL_USER_PASSWORD = "PASSWORD";
    public static final String TABLE_KINGS = "kings";
    public static final String COL_KING_ID = "ID";
    public static final String COL_KING_NAME = "NAME";
    public static final String COL_KING_REIGN = "REIGN";
    public static final String COL_KING_DESC = "DESCRIPTION";
    public static final String COL_KING_IMAGE_PATH = "IMAGE_PATH";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_KINGS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, REIGN TEXT, DESCRIPTION TEXT, IMAGE_PATH TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KINGS);
        onCreate(db);
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_USERNAME, username);
        contentValues.put(COL_USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE USERNAME = ?", new String[]{username});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE USERNAME = ? AND PASSWORD = ?", new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean addKing(String name, String reign, String description, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_KING_NAME, name);
        contentValues.put(COL_KING_REIGN, reign);
        contentValues.put(COL_KING_DESC, description);
        contentValues.put(COL_KING_IMAGE_PATH, imagePath);
        long result = db.insert(TABLE_KINGS, null, contentValues);
        return result != -1;
    }

    public Cursor getAllKings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_KINGS, null);
    }

    public Cursor getKingById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_KINGS + " WHERE ID = ?", new String[]{id});
    }

    public boolean updateKing(String id, String name, String reign, String description, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_KING_NAME, name);
        contentValues.put(COL_KING_REIGN, reign);
        contentValues.put(COL_KING_DESC, description);
        contentValues.put(COL_KING_IMAGE_PATH, imagePath);
        int result = db.update(TABLE_KINGS, contentValues, "ID = ?", new String[]{id});
        return result > 0;
    }

    public Integer deleteKing(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_KINGS, "ID = ?", new String[]{id});
    }
}