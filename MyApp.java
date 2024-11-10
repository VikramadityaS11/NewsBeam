package com.example.myapplication;

import android.app.Application;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PeriodicWorkRequest notificationWorkRequest =
                new PeriodicWorkRequest.Builder(NewsNotificationWorker.class, 5, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(this).enqueue(notificationWorkRequest);
    }
}
