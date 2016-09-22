package com.example.test;

public class Body {

    private String ID;

    private String type;

    private String availableAction;

    private String description;

    private String status;


    Body(String id, String type, String availableAction, String description, String status) {
        super();

        this.ID = id;
        this.type = type;
        this.availableAction = availableAction;
        this.description = description;
        this.status = status;
    }

    public final String GetObjectId() {
        return ID;
    }

    public final String GetObjectType() {
        return type;
    }

    public final String GetObjectAvailableAction() {
        return availableAction;
    }

    public final String GetObjectDescription() {
        return description;
    }

    public final String GetObjectStatus() {
        return status;
    }

}
