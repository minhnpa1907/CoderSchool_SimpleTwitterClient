package com.minhnpa.coderschool.coderschool_simpletwitterclient.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    @SerializedName("id")
    private long id;

    @SerializedName("user")
    private User user;

    @SerializedName("text")
    private String text;

    @SerializedName("created_at")
    private String createdAt;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
