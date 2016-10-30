package com.minhnpa.coderschool.coderschool_simpletwitterclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.R;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.model.Tweet;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.TwitterApplication;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.TwitterClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    public void loginToRest(View view) {
        getClient().connect();
    }
}
