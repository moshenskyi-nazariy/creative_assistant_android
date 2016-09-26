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
import com.example.test.Objects.Object;
import com.example.test.Objects.ObjectsResponse;
import com.example.test.Rooms.Room;
import com.example.test.Rooms.RoomsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseRoomActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    Константы
     */

    /*********************************************************/

    private final String URL = "http://192.168.88.70/";

    /********************************************************/

    /*
    Объекты классов
     */

    /*********************************************************/

    //объект класса intent для перехода или передачи данных на активность roomsActivity
    Intent intent;

    //объект класса intent для перехода или передачи данных на активность LoginActivity
    Intent intent1;

    //объект класса intent для передачи отображения со списком объектов в комнате на roomsActivity
    Intent mapObjectsIntent;

    Intent errorIntent;

    //параметры для кнопок
    LinearLayout.LayoutParams layoutParams;

    Gson gson = new GsonBuilder().create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private RestInterface restInterface = retrofit.create(RestInterface.class);

    Button [] rooms;

    Integer [] ids;

    Response<RoomsResponse> roomResponse;

    Response<ObjectsResponse> objectsResponse;

    Map<String, ArrayList<String>> objectsMap;

    ArrayList<String> arrayList;

    boolean IsError = false;
    /*********************************************************/

    /*
    Элементы на экране
     */

    /*********************************************************/

    //область на экране для размещения элементов
    LinearLayout linearLayout;

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

        errorIntent = new Intent(ChooseRoomActivity.this, errorActivity.class);

        ids = new Integer[6];

        objectsMap = new HashMap<>();

        arrayList = new ArrayList<>();

        ids[0] = R.id.Room1; // Room1 = Corridor
        ids[1] = R.id.Room2; // Room2 = Kitchen
        ids[2] = R.id.Room3; // Room3 = Bathroom
        ids[3] = R.id.Room4; // Room4 = Bedroom
        ids[4] = R.id.Room5; // Room5 = Office
        ids[5] = R.id.Room6; // Room6 = Living Room

        //разрешение выполнения синхронных запросов в главном потоке
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<RoomsResponse> callObject = restInterface.getObjectWithRoomList();

        try {

            roomResponse = callObject.execute();

        } catch (IOException e) {

            startActivity(errorIntent);

            return;
        }

        RoomsResponse roomContainer = roomResponse.body();

        List<Room> roomList = roomContainer.getRooms();

        int size = roomList.size();

        rooms = new Button[size];

        for(int i = 0;i < rooms.length;i++) {

            rooms[i] = new Button(this);
            rooms[i].setOnClickListener(this);
        }

        for(int i = 0; i < rooms.length;i++) {

            rooms[i].setText(roomList.get(i).GetDescription());
            rooms[i].setId(ids[i]);
        }

        /*objectsMap - отображение для объектов комнат,
         *key - имя комнаты
         *value - список названия объектов в комнате
         */
        for(int i = 0;i < size;i++) {

            objectsMap.put(roomList.get(i).GetDescription(),roomList.get(i).GetObjectList() );
        }

        Call<ObjectsResponse> callObject1 = restInterface.getObjectWithObjectList();

        try {

            objectsResponse = callObject1.execute();

        } catch (IOException e) {

            startActivity(errorIntent);

            return;
        }

        ObjectsResponse objectContainer = objectsResponse.body();

        List<Object> objectList = objectContainer.GetObjects();


        //инициализация объектов класса intent
        intent = new Intent(ChooseRoomActivity.this, roomsActivity.class);
        intent1 = new Intent(ChooseRoomActivity.this, LoginActivity.class);
        mapObjectsIntent = new Intent(ChooseRoomActivity.this, roomsActivity.class);


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

            //нажата кнопка "Corridor"
            case R.id.Room1:

                arrayList = objectsMap.get("Corridor");

                mapObjectsIntent.putStringArrayListExtra("roomObjectList", arrayList);

                //выводим сообщение на экран
                Toast.makeText(this, "You have chosen the corridor", Toast.LENGTH_SHORT).show();

                //переходим на активность roomsActivity
                startActivity(mapObjectsIntent);
                break;

            //нажата кнопка "Kitchen"
            case R.id.Room2:

                arrayList = objectsMap.get("Kitchen");

                mapObjectsIntent.putStringArrayListExtra("roomObjectList", arrayList);

                //выводим сообщение на экран
                Toast.makeText(this, "You have chosen the kitchen", Toast.LENGTH_SHORT).show();

                //переходим на активность roomsActivity
                startActivity(mapObjectsIntent);
                break;

            //нажата кнопка "Bathroom"
            case R.id.Room3:

                arrayList = objectsMap.get("Bathroom");

                mapObjectsIntent.putStringArrayListExtra("roomObjectList", arrayList);

                //выводим сообщение на экран
                Toast.makeText(this, "You have chosen the bathroom", Toast.LENGTH_SHORT).show();

                //переходим на активность roomsActivity
                startActivity(mapObjectsIntent);
                break;

            //нажата кнопка "Bedroom"
            case R.id.Room4:

                arrayList = objectsMap.get("Bedroom");

                mapObjectsIntent.putStringArrayListExtra("roomObjectList", arrayList);

                Toast.makeText(this, "You have chosen the bedroom", Toast.LENGTH_SHORT).show();

                startActivity(mapObjectsIntent);
                break;

            //нажата кнопка "Office"
            case R.id.Room5:

                arrayList = objectsMap.get("Office");

                mapObjectsIntent.putStringArrayListExtra("roomObjectList", arrayList);

                Toast.makeText(this, "You have chosen the office", Toast.LENGTH_SHORT).show();

                startActivity(mapObjectsIntent);
                break;

            //нажата кнопка "Living Room"
            case R.id.Room6:

                arrayList = objectsMap.get("Living Room");

                mapObjectsIntent.putStringArrayListExtra("roomObjectList", arrayList);

                Toast.makeText(this, "You have chosen the living room", Toast.LENGTH_SHORT).show();

                startActivity(mapObjectsIntent);
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



