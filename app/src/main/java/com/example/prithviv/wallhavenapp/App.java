package com.example.prithviv.wallhavenapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Parse SDK stuff goes here
        Fresco.initialize(this);
    }
}
