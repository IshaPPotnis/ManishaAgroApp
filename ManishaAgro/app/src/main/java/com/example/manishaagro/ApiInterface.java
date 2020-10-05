package com.example.manishaagro;

import android.support.v4.media.MediaMetadataCompat;

import com.example.manishaagro.model.EmpIdDesignationModel;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    /*@FormUrlEncoded
    @POST("reportAllEmp.php")
    Call<ProfileModel> getEmpid(
            @Field("key") String key,
            @Field("user_name") String username);*/

    @FormUrlEncoded
    @POST("/php/GetEmpIdAndDesignation.php")
    Call<ProfileModel> getEmpIdDesignation(
            @Field("key") String key,
            @Field("user_name") String username,
            @Field("password") String password);
}
