package com.motivation.statusforwhatsapp.Utils;

import android.app.Application;

//import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        //this is for Stetho only for checking database
       // Stetho.initializeWithDefaults(this);
    }
}