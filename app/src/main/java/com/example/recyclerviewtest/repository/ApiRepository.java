package com.example.recyclerviewtest.repository;


import android.support.annotation.NonNull;

import com.example.recyclerviewtest.content.Room;
import com.example.recyclerviewtest.content.RoomResponse;

import java.util.List;

import rx.Observable;

public interface ApiRepository {

    @NonNull
    Observable<List<Room>> rooms();

}
