package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearLayout;

    Button room1;

    Intent intent;
    Intent intent1;



    LinearLayout.LayoutParams layoutParams;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }//мы запрещаем выходить кнопкой "назад" на главную активность
    //вместо этого наше приложение уходит в "фон"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        intent = new Intent(Main2Activity.this,roomsActivity.class);
        intent1 = new Intent(Main2Activity.this,MainActivity.class);



        linearLayout = (LinearLayout) findViewById(R.id.lMain);

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        layoutParams.topMargin = 50;

        layoutParams.gravity = Gravity.CENTER;

        room1 = new Button(this);

        room1.setId(R.id.Room1);

        room1.setText("Kitchen");


        Button room2 = new Button(this);

        room2.setId(R.id.Room2);

        room2.setText("Bathroom");

        Button room3 = new Button(this);

        room3.setId(R.id.Room3);

        room3.setText("Bedroom");

        Button [] Rooms = new Button[3];

        Rooms[0] = room1;
        Rooms[1] = room2;
        Rooms[2] = room3;

        for(int i = 0;i < Rooms.length;++i)
            Rooms[i].setOnClickListener(this);


        GenerateRoomButton(Rooms,layoutParams);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.Room1:
                intent.putExtra("Room", 1);
                Toast.makeText(this, "You have choosen the kitchen", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;



            case R.id.Room2:
                intent.putExtra("Room",2);
                Toast.makeText(this,"You have choosen the bathroom",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;

            case R.id.Room3:
                intent.putExtra("Room",3);
                Toast.makeText(this,"You have choosen the bedroom",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;

            default:
                break;
        }




    }

    private void GenerateButtons(int _number, LinearLayout.LayoutParams layParams){

       Button [] Buttons = new Button[_number];

        for(int i = 0;i < Buttons.length;i++){

            Buttons[i] = new Button(this);
            Buttons[i].setText("Room" + (1+i));
            Buttons[i].setOnClickListener(this);
            linearLayout.addView(Buttons[i],layParams);
        }

    }

    private void GenerateRoomButton(Button [] b1,LinearLayout.LayoutParams layoutParams){

        for (Button b: b1) {
            linearLayout.addView(b,layoutParams);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {

            case R.id.quit:

                intent1.putExtra("Quit",1);
                startActivity(intent1);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}



