package com.mh.systems.sunningdale.ui.activites;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.utils.constants.ApplicationGlobal;
import com.mh.systems.sunningdale.web.api.WebAPI;
import com.mh.systems.sunningdale.web.api.WebServiceMethods;
import com.mh.systems.sunningdale.web.models.bookinglessons.BookingLessonsResponse;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class BookingLessonsWebActivity extends BaseActivity {

    private final String LOG_TAG = BookingLessonsWebActivity.class.getSimpleName();

    @Bind(R.id.tbBookingLessons)
    Toolbar tbBookingLessons;

    @Bind(R.id.wvBookingContent)
    WebView wvBookingContent;

    private String strBookLessonsUrl = "http://www.mhsserver3.com//proagenda/BookLessons?";

    BookingLessonsResponse bookingLessonsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_lessons_web);

        //Initialize view resources.
        ButterKnife.bind(this);

        if (tbBookingLessons != null) {
            tbBookingLessons.setNavigationIcon(R.mipmap.icon_menu);
            setSupportActionBar(tbBookingLessons);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadBookingLessons();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }

    private void loadBookingLessons() {
        /**
         * Check Internet connection.
         */
        if (isOnline(BookingLessonsWebActivity.this)) {
            showPleaseWait("Please wait...");
            //callBookingLessonService();

            strBookLessonsUrl = strBookLessonsUrl +"aclientid="+ApplicationGlobal.TAG_CLIENT_ID
                    +"&amemberid="+getMemberId();

            wvBookingContent.setVisibility(View.VISIBLE);
            wvBookingContent.setVisibility(View.VISIBLE);
            wvBookingContent.setWebViewClient(new myWebClient());
            wvBookingContent.getSettings().setJavaScriptEnabled(true);
            wvBookingContent.getSettings().setBuiltInZoomControls(true);
            wvBookingContent.getSettings().setSupportZoom(true);
            wvBookingContent.setFocusableInTouchMode(false);
            wvBookingContent.setFocusable(false);
            wvBookingContent.loadUrl(strBookLessonsUrl);
        } else {
            setContentView(R.layout.include_display_message);
            wvBookingContent.setVisibility(View.GONE);
        }
    }

    /**
     * Implements this method to hit weather web
     * service.
     */
     private void callBookingLessonService() {

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.proAgendaAPI(
                ApplicationGlobal.TAG_CLIENT_ID,
                getMemberId(),
                new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        updateSuccessResponse(jsonObject);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(LOG_TAG, "RetrofitError : " + error);
                        showAlertOk(error.toString());
                    }
                });
    }

    @SuppressLint("SetJavaScriptEnabled")
   private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "BOOKING LESSONS RESPONSE : " + jsonObject.toString());

        Type type = new TypeToken<BookingLessonsResponse>() {
        }.getType();
        bookingLessonsResponse = new Gson().fromJson(jsonObject.toString(), type);

        if (bookingLessonsResponse.getData() != null) {

            wvBookingContent.setVisibility(View.VISIBLE);
            wvBookingContent.setWebViewClient(new myWebClient());
            wvBookingContent.getSettings().setJavaScriptEnabled(true);
            wvBookingContent.getSettings().setBuiltInZoomControls(true);
            wvBookingContent.getSettings().setSupportZoom(true);
            wvBookingContent.setFocusableInTouchMode(false);
            wvBookingContent.setFocusable(false);
            wvBookingContent.loadUrl(bookingLessonsResponse.getData().getUrl());
        } else {
            showAlertOk(bookingLessonsResponse.getMessage());
        }
    }

    /**
     * Implements a method to get MEMBER-ID from {@link SharedPreferences}
     */
    private String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implement a method to display {@link AlertDialog} with OK button. When user tap
     * on OK then go back to LOGIN screen.
     */
    private void showAlertOk(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            hideProgress();
                            builder = null;
                            onBackPressed();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implements a class to Handle and display progress in
     * Web View.
     */
    private class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgress();
        }
    }
}
