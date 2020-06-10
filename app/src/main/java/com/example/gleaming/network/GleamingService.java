package com.example.gleaming.network;

import com.example.gleaming.model.AccessToken;
import com.example.gleaming.model.PrendaResponse;
import com.example.gleaming.model.ProductSingleResponse;
import com.example.gleaming.model.User;
import com.example.gleaming.model.UserSingleResponse;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GleamingService {
    //TODO: domain.com/api/

    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(
            @Field("name") String name,
            @Field("lastname") String lastname,
            @Field("age") String age,
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(
            @Field("email") String email,
            @Field("password") String password
    );



    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refreshToken(
            @Field("refresh_Token") String refreshToken
    );

    @GET("home")
    Call<PrendaResponse> prendasAll();


    @GET("prenda/{id}")
    Call<ProductSingleResponse> getPrenda(@Path("id") int id);

    @GET("prenda_gender/{gender}")
    Call<PrendaResponse> getPrendaGender(@Path("gender") String gender);



    @POST("logout")
    Call<AccessToken> logout();

    @GET("users")
    Call<UserSingleResponse> getUser();


    @POST("updateUser")
    @FormUrlEncoded
    Call<User> updateUser(
            @Field("name") String name,
            @Field("lastname") String lastname
    );


}
