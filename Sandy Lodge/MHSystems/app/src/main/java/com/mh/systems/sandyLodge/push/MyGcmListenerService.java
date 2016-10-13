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

package com.mh.systems.sandyLodge.push;

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

import com.google.android.gms.gcm.GcmListenerService;
import com.mh.systems.sandyLodge.R;
import com.mh.systems.sandyLodge.activites.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    JSONObject mJson_Object, mJsonObject, mJsonObject_;
    String mStringType, mStringUsername, mStringMessage, mStringCaseId;
    int senderUserId, receiverUserId, CaseId;
    //PrefsManager prefsManager;

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
                mStringType = mJson_Object.getString("message");
                Log.e("mStringType:", "" + mStringType);



            } catch (JSONException e) {
                Log.e("JSON Parser", "Cause " + e.getCause());
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            sendNotification(mStringType, uniqueId);


        } catch (Exception e) {

            Log.e("mStringType: ", "" + mStringType);
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
        Log.e("sendNotification: ", "" + message);

        Intent intent = new Intent(this, SplashActivity.class);
      /*  intent.putExtra("message_username", mStringUsername);
        intent.putExtra("whrlocation", "notification");
        intent.putExtra("userId2", senderUserId);*/
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Bitmap bitmap =  BitmapFactory.decodeResource(this.getResources(),
        // R.drawable.ic_launcher);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) number_push /* ID of notification */, notificationBuilder.build());
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
                .setContentText(mStringMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) number_push /* ID of notification */, notificationBuilder.build());
        // notificationManager.n
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
