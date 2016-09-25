package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.test.Interface.RestInterface;
import com.example.test.Messages.ActionParams;
import com.example.test.Messages.Body;
import com.example.test.Messages.Message;
import com.example.test.Messages.PostResult;
import com.example.test.Objects.Object;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class roomsActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     Константы
     */

    /*********************************************************/

    private final String URL = "http://api.ks-cube.tk/";

    /*********************************************************/

    /*
    Переменные
     */

    /*********************************************************/

    //переменная для хранения значения переданного из активности ChooseRoomActivity
    int room;

    String idDoor;

    String idCurtain;

    String idLight;

    String idVentilations;

    Map<String,String> stateSwitches = new HashMap<>();

    /*********************************************************/

    /*
    Объекты классов
     */

    /*********************************************************/

    //параметры для кнопок
    LinearLayout.LayoutParams layoutParams;

    Gson gson = new GsonBuilder().create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private RestInterface restInterface = retrofit.create(RestInterface.class);

    ActionParams actionParams = new ActionParams();

    ArrayList<String> objectList;

    Response<PostResult> response;

    Intent errorActivity;

    /*********************************************************/

    /*
    Элементы на экране
     */

    /*********************************************************/

    //область на экране для размещения элементов
    LinearLayout linearLayout;

    /*********************************************************/

    /*
    Метод вызываемый при создании активности roomsActivity
     */

    /*********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        errorActivity = new Intent(roomsActivity.this, errorActivity.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        objectList = getIntent().getStringArrayListExtra("roomObjectList");

        for(String s : objectList) {

            if(s.contains("D"))
                idDoor = s;

            if(s.contains("Li"))
                idLight = s;

            if(s.contains("SB"))
                idCurtain = s;

            if(s.contains("F"))
                idVentilations = s;
        }

        if(idDoor != null) {

            Call<Object> objectById = restInterface.getObjectById(idDoor);

            try {

                Response<Object> response = objectById.execute();

                stateSwitches.put(idDoor, response.body().getStatus());

            } catch (IOException e) {
                startActivity(errorActivity);

                return;
            }
        }

        if(idLight != null) {

            Call<Object> objectById = restInterface.getObjectById(idLight);

            try {

                Response<Object> response = objectById.execute();

                stateSwitches.put(idLight, response.body().getStatus());

            } catch (IOException e) {
                startActivity(errorActivity);

                return;
            }
        }

        if(idCurtain != null) {

            Call<Object> objectById = restInterface.getObjectById(idCurtain);

            try {

                Response<Object> response = objectById.execute();

                stateSwitches.put(idCurtain, response.body().getStatus());

            } catch (IOException e) {
                startActivity(errorActivity);

                return;
            }
        }

        if(idVentilations != null) {

            Call<Object> objectById = restInterface.getObjectById(idVentilations);

            try {

                Response<Object> response = objectById.execute();

                stateSwitches.put(idVentilations, response.body().getStatus());

            } catch (IOException e) {
                startActivity(errorActivity);

                return;
            }
        }

        //нахождение элемента экрана по его ID
        linearLayout = (LinearLayout) findViewById(R.id.lineralMain);

        //инициализация объекта класса LinearLayout.LayoutParams
        //WRAP_CONTENT - размер кнопок будет в зависимости от текста внутри
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //отступ сверху
        layoutParams.topMargin = 50;

        //расположение по центру
        layoutParams.gravity = Gravity.CENTER;

        GenerateButtonInRoom(linearLayout, layoutParams, objectList, stateSwitches);

    }

    /*********************************************************/

    /*
    Обработчик нажатий кнопок
     */

    /*********************************************************/

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            //нажата кнопка "Show All Information"
            case R.id.AllInformation:
                //убираем все элементы с экрана
                RemoveAllView(linearLayout);

                //отображаем все действия в комнате на экране
                //GenerateALlButton(room);
                break;

            //нажата кнопка "Door"
            case R.id.Door:

                //находим элемент на экране по его ID
                Switch Door = (Switch) findViewById(R.id.Door);

                /*Если switch переключают в "активное" состояние, то
                 *необходимо выдать сообщение об открытии двери,
                 *в другом случае нужно сообщить о том что дверь была закрыта*/
                if(Door.isChecked()) {

                    DoPost("open", idDoor, actionParams);

                    //создаём сообщение
                    Toast toast = Toast.makeText(this, "Door has opened", Toast.LENGTH_SHORT);

                    //ставим сообщению параметр, с помощью которого оно будет выводится по центру
                    toast.setGravity(Gravity.CENTER, 0, 0);

                    //выводим сообщение на экран
                    toast.show();
                } else {

                    DoPost("close", idDoor, actionParams);

                    //выводим сообщение на экран
                    Toast.makeText(this, "Door has closed", Toast.LENGTH_SHORT).show();
                }
                break;

            //нажата кнопка "Light"
            case R.id.Light:
                //находим элемент на экране по его ID
                Switch Light = (Switch) findViewById(R.id.Light);

                /*Если switch переключают в "активное" состояние,
                 *то необходимо выдать сообщение о включении света,
                 *в другом случае нужно сообщить о том что свет был выключен*/
                if(Light.isChecked()) {

                    DoPost("set_on", idLight, actionParams);

                    //инициализируем объект сообщение
                    Toast toast = Toast.makeText(this, "Light has turned on", Toast.LENGTH_SHORT);

                    //ставим сообщению параметр, с помощью которого оно будет выводится по центру
                    toast.setGravity(Gravity.CENTER, 0, 0);

                    //показываем сообщение
                    toast.show();
                } else {

                    DoPost("set_off", idLight, actionParams);
                    //показываем сообщение на экране
                    Toast.makeText(this, "Light has turned off", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.Curtain:

                Switch Curtain = (Switch) findViewById(R.id.Curtain);

                if(Curtain.isChecked()) {

                    DoPost("open", idCurtain, actionParams);

                    Toast toast = Toast.makeText(this, "Curtain has opened", Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.CENTER, 0, 0);

                    toast.show();
                } else {

                    DoPost("close", idCurtain, actionParams);

                    Toast.makeText(this, "Curtain has closed", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.Ventilation:

                Switch Ventilation = (Switch) findViewById(R.id.Ventilation);

                if(Ventilation.isChecked()) {

                    DoPost("set_on", idVentilations, actionParams);

                    Toast toast = Toast.makeText(this, "Ventilation has opened", Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.CENTER, 0, 0);

                    toast.show();
                } else {

                    DoPost("set_off", idVentilations, actionParams);

                    Toast.makeText(this, "Ventilation has closed", Toast.LENGTH_SHORT).show();
                }
                break;


            default:
                break;
        }
    }

    /*********************************************************/

    /*
    Метод для выведения на экран краткой информации по комнате и кнопки "Show All Information"
     */

    /*********************************************************/
/*
    private void GenerateMainInformation(int roomNumber,
                                         LinearLayout.LayoutParams layoutParams,
                                         LinearLayout linearLayout) {
        switch (roomNumber) {
            //выбрана комната "Kitchen"
            case 1:
                //выводит на экран кнопку двери
                GenerateDoorButton(linearLayout, layoutParams);
                GenerateCurtainButton(linearLayout, layoutParams);


                //выводит на экран кнопку показа всей информаци
                GenerateAllInformationButton(linearLayout, layoutParams);
                break;

            //выбрана комната "Bathroom"
            case 2:
                //выводит на экран кнопку двери
                GenerateDoorButton(linearLayout, layoutParams);

                //выводит на экран кнопку показа всей информаци
                GenerateAllInformationButton(linearLayout, layoutParams);
                break;

            //выбрана комната "Bedroom"
            case 3:
                //выводит на экран кнпоку двери
                GenerateDoorButton(linearLayout, layoutParams);

                //выводит на экран кнопку показа всей информаци
                GenerateAllInformationButton(linearLayout, layoutParams);
                break;

            default:
                break;
        }
    }
*/
    /*********************************************************/

    /*
    Методы для создания и отрисовки элементов на экране
     */

    /*********************************************************/

    //создание "кнопки" двери
    private void GenerateDoorButton(LinearLayout linearLayout,
                                    LinearLayout.LayoutParams layoutParams,
                                    String status) {
        //инициализация объекта Switch
        Switch Door = new Switch(this);

        if(status.equals("opened"))
            Door.setChecked(true);
        else
            Door.setChecked(false);

        //установка текста
        Door.setText("Door");

        //установка ID
        Door.setId(R.id.Door);

        //установка обработчика нажатий на Switch двери
        Door.setOnClickListener(this);

        //добавление "кнопки" двери в linearLayout
        linearLayout.addView(Door, layoutParams);
    }

    //создание "кнопки" света
    private void GenerateLightButton(LinearLayout linearLayout,
                                     LinearLayout.LayoutParams layoutParams,
                                     String status ) {
        //инициализация объекта Switch
        Switch Light = new Switch(this);

        if(status.equals("on"))
            Light.setChecked(true);
        else
            Light.setChecked(false);

        //установка текста
        Light.setText("Light");

        //установка ID
        Light.setId(R.id.Light);

        //установка обработчика нажатий на Switch света
        Light.setOnClickListener(this);

        //добавление "кнопки" света в linearLayout
        linearLayout.addView(Light, layoutParams);
    }

    //создание "кнопки" шторы
    private void GenerateCurtainButton(LinearLayout linearLayout,
                                       LinearLayout.LayoutParams layoutParams,
                                       String status ) {

        Switch Curtain = new Switch(this);

        if(status.equals("opened"))
            Curtain.setChecked(true);
        else
            Curtain.setChecked(false);

        Curtain.setText("Curtain");

        Curtain.setId(R.id.Curtain);

        Curtain.setOnClickListener(this);

        linearLayout.addView(Curtain, layoutParams);
    }

    //создания "кнопки" вентиляции
    private void GenerateVentilationButton(LinearLayout linearLayout,
                                           LinearLayout.LayoutParams layoutParams,
                                           String status) {

        Switch Ventilation = new Switch(this);

        if(status.equals("on"))
            Ventilation.setChecked(true);
        else
            Ventilation.setChecked(false);

        Ventilation.setText("Ventilation");

        Ventilation.setId(R.id.Ventilation);

        Ventilation.setOnClickListener(this);

        linearLayout.addView(Ventilation, layoutParams);
    }


    //создание вспомогательной кнопки всей информации о комнате
    private void GenerateAllInformationButton(LinearLayout linearLayout,
                                              LinearLayout.LayoutParams layoutParams) {
        //инициализация кнопки
        Button AllInformation = new Button(this);

        //установка текста
        AllInformation.setText("Show All Information");

        //установка ID
        AllInformation.setId(R.id.AllInformation);

        //установка обработчика нажатий на кнопку
        AllInformation.setOnClickListener(this);

        //добавление кнопки общей информации в linearLayout
        linearLayout.addView(AllInformation, layoutParams);
    }
    /*********************************************************/

    /*
    Метод удаления всех элементов с экрана
     */

    /*********************************************************/

    private void RemoveAllView(LinearLayout linearLayout) {
        //удаление из главного linearLayout всех элементов
        linearLayout.removeAllViewsInLayout();
    }

    /*********************************************************/

    /*
    Метод выведения на экран всей информации о комнате
     */

    /*********************************************************/
/*
    private void GenerateALlButton(int roomNumber) {

        switch (roomNumber) {
            //выбрана комната "Kitchen"
            case 1:

                //вывести на экран кнопку двери
                GenerateDoorButton(linearLayout, layoutParams);

                //вывести на экран кнпоку света
                GenerateLightButton(linearLayout, layoutParams);

                GenerateCurtainButton(linearLayout, layoutParams);

                GenerateVentilationButton(linearLayout, layoutParams);
                break;

            //выбрана комната "Bathroom"
            case 2:
                //вывести на экран кнпоку двери
                GenerateDoorButton(linearLayout, layoutParams);

                //вывести на экран кнопку света
                GenerateLightButton(linearLayout, layoutParams);
                break;

            //выбрана комната "Bedroom"
            case 3:
                //вывести на экран кнопку двери
                GenerateDoorButton(linearLayout, layoutParams);

                //вывести на экран кнопку света
                GenerateLightButton(linearLayout, layoutParams);
                break;

            default:
                break;
        }
    }
*/
    public void GenerateButtonInRoom(LinearLayout linearLayout,
                                     LinearLayout.LayoutParams layoutParams,
                                     ArrayList<String> objectList,
                                     Map<String,String> stateSwitchesMap) {

        for(String s : objectList) {

            if(s.contains("D"))
                GenerateDoorButton(linearLayout, layoutParams, stateSwitchesMap.get(s));


            if(s.contains("SB"))
                GenerateCurtainButton(linearLayout, layoutParams, stateSwitchesMap.get(s));

            if(s.contains("Li"))
                GenerateLightButton(linearLayout, layoutParams, stateSwitchesMap.get(s));

            if(s.contains("F"))
                GenerateVentilationButton(linearLayout, layoutParams, stateSwitchesMap.get(s));
        }
    }

    /*********************************************************/

    public Message GenerateMessage(String action,
                                   String obj_id,
                                   ActionParams actionParams) {

        Body body = new Body(action, obj_id, actionParams);

        Message message = new Message(body);

        return message;
    }

    public Response<PostResult> DoPost(String action, String id, ActionParams actionParams) {

        Message message = GenerateMessage(action, id, actionParams);

        Call<PostResult> call = restInterface.postMessage(message);

        try {

            response = call.execute();

        } catch (IOException e) {

            startActivity(errorActivity);

        }

        return response;
    }

    /*********************************************************/

}
