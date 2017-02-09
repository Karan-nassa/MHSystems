package com.mh.systems.york.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.york.R;
import com.mh.systems.york.constants.ApplicationGlobal;
import com.mh.systems.york.web.WebAPI;
import com.mh.systems.york.models.ClubNews.AJsonParamsClubNewsDetail;
import com.mh.systems.york.models.ClubNews.ClubNewsData;
import com.mh.systems.york.models.ClubNews.ClubNewsDetailAPI;
import com.mh.systems.york.models.ClubNews.ClubNewsDetailResult;
import com.mh.systems.york.web.api.WebServiceMethods;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class ClubNewsDetailActivity extends BaseActivity {

    private String LOG_TAG = ClubNewsDetailActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    @Bind(R.id.tbClubNewsDetail)
    Toolbar tbClubNewsDetail;

    @Bind(R.id.tvDateOfNews)
    TextView tvDateOfNews;

    @Bind(R.id.tvTimeOfNews)
    TextView tvTimeOfNews;

   /* @Bind(R.id.tvDescOfNews)
    TextView tvDescOfNews;*/

    @Bind(R.id.wvClubNews)
    WebView wvClubNews;

    Typeface tfSFUI_TextRegular;

    ClubNewsDetailAPI clubNewsDetailAPI;
    AJsonParamsClubNewsDetail aJsonParamsClubNewsDetail;

    ClubNewsDetailResult clubNewsDetailResult;

    ClubNewsData clubNewsData;

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    int iClubNewsID;
    private Boolean isDelete, isRead;
    private int iPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_news_detail);

        //Initialize view resources.
        ButterKnife.bind(this);

        initializeAllResources();

        if (tbClubNewsDetail != null) {
            setSupportActionBar(tbClubNewsDetail);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        clubNewsData = (ClubNewsData) bundle.getSerializable("club_news_content");

        if (clubNewsData != null) {
            tvDateOfNews.setText(clubNewsData.getCreatedDate());
            tvTimeOfNews.setText(clubNewsData.getTime());
            iClubNewsID = clubNewsData.getClubNewsID();
            isRead = clubNewsData.getIsRead();
            iPosition = bundle.getInt("iPosition");
        }

        //data == html data which you want to load
        wvClubNews.getSettings().setJavaScriptEnabled(true);
        wvClubNews.loadDataWithBaseURL("", clubNewsData.getMessage(), "text/html", "UTF-8", "");

        //If user haven't read news then call READ API status.
        if (!isRead) {
            isRead = true;
            isDelete = false;
            updateMemberClubNewsStatus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_club_news, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // finish();
                onBackPressed();
                break;

            case R.id.action_delete:
                isRead = true;
                isDelete = true;
                updateMemberClubNewsStatus();
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(ClubNewsDetailActivity.this, ClubNewsActivity.class);
        Bundle informacion = new Bundle();
        informacion.putSerializable("IsRead", isRead);
        informacion.putSerializable("iPosition", iPosition);
        intent.putExtras(informacion);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Implements this method is used for two web services are:
     * <p>
     * - READ   : When user come first time then send READ status TRUE to server.
     * - DELETE : Whenever user tap on DELETE icon on top right corner to delete the selected news.
     */
    private void updateMemberClubNewsStatus() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(this)) {
            requestClubNewsService();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
            hideProgress();
        }
    }

    /**
     * Implement a method to hit News web service to get response.
     */
    public void requestClubNewsService() {

        showPleaseWait("Please wait...");

        aJsonParamsClubNewsDetail = new AJsonParamsClubNewsDetail();
        aJsonParamsClubNewsDetail.setLoginMemberId(getMemberId());
        aJsonParamsClubNewsDetail.setClubNewsID(iClubNewsID);
        aJsonParamsClubNewsDetail.setIsRead(isRead);
        aJsonParamsClubNewsDetail.setIsDelete(isDelete);

        clubNewsDetailAPI = new ClubNewsDetailAPI(getClientId(), "UpdateMemberClubNewsStatus", aJsonParamsClubNewsDetail, ApplicationGlobal.TAG_GCLUB_WEBSERVICES);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.updateClubNews(clubNewsDetailAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                resetValues();

                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertMessage("" + getResources().getString(R.string.error_server_problem));
            }
        });
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "44071043");
    }

    /**
     * Implements this method to UPDATE the data from webservice in
     * COURSE DIARY list if get SUCCESS.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<ClubNewsDetailResult>() {
        }.getType();
        clubNewsDetailResult = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
           /* *
         *  Check "Result" 1 or 0. If 1, means data received successfully.
         */
            if (clubNewsDetailResult.getMessage().equalsIgnoreCase("Success")) {
                if (isDelete) {
                    showAlertOk("News deleted successfully.");
                }
                isRead = true;
            } else {
                showAlertOk(clubNewsDetailResult.getMessage());
                isRead = false;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //resetValues();

        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements this method is used to reset the values.
     */
    private void resetValues() {
        isRead = false;
        isDelete = false;
    }

    /**
     * Implements a method to initialize all view resources and
     * font style.
     */
    private void initializeAllResources() {
        tfSFUI_TextRegular = Typeface.createFromAsset(getAssets(), "fonts/SF-UI-Text-Regular.otf");

        tvDateOfNews.setTypeface(tfSFUI_TextRegular);
        tvTimeOfNews.setTypeface(tfSFUI_TextRegular);
        //tvDescOfNews.setTypeface(tfSFUI_TextRegular);
    }

    /**
     * Implements this method to display successfully delete club news
     * or any other message recieved from server.
     */
    public void showAlertOk(String strMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            onBackPressed();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
