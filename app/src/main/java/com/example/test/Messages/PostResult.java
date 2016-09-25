package com.example.test.Messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResult {

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() { return result;}
}
