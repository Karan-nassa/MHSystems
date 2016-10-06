package com.mh.systems.sunningdale.activites;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.adapter.RecyclerAdapter.ClubNewsSwipeAdapter;
import com.mh.systems.sunningdale.constants.ApplicationGlobal;
import com.mh.systems.sunningdale.constants.WebAPI;
import com.mh.systems.sunningdale.models.ClubNews.AJsonParamsClubNews;
import com.mh.systems.sunningdale.models.ClubNews.AJsonParamsClubNewsDetail;
import com.mh.systems.sunningdale.models.ClubNews.ClubNewsAPI;
import com.mh.systems.sunningdale.models.ClubNews.ClubNewsData;
import com.mh.systems.sunningdale.models.ClubNews.ClubNewsDetailAPI;
import com.mh.systems.sunningdale.models.ClubNews.ClubNewsDetailResult;
import com.mh.systems.sunningdale.models.ClubNews.ClubNewsItems;
import com.mh.systems.sunningdale.util.API.WebServiceMethods;
import com.mh.systems.sunningdale.util.DividerItemDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class ClubNewsActivity extends BaseActivity {
    private String LOG_TAG = ClubNewsActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.rvClubNewsList)
    RecyclerView rvClubNewsList;

    ClubNewsSwipeAdapter clubNewsSwipeAdapter;

    ClubNewsAPI clubNewsAPI;
    AJsonParamsClubNews aJsonParamsClubNews;

    ClubNewsItems clubNewsItems;

    ClubNewsDetailAPI clubNewsDetailAPI;
    AJsonParamsClubNewsDetail aJsonParamsClubNewsDetail;

    ClubNewsDetailResult clubNewsDetailResult;

      /* ++ INTERNET CONNECTION PARAMETERS ++ */

    @Bind(R.id.inc_message_view)
    RelativeLayout inc_message_view;

    @Bind(R.id.ivMessageSymbol)
    ImageView ivMessageSymbol;

    @Bind(R.id.tvMessageTitle)
    TextView tvMessageTitle;

    @Bind(R.id.tvMessageDesc)
    TextView tvMessageDesc;

     /* -- INTERNET CONNECTION PARAMETERS -- */

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    ArrayList<ClubNewsData> clubNewsDataArrayList = new ArrayList<>();

    private Boolean isDelete = true, isRead = true;

    private int iDeletePosition = 0;

    /**
     * Implements HOME icons press listener.
     */
    private View.OnClickListener mHomePressListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_news);

        //Initialize view resources.
        ButterKnife.bind(this);

        // Layout Managers:
        rvClubNewsList.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        rvClubNewsList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());

        //Set Adapter.
        clubNewsSwipeAdapter = new ClubNewsSwipeAdapter(ClubNewsActivity.this, clubNewsDataArrayList);
        rvClubNewsList.setAdapter(clubNewsSwipeAdapter);

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(this)) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            // inc_message_view.setVisibility(View.GONE);
            requestClubNews();
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
            // inc_message_view.setVisibility(View.VISIBLE);
            //showAlertMessage(getResources().getString(R.string.error_no_internet));
            hideProgress();
        }
    }

    /**
     * Implement a method to hit News web service to get response.
     */
    public void requestClubNews() {

        showPleaseWait("Please wait...");

        aJsonParamsClubNews = new AJsonParamsClubNews();
        aJsonParamsClubNews.setLoginMemberId(getMemberId());

        clubNewsAPI = new ClubNewsAPI(getClientId(), "GETCLUBNEWS", aJsonParamsClubNews, ApplicationGlobal.TAG_GCLUB_WEBSERVICES);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getClubNews(clubNewsAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertMessage("" + getResources().getString(R.string.error_please_retry));
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
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "44118078");
    }

    /**
     * Implements this method to UPDATE the data from webservice in
     * COURSE DIARY list if get SUCCESS.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<ClubNewsItems>() {
        }.getType();
        clubNewsItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear Old data from Array-list.
        clubNewsDataArrayList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (clubNewsItems.getMessage().equalsIgnoreCase("Success")) {

                //Take backup of List before changing to record.
                clubNewsDataArrayList.addAll(clubNewsItems.getData());

                if (clubNewsDataArrayList.size() == 0) {
                    clubNewsDataArrayList.clear();
                    clubNewsSwipeAdapter.notifyDataSetChanged();
                    showNoCourseView(false);
                } else {
                    showNoCourseView(true);

                    clubNewsSwipeAdapter.notifyDataSetChanged();
                }
            } else {
                showNoCourseView(false);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements a method to show 'NO COURSE' view and hide it at least one Course event.
     *
     * @param hasData :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     */
    public void showNoCourseView(boolean hasData) {
        if (hasData) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_home_clubnews);
            tvMessageTitle.setText(getResources().getString(R.string.error_no_club_news));
            tvMessageDesc.setText(""/*getResources().getString(R.string.error_try_again)*/);
        }
    }

    /******************************   CLUB NEWS SWIPE TO DELETE FUNCTIONALITY   ******************************/

    /**
     * Implements this method is used to DELETE Club News via swipe to
     * Delte from Club News list.
     *
     * @param iDeletePosition
     * @param clubNewsID
     */
    public void deleteClubNewsService(Integer iDeletePosition, Integer clubNewsID) {


        iDeletePosition = iDeletePosition;

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(this)) {
            requestDeleteNews(clubNewsID);
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
            hideProgress();
        }
    }

    /**
     * Implement a method to hit News web service to get response.
     *
     * @param iClubNewsID
     */
    public void requestDeleteNews(Integer iClubNewsID) {

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

                updateDeleteSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertMessage("" + getResources().getString(R.string.error_server_problem));
            }
        });
    }

    /**
     * Implements this method to UPDATE the data from webservice in
     * COURSE DIARY list if get SUCCESS.
     */
    private void updateDeleteSuccessResponse(JsonObject jsonObject) {
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
            } else {
                showAlertOk(clubNewsDetailResult.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements this method to display successfully delete club news
     * or any other message received from server.
     */
    public void showAlertOk(String strMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things

                            clubNewsDataArrayList.remove(iDeletePosition);
                            clubNewsSwipeAdapter.notifyDataSetChanged();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /******************************   END OF CLUB NEWS SWIPE TO DELETE FUNCTIONALITY   ******************************/
}
