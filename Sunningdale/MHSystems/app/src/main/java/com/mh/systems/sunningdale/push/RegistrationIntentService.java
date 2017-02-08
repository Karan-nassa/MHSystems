/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mh.systems.sunningdale.push;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.sunningdale.constants.ApplicationGlobal;
import com.mh.systems.sunningdale.constants.WebAPI;
import com.mh.systems.sunningdale.models.registerToken.AJsonParamsRegisterToken;
import com.mh.systems.sunningdale.models.registerToken.RegisterTokenAPI;
import com.mh.systems.sunningdale.models.registerToken.RegisterTokenResult;
import com.mh.systems.sunningdale.util.API.WebServiceMethods;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    SharedPreferences sharedPreferences;

    RegisterTokenAPI registerTokenAPI;
    AJsonParamsRegisterToken aJsonParamsRegisterToken;

    String strToken = "";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences(ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // [START get_token]
            //  Log.e("START:","HERe");
            InstanceID instanceID = InstanceID.getInstance(this);
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            //608811502775
            strToken = instanceID.getToken("505018842271", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.e(TAG, "GCM Registration Token: " + strToken);

            /**
             * Sent Token to server if not already sent.
             */
            if (!sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false)) {
                sendRegistrationToServer();
            }

            // Subscribe to topic channels
            //subscribeTopics(token);
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     * <p>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token : The new token.
     */
    private void sendRegistrationToServer() {
        // Add custom implementation, as needed.
        aJsonParamsRegisterToken = new AJsonParamsRegisterToken();
        aJsonParamsRegisterToken.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsRegisterToken.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsRegisterToken.setMemberId(getMemberId());
        aJsonParamsRegisterToken.setDeviceState(1); // 1 for Active state.
        aJsonParamsRegisterToken.setDeviceType(2); // 1 for iOS and 2 for Android.
        aJsonParamsRegisterToken.setDeviceId(strToken);

        registerTokenAPI = new RegisterTokenAPI(getClientId(), "MemberDevice", aJsonParamsRegisterToken, "PUSHNOTIFICATION", ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.registerToken(registerTokenAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "registerToken : " + error);
                sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
            }
        });
    }

    private void updateSuccessResponse(JsonObject jsonObject) {

        //ComonMethods.hideProgress();

        Log.e(TAG, "Register Token : " + jsonObject.toString());

        Type type = new TypeToken<RegisterTokenResult>() {
        }.getType();
        RegisterTokenResult registerTokenResult = new Gson().fromJson(jsonObject.toString(), type);

        if (registerTokenResult.getResult() == 1) {
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            SharedPreferences sharedpreferences = getSharedPreferences(
                    ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(QuickstartPreferences.TAG_DEVICE_ID, strToken);
            editor.putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true);
            editor.commit();

        } else {
            //Toast.makeText(DashboardWeatherService.this, "Oops! Unable to load weather status.", Toast.LENGTH_LONG).show();
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    /**
     * Implements a method to get CLIENT-ID from {@link SharedPreferences}
     */
    public String getClientId() {
        String strClubID = sharedPreferences.getString(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
        return strClubID;
    }

    /**
     * Implements a method to get MEMBER-ID from {@link SharedPreferences}
     */
    public String getMemberId() {
        String strMemberID = sharedPreferences.getString(ApplicationGlobal.KEY_MEMBERID, "");
        return strMemberID;
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}
