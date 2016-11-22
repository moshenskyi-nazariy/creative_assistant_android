package com.example.recyclerviewtest.content;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoomResponse {

    @SerializedName("rooms")
    private List<Room> rooms = new ArrayList<>();

    public RoomResponse(@NonNull List<Room> rooms) {

        this.rooms = rooms;
    }

    @NonNull
    public List<Room> getRooms() { return rooms; }

    public void setRooms(List<Room> mRooms) {
        rooms = mRooms;
    }
}
