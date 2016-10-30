package com.minhnpa.coderschool.coderschool_simpletwitterclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minhnpa.coderschool.coderschool_simpletwitterclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnCancel)
    ImageButton btnCancel;

    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;

    @BindView(R.id.btnTweet)
    Button btnTweet;

    @BindView(R.id.edtTweet)
    EditText edtTweet;

    @BindView(R.id.tvLength)
    TextView tvLength;

    private TextWatcher mTextEditorWatcher;
    private final int MAX_LENGTH = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvLength.setText("140");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvLength.setText(String.valueOf(MAX_LENGTH - s.length()) + "/" + MAX_LENGTH);
            }

            public void afterTextChanged(Editable s) {
            }
        };
        edtTweet.addTextChangedListener(mTextEditorWatcher);
        edtTweet.setFocusable(true);
        btnCancel.setOnClickListener(this);
        btnTweet.setOnClickListener(this);
    }

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
                Intent data = getIntent();

                data.putExtra("tweet", edtTweet.getText().toString());

                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }
}
