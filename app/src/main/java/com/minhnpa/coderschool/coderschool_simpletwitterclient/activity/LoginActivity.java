package com.minhnpa.coderschool.coderschool_simpletwitterclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.R;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.TwitterClient;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.login, menu);
//        return true;
//    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    public void loginToRest(View view) {
        getClient().connect();
    }
}
