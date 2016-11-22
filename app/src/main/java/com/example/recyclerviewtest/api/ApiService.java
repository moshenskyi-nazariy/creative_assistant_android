package com.example.recyclerviewtest.api;

import com.example.recyclerviewtest.content.Room;
import com.example.recyclerviewtest.content.RoomResponse;

import java.util.List;

import rx.Observable;

import retrofit2.http.GET;

public interface ApiService {

    @GET("rooms/")
    Observable<RoomResponse> rooms();

}
