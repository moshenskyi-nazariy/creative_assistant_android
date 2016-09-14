package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    public static final String APP_SETTINGS = "mysettings";

    public final static String PasswordForChecking = "admin";
    public final static String LoginForChecking = "admin";


    public static final String sh_pas = "sh_pas";
    public static final String sh_login = "sh_login";


    String tempPass = "";
    String tempLogin = "";




    SharedPreferences sh;


    EditText login;
    EditText pass;

    Switch sw;


    Intent intent;


    int Quit;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this,Main2Activity.class);

        Quit = getIntent().getIntExtra("Quit",0);







        sh = getSharedPreferences(APP_SETTINGS,MODE_PRIVATE);

        login = (EditText)findViewById(R.id.login);
        pass = (EditText)findViewById(R.id.password);

        sw = (Switch)findViewById(R.id.switch1);
    }




    public void onClick(View view) {

        switch(view.getId()) {





            case R.id.joinButton:


                if(login.getText().toString().equals(LoginForChecking) && pass.getText().toString().equals(PasswordForChecking)) {

                    if(sw.isChecked()){

                        tempLogin = login.getText().toString();
                        tempPass = pass.getText().toString();

                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString(sh_login, tempLogin);
                        editor.putString(sh_pas, tempPass);

                        Quit = 0;

                        editor.apply();

                        startActivity(intent);

                    }

                    else {

                       startActivity(intent);
                    }

                }
                else {

                    Toast.makeText(MainActivity.this,"Login or password incorrect",Toast.LENGTH_SHORT).show();


                }


                break;

            default:
                break;
        }






        }







    protected void onResume(){


        super.onResume();

        if(Quit == 1){

            SharedPreferences.Editor ed = sh.edit();

            ed.putString(sh_login,"");
            ed.putString(sh_pas,"");
            ed.apply();
        }

        if(sh.contains(sh_login) && sh.contains(sh_pas)) {

            String s1 = sh.getString(sh_login,"");
            String s2 = sh.getString(sh_pas,"");

            if(s1.equals("admin") && s2.equals("admin"))
                startActivity(intent);


        }









    }






}
