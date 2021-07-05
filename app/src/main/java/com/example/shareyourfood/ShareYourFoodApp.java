package com.example.shareyourfood;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShareYourFoodApp extends Application {

    private static ShareYourFoodApp instance = null;
    private FirebaseFirestore db;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = FirebaseFirestore.getInstance();
    }

    public static ShareYourFoodApp getInstance()
    {
        return instance;
    }

    public FirebaseFirestore getDb()
    {
        return db;
    }
}
