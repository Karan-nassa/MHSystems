package com.ucreate.mhsystems.activites;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.models.AJsonParamsMembersDatail;
import com.ucreate.mhsystems.models.MembersDetailAPI;
import com.ucreate.mhsystems.models.MembersDetailsItems;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MemberDetailActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MemberDetailActivity.class.getSimpleName();

    String strAddressLine, strMemberEmail, strTelNoHome, strTelNoWork, strTelNoMob, strHandCapPlay;
    int iMemberID;
    //Only send Invite if 'isFriendInvite' false.
    boolean isFriendInvite;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    FloatingActionButton fabFriendInvitation;
    FrameLayout flEmailGroup, flContactGroup, flAddressGroup;
    TextView tvMemberNameDD, tvMemberContact, tvMemberEmail, tvMemberAddress, tvMemberJoinDate, tvHandicapPlayStr, tvHandicapTypeStr;
    ImageView ivActionMap, ivActionEmail, ivActionCall;
    Toolbar tbMemberDetail;

    //List of type books this list will store type Book which is our data model
    private MembersDetailAPI membersDetailAPI;
    AJsonParamsMembersDatail aJsonParamsMembersDatail;
    MembersDetailsItems membersDetailItems;

    /**
     * Implements a CONSTANT field to call
     * functionality.
     */
    private View.OnClickListener mCallMemberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.e("CALL NO ", tvMemberContact.getText().toString().trim().replaceAll(" ", ""));

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + tvMemberContact.getText().toString()/*.trim().replaceAll(" ","")*/));
            if (ActivityCompat.checkSelfPermission(MemberDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        }
    };

    /**
     * Define Floating action button tap Alert Dialog
     * events handle here.
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    fabFriendInvitation.setImageResource(R.mipmap.ic_friend_pending);
                    fabFriendInvitation.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#838383")));

                    //Set TRUE so don't display YES/NO alert dialog.
                    isFriendInvite = true;
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //Cancel button clicked
                    break;
            }
        }
    };

    /**
     * Implements a CONSTANT field to call
     * functionality.
     */
    private View.OnClickListener mEmailMemberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendEmail();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        initializeAppResources();

        iMemberID = getIntent().getExtras().getInt(ApplicationGlobal.KEY_MEMBER_ID);

        tbMemberDetail = (Toolbar) findViewById(R.id.tbMemberDetail);
        setSupportActionBar(tbMemberDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(MemberDetailActivity.this)) {
            //Method to hit Members list API.
            requestMemberDetailService();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
        }

        fabFriendInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isFriendInvite) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberDetailActivity.this);
                    builder.setTitle(getResources().getString(R.string.alert_title_friend_invitation))
                            .setMessage(getResources().getString(R.string.alert_title_friend_invite_message))
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();
                }
            }
        });

        ivActionCall.setOnClickListener(mCallMemberListener);
        ivActionEmail.setOnClickListener(mEmailMemberListener);
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

    /**
     * Implements a method to initialize all resouces using for display
     * data on Members Detail screen.
     */
    private void initializeAppResources() {

        tvMemberNameDD = (TextView) findViewById(R.id.tvMemberNameDD);
        tvMemberJoinDate = (TextView) findViewById(R.id.tvMemberJoinDate);
        tvMemberContact = (TextView) findViewById(R.id.tvMemberContact);
        tvMemberEmail = (TextView) findViewById(R.id.tvMemberEmail);
        tvMemberAddress = (TextView) findViewById(R.id.tvMemberAddress);
        tvHandicapPlayStr = (TextView) findViewById(R.id.tvHandicapPlayStr);
        tvHandicapTypeStr = (TextView) findViewById(R.id.tvHandicapTypeStr);

        ivActionMap = (ImageView) findViewById(R.id.ivActionMap);
        ivActionEmail = (ImageView) findViewById(R.id.ivActionEmail);
        ivActionCall = (ImageView) findViewById(R.id.ivActionCall);

        flEmailGroup = (FrameLayout) findViewById(R.id.flEmailGroup);
        flContactGroup = (FrameLayout) findViewById(R.id.flContactGroup);
        flAddressGroup = (FrameLayout) findViewById(R.id.flAddressGroup);

        fabFriendInvitation = (FloatingActionButton) findViewById(R.id.fabFriendInvitation);
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
        aJsonParamsMembersDatail.setMemberid(iMemberID);

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

                    strMemberEmail = membersDetailItems.getData().getContactDetails().getEMail();
                    strAddressLine = membersDetailItems.getData().getContactDetails().getAddress().getAsLine();
                    strTelNoHome = membersDetailItems.getData().getContactDetails().getTelNoHome();
                    strTelNoMob = membersDetailItems.getData().getContactDetails().getTelNoMob();
                    strTelNoWork = membersDetailItems.getData().getContactDetails().getTelNoWork();
                    strHandCapPlay = membersDetailItems.getData().getHCapPlayStr();

                    displayMembersData();

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
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Implements a method to display data on each view
     * after getting SUCCESS from web service.
     */
    private void displayMembersData() {

        /**
         *  Implements check for empty STRING of HANDICAP.
         */
        if (strHandCapPlay.equals("")) {
            tvHandicapPlayStr.setText("N/A");
        } else {
            tvHandicapPlayStr.setText(strHandCapPlay);
        }

        tvHandicapTypeStr.setText(membersDetailItems.getData().getHCapTypeStr());
        tvMemberNameDD.setText(membersDetailItems.getData().getNameRecord().getFormalName());
        tvMemberJoinDate.setText(getResources().getString(R.string.text_member_since) + " " + getFormateDate(membersDetailItems.getData().getLastJoiningDate()));

        /**
         *  Implements check for EMPTY email.
         */
        if (strMemberEmail.length() > 0) {
            tvMemberEmail.setText(strMemberEmail);
        } else {
           // tvMemberEmail.setText(getResources().getString(R.string.text_member_no_email));
            flEmailGroup.setVisibility(View.GONE);
        }

        /**
         *  Implements check and display contact accordingly.
         */
        if (!strTelNoHome.equalsIgnoreCase("")) {
            tvMemberContact.setText(strTelNoHome);
        } else if (!strTelNoWork.equalsIgnoreCase("")) {
            tvMemberContact.setText(strTelNoWork);
        } else if (!strTelNoMob.equalsIgnoreCase("")){
            tvMemberContact.setText(strTelNoMob);
        }else{
            flContactGroup.setVisibility(View.GONE);
        }

        /**
         *  Implements check for EMPTY Address.
         */
        if (strAddressLine.length() > 0) {
            tvMemberAddress.setText(strAddressLine);
        } else {
            //tvMemberAddress.setText(getResources().getString(R.string.text_member_no_address));
            flAddressGroup.setVisibility(View.GONE);
        }
    }

    /**
     * Implements a method to substring of JOIN date of
     * Member.
     *
     * @param lastJoiningDate : Example => "2009-11-30T18:30:00Z"
     * @return lastJoiningDate  : MM/DD/YYYY [11/30/2009]
     */
    private String getFormateDate(String lastJoiningDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Date date = inputFormat.parse(lastJoiningDate);
            lastJoiningDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return lastJoiningDate;
    }


    /**
     * Implements a method to send EMAIL to
     */
    protected void sendEmail() {
        //  Log.i("Send email", "");
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{strMemberEmail});
        // emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Type EMAIL message here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email..."));
            finish();
            //  Log.i("Finished sending...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MemberDetailActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


}
