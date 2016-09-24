package com.example.test.Objects;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Object {

    @SerializedName("actions")
    @Expose
    private List<String> actions = new ArrayList<String>();

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("type")
    @Expose
    private String type;


    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
