package com.minhnpa.coderschool.coderschool_simpletwitterclient.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("profile_image_url")
    private String profileImageUrl;

//    public static User fromJSON(JSONObject jsonObject) {
//        User user = new User();
//
//        try {
//            user.id = jsonObject.getLong("id");
//            user.name = jsonObject.getString("name");
//            user.screenName = jsonObject.getString("screen_name");
//            user.profileImageUrl = jsonObject.getString("profile_image_url");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return user;
//    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
