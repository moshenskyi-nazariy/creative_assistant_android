package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test.Interface.RestInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseRoomActivity extends AppCompatActivity implements View.OnClickListener {


    /*
    Константы
     */

    /*********************************************************/

    private final String URL = "http://api.ks-cube.tk";

    /********************************************************/



    /*
    Объекты классов
     */

    /*********************************************************/

    //объект класса intent для перехода или передачи данных на активность roomsActivity
    Intent intent;

    //объект класса intent для перехода или передачи данных на активность LoginActivity
    Intent intent1;

    //параметры для кнопок
    LinearLayout.LayoutParams layoutParams;

    Gson gson = new GsonBuilder().create();

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private RestInterface restInterface = retrofit.create(RestInterface.class);

    Button [] rooms;
    /*********************************************************/

    /*
    Элементы на экране
     */

    /*********************************************************/

    //область на экране для размещения элементов
    LinearLayout linearLayout;

    //кнопки комнат
    Button room1;
    Button room2;
    Button room3;

    /*********************************************************/

     /*
    Метод для переопределения кнопки "назад"
     */

    /*********************************************************/

    @Override
    public void onBackPressed() {
        //мы запрещаем выходить кнопкой "назад" на главную активность
        //вместо этого наше приложение уходит в "фон"
        moveTaskToBack(true);
    }

    /*********************************************************/

    /*
    Метод вызываемый при создании активности ChooseRoomActivity
     */

    /*********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Call<RoomResponse> callObject = restInterface.getRoomList();

        try {

            Response<RoomResponse> response = callObject.execute();

            RoomResponse roomResponse = gson.fromJson(response.body().toString(), RoomResponse.class);

            List<Room> roomList = roomResponse.getmRooms();

            rooms = new Button[roomList.size()];

            for(int i = 0;i < rooms.length;i++) {

                rooms[i] = new Button(this);
                rooms[i].setOnClickListener(this);
            }

            for(Room r : roomList) {

                for(Button b : rooms){
                    b.setText(r.GetDescription());
                    //TODO добавить id кнопке
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //инициализация объектов класса intent
        intent = new Intent(ChooseRoomActivity.this, roomsActivity.class);
        intent1 = new Intent(ChooseRoomActivity.this, LoginActivity.class);

        //нахождение элементана экране по его ID
        linearLayout = (LinearLayout) findViewById(R.id.lMain);

        //инициализация объекта класса LinearLayout.LayoutParams
        //WRAP_CONTENT - размер кнопок будет в зависимости от текста внутри
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        //отступ сверху
        layoutParams.topMargin = 50;

        //расположение по центру
        layoutParams.gravity = Gravity.CENTER;

        /*
        //инициализация объектов класса Button
        room1 = new Button(this);
        room2 = new Button(this);
        room3 = new Button(this);

        //установка ID для кнопок
        room1.setId(R.id.Room1);
        room2.setId(R.id.Room2);
        room3.setId(R.id.Room3);

        //установка текста для кнопок
        room1.setText("Kitchen");
        room2.setText("Bathroom");
        room3.setText("Bedroom");

        //инициализация массива состоящего из 3 кнопок
        Button [] Rooms = new Button[3];

        //заполнение массива объектами
        Rooms[0] = room1;
        Rooms[1] = room2;
        Rooms[2] = room3;

        //делаем кнопки кликабельными
        for (Button Room : Rooms)
            Room.setOnClickListener(this);
        */
        //вызываем метод отрисовки кнопок на экран
        GenerateRoomButton(rooms, layoutParams);
    }

    /*********************************************************/

    /*
    Обработчик нажатий кнопок
     */

    /*********************************************************/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //нажата кнопка "Kitchen"
            case R.id.Room1:
                //помещаем в intent значение 1,
                intent.putExtra("Room", 1);

                //выводим сообщение на экран
                Toast.makeText(this, "You have choosen the kitchen", Toast.LENGTH_SHORT).show();

                //переходим на активность roomsActivity
                startActivity(intent);
                break;

            //нажата кнопка "Bathroom"
            case R.id.Room2:
                //помещаем в intent значение 2
                intent.putExtra("Room", 2);

                //выводим сообщение на экран
                Toast.makeText(this, "You have choosen the bathroom", Toast.LENGTH_SHORT).show();

                //переходим на активность roomsActivity
                startActivity(intent);
                break;

            //нажата кнопка "Bedroom"
            case R.id.Room3:
                //помещаем в intent значение 3
                intent.putExtra("Room", 3);

                //выводим сообщение на экран
                Toast.makeText(this, "You have choosen the bedroom", Toast.LENGTH_SHORT).show();

                //переходим на активность roomsActivity
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    /*********************************************************/

    /*
    Метод для отрисовки кнопок на экране
     */

    /*********************************************************/

    private void GenerateRoomButton(Button [] b1, LinearLayout.LayoutParams layoutParams){

        //перебираем массив и каждый объект добавляем в linearLayout
        for (Button b: b1) {
            linearLayout.addView(b,layoutParams);
        }
    }

    /*********************************************************/

    /*
    Методы для работы с меню
     */

    /*********************************************************/

    //Создание menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //Обработчик нажатия кнопок в menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //получаем ID нажатого элемента в menu
        int id = item.getItemId();

        switch(id) {

            //нажата кнопка "Quit"
            case R.id.quit:
                //помещаем в intent1 значение 1
                intent1.putExtra("Quit",1);

                //переходим на активность LoginActivity
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*********************************************************/
}



