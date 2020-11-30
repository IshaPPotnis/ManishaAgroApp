package com.example.manishaagro;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "manishaagro.db";
    private static final int DB_VERSION = 2;
    private static final String EMPLOYEE_DETAILS = "employee_details";
    private static final String COLUMN_EMPI_ID = "emp_id";
    private static final String COLUMN_USERNAME = "user_name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESIGNATION = "designation";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_DOJ = "joining_date";
    private static final String COLUMN_EMAIL_ID = "email_id";
    private static final String COLUMN_CONTACT_DETAIL = "contact_detail";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_HEADQUARTER = "headquarter";
    private static final String COLUMN_IS_ACTIVE = "is_active";
    private static final String CREATE_TABLE_EMPLOYEE_DETAIL = "CREATE TABLE " + EMPLOYEE_DETAILS + "("
            + COLUMN_EMPI_ID + " VARCHAR(100) PRIMARY KEY,"
            + COLUMN_USERNAME + " VARCHAR(100),"
            + COLUMN_PASSWORD + " VARCHAR(800),"
            + COLUMN_NAME + " VARCHAR(150),"
            + COLUMN_DESIGNATION + " VARCHAR(800),"
            + COLUMN_DOB + " date,"
            + COLUMN_DOJ + " date,"
            + COLUMN_EMAIL_ID + " VARCHAR(255),"
            + COLUMN_CONTACT_DETAIL + " VARCHAR(100),"
            + COLUMN_ADDRESS + " VARCHAR(500),"
            + COLUMN_HEADQUARTER + " VARCHAR(100),"
            + COLUMN_IS_ACTIVE + " TINYINT(1)" + ");";

    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EMPLOYEE_DETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_DETAILS);
    }

    void addData(int empId, String empName, String empPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMPI_ID, empId);
        contentValues.put(COLUMN_USERNAME, empName);
        contentValues.put(COLUMN_PASSWORD, empPassword);
        db.insert(EMPLOYEE_DETAILS, null, contentValues);
    }
}
