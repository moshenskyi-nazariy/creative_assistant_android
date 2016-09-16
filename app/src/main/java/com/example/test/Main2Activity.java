package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    /*
    Объекты классов
     */

    /*********************************************************/

    //объект класса intent для перехода или передачи данных на активность roomsActivity
    Intent intent;

    //объект класса intent для перехода или передачи данных на активность MainActivity
    Intent intent1;

    //параметры для кнопок
    LinearLayout.LayoutParams layoutParams;

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
    Метод вызываемый при создании активности Main2Activity
     */

    /*********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //инициализация объектов класса intent
        intent = new Intent(Main2Activity.this, roomsActivity.class);
        intent1 = new Intent(Main2Activity.this, MainActivity.class);

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

        //вызываем метод отрисовки кнопок на экран
        GenerateRoomButton(Rooms, layoutParams);
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

                //переходим на активность MainActivity
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*********************************************************/
}



