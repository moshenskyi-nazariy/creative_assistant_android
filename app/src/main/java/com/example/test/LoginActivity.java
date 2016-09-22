package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    /*
    Константы
     */

    /*********************************************************/

    public final static String APP_SETTINGS = "mysettings";

    public final static String PasswordForChecking = "admin";
    public final static String LoginForChecking = "admin";

    public final static String sh_pas = "sh_pas";
    public final static String sh_login = "sh_login";

    /********************************************************/

    /*
    Переменные
     */

    /*********************************************************/

    //переменные для временного хранения в них логина и пароля
    public String tempPass = "";
    public String tempLogin = "";

    //переменная для хранения значения переданного из активности ChooseRoomActivity
    int Quit;

    /*********************************************************/

    /*
    Объекты классов
     */

    /*********************************************************/

    //обхект класса для сохранения данных
    SharedPreferences sh;

    //объект класса intent для перехода или передачи данных на другую активность
    Intent intent;

    /*********************************************************/

    /*
    Элементы на экране
     */

    /*********************************************************/

    //поле ввода логина
    EditText login;

    //поле ввода пароля
    EditText pass;

    //checkbox если пользователь захочет сохранить логин и пароль
    CheckBox RememberCheckBox;

    /*********************************************************/

    /*
    Метод вызываемый при создании активности LoginActivity
     */

    /*********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //инициализаци интента,который ведёт к антивности ChooseRoomActivity
        intent = new Intent(LoginActivity.this, ChooseRoomActivity.class);

        //получение значения, передающегося от активности ChooseRoomActivity
        //если ничего не пришло,то по умолчанию присваивается 0
        Quit = getIntent().getIntExtra("Quit", 0);

        //инициализация объекта класса, отвечающего за сохранение настроек
        sh = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);

        //нахождение элементов экрана по их ID
        login = (EditText) findViewById(R.id.login);
        pass = (EditText) findViewById(R.id.password);
        RememberCheckBox = (CheckBox) findViewById(R.id.checkBox);
    }

    /*********************************************************/

    /*
    Обработчик нажатий кнопок
     */

    /*********************************************************/

    public void onClick(View view) {

        switch(view.getId()) {

            //нажата кнопка "войти"
            case R.id.joinButton:

                /*если логин и пароль, введённые пользователем, совпадают с "необходимыми", то
                 *проверяем нажата ли кнопка сheckbox, в другом случае выдаём сообщение о
                 *том что пользователь ввёл некорректные данные*/
                if(login.getText().toString().equals(LoginForChecking) && pass.getText().toString().equals(PasswordForChecking)) {

                    /*если нажата кнопка checkbox для проверки сохранения логина и пароля, то
                     *обнуляем переменную, сохраняем логин и пароль и вызываем активность ChooseRoomActivity,
                     *в другом случае просто переходим на активность ChooseRoomActivity*/
                    if(RememberCheckBox.isChecked()) {
                        //обнуляем переменную в которой хранится значение полученное из ChooseRoomActivity
                        Quit = 0;

                        //помещаем логин и пароль введённые пользователем в переменные дря хранения
                        tempLogin = login.getText().toString();
                        tempPass = pass.getText().toString();

                        //сохраняем логин и пароль
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString(sh_login, tempLogin);
                        editor.putString(sh_pas, tempPass);
                        editor.apply();

                        //вызываем активность ChooseRoomActivity
                        startActivity(intent);
                    } else {
                       //вызываем активность ChooseRoomActivity
                       startActivity(intent);
                    }

                } else {
                    //показываем сообщение на экран
                    Toast.makeText(LoginActivity.this, "Login or password incorrect", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    /*********************************************************/

    /*
    Действия при открытии приложения
     */

    /*********************************************************/

    protected void onResume(){
        super.onResume();

        /*Если было передано из ChooseRoomActivity значение 1, то
         *нужно обнулить сохранённые логин и пароль*/
        if(Quit == 1){

            SharedPreferences.Editor ed = sh.edit();

            //Обнуляем логин и пароль
            ed.putString(sh_login, "");
            ed.putString(sh_pas, "");
            ed.apply();
        }

        /*При входе в приложение заново нужно проверить сохранены ли логин и пароль и
         *если данные имеются, то нужно пропустить страницу с аутентификацией
         */
        if(sh.contains(sh_login) && sh.contains(sh_pas)) {

            String s1 = sh.getString(sh_login, "");
            String s2 = sh.getString(sh_pas, "");

            if(s1.equals("admin") && s2.equals("admin"))
                //вызов активности ChooseRoomActivity
                startActivity(intent);
        }
    }

    /*********************************************************/
}
