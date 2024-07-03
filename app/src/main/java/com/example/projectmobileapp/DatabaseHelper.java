package com.example.projectmobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.projectmobileapp.model.TransactionGroups;
import com.example.projectmobileapp.model.Transactions;
import com.example.projectmobileapp.model.User;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String myDb = "expenseApp.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, myDb, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Users(" +
                "Username TEXT primary key," +
                "Password TEXT, " +
                "Email TEXT unique, " +
                "Balance DECIMAL)");
        db.execSQL("create table TransactionGroups(" +
                "GroupID INTEGER primary key autoincrement, " +
                "GroupName TEXT, " +
                "TransactionType TEXT CHECK(TransactionType IN ('Income', 'Expense')))");
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    //Kiểm tra tài khoản người dùng có tồn tại trong hệ thống hay chưa (đăng nhập)
    public boolean checkUser(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
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
    public boolean addTransactionGroup(String groupName, String transactionType) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        if (isGroupExists(groupName)) {
            return false;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put("GroupName", groupName);
        contentValues.put("TransactionType", transactionType);

        long result = myDB.insert("TransactionGroups", null, contentValues);
        if (result == -1) {
            return false;
        } else
            return true;
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

    public List<TransactionGroups> getTransactionGroupsList() {
        List<TransactionGroups> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select * from TransactionGroups", new String[]{});
        if (cursor.moveToFirst()) {
            do {
                int GroupID = cursor.getInt(cursor.getColumnIndexOrThrow("GroupID"));
                String GroupName = cursor.getString(cursor.getColumnIndexOrThrow("GroupName"));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow("TransactionType"));
                TransactionGroups transactionGroups = new TransactionGroups(GroupID, GroupName, transactionType);
                list.add(transactionGroups);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public List<TransactionGroups> getTransactionGroupsExpenseList() {
        List<TransactionGroups> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select * from TransactionGroups where TransactionType = ?", new String[]{"Expense"});
        if (cursor.moveToFirst()) {
            do {
                int GroupID = cursor.getInt(cursor.getColumnIndexOrThrow("GroupID"));
                String GroupName = cursor.getString(cursor.getColumnIndexOrThrow("GroupName"));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow("TransactionType"));
                TransactionGroups transactionGroups = new TransactionGroups(GroupID, GroupName, transactionType);
                list.add(transactionGroups);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public List<TransactionGroups> getTransactionGroupsIncomeList() {
        List<TransactionGroups> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select * from TransactionGroups where TransactionType = ?", new String[]{"Income"});
        if (cursor.moveToFirst()) {
            do {
                int GroupID = cursor.getInt(cursor.getColumnIndexOrThrow("GroupID"));
                String GroupName = cursor.getString(cursor.getColumnIndexOrThrow("GroupName"));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow("TransactionType"));
                TransactionGroups transactionGroups = new TransactionGroups(GroupID, GroupName, transactionType);
                list.add(transactionGroups);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public boolean addTransaction(String username, String transactionType, int groupID, double amount, String notes, int day, int month, int year) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Username", username);
        contentValues.put("TransactionType", transactionType);
        contentValues.put("GroupID", groupID);
        contentValues.put("Amount", amount);
        contentValues.put("Notes", notes);
        String date = formatDate(day, month, year);
        contentValues.put("TransactionDate", date);


        long result = myDB.insert("Transactions", null, contentValues);
        if (result == -1) {
            return false;
        } else return true;
    }

    public List<Transactions> getTransactionList() {
        List<Transactions> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select * from Transactions  ORDER BY TransactionDate DESC", new String[]{});
        if (cursor.moveToFirst()) {
            do {
                int transactionID = cursor.getInt(cursor.getColumnIndexOrThrow("TransactionID"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow("TransactionType"));
                int GroupID = cursor.getInt(cursor.getColumnIndexOrThrow("GroupID"));
                Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("Amount"));
                String note = cursor.getString(cursor.getColumnIndexOrThrow("Notes"));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);

                Transactions transactions = new Transactions(transactionID, username, transactionType, GroupID, amount, note, day, month, year);
                list.add(transactions);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public List<String> getTransactionDayList(String username) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions where Username = ? ORDER BY TransactionDate DESC", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                if (!list.contains(dateStr)){
                    list.add(dateStr);
                }


            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<String> getTransactionMonthList(String username) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions where Username = ? ORDER BY TransactionDate DESC", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                String dateText = month+"-"+year;
                if (!list.contains(dateText)){
                    list.add(dateText);
                }


            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Integer> getTransactionYearList(String username) {
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions where Username = ? ORDER BY TransactionDate DESC", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                if (!list.contains(year)){
                    list.add(year);
                }


            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<String> getTransactionDayListByWeek(String username) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        int yearS,monthS, dayS;
        int yearE = calendar.get(Calendar.YEAR);
        int monthE = calendar.get(Calendar.MONTH)+1;
        int dayE = calendar.get(Calendar.DAY_OF_WEEK)-1;
        dayS = dayE-7;
        yearS = yearE;
        monthS = monthE;
        if (dayS<1){
            monthS = monthE-1;
            if (monthS<1){
                monthS +=12;
                yearS = yearE-1;
            }
            YearMonth yearMonthObject = null;
            int daysInMonth =0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                yearMonthObject = YearMonth.of(yearS, monthS);
                daysInMonth = yearMonthObject.lengthOfMonth();
            }
            dayS+= daysInMonth;
        }

        String dateS = formatDate(dayS, monthS, yearS);
        String dateE = formatDate(dayE, monthE, yearE);


        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions where Username = ? AND TransactionDate Between ? And ? ORDER BY TransactionDate DESC", new String[]{username,dateS,dateE});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                if (!list.contains(dateStr)){
                    list.add(dateStr);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<String> getTransactionDayListByMonth(String username) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        int yearS,monthS, dayS;
        int yearE = calendar.get(Calendar.YEAR);
        int monthE = calendar.get(Calendar.MONTH)+1;
        int dayE = calendar.get(Calendar.DAY_OF_WEEK)-1;
        dayS = dayE;
        yearS = yearE;
        monthS = monthE-1;
        if (monthS<1){
            monthS +=12;
            yearS = yearE-1;
        }

        String dateS = formatDate(dayS, monthS, yearS);
        String dateE = formatDate(dayE, monthE, yearE);


        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions where Username = ? AND TransactionDate Between ? And ? ORDER BY TransactionDate DESC", new String[]{username,dateS,dateE});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                if (!list.contains(dateStr)){
                    list.add(dateStr);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<String> getTransactionDayListByYear(String username) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();

        int yearE = calendar.get(Calendar.YEAR);
        int monthE = calendar.get(Calendar.MONTH) + 1;
        int dayE = calendar.get(Calendar.DAY_OF_MONTH)-1;

        int yearS = yearE - 1;
        int monthS = monthE;
        int dayS = dayE;

        String dateS = formatDate(dayS, monthS, yearS);
        String dateE = formatDate(dayE, monthE, yearE);

        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions where Username = ? AND TransactionDate Between ? And ? ORDER BY TransactionDate DESC", new String[]{username,dateS,dateE});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                if (!list.contains(dateStr)){
                    list.add(dateStr);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }



    public List<String> getTransactionMonthList() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions ORDER BY TransactionDate DESC", new String[]{});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[0]);
                String monthyear = month+"-"+year;
                if (!list.contains(monthyear)){
                    list.add(monthyear);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Integer> getTransactionYearList() {
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("select TransactionDate from Transactions ORDER BY TransactionDate DESC", new String[]{});
        if (cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                if (!list.contains(year)){
                    list.add(year);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }



    public List<Transactions> getTransactionListByDay(String username,int d, int m, int y){
        List<Transactions> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();

        String date = formatDate(d, m, y);

        Cursor cursor = myDB.rawQuery("select * from Transactions where Username = ? AND TransactionDate = ? ORDER BY TransactionDate DESC",new String[]{username,date});
        if (cursor.moveToFirst()) {
            do {
                int transactionID = cursor.getInt(cursor.getColumnIndexOrThrow("TransactionID"));
                String us = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow("TransactionType"));
                int GroupID = cursor.getInt(cursor.getColumnIndexOrThrow("GroupID"));
                Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("Amount"));
                String note = cursor.getString(cursor.getColumnIndexOrThrow("Notes"));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);

                Transactions transactions = new Transactions(transactionID,us, transactionType,GroupID,amount,note,day,month,year);
                list.add(transactions);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Transactions> getTransactionListByMonth(String username, int m, int y){
        List<Transactions> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        String monthText = String.format("%02d", m);
        String yearText = String.valueOf(y);
        String query = "SELECT * FROM Transactions WHERE Username = ? AND strftime('%m', TransactionDate) = ? AND strftime('%Y', TransactionDate) = ? ORDER BY TransactionDate DESC";
        Cursor cursor = myDB.rawQuery(query, new String[]{username, monthText, yearText});
        if (cursor.moveToFirst()) {
            do {
                int transactionID = cursor.getInt(cursor.getColumnIndexOrThrow("TransactionID"));
                String us = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow("TransactionType"));
                int GroupID = cursor.getInt(cursor.getColumnIndexOrThrow("GroupID"));
                Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("Amount"));
                String note = cursor.getString(cursor.getColumnIndexOrThrow("Notes"));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);

                Transactions transactions = new Transactions(transactionID,us, transactionType,GroupID,amount,note,day,month,year);
                list.add(transactions);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Transactions> getTransactionListByYear(String username,int y){
        List<Transactions> list = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        String yearText = String.valueOf(y);
        String query = "SELECT * FROM Transactions WHERE Username = ? AND strftime('%Y', TransactionDate) = ? ORDER BY TransactionDate DESC";
        Cursor cursor = myDB.rawQuery(query, new String[]{username, yearText});
        if (cursor.moveToFirst()) {
            do {
                int transactionID = cursor.getInt(cursor.getColumnIndexOrThrow("TransactionID"));
                String us = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                String transactionType = cursor.getString(cursor.getColumnIndexOrThrow("TransactionType"));
                int GroupID = cursor.getInt(cursor.getColumnIndexOrThrow("GroupID"));
                Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("Amount"));
                String note = cursor.getString(cursor.getColumnIndexOrThrow("Notes"));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("TransactionDate"));
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);

                Transactions transactions = new Transactions(transactionID,us, transactionType,GroupID,amount,note,day,month,year);
                list.add(transactions);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public User getUser(String username) {

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            String us = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("Password"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
            double balance = cursor.getInt(cursor.getColumnIndexOrThrow("Balance"));

            User user = new User(email,password,username,balance);
            return user;
        }
        cursor.close();
        return null;
    }

    public boolean updateUserBalance(String username, double balance) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Balance", balance);
        int result = myDB.update("Users", contentValues, "username = ?", new String[]{username});
        return result > 0;
    }

    // Phương thức định dạng ngày
    private String formatDate(int day, int month, int year) {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
