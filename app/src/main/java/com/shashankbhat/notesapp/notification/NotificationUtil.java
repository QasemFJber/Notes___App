package com.shashankbhat.notesapp.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.shashankbhat.notesapp.MainActivity;
import com.shashankbhat.notesapp.R;
import com.shashankbhat.notesapp.room.Note;

import static com.shashankbhat.notesapp.utils.Constants.PRIORITY_HIGH;
import static com.shashankbhat.notesapp.utils.Constants.PRIORITY_LOW;
import static com.shashankbhat.notesapp.utils.Constants.PRIORITY_MED;

/**
 * Created by SHASHANK BHAT on 22-Jul-20.
 */
public class NotificationUtil {

    public static String NOTIFICATION_CHANNEL_ID = "CHANNEL ID";
    public static int PENDING_INTENT_CHANNEL_ID = 1001;
    public static final int NOTIFICATION_ID = 10;

    public static void notify(Context context, Note note){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.circle)
                .setContentTitle(note.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(note.getDescription()))
                .setAutoCancel(true).setContentIntent(getPendingIntentSee(context))
                .addAction(R.drawable.ic_app_icon, context.getString(R.string.see), getPendingIntentSee(context))
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        if(note.getPriority()==PRIORITY_LOW){
            notificationBuilder
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setColor(ContextCompat.getColor(context, R.color.low));
        }else if(note.getPriority()==PRIORITY_MED){
            notificationBuilder
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setColor(ContextCompat.getColor(context, R.color.medium));
        }else if(note.getPriority()==PRIORITY_HIGH){
            notificationBuilder
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setColor(ContextCompat.getColor(context, R.color.high));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channel_name = context.getString(R.string.channel_name);
            String channel_desc = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channel_name, importance);
            notificationChannel.setDescription(channel_desc);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null)
                notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

    private static PendingIntent getPendingIntentSee(Context context) {
        Intent startTodoListActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, NOTIFICATION_ID, startTodoListActivityIntent, PendingIntent.FLAG_ONE_SHOT);
    }
}
