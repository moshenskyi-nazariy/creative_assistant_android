package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class errorActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;

    Intent intent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        Button b = new Button(this);

        b = (Button) findViewById(R.id.retryButton);

        b.setOnClickListener(this);

        intent = new Intent(errorActivity.this, ChooseRoomActivity.class);

        intent1 = new Intent(errorActivity.this, LoginActivity.class);

    }

    @Override
    public void onClick(View v) {


        switch(v.getId()) {
            case R.id.retryButton:

                startActivity(intent);
        }
    }

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

}
