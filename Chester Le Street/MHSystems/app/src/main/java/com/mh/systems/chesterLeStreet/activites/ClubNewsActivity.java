package com.mh.systems.chesterLeStreet.activites;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.mh.systems.chesterLeStreet.R;
import com.mh.systems.chesterLeStreet.adapter.RecyclerAdapter.ClubNewsSwipeAdapter;
import com.mh.systems.chesterLeStreet.constants.ApplicationGlobal;
import com.mh.systems.chesterLeStreet.web.WebAPI;
import com.mh.systems.chesterLeStreet.models.ClubNews.AJsonParamsClubNewsDetail;
import com.mh.systems.chesterLeStreet.models.ClubNews.ClubNewsDetailAPI;
import com.mh.systems.chesterLeStreet.models.ClubNews.ClubNewsDetailResult;
import com.mh.systems.chesterLeStreet.models.ClubNews.ClubNewsItems;
import com.mh.systems.chesterLeStreet.models.ClubNewsThumbnail.AJsonParamsClubNewsThumbnail;
import com.mh.systems.chesterLeStreet.models.ClubNewsThumbnail.ClubNewsThumbnailAPI;
import com.mh.systems.chesterLeStreet.models.ClubNewsThumbnail.ClubNewsThumbnailData;
import com.mh.systems.chesterLeStreet.models.ClubNewsThumbnail.ClubNewsThumbnailResponse;
import com.mh.systems.chesterLeStreet.push.PushNotificationService;
import com.mh.systems.chesterLeStreet.web.api.WebServiceMethods;

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

