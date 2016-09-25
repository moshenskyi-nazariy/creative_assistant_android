package com.example.test.Interface;


import com.example.test.Messages.Message;
import com.example.test.Messages.PostResult;
import com.example.test.Objects.Object;
import com.example.test.Objects.ObjectsResponse;
import com.example.test.Rooms.RoomsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestInterface {

    @GET("objects/")
    Call<ObjectsResponse>getObjectWithObjectList();

    @GET("rooms/")
    Call<RoomsResponse>getObjectWithRoomList();

    @POST("messages/")
    Call<PostResult>postMessage(@Body Message message);

    @GET("objects/{id}")
    Call<Object>getObjectById(@Path("id") String id);

}
