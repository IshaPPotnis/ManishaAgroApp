package com.example.manishaagro;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;

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
    @POST("/php/GetEmpIdAndDesignation.php")
    Call<ProfileModel> getEmpIdDesignation(
            @Field("key") String key,
            @Field("user_name") String username,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("/php_old/InsertTripStartDetails.php")
    Call<TripModel> insertVisitedStartEntry(
            @Field("key") String key,
            @Field("emp_id") String empid,
            @Field("visited_customer_name") String custname,
            @Field("address") String address);


}
