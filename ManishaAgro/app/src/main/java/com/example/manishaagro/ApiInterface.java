package com.example.manishaagro;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("Includes/get_profile.php")
    Call<ProfileModel> getEmpProfile(
            @Field("key") String key,
            @Field("user_name") String usersname);

}
