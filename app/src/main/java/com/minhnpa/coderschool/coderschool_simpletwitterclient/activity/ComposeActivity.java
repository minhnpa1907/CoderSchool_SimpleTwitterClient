package com.minhnpa.coderschool.coderschool_simpletwitterclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.R;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.model.Tweet;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.TwitterApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnCancel)
    ImageButton btnCancel;

    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;

    @BindView(R.id.btnTweet)
    Button btnTweet;

    @BindView(R.id.edtTweet)
    EditText edtTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        btnCancel.setOnClickListener(this);
        btnTweet.setOnClickListener(this);
    }

//    private void postTweet() {
//        String strTweet = edtTweet.getText().toString();
//        TwitterApplication.getRestClient().postTweet(strTweet, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.d("DEBUG", response.toString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnTweet:
                Toast.makeText(this, "Tweet", Toast.LENGTH_SHORT).show();
                Intent data = new Intent();
                Bundle bundle = new Bundle();

                bundle.putString("tweet", edtTweet.getText().toString());

                data.putExtras(bundle);
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }
}