//    ClubNewsAPI clubNewsAPI;
//    AJsonParamsClubNews aJsonParamsClubNews;

    ClubNewsItems clubNewsItems;

    ClubNewsDetailAPI clubNewsDetailAPI;
    AJsonParamsClubNewsDetail aJsonParamsClubNewsDetail;

    ClubNewsDetailResult clubNewsDetailResult;

    /**
     * Add Club News Thumbnail api.
     */
    ClubNewsThumbnailAPI clubNewsThumbnailAPI;
    AJsonParamsClubNewsThumbnail aJsonParamsClubNewsThumbnail;

    ClubNewsThumbnailResponse clubNewsThumbnailResponse;

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
    //ArrayList<ClubNewsData> clubNewsDataArrayList = new ArrayList<>();
    ArrayList<ClubNewsThumbnailData> clubNewsThumbnailList = new ArrayList<>();

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

        //Clear Push ArrayList.
        PushNotificationService.strArrList.clear();

        // Layout Managers:
        rvClubNewsList.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        //rvClubNewsList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());

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

        clubNewsSwipeAdapter = new ClubNewsSwipeAdapter(ClubNewsActivity.this, clubNewsThumbnailList);
        rvClubNewsList.setAdapter(clubNewsSwipeAdapter);

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
    }

   /* @Override
    protected void onResume() {
        super.onResume();

        clubNewsSwipeAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ClubNewsActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {

            boolean IsRead = (boolean) data.getSerializableExtra("TAG_CLUB_NEWS_IS_READ");
            int iPosition = (int) data.getSerializableExtra("TAG_CLUB_NEWS_POSITION");
            boolean isDelete = (boolean) data.getSerializableExtra("TAG_CLUB_NEWS_IS_DELETE");

            if (isDelete) {
                clubNewsThumbnailList.remove(iPosition);
            }

            Log.e("onActivityResult", "TAG_CLUB_NEWS_IS_READ : " + IsRead);
            Log.e("onActivityResult", "TAG_CLUB_NEWS_POSITION : " + iPosition);

            clubNewsThumbnailList.get(iPosition).setIsRead(IsRead);
            clubNewsSwipeAdapter.notifyDataSetChanged();
        }
    }

    /* @Override
    protected void onResume() {
        super.onResume();

        *//**
     *  Check internet connection before hitting server request.
     *//*
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
    }*/

    /**
     * Implement a method to hit News web service to get response.
     */
  /*  public void requestClubNews() {

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
    }*/

    /**
     * Implements this method to UPDATE the data from webservice in
     * COURSE DIARY list if get SUCCESS.
     */
    /*private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<ClubNewsItems>() {
        }.getType();
        clubNewsItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear Old data from Array-list.
        clubNewsDataArrayList.clear();

        try {
            *//**
     *  Check "Result" 1 or 0. If 1, means data received successfully.
     *//*
            if (clubNewsItems.getMessage().equalsIgnoreCase("Success")) {

                //Take backup of List before changing to record.
                clubNewsDataArrayList.addAll(clubNewsItems.getData());

                if (clubNewsDataArrayList.size() == 0) {
                    clubNewsDataArrayList.clear();
                    clubNewsSwipeAdapter.notifyDataSetChanged();
                    showNoCourseView(false);
                } else {
                    showNoCourseView(true);

                    //Set Adapter.
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
    }*/

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
     * Delete from Club News list.
     *
     * @param iDeletePosition
     * @param clubNewsID
     */
    public void deleteClubNewsService(Integer iDeletePosition, Integer clubNewsID) {


        this.iDeletePosition = iDeletePosition;

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(this)) {
            requestDeleteNews(clubNewsID);
        } else {
            showAlertOk(getResources().getString(R.string.error_no_internet), false);
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
                showAlertOk("" + getResources().getString(R.string.error_server_problem), false);
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

                    clubNewsSwipeAdapter.notifyDataSetChanged();
                    showAlertOk("News deleted successfully.", true);
                }
            } else {
                showAlertOk(clubNewsDetailResult.getMessage(), false);
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
    public void showAlertOk(String strMessage, final boolean isCallFromDelete) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            if (isCallFromDelete) {
                                clubNewsThumbnailList.remove(iDeletePosition);
                                clubNewsSwipeAdapter.notifyDataSetChanged();
                            } else {
                                onBackPressed();
                            }
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /******************************   END OF CLUB NEWS SWIPE TO DELETE FUNCTIONALITY   ******************************/

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

    /******************************  START OF CLUB NEWS THUMBNAIL IMAGE FUNCTIONALITY  ******************************/

    /**
     * Implement a method to hit News web service to get response.
     */
    public void requestClubNews() {

        showPleaseWait("Please wait...");

        aJsonParamsClubNewsThumbnail = new AJsonParamsClubNewsThumbnail();
        aJsonParamsClubNewsThumbnail.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsClubNewsThumbnail.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsClubNewsThumbnail.setLoginMemberId(getMemberId());

        clubNewsThumbnailAPI = new ClubNewsThumbnailAPI(
                getClientId(),
                "GETALLCLUBNEWSWITHTHUMBNAIL",
                aJsonParamsClubNewsThumbnail,
                ApplicationGlobal.TAG_GCLUB_WEBSERVICES,
                ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getClubNewsThumbnail(clubNewsThumbnailAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertOk("" + getResources().getString(R.string.error_please_retry), false);
            }
        });
    }

    /**
     * Implements this method to UPDATE success of club news
     * with Thumbnail.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<ClubNewsThumbnailResponse>() {
        }.getType();
        clubNewsThumbnailResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear Old data from Array-list.
        clubNewsThumbnailList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (clubNewsThumbnailResponse.getMessage().equalsIgnoreCase("Success")) {

                //Take backup of List before changing to record.
                clubNewsThumbnailList.addAll(clubNewsThumbnailResponse.getData());

                if (clubNewsThumbnailList.size() == 0) {
                    clubNewsSwipeAdapter.notifyDataSetChanged();
                    showNoCourseView(false);
                } else {
                    showNoCourseView(true);

                    //Set Adapter.
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

    /******************************  END OF CLUB NEWS THUMBNAIL IMAGE FUNCTIONALITY  ******************************/
}
