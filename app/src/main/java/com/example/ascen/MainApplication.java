package com.example.ascen;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class MainApplication extends Application {

    private static MainApplication mInstance;


    public static Context getStaticContext() {
        return MainApplication.getInstance().getApplicationContext();
    }

    public static MainApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseApp.initializeApp(this);
    }
}
