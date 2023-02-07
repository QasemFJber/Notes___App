package com.shashankbhat.notesapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import com.shashankbhat.notesapp.worker.ShowNotesWorker;


public class NotifyNotes extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


//        Constraints constraints = new Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.UNMETERED)
//                .setRequiresCharging(true)
//                .setRequiresBatteryNotLow(true)
//                .setRequiresDeviceIdle(true)
//                .build();

        WorkManager workManager = WorkManager.getInstance(context);
        WorkRequest workRequest = new OneTimeWorkRequest
                .Builder(ShowNotesWorker.class)
//                .setConstraints(constraints)
                .build();

        workManager.enqueue(workRequest);

    }
}
