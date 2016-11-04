package space.dotcat.assistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import space.dotcat.assistant.Interface.RestInterface;
import space.dotcat.assistant.Messages.ActionParams;
import space.dotcat.assistant.Messages.Body;
import space.dotcat.assistant.Messages.Message;
import space.dotcat.assistant.Messages.PostResult;
import space.dotcat.assistant.Objects.Object;
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

public class roomsActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     Константы
     */

    /*********************************************************/

  //  private final String URL = "http://api.ks-cube.tk/";

      private final String APP_SETTINGS = "my settings";


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

    String idPlayer;

    List<String> idsDoor = new ArrayList<>();

    List<String> idsCurtain = new ArrayList<>();

    List<String> idsLight = new ArrayList<>();

    List<String> idsVentilation = new ArrayList<>();

    List<String> idsPlayer = new ArrayList<>();

    int i = 0;


    Map<String,String> stateSwitches = new HashMap<>();

    /*********************************************************/

    /*
    Объекты классов
     */

    /*********************************************************/

    //параметры для кнопок
    LinearLayout.LayoutParams layoutParams;

    Gson gson = new GsonBuilder().create();

    /*
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private RestInterface restInterface = retrofit.create(RestInterface.class);
    */

    private Retrofit retrofit;

    private RestInterface restInterface;

    ActionParams actionParams = new ActionParams();

    ArrayList<String> objectList;

    List<List<String>> objectIds = new ArrayList<>();

    ArrayList<Integer> ids;

    Response<PostResult> response;

    Intent errorActivity;

    Intent intent1;

    SharedPreferences sh;

    /*********************************************************/

    /*
    Элементы на экране
     */

    /*********************************************************/

    //область на экране для размещения элементов
    LinearLayout linearLayout;

    /*********************************************************/

    /*
    Переменная для взаимодействия с фичей pull to refresh
    */

    private SwipeRefreshLayout swipeContainer;
    /*********************************************************/

    /*
    Метод вызываемый при создании активности roomsActivity
     */

    /*********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);


        ids = new ArrayList<Integer>();

        ids.add(R.id.Light);
        ids.add(R.id.Light1);


        //Ищем наш контейнер с фичей Pull To Refresh
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        //Устанавливаем слушателя для события оттяжения окна вниз.
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout
                .OnRefreshListener() {
            //указываем необходимое действие на оттяжение окна вниз
            @Override
            public void onRefresh() {
                //получаем состояние всех переключателей с сервера
                stateSwitches = getStateSwitches(objectIds);
                //удаляем все с экрана
                RemoveAllView(linearLayout);
                //генерируем все заново
                GenerateButtonInRoom(linearLayout
                        ,layoutParams
                        ,objectList
                        ,stateSwitches);
                //завершаем обновление данных
                swipeContainer.setRefreshing(false);

            }
        });

        //setColorSchemeResources - устанавливаем цвета, которыми будет переливатся наша анимация. 
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        errorActivity = new Intent(roomsActivity.this
                ,errorActivity.class);

        intent1 = new Intent(roomsActivity.this,
                LoginActivity.class);

        //разрешение использования синронных запросов в главном потоке
        StrictMode.ThreadPolicy policy = new StrictMode
                    .ThreadPolicy
                    .Builder()
                    .permitAll()
                    .build();
        StrictMode.setThreadPolicy(policy);

        //получение списка объекта в комнате
        objectList = getIntent()
                .getStringArrayListExtra("roomObjectList");
        String url = getIntent()
                .getStringExtra("url");

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restInterface = retrofit.create(RestInterface.class);

        //получение ID элементов
        for(String s : objectList) {

            if(s.contains("D")) {
                idsDoor.add(s);
                objectIds.add(idsDoor);
            }

            if(s.contains("Li")) {
                idsLight.add(s);
                objectIds.add(idsLight);
            }

            if(s.contains("SB")) {
                idsCurtain.add(s);
                objectIds.add(idsCurtain);
            }

            if(s.contains("F")) {
                idsVentilation.add(s);
                objectIds.add(idsVentilation);
            }
            if(s.contains("PLAY")) {
                idsPlayer.add(s);
                objectIds.add(idsPlayer);
            }
        }

        //получение состояний переключателей
        stateSwitches = getStateSwitches(objectIds);


        //нахождение элемента экрана по его ID
        linearLayout = (LinearLayout) findViewById(R.id.lineralMain);

        //инициализация объекта класса LinearLayout.LayoutParams
        //WRAP_CONTENT - размер кнопок будет в зависимости от текста внутри
        layoutParams = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams
                    .WRAP_CONTENT
                ,LinearLayout
                    .LayoutParams
                    .WRAP_CONTENT);

        //отступ сверху
        layoutParams.topMargin = 50;

        //расположение по центру
        layoutParams.gravity = Gravity.CENTER;

        //отрисовка всех действий в комнате на экран
        GenerateButtonInRoom(linearLayout
                ,layoutParams
                ,objectList
                ,stateSwitches);
    }

    /*********************************************************/

    /*
    Обработчик нажатий кнопок
     */

    /*********************************************************/


    private void CheckValueIsChecked(int _id
            ,String _stringIdType
            ,String _action1
            ,String _action2
            ,String _textToShow1
            ,String _textToShow2){

        Switch value = (Switch) findViewById(_id);

        if(value.isChecked()) {

            DoPost(_action1
                    ,_stringIdType
                    ,actionParams);

            Toast toast = Toast.makeText(this
                    ,_textToShow1
                    ,Toast.LENGTH_SHORT);

            toast.setGravity(Gravity.CENTER
                    ,0
                    ,0);

            toast.show();
        } else {

            DoPost(_action2
                    ,_stringIdType
                    ,actionParams);

            Toast.makeText(this
                    ,_textToShow2
                    ,Toast.LENGTH_SHORT)
                    .show();
        }
    }

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
                CheckValueIsChecked(R.id.Door
                        ,idDoor
                        ,"open"
                        ,"close"
                        ,"Door has opened"
                        ,"Door has closed");
                break;

            //нажата кнопка "Light"
            case R.id.Light:
                CheckValueIsChecked(R.id.Light
                        ,idsLight.get(0)
                        ,"set_on"
                        ,"set_off"
                        ,"Light has turned on"
                        ,"Light has turned off");
                break;

            case R.id.Light1:
                CheckValueIsChecked(R.id.Light1,
                        idsLight.get(1),
                        "set_on",
                        "set_off",
                        "Light_has turned on",
                        "Light has turned off");
                break;

            case R.id.Curtain:
                CheckValueIsChecked(R.id.Curtain
                        ,idCurtain
                        ,"open"
                        ,"close"
                        ,"Curtain has opened"
                        ,"Curtain has closed");
                break;

            case R.id.Ventilation:
                CheckValueIsChecked(R.id.Ventilation
                        ,idVentilations
                        ,"set_on"
                        ,"set_off"
                        ,"Ventilation has opened"
                        ,"Ventilation has closed");
                break;

            case R.id.Player:
                CheckValueIsChecked(R.id.Player
                        ,idPlayer
                        ,"play"
                        ,"stop"
                        ,"Player has started"
                        ,"PLayer has stopped");
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


    private void GenerateTypeButton(LinearLayout linearLayout,
                                       LinearLayout.LayoutParams layoutParams,
                                       String status, String _status, String _textToSet, int _id ) {

        Switch modul = new Switch(this);

        if(status.equals(_status))
            modul.setChecked(true);
        else
            modul.setChecked(false);

        modul.setText(_textToSet);

        modul.setId(_id);

        modul.setOnClickListener(this);

        linearLayout.addView(modul, layoutParams);
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

    public void GenerateButtonInRoom(LinearLayout linearLayout
            ,LinearLayout.LayoutParams layoutParams
            ,ArrayList<String> objectList
            ,Map<String,String> stateSwitchesMap) {

        for(String s : objectList) {

            if(s.contains("D"))
                GenerateTypeButton(linearLayout
                        ,layoutParams
                        ,stateSwitchesMap.get(s)
                        ,"opened"
                        ,"Door"
                        ,R.id.Door) ;

            if(s.contains("SB"))
                GenerateTypeButton(linearLayout
                        ,layoutParams
                        ,stateSwitchesMap.get(s)
                        ,"opened"
                        ,"Curtain"
                        ,R.id.Curtain);

            if(s.contains("Li"))
                GenerateTypeButton(linearLayout
                        ,layoutParams
                        ,stateSwitchesMap.get(s)
                        ,"on"
                        ,"Light"
                        ,ids.get(i++));

            if(s.contains("F"))
                GenerateTypeButton(linearLayout
                        ,layoutParams
                        ,stateSwitchesMap.get(s)
                        ,"on"
                        ,"Ventilation"
                        ,R.id.Ventilation);

            if(s.contains("PLAY"))
                GenerateTypeButton(linearLayout,
                        layoutParams,
                        stateSwitches.get(s),
                        "playing",
                        "Player",
                        R.id.Player);

        }
        i = 0;
    }

    /*********************************************************/

      /*
       Методы генерации сообщения для пост запроса
       */

    /*********************************************************/

    public Message GenerateMessage(String action
            ,String obj_id
            ,ActionParams actionParams) {

        Body body = new Body(action, obj_id, actionParams);

        Message message = new Message(body);

        return message;
    }

    /*********************************************************/


      /*
       Методы для выполнения пост запроса
       */

    /*********************************************************/

    public Response<PostResult> DoPost(String action
            ,String id
            ,ActionParams actionParams) {

        Message message = GenerateMessage(action
                ,id
                ,actionParams);

        Call<PostResult> call = restInterface.postMessage(message);

        try {

            response = call.execute();

        } catch (IOException e) {

            startActivity(errorActivity);

        }

        return response;
    }

    /*********************************************************/

     /*
    Методы для работы с меню
     */

    /*********************************************************/

    //Создание menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //Обработчик нажатия кнопок в menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //получаем ID нажатого элемента в menu
        int id = item.getItemId();

        switch (id) {

            //нажата кнопка "Quit"
            case R.id.update:

                stateSwitches = getStateSwitches(objectIds);

                RemoveAllView(linearLayout);

                GenerateButtonInRoom(linearLayout, layoutParams, objectList, stateSwitches);

                return true;

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


    /*
     Получение состояния переключателей
     */

    /*********************************************************/

    private void CheckToException(List<List<String>> ObjectLists)
    {
        if (objectList != null) {

            for(List<String> l : ObjectLists){

               for(String id : l) {

                   Call<Object> objectById = restInterface.getObjectById(id);

                   try {

                       Response<Object> response = objectById.execute();

                       stateSwitches.put(id
                                        ,response
                                         .body()
                                         .getStatus());

                   } catch (IOException e) {
                       startActivity(errorActivity);

                   }
               }
               }


            }


    }


    public Map<String, String> getStateSwitches(List<List<String>> ObjectLists ) {

        CheckToException(ObjectLists);
        return stateSwitches;
    }

    /*********************************************************/

}
