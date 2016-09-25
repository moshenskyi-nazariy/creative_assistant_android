package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class errorActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        Button b = new Button(this);

        b = (Button) findViewById(R.id.retryButton);

        b.setOnClickListener(this);

        intent = new Intent(errorActivity.this, ChooseRoomActivity.class);


    }

    @Override
    public void onClick(View v) {


        switch(v.getId()) {
            case R.id.retryButton:
                startActivity(intent);
        }
    }
}
