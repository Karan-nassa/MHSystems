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
package com.mh.systems.hartsbourne.dataaccess.push;

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
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.ui.activites.ClubNewsActivity;
import com.mh.systems.hartsbourne.utils.constants.ApplicationGlobal;
import com.rollbar.android.Rollbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class PushNotificationService extends GcmListenerService {

    private final String LOG_TAG = PushNotificationService.class.getSimpleName();
    String strMessage;

    NotificationCompat.Builder notificationBuilder;
    private JSONObject mJSONObject;

    public static ArrayList<String> strArrList = new ArrayList<>();

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data CompEligiblePlayersData bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = "";
        try {

            message = data.getString("message");

            try {
                mJSONObject = new JSONObject(message);
                strMessage = mJSONObject.getString("message");

                if (ApplicationGlobal.TAG_CLIENT_ID.equals(mJSONObject.getString("receiver_clientId"))) {

                    strArrList.add(strMessage);

                    sendNotification();
                } else {
                    Log.e(LOG_TAG, "Push sends for other club.");
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException Error parsing data " + e.toString());
            }

        } catch (Exception e) {

            Log.e(LOG_TAG, "Exception : " + e.toString());
            Rollbar.reportMessage("GCM String Builder value : " + strArrList.toString(), "--GCM Message:-" + message);
        }
    }

    /**
     * Create and show a simple notification containing
     * the received GCM message.
     */
    private void sendNotification() {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, ClubNewsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                0);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getUnexpandedContentText(strArrList.size()))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(getExpandedNotificationStyle(strArrList));

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (int iCount = 0; iCount < strArrList.size(); iCount++) {
            inboxStyle.addLine(strArrList.get(iCount));
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    /**
     * Create custom method to display notification like inbox style.
     */
    private NotificationCompat.Style getExpandedNotificationStyle(ArrayList<String> names) {
        NotificationCompat.InboxStyle expandedNotificationStyle = new NotificationCompat.InboxStyle();
        expandedNotificationStyle.setBigContentTitle(getResources().getString(R.string.app_name));

        /**
         * There seems to be a bug in the notification display system where, unless you set
         * summary text, single line expanded inbox state will not expand when the notif
         * drawer is fully pulled down. However, it still works in the lock-screen.
         */
        expandedNotificationStyle.setSummaryText(strArrList.size() + " news received."/*"M H S Group Ltd Sports"*/);

        for (String name : names) {
            expandedNotificationStyle.addLine(name);
        }
        return expandedNotificationStyle;
    }

    /**
     * Implements method for unExpanded content area.
     */
    private String getUnexpandedContentText(int numOfNotifications) {
        /*switch (numOfNotifications) {
            case 0:
                return "";
            case 1:
                return "";
            default:*/
        return numOfNotifications + " news received";
    }
}
