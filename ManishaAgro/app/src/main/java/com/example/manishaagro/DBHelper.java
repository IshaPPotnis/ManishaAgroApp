package com.example.manishaagro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "manishaagro.db";
    private static final int DB_VERSION = 2;

    public static final String PRODUCT_DETAILS = "product_details";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_quantity_used = "quantity_used";
    public static final String COLUMN_PRODUCT_packing = "packing";


    public static final String DEALER_PRODUCT_MAP = "dealer_product_map";
    public static final String COLUMN_DEALER_PRODUCT_MAP_date_of_purchase = "date_of_purchase";
    public static final String COLUMN_DEALER_PRODUCT_MAP_product_name = "product_name";
    public static final String COLUMN_DEALER_PRODUCT_MAP_packing = "packing";
    public static final String COLUMN_DEALER_PRODUCT_MAP_product_quantity = "product_quantity";


    public static final String EMPLOYEE_DEALERS = "employee_dealers";
    public static final String COLUMN_EMPLOYEE_DEALERS_EMP_ID = "emp_id";
    public static final String COLUMN_EMPLOYEE_DEALERS_dealer_name = "dealer_name";
    public static final String COLUMN_EMPLOYEE_DEALERS_date_of_purchase = "date_of_purchase";
    public static final String COLUMN_EMPLOYEE_DEALERS_product_count = "product_count";
    public static final String COLUMN_EMPLOYEE_DEALERS_purpose = "purpose";
    public static final String COLUMN_EMPLOYEE_DEALERS_contactdetail = "contactdetail";



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
////////


    private static final String CREATE_TABLE_EMPLOYEE_DEALERS = "CREATE TABLE " + EMPLOYEE_DEALERS + "("
            + COLUMN_EMPLOYEE_DEALERS_EMP_ID + " VARCHAR(100),"
            + COLUMN_EMPLOYEE_DEALERS_dealer_name + " VARCHAR(50),"
            + COLUMN_EMPLOYEE_DEALERS_date_of_purchase + " DATETIME,"
            + COLUMN_EMPLOYEE_DEALERS_product_count + " int(11),"
            + COLUMN_EMPLOYEE_DEALERS_purpose + " VARCHAR(200),"
            + COLUMN_EMPLOYEE_DEALERS_contactdetail + " VARCHAR(100),"
            + " FOREIGN KEY ( " +COLUMN_EMPLOYEE_DEALERS_EMP_ID+ " ) REFERENCES "+EMPLOYEE_DETAILS+"( " +COLUMN_EMPI_ID+ " ));";

    private static final String CREATE_TABLE_DEALER_PRODUCT_MAP = "CREATE TABLE " + DEALER_PRODUCT_MAP + "("
            + COLUMN_DEALER_PRODUCT_MAP_date_of_purchase + " DATETIME,"
            + COLUMN_DEALER_PRODUCT_MAP_product_name + " VARCHAR(100),"
            + COLUMN_DEALER_PRODUCT_MAP_packing + " VACHAR(100),"
            + COLUMN_DEALER_PRODUCT_MAP_product_quantity + " VARCHAR(100),"
            + " FOREIGN KEY ( " +COLUMN_DEALER_PRODUCT_MAP_date_of_purchase+ " ) REFERENCES "+EMPLOYEE_DEALERS+"( " +COLUMN_EMPLOYEE_DEALERS_date_of_purchase+ " ));";

    //////////////



    private static final String CREATE_TABLE_PRODUCT_DETAILS ="CREATE TABLE " + PRODUCT_DETAILS + "("
            + COLUMN_PRODUCT_ID + " int(11) PRIMARY KEY,"
            + COLUMN_PRODUCT_NAME + " VARCHAR(40),"
            + COLUMN_PRODUCT_quantity_used + " VARCHAR(20),"
            + COLUMN_PRODUCT_packing + " VARCHAR(100)" + ");";



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
            + COLUMN_TRIP_Syn_Status + " VARCHAR(100),"
            + " FOREIGN KEY ( " +COLUMN_TRIP_EMPI_ID+ " ) REFERENCES "+EMPLOYEE_DETAILS+"( " +COLUMN_EMPI_ID+ " ));";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_EMPLOYEE_DETAIL);
        db.execSQL(CREATE_TABLE_EMPLOYEE_TRIPS);
        db.execSQL(CREATE_TABLE_PRODUCT_DETAILS);
        db.execSQL(CREATE_TABLE_EMPLOYEE_DEALERS);
        db.execSQL(CREATE_TABLE_DEALER_PRODUCT_MAP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_DEALERS);
        db.execSQL("DROP TABLE IF EXISTS " + DEALER_PRODUCT_MAP);
    }



    public boolean addProdtTableDeatilsdata(int prodtId, String ProdtUName, String ProdtQtyuse, String ProdtPack) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_ID, prodtId);
        contentValues.put(COLUMN_PRODUCT_NAME, ProdtUName);
        contentValues.put(COLUMN_PRODUCT_quantity_used, ProdtQtyuse);
        contentValues.put(COLUMN_PRODUCT_packing,ProdtPack);
        long res= db.insert(PRODUCT_DETAILS, null, contentValues);
        if (res==-1) { return false;
        }
        else
        {
            return true;
        }



    }




