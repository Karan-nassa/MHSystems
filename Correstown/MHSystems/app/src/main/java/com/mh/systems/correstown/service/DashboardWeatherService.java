package com.mh.systems.correstown.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.correstown.R;
import com.mh.systems.correstown.constants.ApplicationGlobal;
import com.mh.systems.correstown.constants.WebAPI;
import com.mh.systems.correstown.models.weather.WeatherData;
import com.mh.systems.correstown.util.API.WebServiceMethods;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class DashboardWeatherService extends Service {
    private static final String TAG = "DashboardWeatherService";
    private boolean isRunning = false;
    private Looper looper;
    private MyServiceHandler myServiceHandler;
    //Instance of Weather api.
    WeatherData weatherData;
    final long changeTime = 11000000000L;


    @Override
    public void onCreate() {
        HandlerThread handlerthread = new HandlerThread("MyThread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerthread.start();
        looper = handlerthread.getLooper();
        myServiceHandler = new MyServiceHandler(looper);
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = myServiceHandler.obtainMessage();
        msg.arg1 = startId;
        myServiceHandler.sendMessage(msg);

        //If service is killed while starting, it restarts.
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        isRunning = false;

    }

    private final class MyServiceHandler extends Handler {
        public MyServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            synchronized (this) {
                for (int i = 0; ; i++) {
                    try {
                        Log.i(TAG, "DashboardWeatherService running...");
                        callWeatherService();
                        Thread.sleep(changeTime);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                    if (!isRunning) {
                        break;
                    }
                }
            }
            //stops the service for the start id.
            stopSelfResult(msg.arg1);
        }
    }

    /****************** ++ WEATHER API FEATURE ++ ******************/

    /**
     * Implements this method to hit weather web
     * service.
     */
    private void callWeatherService() {

        //showPleaseWait("Loading ");

        String strCityName = "";
        strCityName = strCityName.length() != 0 ? strCityName : "LONDON,UK";

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_WEATHER_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.getWeatherState("Weather",
                "JSON",
                getResources().getString(R.string.apiKey),
                "51.388654",
                "-0.631297",
                new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        updateSuccessResponse(jsonObject);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Log.e(TAG, "RetrofitError : " + error);
                        // ComonMethods.hideProgress();

                        Log.e(TAG, "ERROR : " + error);
                    }
                });
    }

    private void updateSuccessResponse(JsonObject jsonObject) {

        //ComonMethods.hideProgress();

        Log.e(TAG, "Weather response : " + jsonObject.toString());

        Type type = new TypeToken<WeatherData>() {
        }.getType();
        weatherData = new Gson().fromJson(jsonObject.toString(), type);

        if (weatherData.getCod() == 200) {
            String desc = weatherData.getWeather().get(0).getDescription();
            SharedPreferences sharedpreferences = getSharedPreferences(
                    ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(ApplicationGlobal.KEY_TEMPKEY_TEMPERATURE, ("" + ((int) (weatherData.getMain().getTemp() - 273.15f)) + "°C"));
            editor.putString(ApplicationGlobal.KEY_TEMPKEY_WEATHER, ("Today, " + (desc.substring(0, 1).toUpperCase() + desc.substring(1))));
            editor.putString(ApplicationGlobal.KEY_TEMPKEY_LOCATION, weatherData.getName());
            editor.putString(ApplicationGlobal.KEY_TEMPKEY_IMAGE, ("e" + weatherData.getWeather().get(0).getIcon()));
            editor.commit();


        } else {
            Toast.makeText(DashboardWeatherService.this, "Oops! Unable to load weather status.", Toast.LENGTH_LONG).show();
        }
    }

}