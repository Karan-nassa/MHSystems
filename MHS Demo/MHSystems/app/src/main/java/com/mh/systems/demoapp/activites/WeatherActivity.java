package com.mh.systems.demoapp.activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.forecast.ForecastMain;
import com.mh.systems.demoapp.util.API.WebServiceMethods;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Karan Nassa on 03-11-2016.
 * <p>
 * Organization : ucreate.it
 * Email        : karan@ucreate.it
 */
public class WeatherActivity extends BaseActivity {

    //Instance of Weather api.
    ForecastMain getWeatherResponse;
    private final String LOG_TAG = WeatherActivity.class.getSimpleName();


    @Bind(R.id.tbWeather)
    Toolbar tbWeather;

    @Bind(R.id.tvWeatherDate)
    TextView tvWeatherDate;

    @Bind(R.id.ivWeatherView)
    ImageView ivWeatherView;

    @Bind(R.id.tvWeatherTime)
    TextView tvWeatherTime;

    @Bind(R.id.tvWeatherType)
    TextView tvWeatherType;

    @Bind(R.id.tvWindPressure)
    TextView tvWindPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(WeatherActivity.this);

        if (tbWeather != null) {
            setSupportActionBar(tbWeather);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tbWeather.setNavigationIcon(R.mipmap.icon_menu);
            getSupportActionBar().setTitle("Weather forecast");
        }
        //callWeatherService();
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

        api.getWeatherState("Forecast",
                "JSON",
                getResources().getString(R.string.apiKey),
                "51.388654",
                "-0.631297",
                new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        //    updateSuccessResponse(jsonObject);


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Log.e(LOG_TAG, "RetrofitError : " + error);
                        // ComonMethods.hideProgress();

                        Log.e(LOG_TAG, "ERROR : " + error);
                    }
                });
    }

    private void updateSuccessResponse(JsonObject jsonObject) {

        //ComonMethods.hideProgress();

        Log.e(LOG_TAG, "Weather response : " + jsonObject.toString());

        Type type = new TypeToken<ForecastMain>() {
        }.getType();
        getWeatherResponse = new Gson().fromJson(jsonObject.toString(), type);

        if (getWeatherResponse.getCod().equals("200")) {

            //   llWeatherGroup.setVisibility(View.VISIBLE);

            //Get Data to local instances.
            String desc = getWeatherResponse.getList().get(0).getWeather().get(0).getDescription();


            SimpleDateFormat format = new SimpleDateFormat("ddd dd,MMM");
            String date = format.format(Date.parse(getWeatherResponse.getList().get(0).getDtTxt()));
            Log.e("date",""+date);

//            tvTodayTemperature.setText("" + ((int) (getWeatherResponse.getMain().getTemp() - 273.15f)) + "°C");
//            tvWeatherDesc.setText("Today, "+(desc.substring(0, 1).toUpperCase() + desc.substring(1)));
//            //        tvNameOfLocation.setText(getWeatherResponse.getName() + ", " + getWeatherResponse.getSys().getCountry());
//            tvNameOfLocation.setText(getWeatherResponse.getName());
//            todayIcon.setImageURI(Uri.parse("http://openweathermap.org/img/w/" + getWeatherResponse.getWeather().get(0).getIcon() + ".png"));
//            Resources res=getResources();
//            int resID = res.getIdentifier("e"+getWeatherResponse.getWeather().get(0).getIcon(), "mipmap", getPackageName());
//            Drawable drawable = res.getDrawable(resID);
//            todayIcon.setImageDrawable(drawable);

//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_TEMPERATURE, ("" + ((int) (getWeatherResponse.getMain().getTemp() - 273.15f)) + "°C"));
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_WEATHER, ("Today, "+(desc.substring(0, 1).toUpperCase() + desc.substring(1))));
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_LOCATION, getWeatherResponse.getName());
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_IMAGE, ("e"+getWeatherResponse.getWeather().get(0).getIcon()));

        } else {
            Toast.makeText(WeatherActivity.this, "Oops! Unable to load weather status.", Toast.LENGTH_LONG).show();
        }
    }

    /****************** ~~ WEATHER API FEATURE ~~ ******************/

}
