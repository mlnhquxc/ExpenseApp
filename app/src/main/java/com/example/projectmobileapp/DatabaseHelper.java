package com.example.projectmobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String myDb = "expenseApp.db";

    public DatabaseHelper(@Nullable Context context) {super(context, myDb, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Users(" +
                "Username TEXT primary key," +
                "Password TEXT, " +
                "Email TEXT unique, " +
                "Balance DECIMAL)");
        db.execSQL("create table TransactionGroups(" +
                "GroupID INTEGER primary key autoincrement, " +
                "GroupName TEXT)");
        db.execSQL("create table Transactions(" +
                "TransactionID INTEGER primary key autoincrement, " +
                "Username TEXT, " +
                "TransactionType TEXT CHECK(TransactionType IN ('Income', 'Expense')), " +
                "GroupID INTEGER, " +
                "Amount DECIMAL, " +
                "Notes TEXT, " +
                "TransactionDate DATE, " +
                "FOREIGN KEY (Username) REFERENCES Users(Username), " +
                "FOREIGN KEY (GroupID) REFERENCES TransactionGroups(GroupID))");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if EXISTS Users");
        db.execSQL("DROP table if EXISTS TransactionGroups");
        db.execSQL("DROP table if EXISTS Transactions");
    }

    //Thêm người dùng vào cơ sở dữ liệu
    public boolean addUser(String username, String password, String email, int balance) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("balance", balance);
        long result = myDB.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else return true;
    }

    //Kiểm tra username có tồn tại trong hệ thống hay chưa (đăng ký)
    public boolean checkUsername(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[]{username});
        if (cursor.getCount()>0) {
            return true;
        } else return false;
    }

    //Kiểm tra tài khoản người dùng có tồn tại trong hệ thống hay chưa (đăng nhập)
    public boolean checkUser(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() >0 ) {
            return true;
        } else return false;
    }

    //Cập nhật mật khẩu của người dùng
    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);
        int result = myDB.update("Users", contentValues, "username = ?", new String[]{username});
        return result > 0;
    }

    //Xóa người dùng
    public boolean deleteUser(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete("users", "username = ?", new String[]{username});
        return result > 0;
    }

    //Lấy email của người dùng
    public String getUserEmail(String username) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select Email from Users where Username = ?", new String[]{username});

        if (cursor != null && cursor.moveToFirst()) {
            int emailIndex = cursor.getColumnIndex("Email");
            if (emailIndex != -1) {
                String email = cursor.getString(emailIndex);
                cursor.close();
                return email;
            } else {
                cursor.close();
                return "Email column not found";
            }
        } else {
            return "Email not found";
        }
    }

    // Thêm nhóm giao dịch
    public boolean addTransactionGroup(String groupName) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        if (isGroupExists(groupName)) {
            return false;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("GroupName", groupName);
        long result = myDB.insert("TransactionGroups", null, contentValues);
        return result != -1;
    }

    // kiểm tra xem nhóm giao dịch đã tồn tại hay chưa
    public boolean isGroupExists(String groupName) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        String query = "SELECT * FROM TransactionGroups WHERE GroupName=?";
        Cursor cursor = myDB.rawQuery(query, new String[]{groupName});
        boolean exists = cursor.moveToFirst();

        cursor.close();
        return exists;
    }
}
