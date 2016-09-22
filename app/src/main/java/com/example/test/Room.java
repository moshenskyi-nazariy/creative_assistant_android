package com.example.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Room {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("objects")
    @Expose
    private List<String> objects = new ArrayList<String>();



    public String GetId() {
        return id;
    }

    public String GetDescription() {
        return description;
    }

    public List<String> GetObjectList() {
        return objects;
    }
    

}
