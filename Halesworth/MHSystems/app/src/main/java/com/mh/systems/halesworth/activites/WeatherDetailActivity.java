package com.mh.systems.halesworth.activites;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.adapter.RecyclerAdapter.ForecastRecyclerAdapter;
import com.mh.systems.halesworth.adapter.RecyclerAdapter.WeatherMainRecyclerAdapter;
import com.mh.systems.halesworth.constants.ApplicationGlobal;
import com.mh.systems.halesworth.constants.WebAPI;
import com.mh.systems.halesworth.models.forecast.ForecastApiResponse;
import com.mh.systems.halesworth.models.forecast.ListOfDay;
import com.mh.systems.halesworth.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    @Bind(R.id.tvCurrentWeather)
    TextView tvCurrentWeather;

    @Bind(R.id.tvNameOfLoc)
    TextView tvNameOfLoc;

    @Bind(R.id.tbWeather)
    Toolbar tbWeather;

    @Bind(R.id.tvWeatherDate)
    TextView tvWeatherDate;

    @Bind(R.id.ivWeatherView)
    ImageView ivWeatherView;

    @Bind(R.id.tvWeatherType)
    TextView tvWeatherType;

    @Bind(R.id.tvWindPressure)
    TextView tvWindPressure;

    @Bind(R.id.rvWeatherDetail)
    RecyclerView rvWeatherDetail;

    @Bind(R.id.rvWeatherMain)
    RecyclerView rvWeatherMain;

    //Instance of Weather api.
    ForecastApiResponse forecastApiResponse;

    //Instance of Recycler Adapter.
    ForecastRecyclerAdapter forecastRecyclerAdapter;
    WeatherMainRecyclerAdapter weatherMainRecyclerAdapter;

    List<List<ListOfDay>> listArrayList = new ArrayList<>();

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

        strNameOfWeatherLoc = getIntent().getStringExtra("WEATHER_LOC");

        callWeatherService();

        //Initialize Days Adapter.
        weatherMainRecyclerAdapter = new WeatherMainRecyclerAdapter(WeatherDetailActivity.this, listArrayList);
        rvWeatherMain.setAdapter(weatherMainRecyclerAdapter);
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

            listArrayList.addAll(forecastApiResponse.getData().getListOfDay());

            updateDetailUI(0);

            tvNameOfLoc.setText(strNameOfWeatherLoc + ", " + forecastApiResponse.getData().getCity().getCountry());

            updateWeatherDaysUI();

        } else {
            Toast.makeText(WeatherDetailActivity.this, forecastApiResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Implements this method to update weather main i.e.
     * weather DAYS UI.
     */
    private void updateWeatherDaysUI() {

        final int iWeatherDaySize = forecastApiResponse.getData().getListOfDay().size();

        // Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 120, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return (120/iWeatherDaySize);
            }
        });
        // Layout Managers:
        rvWeatherMain.setLayoutManager(layoutManager);
        weatherMainRecyclerAdapter.notifyDataSetChanged();

        //rvWeatherMain.findViewHolderForAdapterPosition(0).itemView.performClick();
        rvWeatherMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                rvWeatherMain.findViewHolderForAdapterPosition(0).itemView.performClick();

            }
        },100);
    }

    /**
     * Update UI with selected day weather
     * data.
     */
    public void updateDetailUI(int iPosition) {

        if (iPosition < forecastApiResponse.getData().getListOfDay().size()) {

            String strDateTime = forecastApiResponse.getData().getListOfDay().get(iPosition).get(0).getDtTxt();

            if (iPosition == 0) {
                tvWeatherDate.setText(("Today, " + getFormateDate(strDateTime)));
            } else {
                tvWeatherDate.setText(getFormateDate(strDateTime));
            }

            ivWeatherView.setImageDrawable(getWeatherIcon(forecastApiResponse.getData().getListOfDay().get(iPosition).get(0).getWeather().get(0).getIcon()));
            tvCurrentWeather.setText("" + ((int) (forecastApiResponse.getData().getListOfDay().get(iPosition).get(0).getMain().getTemp() - 273.15f)) + "°C");
            //  tvWeatherTime.setText(getFormateTime(strDateTime));
            tvWeatherType.setText(forecastApiResponse.getData().getListOfDay().get(iPosition).get(0).getWeather().get(0).getDescription());
            tvWindPressure.setText("" + new Double(forecastApiResponse.getData().getListOfDay().get(iPosition).get(0).getWind().getSpeed()).intValue() + " mph");
        }

        forecastRecyclerAdapter = new ForecastRecyclerAdapter(WeatherDetailActivity.this, listArrayList.get(iPosition));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvWeatherDetail.setLayoutManager(layoutManager);
        rvWeatherDetail.setAdapter(forecastRecyclerAdapter);
        forecastRecyclerAdapter.notifyDataSetChanged();
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
     * Implements this method to get current time of
     * device to get weather status.
     */
    public String getCurrentTime() {

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(System.currentTimeMillis());
        String strCurrentTime = DateFormat.format("HH", cal).toString();

        return strCurrentTime;
    }
}
