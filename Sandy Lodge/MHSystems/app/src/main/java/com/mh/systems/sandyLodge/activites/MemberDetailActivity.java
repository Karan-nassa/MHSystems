package com.mh.systems.sandylodge.activites;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.mh.systems.sandylodge.R;
import com.mh.systems.sandylodge.constants.ApplicationGlobal;
import com.mh.systems.sandylodge.web.WebAPI;
import com.mh.systems.sandylodge.models.AJsonParamsAddMember;
import com.mh.systems.sandylodge.models.AddMemberAPI;
import com.mh.systems.sandylodge.models.AddRequestResult;
import com.mh.systems.sandylodge.models.Friends.AJsonParamsRemoveFriend;
import com.mh.systems.sandylodge.models.Friends.RemoveFriendAPI;
import com.mh.systems.sandylodge.models.Friends.RemoveFriendItems;
import com.mh.systems.sandylodge.web.api.WebServiceMethods;
import com.mh.systems.sandylodge.models.AJsonParamsMembersDatail;
import com.mh.systems.sandylodge.models.MembersDetailAPI;
import com.mh.systems.sandylodge.models.MembersDetailsItems;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * The {@link MemberDetailActivity} used to display the detail of selected
 * member from {@link com.mh.systems.sandylodge.fragments.MembersFragment} or
 * {@link com.mh.systems.sandylodge.fragments.FriendsFragment} by passing MemberId.
 *
 * @author {@link karan@mh.co.in}
 * @version 1.0
 * @since 12 May, 2016
 */
