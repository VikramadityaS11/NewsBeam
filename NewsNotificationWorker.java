package com.example.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.util.Log;

public class NewsNotificationWorker extends Worker {
    public NewsNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Log a message to check if this method is called
        Log.d("NewsNotificationWorker", "Notification worker is running");

        NotificationHelper.showNotification(getApplicationContext());
        return Result.success();
    }
}

