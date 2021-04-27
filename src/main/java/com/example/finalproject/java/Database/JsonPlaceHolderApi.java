package com.example.finalproject.java.Database;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("createMailStatus")
    Call<StatusPostApi> createPost(@Body StatusPostApi post);

    @POST("createCourse")
    Call<EnrollPostApi> createPost(@Body EnrollPostApi post);

    @GET("fetchAllMailStatuses")
    Call<List<StatusPostApi>> getPost();

    @GET("fetchAllCourses")
    Call<List<EnrollPostApi>> getCourses();

    @POST("createAssignment")
    Call<AssignmentApi> createPost(@Body AssignmentApi post);

    @GET("fetchAllAssignments")
    Call<List<AssignmentApi>> getPosts();
}
