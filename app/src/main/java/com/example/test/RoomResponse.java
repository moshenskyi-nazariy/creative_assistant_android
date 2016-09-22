package com.example.test;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoomResponse {

    @SerializedName("rooms")
    @Expose
    private List<Room> rooms = new ArrayList<>();

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> mRooms) {
        this.rooms = mRooms;
    }
}
