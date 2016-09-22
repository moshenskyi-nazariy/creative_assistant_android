package com.example.test.Interface;


import com.example.test.Body;
import com.example.test.Room;
import com.example.test.RoomResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface RestInterface {

    @GET("/rooms/")
    Call<RoomResponse>getRoomList();

    @GET("/objects")
    Call<List<Body>>getObjectList();

    //TODO POST запросы

}
