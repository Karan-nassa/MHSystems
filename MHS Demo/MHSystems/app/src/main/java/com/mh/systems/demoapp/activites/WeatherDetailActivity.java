package com.mh.systems.demoapp.activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.forecast.ForecastApiResponse;
import com.mh.systems.demoapp.models.forecast.List;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
public class WeatherDetailActivity extends BaseActivity {

    private final String LOG_TAG = WeatherDetailActivity.class.getSimpleName();


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

    //Instance of Weather api.
    ForecastApiResponse forecastApiResponse;

    private Date mLastDate = null;

    ArrayList<List> listArrayList = new ArrayList<>();
    ArrayList<String> integerArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        ButterKnife.bind(WeatherDetailActivity.this);

        if (tbWeather != null) {
            setSupportActionBar(tbWeather);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tbWeather.setNavigationIcon(R.mipmap.icon_menu);
            getSupportActionBar().setTitle("Weather forecast");
        }

        callWeatherService();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /**
             *  Tool bar back arrow handler.
             */
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /****************** ++ WEATHER API FEATURE ++ ******************/

    /**
     * Implements this method to hit weather web
     * service.
     */
    private void callWeatherService() {

        showPleaseWait("Loading ");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.weatherAPI("forecast",
                ApplicationGlobal.TAG_CLIENT_ID,
                new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        updateSuccessResponse(jsonObject);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Log.e(LOG_TAG, "RetrofitError : " + error);
                        hideProgress();
                    }
                });
    }

    private void updateSuccessResponse(JsonObject jsonObject) {

        hideProgress();

        Log.e(LOG_TAG, "Weather response : " + jsonObject.toString());

        Type type = new TypeToken<ForecastApiResponse>() {
        }.getType();
        forecastApiResponse = new Gson().fromJson(jsonObject.toString(), type);

        if (forecastApiResponse.getData().getCod().equals("200")) {

            //   llWeatherGroup.setVisibility(View.VISIBLE);

            //Get Data to local instances.
            String desc = forecastApiResponse.getData().getList().get(0).getWeather().get(0).getDescription();

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance(Locale.getDefault());
                cal.setTimeInMillis(System.currentTimeMillis());
                String date = DateFormat.format("yyyy-MM-dd HH:MM:ss", cal).toString();
                Date date1 = formatter.parse(date);

                for (int iCount = 0; iCount < forecastApiResponse.getData().getCnt(); iCount++) {

                    if(mLastDate == null){
                        mLastDate = formatter.parse(forecastApiResponse.getData().getList().get(iCount).getDtTxt());
                    }

                    Date date2 = formatter.parse(forecastApiResponse.getData().getList().get(iCount).getDtTxt());

                    Date dat = formatter1.parse(forecastApiResponse.getData().getList().get(iCount).getDtTxt());

                    if ((!integerArrayList.contains(dat.toString()))) {
                        listArrayList.add(forecastApiResponse.getData().getList().get(iCount));
                        integerArrayList.add(dat.toString());
                    }

                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            Log.e(LOG_TAG, "filter array size " + listArrayList.size());

//            tvTodayTemperature.setText("" + ((int) (weatherData.getMain().getTemp() - 273.15f)) + "°C");
//            tvWeatherDesc.setText("Today, "+(desc.substring(0, 1).toUpperCase() + desc.substring(1)));
//            //        tvNameOfLocation.setText(weatherData.getName() + ", " + weatherData.getSys().getCountry());
//            tvNameOfLocation.setText(weatherData.getName());
//            todayIcon.setImageURI(Uri.parse("http://openweathermap.org/img/w/" + weatherData.getWeather().get(0).getIcon() + ".png"));
//            Resources res=getResources();
//            int resID = res.getIdentifier("e"+weatherData.getWeather().get(0).getIcon(), "mipmap", getPackageName());
//            Drawable drawable = res.getDrawable(resID);
//            todayIcon.setImageDrawable(drawable);

//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_TEMPERATURE, ("" + ((int) (weatherData.getMain().getTemp() - 273.15f)) + "°C"));
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_WEATHER, ("Today, "+(desc.substring(0, 1).toUpperCase() + desc.substring(1))));
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_LOCATION, weatherData.getName());
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_IMAGE, ("e"+weatherData.getWeather().get(0).getIcon()));

        } else {
            Toast.makeText(WeatherDetailActivity.this, "Oops! Unable to load weather status.", Toast.LENGTH_LONG).show();
        }
    }

    /****************** ~~ WEATHER API FEATURE ~~ ******************/

}
