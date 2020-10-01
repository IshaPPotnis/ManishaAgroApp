package com.example.manishaagro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("reportAllEmp.php")
    Call<ProfileModel> getEmpProfile(
            @Field("key") String key,
            @Field("user_name") String usersname);

    @FormUrlEncoded
    @POST("reportAllEmp.php")
    Call<List<ProfileModel>> getAllReportsEmp(
            @Field("key") String key);
    @FormUrlEncoded
    @POST("visitedAllCust.php")
    Call<List<TripModel>> getVisitedAllCust(
            @Field("key") String key,
            @Field("emp_id") String empid);
    @FormUrlEncoded
    @POST("reportAllEmp.php")
    Call<ProfileModel> getEmpid(
            @Field("key") String key,
            @Field("user_name") String usersname);
}
