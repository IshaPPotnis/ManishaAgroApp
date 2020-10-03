package com.example.manishaagro;

import com.example.manishaagro.model.EmpIdDesignationModel;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

   @FormUrlEncoded
   @POST("GetEmployeeDetails.php")
   Call<ProfileModel> getEmpProfile(
           @Field("key") String key,
           @Field("emp_id") String empid);

    @FormUrlEncoded
    @POST("GetEmployeeForManager.php")
    Call<List<ProfileModel>> getAllReportsEmp(
            @Field("key") String key,
            @Field("reports_to_emp_id") String empid);


    @FormUrlEncoded
    @POST("GetVisitedCustomerFromEmployeeId.php")
    Call<List<TripModel>> getVisitedAllCust(
            @Field("key") String key,
            @Field("emp_id") String empid);

    /*@FormUrlEncoded
    @POST("reportAllEmp.php")
    Call<ProfileModel> getEmpid(
            @Field("key") String key,
            @Field("user_name") String username);*/

    @FormUrlEncoded
    @POST("GetEmpIdAndDesignation.php")
    Call<EmpIdDesignationModel> getEmpIdDesignation(
            @Field("key") String key,
            @Field("user_name") String username,
            @Field("password") String password);
}
