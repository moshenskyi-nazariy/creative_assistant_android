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


    private Room(String description, String id, List<String> objects) {

        this.description = description;

        this.id = id;

        this.objects = objects;
    }

    public String GetId() {
        return id;
    }

    public String GetDescription() {
        return description;
    }

    public List<String> GetObjectList() {
        return objects;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

}
