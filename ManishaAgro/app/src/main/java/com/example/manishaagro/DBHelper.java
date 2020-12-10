package com.example.manishaagro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "manishaagro.db";
    private static final int DB_VERSION = 2;
    public static final String EMPLOYEE_DETAILS = "employee_details";
    public static final String COLUMN_EMPI_ID = "emp_id";
    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESIGNATION = "designation";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_DOJ = "joining_date";
    public static final String COLUMN_EMAIL_ID = "email_id";
    public static final String COLUMN_CONTACT_DETAIL = "contact_detail";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_HEADQUARTER = "headquarter";
    public static final String COLUMN_IS_ACTIVE = "is_active";


    public static final String EMPLOYEE_TRIPS = "employee_trips";
    public static final String COLUMN_TRIP_EMPI_ID = "emp_id";
    public static final String COLUMN_TRIP_visited_customer_name = "visited_customer_name";
    public static final String COLUMN_TRIP_address = "address";
    public static final String COLUMN_TRIP_date_of_travel = "date_of_travel";
    public static final String COLUMN_TRIP_date_of_return = "date_of_return";
    public static final String COLUMN_TRIP_demo_type = "demo_type";
    public static final String COLUMN_TRIP_village = "village";
    public static final String COLUMN_TRIP_taluka = "taluka";
    public static final String COLUMN_TRIP_district = "district";
    public static final String COLUMN_TRIP_contact_detail = "contact_detail";
    public static final String COLUMN_TRIP_acre = "acre";
    public static final String COLUMN_TRIP_purpose = "purpose";
    public static final String COLUMN_TRIP_crops = "crops";
    public static final String COLUMN_TRIP_crop_health = "crop_health";
    public static final String COLUMN_TRIP_demo_name = "demo_name";
    public static final String COLUMN_TRIP_usage_type = "usage_type";
    public static final String COLUMN_TRIP_water_quantity = "water_quantity";
    public static final String COLUMN_TRIP_water_additions = "water_additions";
    public static final String COLUMN_TRIP_additions = "additions";
    public static final String COLUMN_TRIP_follow_up_required = "follow_up_required";
    public static final String COLUMN_TRIP_follow_up_date = "follow_up_date";
    public static final String COLUMN_TRIP_demo_image = "demo_image";
    public static final String COLUMN_TRIP_selfie_with_customer = "selfie_with_customer";
    public static final String COLUMN_TRIP_observations = "observations";
    public static final String COLUMN_TRIP_customer_rating = "customer_rating";
    public static final String COLUMN_TRIP_customer_review = "customer_review";
    public static final String COLUMN_TRIP_follow_up_image = "follow_up_image";
    public static final String COLUMN_TRIP_demo_required = "demo_required";
    public static final String COLUMN_TRIP_crop_growth = "crop_growth";
    public static final String COLUMN_TRIP_health_bad_reason = "health_bad_reason";
    public static final String COLUMN_TRIP_Syn_Status = "visit_syn_status";









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




    private static final String CREATE_TABLE_EMPLOYEE_TRIPS = "CREATE TABLE " + EMPLOYEE_TRIPS + "("
            + COLUMN_TRIP_EMPI_ID + " VARCHAR(100),"
            + COLUMN_TRIP_visited_customer_name + " VARCHAR(150),"
            + COLUMN_TRIP_address + " VARCHAR(500),"
            + COLUMN_TRIP_date_of_travel + " DATETIME,"
            + COLUMN_TRIP_date_of_return + " DATETIME,"
            + COLUMN_TRIP_demo_type + " VARCHAR(100),"
            + COLUMN_TRIP_village + " VARCHAR(100),"
            + COLUMN_TRIP_taluka + " VARCHAR(100),"
            + COLUMN_TRIP_district + " VARCHAR(100),"
            + COLUMN_TRIP_contact_detail + " VARCHAR(100),"
            + COLUMN_TRIP_acre + " double,"
            + COLUMN_TRIP_purpose + " VARCHAR(200),"
            + COLUMN_TRIP_crops + " VARCHAR(100),"
            + COLUMN_TRIP_crop_health + " VARCHAR(100),"
            + COLUMN_TRIP_demo_name + " VARCHAR(100),"
            + COLUMN_TRIP_usage_type + " VARCHAR(100),"
            + COLUMN_TRIP_water_quantity + " VARCHAR(100),"
            + COLUMN_TRIP_water_additions + " VARCHAR(100),"
            + COLUMN_TRIP_additions + " VARCHAR(100),"
            + COLUMN_TRIP_follow_up_required + " TINYINT(1),"
            + COLUMN_TRIP_follow_up_date + " DATETIME,"
            + COLUMN_TRIP_demo_image + " VARCHAR(300),"
            + COLUMN_TRIP_selfie_with_customer + " VARCHAR(300),"
            + COLUMN_TRIP_observations + " VARCHAR(300),"
            + COLUMN_TRIP_customer_rating + " int(11),"
            + COLUMN_TRIP_customer_review + " VARCHAR(100),"
            + COLUMN_TRIP_follow_up_image + " VARCHAR(300),"
            + COLUMN_TRIP_demo_required + " tinyint(1),"
            + COLUMN_TRIP_crop_growth + " VARCHAR(300),"
            + COLUMN_TRIP_health_bad_reason + " VARCHAR(500),"
            + COLUMN_TRIP_Syn_Status + " TINYINT(1),"
            + " FOREIGN KEY ( " +COLUMN_TRIP_EMPI_ID+ " ) REFERENCES "+EMPLOYEE_DETAILS+"( " +COLUMN_EMPI_ID+ " ));";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_EMPLOYEE_DETAIL);
        db.execSQL(CREATE_TABLE_EMPLOYEE_TRIPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TRIPS);
    }

    public boolean addvisitdata(String empId, String FrName, String FrAdd, String FrVllg,String FrTaluka,String FrDistrict,String FrCon,Double FrAcre,String FrPurpose,String FrVistDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRIP_EMPI_ID, empId);
        contentValues.put(COLUMN_TRIP_visited_customer_name, FrName);
        contentValues.put(COLUMN_TRIP_address, FrAdd);
        contentValues.put(COLUMN_TRIP_date_of_travel,FrVistDate);
        contentValues.put(COLUMN_TRIP_village,FrVllg);
        contentValues.put(COLUMN_TRIP_taluka,FrTaluka);
        contentValues.put(COLUMN_TRIP_district,FrDistrict);
        contentValues.put(COLUMN_TRIP_contact_detail,FrCon);
        contentValues.put(COLUMN_TRIP_acre,FrAcre);
        contentValues.put(COLUMN_TRIP_purpose,FrPurpose);
        contentValues.put(COLUMN_TRIP_Syn_Status,0);
       long res= db.insert(EMPLOYEE_TRIPS, null, contentValues);
        if (res==-1) { return false;
        }
        else
        {
            return true;
        }



    }



    public boolean updatevisitdata(String dEmpid,String dFrname,String dDType,String dCrop,String dCropHealth,String dname,String dusageType,String dBadReason,String dwaterQty,String dwaterAdd,String dAdditions,String dcropGrowth,int dFallowUp, String dFollowUpDate,int ddemoVisit) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COLUMN_TRIP_demo_type,dDType);
        contentValues.put(COLUMN_TRIP_crops,dCrop);
        contentValues.put(COLUMN_TRIP_crop_health,dCropHealth);
        contentValues.put(COLUMN_TRIP_demo_name,dname);
        contentValues.put(COLUMN_TRIP_usage_type,dusageType);
        contentValues.put(COLUMN_TRIP_water_quantity,dwaterQty);
        contentValues.put(COLUMN_TRIP_water_additions,dwaterAdd);
        contentValues.put(COLUMN_TRIP_additions,dAdditions);
        contentValues.put(COLUMN_TRIP_follow_up_required,dFallowUp);
        contentValues.put(COLUMN_TRIP_follow_up_date,dFollowUpDate);
        contentValues.put(COLUMN_TRIP_demo_required,ddemoVisit);
        contentValues.put(COLUMN_TRIP_crop_growth,dcropGrowth);
        contentValues.put(COLUMN_TRIP_health_bad_reason,dBadReason);

        long res2= db.update(EMPLOYEE_TRIPS,contentValues,"emp_id = ? and visited_customer_name = ? and crops IS NULL ",new String[]{dEmpid,dFrname});
        // return true;
        if (res2==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }



    public Cursor getIDDesig(String usernm, String pass) {
        Log.d("Code",usernm);
        Log.d("date",pass);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res1=db.rawQuery("Select * from " + EMPLOYEE_DETAILS + " where " + COLUMN_USERNAME +"=? and " + COLUMN_PASSWORD +"=?",new String[]{usernm,pass});


       // Log.d("Resourses"  ,"select * from "+M_Table+" where Society="+codeS+""+" and Date="+dt+"", null );
        Log.d("Count1",String.valueOf(res1.getCount()));
        return res1;
    }
}
