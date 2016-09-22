package com.example.test;

import java.util.List;

public class Room {

    private String ID;

    private String type;

    private String description;

    private List<Body> bodyList;

    Room(String id, String type, String description, List<Body> bodyList) {

        super();

        this.ID = id;
        this.type = type;
        this.description = description;
        this.bodyList = bodyList;
    }

    public final String GetId() {
        return ID;
    }

    public final String GetType() {
        return type;
    }

    public final String GetDescription() {
        return description;
    }

    public final List<Body> GetObjectList() {
        return bodyList;
    }

}
