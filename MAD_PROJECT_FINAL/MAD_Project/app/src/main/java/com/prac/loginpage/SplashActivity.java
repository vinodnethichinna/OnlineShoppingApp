package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //sqLiteHelper = new SQLiteHelper(this, "ItemsDB.sqlite", null, 1);
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteHelper.queryData(sqLiteHelper.getWritableDatabase(),"CREATE TABLE IF NOT EXISTS ITEMS(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB)");
        sqLiteHelper.queryData(sqLiteHelper.getWritableDatabase(),"CREATE TABLE IF NOT EXISTS USERDETAILS(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR, phone VARCHAR NULL, age VARCHAR NULL, email VARCHAR NULL )");
        final String FILENAME = "logindetails.csv";
        DummyDataLoad csvLoader = new DummyDataLoad(FILENAME);

        try {
            csvLoader.csvToDB(this);
        } catch (IOException e) {
            Log.d("Error @ readingTheFile", e.getMessage());
        }


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this,MainActivity.class));

            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,3000);
    }
}
