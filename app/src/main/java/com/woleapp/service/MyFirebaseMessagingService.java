package com.woleapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.woleapp.R;
import com.woleapp.model.User;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.SharedPrefManager;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";
    public static final String CALL_FROM_NOTOFICATION = "call_from_notification";
    private NotificationUtils notificationUtils;
    public static final String PUSH_NOTIFICATION = "pushNotification";
    //    String from_id="";
//    String geek_id="";
    int from_id = 0, to_id = 0;
    Long geek_id = 0L;


    String msg;
    String project_name;
    //SharedPreferences sharedpreferences;
    User user;

    private NotificationManager notifManager;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("Token", s + "---------");
        SharedPrefManager.saveDeviceToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ///home/mobileappz/Downloads/2019/November/Nov 9/myptcappcode/src/pages
        super.onMessageReceived(remoteMessage);
        Log.e("onMessageReceived", "onMessageReceived");
        Map<String, String> notificationMap = remoteMessage.getData();
        String title = notificationMap.containsKey("title") ? notificationMap.get("title") : getApplicationContext().getResources().getString(R.string.app_name);
        String message = notificationMap.containsKey("message") ? notificationMap.get("message") : getApplicationContext().getResources().getString(R.string.app_name);
        String amount = notificationMap.containsKey("amount") ? notificationMap.get("amount") : null;
        Bundle extras = new Bundle();
        for (Map.Entry<String, String> entry : notificationMap.entrySet()) {
            extras.putString(entry.getKey(), entry.getValue());
        }


        extras.putString("title", title);
        if (!TextUtils.isEmpty(amount)) {
            extras.putString("amount", amount);
        }
        Log.e("remoteMsgData", "Not Empty");

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

 /*           user = SharedPrefManager.getInstance(this).getUser();
            geek_id = user.getUser_id();
            Log.e("UserId", geek_id + "--");

            Map<String, String> notificationMap = remoteMessage.getData();

            if (notificationMap != null && notificationMap.size() > 0) {

                String title = notificationMap.containsKey("title") ? notificationMap.get("title") : getApplicationContext().getResources().getString(R.string.app_name);
                String message = notificationMap.containsKey("message") ? notificationMap.get("message") : getApplicationContext().getResources().getString(R.string.app_name);
                String amount = notificationMap.containsKey("amount") ? notificationMap.get("amount") : null;
                Log.e("remoteMsgData", "Not Empty");

                Log.e(TAG, "Data Payload: " + notificationMap.toString());

                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : notificationMap.entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }


                extras.putString("title", title);
                if (!TextUtils.isEmpty(amount)) {
                    extras.putString("amount", amount);
                }

                handleDataMessage(title, message, extras);


            } else if (remoteMessage.getNotification() != null) {

                Log.e("remoteMsgData", "Empty");
                Log.e("remoteMsgNotification", "Empty");
                String body = remoteMessage.getNotification().getBody();
                if (body != null) {

                    body = remoteMessage.getNotification().getBody();
                    Log.e(TAG, "Notification Body: " + body);

                }

            } else {

                Log.e("remoteMsgData", "Empty");

            }*/

            Log.d(MyFirebaseMessagingService.TAG, "sending broadcast");

            createNotification(title,message,extras);


        } else if(NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            createNotification(title,message,extras);
        }
    }


    private void handleDataMessage(String title, String message, Bundle extras) {
        Log.e(TAG, "push json: " + message.toString());

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                // app is in foreground, broadcast the push message
                Log.e("FCMService", "app is in foreground");

                String title1 = extras.containsKey("title") ? extras.getString("title") : getApplicationContext().getResources().getString(R.string.app_name);
                String message1 = extras.containsKey("message") ? extras.getString("message") : getApplicationContext().getResources().getString(R.string.app_name);
                String id = extras.containsKey("id") ? extras.getString("id") : "0";
                String amount = extras.containsKey("amount") ? extras.getString("amount") : null;

                if (!extras.containsKey("message")) {
                    extras.putString("message", message1);
                }
                if (!extras.containsKey("title")) {
                    extras.putString("title", title1);
                }
                if (!TextUtils.isEmpty(amount)) {
                    extras.putString("amount", amount);
                }

                createNotification(title1, message1, extras);


            } else {
                // app is in background, broadcast the push message

                /*Intent intent = new Intent("NewMessage");
                sendBroadcast(intent);*/

                Intent intent = new Intent("NewMessage");
                extras.putBoolean("fromFCM", true);
                intent.putExtra("notification_data", extras);
                sendBroadcast(intent);
                createNotification(title, message, extras);

            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    public void createNotification(String title, String message, Bundle extras)//String activity_id, String title, String message
    {
        final int NOTIFY_ID = 1002;

        Intent intent;
        NotificationCompat.Builder builder;
        try {
            Log.e("createNotification", "createNotification");
            if (notifManager == null) {
                notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }

            intent = new Intent(this, HomeActivity.class);
            //intent.putExtras(bundle);
            intent.putExtra("notification_data", extras);
            builder = getNotificationBuilder(intent, title, message);
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public NotificationCompat.Builder getNotificationBuilder(Intent intent, String title, String message) {
        String name = "channel_default";
        String id = "channel_default"; // The user-visible name of the channel.
        String description = "channel_default"; // The user-visible description of the channel.

        NotificationCompat.Builder builder;
        PendingIntent pendingIntent;

        Log.e("OS_Version", Build.VERSION.SDK_INT + "--");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200});//100, 200, 300, 400, 500, 400, 300, 200, 400

                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);
            builder.setSmallIcon(R.mipmap.ic_launcher_round);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);//ic_launcher

            builder.setColor(getResources().getColor(android.R.color.transparent));
            builder.setLargeIcon(bitmap);


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_UPDATE_CURRENT


            builder.setContentTitle(title)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("")
                    .setVibrate(new long[]{0})
                    .setPriority(Notification.PRIORITY_HIGH);
            ;
        } else {

            builder = new NotificationCompat.Builder(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.mipmap.ic_launcher_round);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);//ic_launcher

                builder.setColor(getResources().getColor(android.R.color.transparent));
                builder.setLargeIcon(bitmap);
            } else {
                builder.setSmallIcon(R.mipmap.ic_launcher_round);
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_UPDATE_CURRENT

            builder.setContentTitle(title)                           // required
                    .setSmallIcon(R.mipmap.ic_launcher_round) // required
                    .setContentText(message)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("")
                    .setVibrate(new long[]{0})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        return builder;
    }

    public void parseNotificationData(Bundle bun) {
        if (bun != null) {
            Set<String> set = bun.keySet();

        }
        Set<String> set = bun.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Log.e(key, " : " + bun.getString(key));
        }
        int id = Integer.parseInt(bun.getString("id"));
        /*if(!recentlyAddedMessageIDs.contains(id))
        {

        }*/

        int client_id = Integer.parseInt(bun.getString("client_id", "0"));
        int jobId = Integer.parseInt(bun.getString("project_id"));
        String project_name = bun.getString("project_name", "");
        int to_id;
        if (bun.containsKey("receiverId")) {
            to_id = Integer.parseInt(bun.getString("receiverId"));
        } else {
            to_id = 0;
        }

        int from_id = Integer.parseInt(bun.getString("senderId"));
        String message = bun.getString("message");
        String date_time_ind = bun.getString("date_time_ind");
        String attached_file = bun.getString("attached_file");
        String sender_name = bun.getString("sender_name", "");

    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }


}
