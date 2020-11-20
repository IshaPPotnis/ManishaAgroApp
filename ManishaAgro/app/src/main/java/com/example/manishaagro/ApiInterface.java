package com.example.manishaagro;

import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.DealerProductMap;
import com.example.manishaagro.model.ExpEmpMapModel;
import com.example.manishaagro.model.ExpenseModel;
import com.example.manishaagro.model.MeterModel;
import com.example.manishaagro.model.ProductModel;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.model.VisitProductMapModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/php/GetEmployeeDetails.php")
    Call<ProfileModel> getEmpProfile(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/GetEmpExpenseInRpt.php")
    Call<DailyEmpExpenseModel> getEmpExpenseInRpt(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/GetCustomerVisitedDetailsOfEmp.php")
    Call<TripModel> getCustomerVisitedDetailsOfEmp(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String visitedcustomername,
            @Field("date_of_travel") String dateoftravel,
            @Field("date_of_return") String dateofreturn);
    @FormUrlEncoded
    @POST("/php/GetProductofVisitedID.php")
    Call<List<VisitProductMapModel>> GetProductofVisitedID(
            @Field("key") String key,
            @Field("visit_id") int visitid);
    @FormUrlEncoded
    @POST("/php/GetAllProductofDealerID.php")
    Call<List<DealerProductMap>> GetAllProductofDealerID(
            @Field("key") String key,
            @Field("dealer_id") int dealerid);
    @FormUrlEncoded
    @POST("/php/GetEmployeeTrip.php")
    Call<TripModel> getEmpTotalTrip(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/getDealerTotalSale.php")
    Call<DealerModel> getDealerTotalSale(
            @Field("key") String key,
            @Field("emp_id") String empid);

    @FormUrlEncoded
    @POST("/php/GetEmployeeForManager.php")
    Call<List<ProfileModel>> getAllReportsEmp(
            @Field("key") String key,
            @Field("reports_to_emp_id") String empid);



    @FormUrlEncoded
    @POST("/php/GetVisitedCustomerFromEmployeeId.php")
    Call<List<TripModel>> getVisitedAllCust(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/GetAllVisitedDealerList.php")
    Call<List<DealerModel>> getAllDealerListInEmp(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/getAllPendingDataList.php")
    Call<List<TripModel>> getPendingListInEmp(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/pendingCount.php")
    Call<TripModel>  getVisitPendingCountInEmp(
            @Field("key") String key,
            @Field("emp_id") String empid);

    @FormUrlEncoded
    @POST("/php/GetAllVisitedDealerList.php")
    Call<DealerModel> getAllDealerIdInEmp(
            @Field("key") String key,
            @Field("emp_id") String empsid,
            @Field("dealer_name") String name,
            @Field("date_of_purchase") String datepur);

    @FormUrlEncoded
    @POST("/php/checkAndGetEndTripRemain.php")
    Call<List<TripModel>> checkAndGetEndTrip(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/getEmpStarRead.php")
    Call<List<MeterModel>> getStartRead(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("/php/getEmpCloaseRead.php")
    Call<List<MeterModel>> getCloseRead(
            @Field("key") String key,
            @Field("emp_id") String empid);


    @FormUrlEncoded
    @POST("/php/checkAndGetAllFollowUp.php")
    Call<List<TripModel>> checkFollowUp(
            @Field("key") String key,
            @Field("emp_id") String empid);


    @FormUrlEncoded
    @POST("/php/getAllVisitedDetailsOfEmployee.php")
    Call<List<TripModel>> getAllVisit(
            @Field("key") String key,
            @Field("emp_id") String empid);





    @FormUrlEncoded
    @POST("/php/GetEmpIdAndDesignation.php")
    Call<ProfileModel> getEmpIdDesignation(
            @Field("key") String key,
            @Field("user_name") String username,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("/php/InsertDemoPhoto.php")
    Call<TripModel> insertDemoPhotoEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String custname,
            @Field("demo_image") String dmimg);
    @FormUrlEncoded
    @POST("/php/FollowupEntryUpdate.php")
    Call<TripModel> FollowupEntryUpdate(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String custname,
            @Field("observations") String observations,
            @Field("customer_review") String customer_review,
            @Field("customer_rating") int customer_rating,
            @Field("follow_up_image") String follow_up_image);


    @FormUrlEncoded
    @POST("/php/getProductListName.php")
    Call<ArrayList<ProductModel>> getProductList(
            @Field("key") String key1
    );
    @FormUrlEncoded
    @POST("/php/getListExpenseName.php")
    Call<ArrayList<ExpenseModel>> getExpenseList(
            @Field("key") String key1
    );
    @FormUrlEncoded
    @POST("/php/getEmployeeNameList.php")
    Call<ArrayList<ProfileModel>> getEmpList(
            @Field("key") String key1
    );


    @FormUrlEncoded
    @POST("/php/getFarmerListName.php")
    Call<ArrayList<TripModel>> getFarmerNameList(
            @Field("key") String key1,
            @Field("emp_id") String empid
    );
    @FormUrlEncoded
    @POST("/php/getforDemoImgFarmerListName.php")
    Call<ArrayList<TripModel>> getForDemoImgFarmerNameList(
            @Field("key") String key1,
            @Field("emp_id") String empid
    );
    @FormUrlEncoded
    @POST("/php/getPackingListName.php")
    Call<ArrayList<ProductModel>> getPackingList(
            @Field("key") String key1,
            @Field("product_name") String prodctnm
    );


    @FormUrlEncoded
    @POST("/php/InsertSelfiePhoto.php")
    Call<TripModel> insertSelfiePhotoEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String custname,
            @Field("selfie_with_customer") String selfie);

    @FormUrlEncoded
    @POST("/php/InsertTripStartDetails.php")
    Call<TripModel> insertVisitedStartEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String custname,
            @Field("address") String address,
            @Field("village") String village,
            @Field("taluka") String taluka,
            @Field("district") String district,
            @Field("contact_detail") String contactdtl);

    @FormUrlEncoded
    @POST("/php/InsertExpenseDetails.php")
    Call<ExpenseModel> insertExpenseEntry(
            @Field("key") String key,
            @Field("expense_name") String expname,
            @Field("da") double da,
            @Field("out_da") double outda,
            @Field("lodgeT") double lodgeT,
            @Field("lodgeD") double lodgeD,
            @Field("mobile") double mbl,
            @Field("km_limit") int kmlimit);
    @FormUrlEncoded
    @POST("/php/InsertOtherExpense.php")
    Call<DailyEmpExpenseModel> insertOtherExpenseEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("lodgeT") double lodgeT,
            @Field("lodgeD") double lodgeD,
            @Field("Remark") String rmk);

    @FormUrlEncoded
    @POST("/php/UpdateEndTripDate.php")
    Call<TripModel> updateEndtripDateEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String custname,
            @Field("address") String address);








    @FormUrlEncoded
    @POST("/php/InsertDemoDetails.php")
    Call<TripModel> insertDemoEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String custname,
            @Field("demo_type") String demotype,
            @Field("crops") String crops,
            @Field("crop_health") String crophealth,
            @Field("demo_name") String demoname,
            @Field("usage_type") String usagetype,
            @Field("water_quantity") String waterqty,
            @Field("water_additions") String addwaterqty,
            @Field("additions") String additions,
            @Field("follow_up_required") int followuprequired,
            @Field("follow_up_date") String followupdate,
            @Field("demo_required") int demorequired);

    @FormUrlEncoded
    @POST("/php/InsertVisitProductData.php")
    Call<VisitProductMapModel> insertProductDataEntry(
            @Field("key") String key,
            @Field("visit_id") int visitid,
            @Field("product_name") String productsname,
            @Field("packing") String packs,
            @Field("product_quantity") String qtys);

    @FormUrlEncoded
    @POST("/php/InsertExpEmpMapData.php")
    Call<ExpEmpMapModel> insertExpEmpMapDataEntry(
            @Field("key") String key,
            @Field("expense_id") int visitid,
            @Field("emp_id") String productsname);

    @FormUrlEncoded
    @POST("/php/InsertDealerProductData.php")
    Call<DealerProductMap> insertDealersProductDataEntry(
            @Field("key") String key,
            @Field("dealer_id") int dealertid,
            @Field("product_name") String productssname,
            @Field("packing") String packings,
            @Field("product_quantity") String productqtys);


    @FormUrlEncoded
    @POST("/php/InsertDealerEntry.php")
    Call<DealerModel> insertDealerEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("dealer_name") String dealer_name);

    @FormUrlEncoded
    @POST("/php/InsertStartReadEntry.php")
    Call<MeterModel> InsertStartReadEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("start_meter_reading") int readstart);

    @FormUrlEncoded
    @POST("/php/InsertStartReadEntry.php")
    Call<MeterModel> checkInsertStartReadEntry(
            @Field("key") String key,
            @Field("emp_id") String empid);


    @FormUrlEncoded
    @POST("/php/UpdateEndReadEntry.php")
    Call<MeterModel> UpdateEndReadEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("end_meter_reading") int readend);

    @FormUrlEncoded
    @POST("/php/UpdateEndReadEntry.php")
    Call<MeterModel> checkUpdateEndReadEntry(
            @Field("key") String key,
            @Field("emp_id") String empid);

}
