package com.example.aryansingh.github_demo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Aryan Singh on 7/12/2018.
 */

public interface UserService {

        @GET("users/{name}")// this is after the base url
        Call<ArrayList<User>> getUsers(@Path("name") String name);

//    @GET("posts/{id}/comments")// this is after the base url
//    Call<ArrayList<Comment>> getComments(@Path("id") int postId);



}
