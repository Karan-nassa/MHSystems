package com.mh.systems.sunningdale.activites;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.constants.ApplicationGlobal;
import com.mh.systems.sunningdale.constants.WebAPI;
import com.mh.systems.sunningdale.models.forecast.ForecastApiResponse;
import com.mh.systems.sunningdale.models.forecast.List;
import com.mh.systems.sunningdale.util.API.WebServiceMethods;
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
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Karan Nassa on 03-11-2016.
 * <p>
 * Organization : ucreate.it
 * Email        : karan@ucreate.it
 */
public class WeatherDetailActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = WeatherDetailActivity.class.getSimpleName();

    @Bind(R.id.tvCurrentWeather)
    TextView tvCurrentWeather;

    @Bind(R.id.tvNameOfLoc)
    TextView tvNameOfLoc;

    @Bind(R.id.tbWeather)
    Toolbar tbWeather;

    @Bind(R.id.flDayGroup1)
    FrameLayout flDayGroup1;

    @Bind(R.id.flDayGroup2)
    FrameLayout flDayGroup2;

    @Bind(R.id.flDayGroup3)
    FrameLayout flDayGroup3;

    @Bind(R.id.flDayGroup4)
    FrameLayout flDayGroup4;

    @Bind(R.id.flDayGroup5)
    FrameLayout flDayGroup5;

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

    @Bind(R.id.tvDayName1)
    TextView tvDayName1;

    @Bind(R.id.tvDayName2)
    TextView tvDayName2;

    @Bind(R.id.tvDayName3)
    TextView tvDayName3;

    @Bind(R.id.tvDayName4)
    TextView tvDayName4;

    @Bind(R.id.tvDayName5)
    TextView tvDayName5;

    @Bind(R.id.ivWeatherDay1)
    ImageView ivWeatherDay1;

    @Bind(R.id.ivWeatherDay2)
    ImageView ivWeatherDay2;

    @Bind(R.id.ivWeatherDay3)
    ImageView ivWeatherDay3;

    @Bind(R.id.ivWeatherDay4)
    ImageView ivWeatherDay4;

    @Bind(R.id.ivWeatherDay5)
    ImageView ivWeatherDay5;

    @Bind(R.id.tvTempDay1)
    TextView tvTempDay1;

    @Bind(R.id.tvTempDay2)
    TextView tvTempDay2;

    @Bind(R.id.tvTempDay3)
    TextView tvTempDay3;

    @Bind(R.id.tvTempDay4)
    TextView tvTempDay4;

    @Bind(R.id.tvTempDay5)
    TextView tvTempDay5;

    View[] tvDayNameArr;
    View[] ivWeatherDayArr;
    View[] tvTempDayArr;

    FrameLayout flLastSelectedView = flDayGroup1;

    //Instance of Weather api.
    ForecastApiResponse forecastApiResponse;

    private Date mLastDate = null;

    ArrayList<List> listArrayList = new ArrayList<>();
    ArrayList<String> integerArrayList = new ArrayList<>();

    String strNameOfWeatherLoc;

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

        //Initialize Array with view.
        tvDayNameArr = new View[]{tvDayName1, tvDayName2, tvDayName3, tvDayName4, tvDayName5};
        ivWeatherDayArr = new View[]{ivWeatherDay1, ivWeatherDay2, ivWeatherDay3, ivWeatherDay4, ivWeatherDay5};
        tvTempDayArr = new View[]{tvTempDay1, tvTempDay2, tvTempDay3, tvTempDay4, tvTempDay5};

        strNameOfWeatherLoc = getIntent().getStringExtra("WEATHER_LOC");

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

    @OnClick({R.id.flDayGroup1, R.id.flDayGroup2, R.id.flDayGroup3, R.id.flDayGroup4, R.id.flDayGroup5})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.flDayGroup1:
                updateDetailUI(0);
                flDayGroup1.setBackgroundColor(ContextCompat.getColor(WeatherDetailActivity.this, R.color.color313130));
                break;

            case R.id.flDayGroup2:
                updateDetailUI(1);
                flDayGroup2.setBackgroundColor(ContextCompat.getColor(WeatherDetailActivity.this, R.color.color313130));
                break;

            case R.id.flDayGroup3:
                updateDetailUI(2);
                flDayGroup3.setBackgroundColor(ContextCompat.getColor(WeatherDetailActivity.this, R.color.color313130));
                break;

            case R.id.flDayGroup4:
                updateDetailUI(3);
                flDayGroup4.setBackgroundColor(ContextCompat.getColor(WeatherDetailActivity.this, R.color.color313130));
                break;

            case R.id.flDayGroup5:
                updateDetailUI(4);
                flDayGroup5.setBackgroundColor(ContextCompat.getColor(WeatherDetailActivity.this, R.color.color313130));
                break;
        }

        if (flLastSelectedView == null) {
            flLastSelectedView = flDayGroup1;
        }
        flLastSelectedView.setBackgroundColor(ContextCompat.getColor(WeatherDetailActivity.this, R.color.color242422));
        flLastSelectedView = (FrameLayout) v;
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

        api.forcastAPI(
                ApplicationGlobal.TAG_CLIENT_ID,
                getCurrentTime(),
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
                        callWeatherService();
                    }
                });
    }

    /**
     * Implements this method to update the
     * success response.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        hideProgress();

        Log.e(LOG_TAG, "Weather response : " + jsonObject.toString());

        Type type = new TypeToken<ForecastApiResponse>() {
        }.getType();
        forecastApiResponse = new Gson().fromJson(jsonObject.toString(), type);

        if (forecastApiResponse.getData() != null) {

            updateDetailUI(0);

            tvNameOfLoc.setText(strNameOfWeatherLoc + ", " + forecastApiResponse.getData().getCity().getCountry());

            for (int iCount = 0; iCount < forecastApiResponse.getData().getList().size(); iCount++) {
                ((TextView) tvDayNameArr[iCount]).setText(getFormateDayName(forecastApiResponse.getData().getList().get(iCount).getDtTxt()));
                ((ImageView) ivWeatherDayArr[iCount]).setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getList().get(iCount).getWeather().get(0).getIcon()));
                ((TextView)tvTempDayArr[iCount]).setText("" + ((int) (forecastApiResponse.getData().getList().get(iCount).getMain().getTemp() - 273.15f)) + "°C");
            }

//            tvDayName1.setText(getFormateDayName(forecastApiResponse.getData().getList().get(0).getDtTxt()));
//            tvDayName2.setText(getFormateDayName(forecastApiResponse.getData().getList().get(1).getDtTxt()));
//            tvDayName3.setText(getFormateDayName(forecastApiResponse.getData().getList().get(2).getDtTxt()));
//            tvDayName4.setText(getFormateDayName(forecastApiResponse.getData().getList().get(3).getDtTxt()));
//            tvDayName5.setText(getFormateDayName(forecastApiResponse.getData().getList().get(4).getDtTxt()));
//
//            ivWeatherDay1.setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getList().get(0).getWeather().get(0).getIcon()));
//            ivWeatherDay2.setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getList().get(1).getWeather().get(0).getIcon()));
//            ivWeatherDay3.setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getList().get(2).getWeather().get(0).getIcon()));
//            ivWeatherDay4.setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getList().get(3).getWeather().get(0).getIcon()));
//            ivWeatherDay5.setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getList().get(4).getWeather().get(0).getIcon()));
//
//            tvTempDay1.setText("" + ((int) (forecastApiResponse.getData().getList().get(0).getMain().getTemp() - 273.15f)) + "°C");
//            tvTempDay2.setText("" + ((int) (forecastApiResponse.getData().getList().get(1).getMain().getTemp() - 273.15f)) + "°C");
//            tvTempDay3.setText("" + ((int) (forecastApiResponse.getData().getList().get(2).getMain().getTemp() - 273.15f)) + "°C");
//            tvTempDay4.setText("" + ((int) (forecastApiResponse.getData().getList().get(3).getMain().getTemp() - 273.15f)) + "°C");
//            tvTempDay5.setText("" + ((int) (forecastApiResponse.getData().getList().get(4).getMain().getTemp() - 273.15f)) + "°C");

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
            Toast.makeText(WeatherDetailActivity.this, forecastApiResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Update UI with selected day weather
     * data.
     */
    private void updateDetailUI(int iPosition) {

        String strDateTime = forecastApiResponse.getData().getList().get(iPosition).getDtTxt();

        if (iPosition == 0) {
            tvWeatherDate.setText(("Today, " + getFormateDate(strDateTime)));
        } else {
            tvWeatherDate.setText(getFormateDate(strDateTime));
        }

        ivWeatherView.setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getList().get(iPosition).getWeather().get(0).getIcon()));
        tvCurrentWeather.setText("" + ((int) (forecastApiResponse.getData().getList().get(iPosition).getMain().getTemp() - 273.15f)) + "°C");
        tvWeatherTime.setText(getFormateTime(strDateTime));
        tvWeatherType.setText(forecastApiResponse.getData().getList().get(iPosition).getWeather().get(0).getDescription());
        tvWindPressure.setText("" + new Double(forecastApiResponse.getData().getList().get(iPosition).getWind().getSpeed()).intValue() + " mph");
    }

    /**
     * Generate Weather Icon from string.
     *
     * @param strNameOfIcon : Name of String.
     * @return drawable     : Drawable type icon.
     */
    private Drawable getWeatherIcon(String strNameOfIcon) {
        int resID = getResources().getIdentifier("e" + strNameOfIcon, "mipmap", getPackageName());
        Drawable drawable = ContextCompat.getDrawable(WeatherDetailActivity.this, resID);
        return drawable;
    }

    /**
     * Implements a method to RETURN the complete date from
     * specific date format.
     *
     * @param strDate : Example => "yyyy-MM-dd HH:mm:ss"
     * @return strDate  : EEEE, dd MMMM
     */
    public static String getFormateDate(String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM");

        try {
            Date date = inputFormat.parse(strDate);
            strDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strDate;
    }

    /**
     * Implements a method to RETURN the name of DAY from
     * specific date format.
     *
     * @param strDate : Example => "yyyy-MM-dd HH:mm:ss"
     * @return strDate  : E [Tue]
     */
    public static String getFormateDayName(String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("E");

        try {
            Date date = inputFormat.parse(strDate);
            strDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strDate;
    }

    /**
     * Implements a method to RETURN the name of DAY from
     * specific date format.
     *
     * @param strTime : Example => "yyyy-MM-dd HH:mm:ss"
     * @return strTime  : HH:mm [14:00]
     */
    public static String getFormateTime(String strTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

        try {
            Date date = inputFormat.parse(strTime);
            strTime = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strTime;
    }

    /**
     * Implements this method to get current time of
     * device to get weather status.
     */
    public String getCurrentTime() {

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(System.currentTimeMillis());
        String strCurrentTime = DateFormat.format("HH", cal).toString();

        return strCurrentTime;
    }

    /****************** ~~ WEATHER API FEATURE ~~ ******************/

}