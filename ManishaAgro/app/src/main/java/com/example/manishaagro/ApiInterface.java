package com.example.manishaagro;

import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.ProductModel;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;
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
    @POST("/php/GetEmployeeTrip.php")
    Call<TripModel> getEmpTotalTrip(
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
    @POST("/php/checkAndGetEndTripRemain.php")
    Call<List<TripModel>> checkAndGetEndTrip(
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
            @Field("product_name") String productname,
            @Field("packing") String packing,
            @Field("product_quantity") String productqty,
            @Field("water_quantity") String waterqty,
            @Field("additions") String additions,
            @Field("follow_up_required") int followuprequired,
            @Field("follow_up_date") String followupdate);




    @FormUrlEncoded
    @POST("/php/InsertDealerEntry.php")
    Call<DealerModel> insertDealerEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("dealer_name") String dealer_name,
            @Field("product_name") String proname,

            @Field("quantity") int quanPro,
            @Field("packing") String pcking);

}
