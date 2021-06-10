package com.developer.bshapp.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.developer.bshapp.MainActivity;
import com.developer.bshapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by HP on 21-03-2017.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    Bitmap bitmap;
    String id;
    String title="Trick11";
    String message;
    String type="";
    int x = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        Log.i(TAG+"1", "From: " + remoteMessage.getFrom());

        Log.i("Received notification",remoteMessage.toString());

        System.out.println("From: " + remoteMessage.getFrom());

        id=  remoteMessage.getMessageId();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG+"2", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG+"3", "Message Notification Body: " + remoteMessage.getNotification().getTitle());
            message= remoteMessage.getNotification().getBody();
            title= remoteMessage.getNotification().getTitle();
        }

        Log.i("mess",remoteMessage.getData().toString());

        System.out.println("mess  "+remoteMessage.getData().toString());

        String data=remoteMessage.getData().toString();
        Log.i(TAG,data);
        try {
            JSONObject mainob=new JSONObject(data);

            JSONObject ob=mainob.getJSONObject("data");

            title = ob.getString("title");
            message = ob.getString("message");

            type= "Notification";

        } catch (JSONException e) {
            e.printStackTrace();
        }

        noti(message,title, bitmap, type);
    }

    public void noti(String messageBody, String title, Bitmap image, String TrueOrFalse)
    {

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent =
                PendingIntent.getActivity(this, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String message=messageBody;
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();

        Notification notification;

        if(!type.equals("")) {
             notification = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(intent)
                    .setSmallIcon(icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setWhen(when)
                     .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(image).setSummaryText(message))
                    .build();
        }

        else {
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(intent)
                    .setSmallIcon(icon)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                    .setWhen(when)
                    .build();
        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notification.defaults |= Notification.DEFAULT_SOUND;

        notification.defaults |= Notification.DEFAULT_VIBRATE;

        x= new Random().nextInt(101);
        notificationManager.notify(x, notification);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_002");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(message);
//        bigText.setBigContentTitle("title 2");
//        bigText.setSummaryText("title 3");

        Log.i("FCM Message",message);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        mBuilder.setContentTitle(title);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentText(message).build();

        /*notification = new NotificationCompat.Builder(this,"notify_002")
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(intent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(when)
                .setPriority(Notification.PRIORITY_MAX)
                .setStyle(bigText)
                .build();*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_002",
                    "Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(x, mBuilder.build());
    }
}