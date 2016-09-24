package com.example.test.Interface;


import com.example.test.Objects.ObjectsResponse;
import com.example.test.Rooms.Room;
import com.example.test.Rooms.RoomsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestInterface {

    @GET("room_list/")
    Call<List<Room>>getRoomList();

    @GET("objects/")
    Call<ObjectsResponse>getObjectWithObjectList();

    @GET("rooms/")
    Call<RoomsResponse>getObjectWithRoomList();


    //TODO POST запросы

}