public class MemberDetailActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MemberDetailActivity.class.getSimpleName();

    String strAddressLine, strMemberEmail, strTelNoHome, strTelNoWork, strTelNoMob, strHandCapPlay;
    String strNameOfMember;
    int iMemberID;

    /**
     * iCallFrom will be
     * <br> 1, if {@link com.mh.systems.sandylodge.fragments.MembersFragment}
     * <br> 2, if {@link com.mh.systems.sandylodge.fragments.FriendsFragment}
     */
    int iCallFrom;

    //Used to refresh data on REMOVE friend.
    public static boolean isRefreshData = false;

    //Only send Invite if 'isFriendInvite' false.
    boolean isFriendInvite;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    FloatingActionButton fabFriendInvitation;
    FrameLayout flEmailGroup, flContactGroup, flAddressGroup, flWorkGroup, flHomeGroup;
    TextView tvMemberNameDD, tvMobContact, tvWorkContact, tvHomeContact, tvMemberEmail, tvMemberAddress, tvMemberJoinDate, tvHandicapPlayStr, tvHandicapTypeStr;
    ImageView ivActionMap, ivActionEmail, ivActionCall;
    Toolbar tbMemberDetail;

    //List of type Members list will store type Book which is our data model
    private MembersDetailAPI membersDetailAPI;
    AJsonParamsMembersDatail aJsonParamsMembersDatail;
    MembersDetailsItems membersDetailItems;

    //List of type Remove Friend this list will store type Book which is our data model
    private RemoveFriendAPI removeFriendAPI;
    AJsonParamsRemoveFriend aJsonParamsRemoveFriend;
    RemoveFriendItems removeFriendItems;

    private AddMemberAPI addMemberAPI;
    AJsonParamsAddMember aJsonParamsAddMember;
    //Create instance of Model class for display result.
    AddRequestResult addRequestResult;

    CollapsingToolbarLayout collapseMemberDetail;
    AppBarLayout appBarLayout;

    /**
     * Implements a CONSTANT field to call
     * functionality.
     */
    private View.OnClickListener mCallMemberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.e("CALL NO ", tvMobContact.getText().toString().trim().replaceAll(" ", ""));

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + tvMobContact.getText().toString()/*.trim().replaceAll(" ","")*/));
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
                    //Yes button clicked...
                    requestAddMemberService();
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
        iCallFrom = getIntent().getExtras().getInt("PASS_FROM");

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
                    if (iCallFrom == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MemberDetailActivity.this);
                        builder.setTitle(getResources().getString(R.string.alert_title_friend_invitation))
                                .setMessage(getResources().getString(R.string.alert_title_friend_invite_message))
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("Cancel", dialogClickListener).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MemberDetailActivity.this);
                        builder.setTitle(getResources().getString(R.string.alert_title_friend_remove))
                                .setMessage(getResources().getString(R.string.alert_title_friend_remove_message))
                                .setPositiveButton("Remove", dialogClickListener)
                                .setNegativeButton("Keep", dialogClickListener).show();
                    }

                }
            }
        });

        showTitleOnCollapse();

        ivActionCall.setOnClickListener(mCallMemberListener);
        ivActionEmail.setOnClickListener(mEmailMemberListener);
    }

    private void showTitleOnCollapse() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapseMemberDetail.setTitle(strNameOfMember);
                    isShow = true;
                } else if(isShow) {
                    collapseMemberDetail.setTitle("");
                    isShow = false;
                }
            }
        });
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
        tvMobContact = (TextView) findViewById(R.id.tvMobContact);
        tvWorkContact = (TextView) findViewById(R.id.tvWorkContact);
        tvHomeContact = (TextView) findViewById(R.id.tvHomeContact);
        tvMemberEmail = (TextView) findViewById(R.id.tvMemberEmail);
        tvMemberAddress = (TextView) findViewById(R.id.tvMemberAddress);
        tvHandicapPlayStr = (TextView) findViewById(R.id.tvHandicapPlayStr);
        tvHandicapTypeStr = (TextView) findViewById(R.id.tvHandicapTypeStr);

        ivActionMap = (ImageView) findViewById(R.id.ivActionMap);
        ivActionEmail = (ImageView) findViewById(R.id.ivActionEmail);
        ivActionCall = (ImageView) findViewById(R.id.ivActionCall);

        flEmailGroup = (FrameLayout) findViewById(R.id.flEmailGroup);
        flContactGroup = (FrameLayout) findViewById(R.id.flContactGroup);
        flWorkGroup = (FrameLayout) findViewById(R.id.flWorkGroup);
                flHomeGroup = (FrameLayout) findViewById(R.id.flHomeGroup);
        flAddressGroup = (FrameLayout) findViewById(R.id.flAddressGroup);

        fabFriendInvitation = (FloatingActionButton) findViewById(R.id.fabFriendInvitation);

        collapseMemberDetail = (CollapsingToolbarLayout) findViewById(R.id.collapseMemberDetail);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
    }

    /**
     * Implement a method to ADD MEMBER REQUEST
     * web service.
     */
    private void requestAddMemberService() {
        showPleaseWait("Loading...");

        aJsonParamsAddMember = new AJsonParamsAddMember();
        aJsonParamsAddMember.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsAddMember.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsAddMember.setMemberid(loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784"));
        aJsonParamsAddMember.setFriendid(iMemberID);

        addMemberAPI = new AddMemberAPI(getClientId(), getCommandA(), aJsonParamsAddMember, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getAddMember(addMemberAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                if (iCallFrom == 1) {
                    addMemberSuccessResponse(jsonObject);
                } else {
                    updateRemoveResponse(jsonObject);
                }
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
     * Implements a method to PASS 'aCommand' key to ADD and REMOVE
     * friend according choice of user.
     * <p>
     * ADD MEMBER     : ADDLINKTOMEMBER
     * REMOVE MEMBER  : REMOVELINKTOMEMBER
     */
    private String getCommandA() {
        if (iCallFrom == 1) {
            return "ADDLINKTOMEMBER";
        } else {
            return "REMOVELINKTOMEMBER";
        }
    }

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestMemberDetailService() {

        showPleaseWait("Loading...");

        aJsonParamsMembersDatail = new AJsonParamsMembersDatail();
        aJsonParamsMembersDatail.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsMembersDatail.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsMembersDatail.setMemberid(""+iMemberID);
        aJsonParamsMembersDatail.setLoginMemberId(loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784"));

        membersDetailAPI = new MembersDetailAPI(getClientId(), "GETMEMBER", aJsonParamsMembersDatail, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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

                showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of ADD MEMBER web service.
     */
    private void addMemberSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<AddRequestResult>() {
        }.getType();
        addRequestResult = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (membersDetailItems.getMessage().equalsIgnoreCase("Success")) {
                fabFriendInvitation.setImageResource(R.mipmap.ic_friends);
                fabFriendInvitation.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D6D0C9")));

                //Set TRUE so don't display YES/NO alert dialog.
                isFriendInvite = true;
            }

            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service after REMOVE
     * friend.
     */
    private void updateRemoveResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<RemoveFriendItems>() {
        }.getType();
        removeFriendItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (membersDetailItems.getMessage().equalsIgnoreCase("Success")) {

//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage(removeFriendItems.getCompResultData())
//                        .setCancelable(false)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //do things
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();

                isRefreshData = true;
                finish();

                //onBackPressed();
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
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

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (membersDetailItems.getMessage().equalsIgnoreCase("Success")) {

                //  membersDatas.add(membersItems.getCompResultData());

                if (membersDetailItems.getData() != null) {

                    strMemberEmail = membersDetailItems.getData().getContactDetails().getEMail();
                    strAddressLine = membersDetailItems.getData().getContactDetails().getAddress().getAsLine();
                    strTelNoHome = membersDetailItems.getData().getContactDetails().getTelNoHome();
                    strTelNoMob = membersDetailItems.getData().getContactDetails().getTelNoMob();
                    strTelNoWork = membersDetailItems.getData().getContactDetails().getTelNoWork();
                    strHandCapPlay = membersDetailItems.getData().getHCapPlayStr();

                    updateIsFriendUI(membersDetailItems.getData().getIsfriend());

                    displayMembersData();
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
     * Implements a method to check if selected member is already friend
     * then show FRIENDS icon in {@link FloatingActionButton} otherwise
     * show ADD FRIEND icon.
     *
     * @param isfriend : TRUE, if FRIENDS
     */
    private void updateIsFriendUI(boolean isFriend) {

        /**
         *  iCallFrom will be
         * <br> 1, if {@link com.mh.systems.sandylodge.fragments.MembersFragment}
         * <br> 2, if {@link com.mh.systems.sandylodge.fragments.FriendsFragment}
         *
         */
        switch (iCallFrom) {

            case 1:
                if (isFriend) {
                    fabFriendInvitation.setVisibility(View.VISIBLE);
                    fabFriendInvitation.setImageResource(R.mipmap.ic_friends);
                    fabFriendInvitation.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D6D0C9")));
                    //Set TRUE so don't display YES/NO alert dialog.
                    isFriendInvite = true;
                } else {
                    fabFriendInvitation.setVisibility(View.VISIBLE);
                    fabFriendInvitation.setImageResource(R.mipmap.ic_members_add_friend);
                    fabFriendInvitation.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0995B")));
                    //Set TRUE so don't display YES/NO alert dialog.
                    isFriendInvite = false;
                }
                break;

            case 2:
                switch (getIntent().getExtras().getInt("SPINNER_ITEM")) {
                    case 1:
                        fabFriendInvitation.setVisibility(View.VISIBLE);
                        fabFriendInvitation.setImageResource(R.mipmap.ic_remove_friend);
                        fabFriendInvitation.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                        //Set TRUE so don't display YES/NO alert dialog.
                        isFriendInvite = false;
                        break;

                    case 2:
                        fabFriendInvitation.setVisibility(View.INVISIBLE);
                        break;
                }

                break;
        }

    }

    /**
     * Implements a method to display data on each view
     * after getting SUCCESS from web service.
     */
    private void displayMembersData() {

        strNameOfMember = membersDetailItems.getData().getNameRecord().getFullName();

        /**
         *  Implements check for empty STRING of HANDICAP.
         */
        if (strHandCapPlay.equals("")) {
            tvHandicapPlayStr.setText("N/A");
        } else {
            tvHandicapPlayStr.setText(strHandCapPlay);
        }

        tvHandicapTypeStr.setText(membersDetailItems.getData().getMembershipStatus());
        tvMemberNameDD.setText(strNameOfMember);
        tvMemberJoinDate.setText(getResources().getString(R.string.text_member_since) + " " + getFormateDate(membersDetailItems.getData().getStrLastJoiningDate()));

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
         *  Implements for MOBILE contact empty check.
         */
        if (strTelNoMob.length() > 0) {
            tvMobContact.setText(strTelNoMob);
        } else {
            flContactGroup.setVisibility(View.GONE);
        }

        /**
         *  Implements for WORK contact empty check.
         */
        if (strTelNoWork.length() > 0) {
            tvWorkContact.setText(strTelNoWork);
        } else {
            flWorkGroup.setVisibility(View.GONE);
        }

        /**
         *  Implements for HOME contact empty check.
         */
        if (strTelNoHome.length() > 0) {
            tvHomeContact.setText(strTelNoHome);
        } else {
            flHomeGroup.setVisibility(View.GONE);
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
