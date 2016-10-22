package space.dotcat.assistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import space.dotcat.assistant.Interface.RestInterface;
import space.dotcat.assistant.Objects.Object;
import space.dotcat.assistant.Objects.ObjectsResponse;
import space.dotcat.assistant.Rooms.Room;
import space.dotcat.assistant.Rooms.RoomsResponse;
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

    //  private final String URL = "http://api.ks-cube.tk/";

    private String url;

    private final String APP_SETTINGS = "my settings";

    private final static String URL = "url111";

    /********************************************************/

    /*
    Объекты классов
     */

    /*********************************************************/

    //объект класса RoomsIntent для перехода или передачи данных на активность roomsActivity
    Intent RoomsIntent;

    //объект класса LoginIntent для перехода или передачи данных на активность LoginActivity
    Intent LoginIntent;

    //объект класса intent для передачи отображения со списком объектов в комнате на roomsActivity
    Intent mapObjectsIntent;

    Intent errorIntent;

    //параметры для кнопок
    LinearLayout.LayoutParams layoutParams;

    //конвертор gson для преобразования из JSON в JAVA объект
    Gson gson = new GsonBuilder().create();


    /*
    //создание объекта библиотеки Retrofit
    private Retrofit retrofit = new Retrofit.Builder()
            //указание базового адреса
            .baseUrl(URL)
            //добавление конвертора
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    // создание интерфейса для выполнения запросов
    private RestInterface restInterface = retrofit.create(RestInterface.class);
    */
    Button[] rooms;

    Integer[] ids;

    Response<RoomsResponse> roomResponse;

    Response<ObjectsResponse> objectsResponse;

    Map<String, ArrayList<String>> objectsMap;

    ArrayList<String> arrayList;

    SharedPreferences sh;
    /*********************************************************/

    /*
    Элементы на экране
     */

    /*********************************************************/

    //область на экране для размещения элементов
    LinearLayout linearLayout;
    Call<RoomsResponse> callObjectWithRoomList;
    Call<ObjectsResponse> callObjectWithObjectList;
    RestInterface restInterface;

    List<Room> roomList;
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
        setContentView(R.layout.activity_choose_room);
        ids = new Integer[6];

        sh = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);

        url = getIntent().getStringExtra("url");

        if(url == null)
            url = sh.getString(URL,"");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restInterface = retrofit.create(RestInterface.class);
        objectsMap = new HashMap<>();
        arrayList = new ArrayList<>();
        //инициализация объектов класса intent
        RoomsIntent = new Intent(ChooseRoomActivity.this
                ,roomsActivity.class);
        LoginIntent = new Intent(ChooseRoomActivity.this
                ,LoginActivity.class);
        mapObjectsIntent = new Intent(ChooseRoomActivity.this
                ,roomsActivity.class);
        errorIntent = new Intent(ChooseRoomActivity.this
                ,errorActivity.class);

        //разрешение выполнения синхронных запросов в главном потоке
        StrictMode.ThreadPolicy policy = new StrictMode
                .ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);

        //вызываем у интерфейса метод по получению объекта со списком комнат
        CheckConnectingToServer("RoomList");

        if(roomResponse.body() == null) {

            SharedPreferences.Editor ed = sh.edit();

            ed.putString(URL, url);
            ed.apply();

            startActivity(errorIntent);
            return;
        }


        // записываю тело ответа в JAVA объект
        RoomsResponse roomContainer = roomResponse.body();
        
        //получаю список всех комнат
        GenerateButton(roomContainer);

        CheckConnectingToServer("ObjectList");

        if(objectsResponse.body() == null) {
            startActivity(errorIntent);
            return;
        }
        ObjectsResponse objectContainer = objectsResponse.body();

        List<Object> objectList = objectContainer.GetObjects();
        //нахождение элементана экране по его ID
        linearLayout = (LinearLayout) findViewById(R.id.lMain);

        SetParamButton();
        DrawRoomButton(rooms, layoutParams);
    }


    private void SetParamButton(){
        //инициализация объекта класса LinearLayout.LayoutParams
        //WRAP_CONTENT - размер кнопок будет в зависимости от текста внутри
        layoutParams = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams
                .WRAP_CONTENT
                ,LinearLayoutCompat
                .LayoutParams
                .WRAP_CONTENT);
        //отступ сверху
        layoutParams.topMargin = 50;
        //расположение, по центру
        layoutParams.gravity = Gravity.CENTER;
        //вызываем метод отрисовки кнопок на экран
    }

    private void initializingButtonID(){
        ids[0] = R.id.Room1; // Room1 = Corridor
        ids[1] = R.id.Room2; // Room2 = Kitchen
        ids[2] = R.id.Room3; // Room3 = Bathroom
        ids[3] = R.id.Room4; // Room4 = Bedroom
        ids[4] = R.id.Room5; // Room5 = Office
        ids[5] = R.id.Room6; // Room6 = Living Room
    }

    private void GenerateButton(RoomsResponse _container){
        initializingButtonID();
        roomList = _container.getRooms();
        int size = roomList.size();
        rooms = new Button[size];

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new Button(this);
            rooms[i].setOnClickListener(this);
        }
        for (int i = 0; i < rooms.length; i++) {
            rooms[i].setText(roomList.get(i).GetDescription());
            rooms[i].setId(ids[i]);
        }
        /*objectsMap - отображение для объектов комнат,
         *key - имя комнаты
         *value - список названия объектов в комнате
         */
        for (int i = 0; i < size; i++) {

            objectsMap.put(roomList.get(i)
                    .GetDescription()
                    ,roomList.get(i)
                            .GetObjectList());
        }
    }


    private void CheckConnectingToServer(String _type) {
        switch (_type) {
            case ("RoomList"):
                try {
                    callObjectWithRoomList = restInterface.getObjectWithRoomList();
                    //делаем запрос и записываем ответ от сервера в переменную
                    roomResponse = callObjectWithRoomList.execute();
                } catch (IOException e) {

                    startActivity(errorIntent);

                    return;
                }
                break;
            case ("ObjectList"):
                callObjectWithObjectList = restInterface.getObjectWithObjectList();
                try {

                    objectsResponse = callObjectWithObjectList.execute();
                } catch (IOException q) {
                    startActivity(errorIntent);
                    return;
                }
                break;
            default:
                break;
        }
    }




    /*********************************************************/

    /*
    Обработчик нажатий кнопок
     */

    /*********************************************************/

    private void GoToRoom(String _nameRoom
            ,String _massage){
        arrayList = objectsMap.get(_nameRoom);

        mapObjectsIntent.putStringArrayListExtra("roomObjectList"
                ,arrayList);

        mapObjectsIntent.putExtra("url"
                ,url);

        //выводим сообщение на экран
        Toast.makeText(this, _massage
                ,Toast.LENGTH_SHORT)
                .show();

        //переходим на активность roomsActivity
        startActivity(mapObjectsIntent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            /*
             В зависимости от того какая комната была выбрана, извлекаем список действий по комнате
             из словаря objectMap и передаем этот список на roomsActivity
             */

            //нажата кнопка "Corridor"
            case R.id.Room1:
                GoToRoom("Corridor"
                        ,"You have chosen the corridor");
                break;

            //нажата кнопка "Kitchen"
            case R.id.Room2:
                GoToRoom("Kitchen"
                        ,"You have chosen the kitchen");
                break;

            //нажата кнопка "Bathroom"
            case R.id.Room3:
                GoToRoom("Bathroom"
                        ,"You have chosen the bathroom");
                break;

            //нажата кнопка "Bedroom"
            case R.id.Room4:
                GoToRoom("Bedroom"
                        ,"You have chosen the bedroom");
                break;

            //нажата кнопка "Office"
            case R.id.Room5:
                GoToRoom("Office"
                        ,"You have chosen the office");
                break;

            //нажата кнопка "Living Room"
            case R.id.Room6:
                GoToRoom("Living Room"
                        ,"You have chosen the living room");
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

    private void DrawRoomButton(Button [] b1
            ,LinearLayout.LayoutParams layoutParams){

        //перебираем массив и каждый объект добавляем в linearLayout
        for (Button b: b1) {
            linearLayout.addView(b
                    ,layoutParams);
        }
    }

    /*********************************************************/

    /*
    Методы для работы с меню
     */

    /*********************************************************/
/*
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
                //помещаем в LoginIntent значение 1
                LoginIntent.putExtra("Quit",1);

                //переходим на активность LoginActivity
                startActivity(LoginIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
    /*********************************************************/


    public void onPause() {

        super.onPause();

        SharedPreferences.Editor ed = sh.edit();

        ed.putString(URL, url);
        ed.apply();
    }

}



