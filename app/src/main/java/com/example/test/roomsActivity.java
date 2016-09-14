package com.example.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class roomsActivity extends AppCompatActivity implements View.OnClickListener {


    int room;

    LinearLayout linearLayout;

    private LinearLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        linearLayout = (LinearLayout) findViewById(R.id.lineralMain);

        room = getIntent().getExtras().getInt("Room");

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.topMargin = 50;

        layoutParams.gravity = Gravity.CENTER;

       switch (room){
           case 1:
               GenerateMainInformation(1,layoutParams,linearLayout);
               break;
           case 2:
               GenerateMainInformation(2,layoutParams,linearLayout);
               break;
           case 3:
               GenerateMainInformation(3,layoutParams,linearLayout);
               break;

           default:
               break;
       }


    }


    @Override
    public void onClick(View v) {


        switch(v.getId()){

            case R.id.AllInformation:
                RemoveAllView(linearLayout);
                GenerateALlButton(room);
                break;

            case R.id.Door:

                Switch Door = (Switch) findViewById(R.id.Door);


                if(Door.isChecked())
                {
                   Toast toast = Toast.makeText(this,"Door has opened",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else{
                    Toast.makeText(this,"Door has closed",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.Light:

                Switch Light = (Switch) findViewById(R.id.Light);

                if(Light.isChecked()){
                    Toast toast = Toast.makeText(this,"Light has turned on",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else {
                    Toast.makeText(this,"Light has turned off",Toast.LENGTH_SHORT).show();
                }


            default:
                break;

        }



    }


    private void GenerateMainInformation(int roomNumber,LinearLayout.LayoutParams layoutParams,LinearLayout linearLayout)
    {
        switch (roomNumber){
            case 1:
                GenerateDoorButton(linearLayout,layoutParams);
                GenerateAllInformationButton(linearLayout,layoutParams);
                break;

            case 2:
                GenerateDoorButton(linearLayout,layoutParams);
                GenerateAllInformationButton(linearLayout,layoutParams);
                break;

            case 3:
                GenerateDoorButton(linearLayout,layoutParams);
                GenerateAllInformationButton(linearLayout,layoutParams);
                break;

            default:
                break;
        }



    }

    private void GenerateDoorButton(LinearLayout linearLayout,LinearLayout.LayoutParams layoutParams)
    {


       Switch Door = new Switch(this);

        Door.setText("Door");
        Door.setId(R.id.Door);
        Door.setOnClickListener(this);

        linearLayout.addView(Door,layoutParams);
    }

    private void GenerateLightButton(LinearLayout linearLayout,LinearLayout.LayoutParams layoutParams)
    {
        Switch Light = new Switch(this);

        Light.setText("Light");
        Light.setId(R.id.Light);
        Light.setOnClickListener(this);

        linearLayout.addView(Light,layoutParams);
    }

    private void GenerateAllInformationButton(LinearLayout linearLayout,LinearLayout.LayoutParams layoutParams){

        Button AllInformation = new Button(this);

        AllInformation.setText("Show All Information");
        AllInformation.setId(R.id.AllInformation);
        AllInformation.setOnClickListener(this);

        linearLayout.addView(AllInformation,layoutParams);
    }

    private void RemoveAllView(LinearLayout linearLayout){
        linearLayout.removeAllViewsInLayout();
    }

    private void GenerateALlButton(int roomNumber){

        switch (roomNumber){
            case 1:
                GenerateDoorButton(linearLayout,layoutParams);
                GenerateLightButton(linearLayout,layoutParams);
                break;

            case 2:
                GenerateDoorButton(linearLayout,layoutParams);
                GenerateLightButton(linearLayout,layoutParams);
                break;

            case 3:
                GenerateDoorButton(linearLayout,layoutParams);
                GenerateLightButton(linearLayout,layoutParams);
                break;

            default:
                break;
        }

    }


}
