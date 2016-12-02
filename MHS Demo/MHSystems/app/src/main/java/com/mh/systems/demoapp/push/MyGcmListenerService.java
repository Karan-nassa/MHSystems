/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mh.systems.demoapp.push;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.gcm.GcmListenerService;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.activites.ClubNewsActivity;
import com.mh.systems.demoapp.activites.DashboardActivity;
import com.mh.systems.demoapp.activites.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    JSONObject mJson_Object, mJsonObject, mJsonObject_;
    String mStringUsername, strMessage, mStringCaseId;
    int senderUserId, receiverUserId, CaseId;
    //PrefsManager prefsManager;

    ArrayList<String> notifiyArrList = new ArrayList<>();

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data CompEligiblePlayersData bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        Date now = new Date();
        long uniqueId = now.getTime();
        String message;
        try {
            message = data.getString("message");
            Log.e("message:", "" + message);

            try {
                mJson_Object = new JSONObject(message);
                strMessage = mJson_Object.getString("message");

                notifiyArrList.add(strMessage);

                Log.e("strMessage:", "" + strMessage);

            } catch (JSONException e) {
                Log.e("JSON Parser", "Cause " + e.getCause());
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            //sendNotification(strMessage, uniqueId);
            CustomNotification(strMessage, uniqueId);

        } catch (Exception e) {

            Log.e("mStringType: ", "" + strMessage);
            //    Rollbar.reportException(e, "critical", "My Gcmlistener services crash");
            //   Rollbar.reportMessage("GCM MessageType: " + mStringType, "--GCM Message:-" + mStringMessage);
        }

        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message, long number_push) {
        //Log.e("sendNotification: ", "" + message);

        //Intent intent = new Intent(this, DashboardActivity.class);
      /*  intent.putExtra("message_username", mStringUsername);
        intent.putExtra("whrlocation", "notification");
        intent.putExtra("userId2", senderUserId);*/
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                0);

        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH);

        Notification notification = new Notification.InboxStyle(notificationBuilder)
                .addLine(message)
                /*.addLine("Second Message")
                .addLine("Third Message")
                .addLine("Fourth Message")*/
                .setBigContentTitle(getResources().getString(R.string.app_name))
                .setSummaryText("+3 more")
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) number_push, notification);

        //Bitmap bitmap =  BitmapFactory.decodeResource(this.getResources(),
        // R.drawable.ic_launcher);
       /* NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) number_push *//* ID of notification *//*, notificationBuilder.build());*/
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void send_Notification(String message, long number_push) {
        Log.e("send_Notification: ", "" + message);

        Intent intent;
        intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Bitmap bitmap =  BitmapFactory.decodeResource(this.getResources(),
        // R.drawable.ic_launcher);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(strMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) number_push /* ID of notification */, notificationBuilder.build());
        // notificationManager.n
    }

    /**
     * Implements custom notification view
     * for push.
     */
    public void CustomNotification(String message, long number_push) {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.custom_notification_view);

        // Set Notification Title
        //String strtitle = getString(R.string.customnotificationtitle);
        // Set Notification Text
        //String strtext = getString(R.string.customnotificationtext);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, ClubNewsActivity.class);

        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.ic_launcher)
                // Set Ticker Message
                .setTicker("Hello Ticker")
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Set RemoteViews into Notification
                .setContent(remoteViews)
                //Set Sound of Notification.
                .setSound(defaultSoundUri);

        Log.e("CustomNotification", "message: " + message);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.tvNotifiTitle, getString(R.string.app_name));
        remoteViews.setTextViewText(R.id.tvNotfiDesc, message);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify((int) number_push, builder.build());

    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
