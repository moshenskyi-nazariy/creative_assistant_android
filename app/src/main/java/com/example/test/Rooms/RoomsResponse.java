package com.example.test.Rooms;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoomsResponse {

    @SerializedName("rooms")
    @Expose
    private List<Room> rooms = new ArrayList<>();

    private RoomsResponse(List<Room> rooms) {

        this.rooms = rooms;

    }

    public List<Room> getRooms() { return rooms; }

    public void setRooms(List<Room> mRooms) {
        rooms = mRooms;
    }
}
