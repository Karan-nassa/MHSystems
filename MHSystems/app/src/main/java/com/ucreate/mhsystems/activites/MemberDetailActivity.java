package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.util.pojo.AJsonParamsMembersDatail;
import com.ucreate.mhsystems.util.pojo.MembersDetailAPI;
import com.ucreate.mhsystems.util.pojo.MembersDetailsItems;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MemberDetailActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MemberDetailActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    LinearLayout llMembersDetailBack;
    //List of type books this list will store type Book which is our data model
    private MembersDetailAPI membersDetailAPI;
    AJsonParamsMembersDatail aJsonParamsMembersDatail;

    MembersDetailsItems membersDetailItems;

    /**
     * Implements HOME icons press
     * listener.
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
        setContentView(R.layout.activity_member_detail);

        llMembersDetailBack = (LinearLayout) findViewById(R.id.llMembersDetailBack);

        Log.e(LOG_TAG, "" + getIntent().getExtras().getString(ApplicationGlobal.KEY_MEMBER_ID));

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(MemberDetailActivity.this)) {
            //Method to hit Members list API.
            requestMemberDetailService();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Set click listener events declaration.
        llMembersDetailBack.setOnClickListener(mHomePressListener);
    }

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestMemberDetailService() {

        showPleaseWait("Loading...");

        aJsonParamsMembersDatail = new AJsonParamsMembersDatail();
        aJsonParamsMembersDatail.setCallid("1456315336575");
        aJsonParamsMembersDatail.setVersion(1);
        aJsonParamsMembersDatail.setMemberid(10784);

        membersDetailAPI = new MembersDetailAPI(44118078, "GETMEMBER", aJsonParamsMembersDatail, "WEBSERVICES", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getMembersDetail(membersDetailAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();

                showAlertMessage("" + error);
            }
        });

    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<MembersDetailsItems>() {
        }.getType();
        membersDetailItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        //membersDatas.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (membersDetailItems.getMessage().equalsIgnoreCase("Success")) {

                //  membersDatas.add(membersItems.getData());

                if (membersDetailItems.getData() != null) {

                    Log.e(LOG_TAG, "MemberID : " + membersDetailItems.getData().getMemberID());
                    Log.e(LOG_TAG, "EMail : " + membersDetailItems.getData().getContactDetails().getEMail());
                } else {
                    showAlertMessage(getResources().getString(R.string.error_no_data));
                }
            } else {
                //If web service not respond in any case.
                showAlertMessage(membersDetailItems.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

    }

}