////////

    public boolean addEmpDeatilsdata(String empId, String UName, String UPass, String EmpName,String EmpDesig,String Empdob,String Empjod,String EmpEmail,String EmpConDetl,String EmpAdd,String EmpHead,int empIsAct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMPI_ID, empId);
        contentValues.put(COLUMN_USERNAME, UName);
        contentValues.put(COLUMN_PASSWORD, UPass);
        contentValues.put(COLUMN_NAME,EmpName);
        contentValues.put(COLUMN_DESIGNATION,EmpDesig);
        contentValues.put(COLUMN_DOB,Empdob);
        contentValues.put(COLUMN_DOJ,Empjod);
        contentValues.put(COLUMN_EMAIL_ID,EmpEmail);
        contentValues.put(COLUMN_CONTACT_DETAIL,EmpConDetl);
        contentValues.put(COLUMN_ADDRESS,EmpAdd);
        contentValues.put(COLUMN_HEADQUARTER,EmpHead);
        contentValues.put(COLUMN_IS_ACTIVE,empIsAct);
        long res= db.insert(EMPLOYEE_DETAILS, null, contentValues);
        if (res==-1) { return false;
        }
        else
        {
            return true;
        }



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
        contentValues.put(COLUMN_TRIP_Syn_Status,"No");
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




    public boolean updateendVisitdata(String endEmpid,String endFrname,String endFadd,String endCurdate) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COLUMN_TRIP_date_of_return,endCurdate);


        long res2= db.update(EMPLOYEE_TRIPS,contentValues,"emp_id = ? and visited_customer_name = ? and address = ? ",new String[]{endEmpid,endFrname,endFadd});
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





    public List<TripModel> recordsList(String filter) {

        List<TripModel> recordsLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + EMPLOYEE_TRIPS + " where emp_id="+"'"+filter+"'"+" and date_of_return IS NULL",null);
        TripModel records;
        if(cursor.moveToFirst()) {
            do
            {
                records = new TripModel();
                records.setVisitedCustomerName(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_visited_customer_name)));
                records.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_address)));
                records.setDateOfTravel(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_date_of_travel)));
                recordsLinkedList.add(records);

            }
            while(cursor.moveToNext());
        }
        Log.d("listlist", String.valueOf(recordsLinkedList));
        return recordsLinkedList;

    }


    public ArrayList<TripModel> getAllEmpTriprecordsList() {

        ArrayList<TripModel> recordsLinkedList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM employee_trips where visit_syn_status = '"+"No"+"' And date_of_return IS NOT NULL",null);
        TripModel records;
        if(cursor.moveToFirst()) {
            do
            {
                records = new TripModel();
                records.setEmpId(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_EMPI_ID)));
                records.setVisitedCustomerName(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_visited_customer_name)));
                records.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_address)));
                records.setDateOfTravel(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_date_of_travel)));
                records.setDateOfReturn(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_date_of_return)));
                records.setDemotype(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_demo_type)));
                records.setVillage(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_village)));
                records.setTaluka(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_taluka)));
                records.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_district)));
                records.setContactdetail(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_contact_detail)));
                records.setAcre(cursor.getDouble(cursor.getColumnIndex(COLUMN_TRIP_acre)));
                records.setVisitpurpose(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_purpose)));
                records.setCrops(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_crops)));
                records.setCrophealth(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_crop_health)));
                records.setDemoname(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_demo_name)));
                records.setUsagetype(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_usage_type)));
                records.setWaterquantity(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_water_quantity)));
                records.setWateradditions(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_water_additions)));
                records.setAdditions(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_additions)));
                records.setFollowuprequired(cursor.getInt(cursor.getColumnIndex(COLUMN_TRIP_follow_up_required)));
                records.setFollowupdate(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_follow_up_date)));
                records.setDemoimage(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_demo_image)));
                records.setSelfiewithcustomer(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_selfie_with_customer)));
                records.setObservations(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_observations)));
                records.setCustomer_rating(cursor.getInt(cursor.getColumnIndex(COLUMN_TRIP_customer_rating)));
                records.setCustomer_review(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_customer_review)));
                records.setFollow_up_image(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_follow_up_image)));
                records.setDemorequired(cursor.getInt(cursor.getColumnIndex(COLUMN_TRIP_demo_required)));
                records.setCrop_growth(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_crop_growth)));
                records.setHealth_bad_reason(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_health_bad_reason)));

                recordsLinkedList.add(records);

            }
            while(cursor.moveToNext());
        }
        Log.d("listlist", String.valueOf(recordsLinkedList));
        return recordsLinkedList;

    }






    public List<TripModel> recordsAllVisitdataList(String filter) {

        List<TripModel> recordsLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + EMPLOYEE_TRIPS + " where emp_id="+"'"+filter+"'"+" and date_of_return IS NOT NULL",null);



        TripModel records;
        if(cursor.moveToFirst()) {
            do
            {
                records = new TripModel();
                records.setVisitedCustomerName(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_visited_customer_name)));
                records.setDateOfTravel(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_date_of_travel)));
                records.setDateOfReturn(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_date_of_return)));
                recordsLinkedList.add(records);

            }
            while(cursor.moveToNext());
        }
        Log.d("listlist", String.valueOf(recordsLinkedList));
        return recordsLinkedList;

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




    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM employee_trips";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("date_of_travel", cursor.getString(0));
                map.put("visited_customer_name", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }


    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  date_of_travel,visited_customer_name FROM employee_trips where visit_syn_status = '"+"no"+"' And date_of_return IS NOT NULL";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("date_of_travel", cursor.getString(0));
                map.put("visited_customer_name", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        System.out.print("jason format check : : "+gson.toJson(wordList));
        return gson.toJson(wordList);
    }


    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync needed\n";
        }
        return msg;
    }

    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM employee_trips where visit_syn_status = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    public int dbEmpDtlTblCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM employee_details";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }


    public void updateSyncStatus(String id,String dofT,String DofR,String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update employee_trips set visit_syn_status = '"+ status +"' where date_of_travel="+"'"+ dofT +"'"+"And emp_id="+"'"+ id +"'"+" AND date_of_return="+"'"+ DofR +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

}
