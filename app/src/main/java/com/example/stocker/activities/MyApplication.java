package com.example.stocker.activities;

import android.app.Application;

import java.util.Timer;

public class MyApplication extends Application {
    private static Timer timer=null;
    public static Timer getTimer() {
        if(timer==null){
            timer = new Timer();
        }
        return timer;
    }
}