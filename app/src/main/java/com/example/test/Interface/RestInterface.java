package com.example.test.Interface;


import com.example.test.Body;
import com.example.test.Room;
import com.example.test.RoomResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestInterface {

    @GET("room_list/")
    Call<List<Room>>getRoomList();

    @GET("objects/")
    Call<List<Body>>getObjectList();

    //TODO POST запросы

}
