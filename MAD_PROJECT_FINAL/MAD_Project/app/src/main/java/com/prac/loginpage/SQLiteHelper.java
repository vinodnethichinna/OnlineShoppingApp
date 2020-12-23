package com.prac.loginpage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DatabaseVersion = 2;
    public static final String DatabaseName = "myShopping.db";

    public SQLiteHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("oncreate :","creating");
        this.queryData(db,"CREATE TABLE IF NOT EXISTS ITEMS(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB)");
        this.queryData(db,"CREATE TABLE IF NOT EXISTS USERDETAILS(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR, phone VARCHAR NULL, age VARCHAR NULL, email VARCHAR NULL )");
        this.queryData(db,"CREATE TABLE IF NOT EXISTS CART(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, qty VARCHAR, size VARCHAR, price VARCHAR)");
        this.queryData(db,"CREATE TABLE IF NOT EXISTS WISHLIST(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, qty VARCHAR, size VARCHAR, price VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d("Downgrade :","Deleting");
        this.queryData(db,"drop table if exists ITEMS");
        this.queryData(db,"drop table if exists USERDETAILS");
        this.queryData(db,"drop table if exists CART");
        this.queryData(db,"drop table if exists WISHLIST");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.queryData(db,"drop table if exists ITEMS");
        this.queryData(db,"drop table if exists USERDETAILS");
        this.queryData(db,"drop table if exists CART");
        this.queryData(db,"drop table if exists WISHLIST");
        onCreate(db);
    }

    public void queryData(SQLiteDatabase db,String sql) {
        db.execSQL(sql);
    }

    public boolean checkUser(String username, String password) {
        boolean flag;
        String[] columns = {};
        SQLiteDatabase db = getReadableDatabase();
        String selection = "name" + "=?" + " and " + "password" + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("USERDETAILS", columns, selection, selectionArgs, null, null, null);
        // Cursor cursor=db.rawQuery("select * from USERDETAILS",null);
        int count = cursor.getCount();


        String s;
        if (count > 0) {
            flag = true;

            cursor.moveToNext();

        } else
            flag = false;

        cursor.close();
        db.close();

        return flag;
    }

    public void addtoWishList(String name, String QTY, String size, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("qty", QTY);
        values.put("size", size);
        values.put("price", price);
        db.insert("WISHLIST", null, values);
        db.close();
    }

    public List<String> getWishList() {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean flag;
        String[] columns = {};
        //db.getReadableDatabase();
        String selection = "";
        String[] selectionArgs = {};
        Cursor cursor;
        cursor = db.query("WISHLIST", null, null, null, null, null, null);

        int count = cursor.getCount();

        List<String> myList = new ArrayList<>();
        if (count > 0) {
            while (cursor.moveToNext()) {
                myList.add(cursor.getString(0) + "," + cursor.getString(1) + ',' + cursor.getString(2) + ',' + cursor.getString(3) + ',' + cursor.getString(4));
            }
        }

        cursor.close();
        db.close();
        return myList;
    }

    public void deleteWishListItem(String idNum) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "Id=?";
        db.delete("WISHLIST", where, new String[]{idNum});
        db.close();
    }


    public void addtoCart(String name, String QTY, String size, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("qty", QTY);
        values.put("size", size);
        values.put("price", price);
        db.insert("CART", null, values);
        db.close();
    }

    public List<String> getCart() {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean flag;
        String[] columns = {};
        String selection = "";
        String[] selectionArgs = {};
        Cursor cursor;
        cursor = db.query("CART", null, null, null, null, null, null);

        int count = cursor.getCount();

        List<String> myList = new ArrayList<>();
        if (count > 0) {
            while (cursor.moveToNext()) {
                myList.add(cursor.getString(0) + "," + cursor.getString(1) + ',' + cursor.getString(2) + ',' + cursor.getString(3) + ',' + cursor.getString(4));
            }
        }

        cursor.close();
        return myList;
    }

    public void deleteCartItem(String idnum) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "Id=?";
        int numberOFEntriesDeleted = db.delete("CART", where, new String[]{idnum});
    }

    public List<String> getItemList() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query("ITEMS", null, null, null, null, null, null);

        int count = cursor.getCount();

        List<String> myList = new ArrayList<>();
        if (count > 0) {
            while (cursor.moveToNext()) {
                myList.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getBlob(3));
            }
        }

        cursor.close();
        return myList;
    }

    public boolean register(String name, String password, String phone, String age, String email) {

        SQLiteDatabase sqlDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("phone", phone);
        contentValues.put("age", age);
        contentValues.put("email", email);


        long res = sqlDb.insert("USERDETAILS", null, contentValues);

        sqlDb.close();
        if (res > 0) {
            return true;
        } else
            return false;
    }


    public void insertData(String name, String price, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ITEMS VALUES (NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);

        statement.executeInsert();
    }

    public void updateData(String name, String price, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE ITEMS SET name = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double) id);

        statement.execute();
        database.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM ITEMS WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    public boolean ifDummyDataNotExists(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query("ITEMS", null, null, null, null, null, null);

        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount<=0;
    }

}
