package com.mh.systems.demoapp.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.BaseAdapter.CompTimeGridAdapter;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.web.WebAPI;
import com.mh.systems.demoapp.models.competitionsEntry.AJsonParamsUpdateEntry;
import com.mh.systems.demoapp.models.competitionsEntry.EligibleMember;
import com.mh.systems.demoapp.models.competitionsEntry.Entry;
import com.mh.systems.demoapp.models.competitionsEntry.GetClubEventData;
import com.mh.systems.demoapp.models.competitionsEntry.NameRecord;
import com.mh.systems.demoapp.models.competitionsEntry.Player;
import com.mh.systems.demoapp.models.competitionsEntry.Slot;
import com.mh.systems.demoapp.models.competitionsEntry.UpdateCompEntryAPI;
import com.mh.systems.demoapp.models.competitionsEntry.UpdateCompEntryResponse;
import com.mh.systems.demoapp.web.api.WebServiceMethods;
import com.mh.systems.demoapp.util.ExpandableHeightGridView;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Create {@link CompetitionEntryActivity} to choose Players/Members and TimeSlots.
 *
 * @author karan@ucreate.co.in
 */
public class CompetitionEntryActivity extends BaseActivity {

    private final String LOG_TAG = CompetitionEntryActivity.class.getSimpleName();

    /**
     * iMemberPosition is used to keep record of which Member position user going to update so that
     * update name of Member when get back from {@link EligiblePlayersActivity}.
     */
    int iMemberPosition;

    //It will describes whether member already entered for this competition then update values.
    int iEntryID = 0;

    String strEventId, strEventPrize, strMemberName;

    int iSlotNo = -1, iTeamSize;

    boolean isAllowCompEntryAdHocSelection, IsTeeTimeSlotsAllowed;

    @Bind(R.id.tbBookingDetail)
    Toolbar tbBookingDetail;

    @Bind(R.id.tvDetailPrice)
    TextView tvDetailPrice;

    @Bind(R.id.tvTotalPrice)
    TextView tvTotalPrice;

    @Bind(R.id.svPlayerContent)
    ScrollView svPlayerContent;

    @Bind(R.id.gvTimeSlots)
    ExpandableHeightGridView gvTimeSlots;

    @Bind(R.id.tvPlayerName1)
    TextView tvPlayerName1;

    @Bind(R.id.llPlayerGroup2)
    LinearLayout llPlayerGroup2;

    @Bind(R.id.llPlayerGroup3)
    LinearLayout llPlayerGroup3;

    @Bind(R.id.llPlayerGroup4)
    LinearLayout llPlayerGroup4;

    @Bind(R.id.llPlayerRow2)
    LinearLayout llPlayerRow2;

    @Bind(R.id.llPlayerRow3)
    LinearLayout llPlayerRow3;

    @Bind(R.id.llPlayerRow4)
    LinearLayout llPlayerRow4;

    @Bind(R.id.tvPlayerName2)
    TextView tvPlayerName2;

    @Bind(R.id.tvPlayerName3)
    TextView tvPlayerName3;

    @Bind(R.id.tvPlayerName4)
    TextView tvPlayerName4;

    @Bind(R.id.ivCrossPlayer2)
    ImageView ivCrossPlayer2;

    @Bind(R.id.ivCrossPlayer3)
    ImageView ivCrossPlayer3;

    @Bind(R.id.ivCrossPlayer4)
    ImageView ivCrossPlayer4;

    @Bind(R.id.tvTeeTimeValue)
    TextView tvTeeTimeValue;

    @Bind(R.id.tvSelectHint)
    TextView tvSelectHint;

    @Bind(R.id.llTimeSlotsGroup)
    LinearLayout llTimeSlotsGroup;

    @Bind(R.id.llTeeTimeGroup)
    LinearLayout llTeeTimeGroup;

    Intent intent;

    CompTimeGridAdapter compTimeGridAdapter;

    List<Slot> slotArrayList = new ArrayList<>();
    ArrayList<EligibleMember> playersArrayList = new ArrayList<>();
    ArrayList<Player> nameOfPlayersList = new ArrayList<>();

    UpdateCompEntryAPI updateCompEntryAPI;
    AJsonParamsUpdateEntry aJsonParamsUpdateEntry;

    UpdateCompEntryResponse updateCompEntryResponse;

    private GetClubEventData getClubEventData;
    private Entry entryInstance;

    /**
     * Implements this method to call when user tap to Add Member.
     */
    private View.OnClickListener mPlayerSelectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvPlayerName2:
                case R.id.llPlayerGroup2:
                    if (tvPlayerName2.getText().toString().equals("Add (optionaly)")) {
                        iMemberPosition = 2;
                    } else
                        return;
                    break;

                case R.id.tvPlayerName3:
                case R.id.llPlayerGroup3:
                    if (tvPlayerName3.getText().toString().equals("Add (optionaly)")) {
                        iMemberPosition = 3;
                    } else
                        return;
                    break;

                case R.id.tvPlayerName4:
                case R.id.llPlayerGroup4:
                    if (tvPlayerName4.getText().toString().equals("Add (optionaly)")) {
                        iMemberPosition = 4;
                    } else
                        return;
                    break;
            }

            intent = new Intent(CompetitionEntryActivity.this, EligiblePlayersActivity.class);
            intent.putExtra("COMPETITIONS_eventId", strEventId);
            intent.putExtra("COMPETITIONS_TeamSize", iTeamSize);
            intent.putExtra("COMPETITIONS_iEntryID", iEntryID);
            Bundle informacion = new Bundle();
            informacion.putSerializable("MEMBER_LIST", playersArrayList);
            intent.putExtras(informacion);
            startActivityForResult(intent, 1);
        }
    };

    /**
     * Implements this method to call when user tap on Cross button.
     */
    private View.OnClickListener mCrossListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String strNameOfMember = "";

            switch (view.getId()) {
                case R.id.ivCrossPlayer2:
                    strNameOfMember = tvPlayerName2.getText().toString();
                    tvPlayerName2.setText("Add (optionaly)");
                    ivCrossPlayer2.setVisibility(View.INVISIBLE);
                    showAlertOk();
                    break;

                case R.id.ivCrossPlayer3:
                    strNameOfMember = tvPlayerName3.getText().toString();
                    tvPlayerName3.setText("Add (optionaly)");
                    ivCrossPlayer3.setVisibility(View.INVISIBLE);
                    showAlertOk();
                    break;

                case R.id.ivCrossPlayer4:
                    strNameOfMember = tvPlayerName4.getText().toString();
                    tvPlayerName4.setText("Add (optionaly)");
                    ivCrossPlayer4.setVisibility(View.INVISIBLE);
                    showAlertOk();
                    break;
            }

            removeMemberFromList(strNameOfMember);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_entry);

        ButterKnife.bind(this);

        if (tbBookingDetail != null) {
            setSupportActionBar(tbBookingDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String jsonFavorites = getIntent().getExtras().getString("RESPONSE_GET_CLUB_EVENT_DATA");
        Gson gson = new Gson();
        getClubEventData = gson.fromJson(jsonFavorites, GetClubEventData.class);

        strEventId = getIntent().getExtras().getString("COMPETITIONS_eventId");
        strEventPrize = getIntent().getExtras().getString("COMPETITIONS_EVENT_PRIZE");
        strMemberName = getIntent().getExtras().getString("COMPETITIONS_MEMBER_NAME");

        iTeamSize = getClubEventData.getTeamSize();

        //If FALSE then user hasn't ability to add another members.
        isAllowCompEntryAdHocSelection = getClubEventData.getAllowCompEntryAdHocSelection();
        IsTeeTimeSlotsAllowed = getClubEventData.getIsTeeTimeSlotsAllowed();

        tvPlayerName1.setText(strMemberName);

        updateMemberUI();
        updateTimeSlots();

        llPlayerGroup2.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName2.setOnClickListener(mPlayerSelectionListener);

        llPlayerGroup3.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName3.setOnClickListener(mPlayerSelectionListener);

        llPlayerGroup4.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName4.setOnClickListener(mPlayerSelectionListener);

        ivCrossPlayer2.setOnClickListener(mCrossListener);
        ivCrossPlayer3.setOnClickListener(mCrossListener);
        ivCrossPlayer4.setOnClickListener(mCrossListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_save:

                if (IsTeeTimeSlotsAllowed) {
                    if (iSlotNo == -1) {
                        showAlertMessage("Please select any Tee time slot.");
                    } else {
                        showAlertConfirm();
                    }
                } else {
                    showAlertConfirm();
                }
                break;

            default:
                break;
        }

        return true;
    }

    /**
     * Implements this method to get TEE time slots for book
     * paid Competitions.
     */
    private void updateTimeSlots() {

        //Time Slots should be visible if key 'IsTeeTimeSlotsAllowed' TRUE.
        if (IsTeeTimeSlotsAllowed) {

            //Timeslots and TeeTime should be visible if allowed according to selected competition.
            llTimeSlotsGroup.setVisibility(View.VISIBLE);
            llTeeTimeGroup.setVisibility(View.VISIBLE);

            try {
                slotArrayList = getClubEventData.getClubEventStartSheet().getZones().get(0).getSlots();

                gvTimeSlots.setExpanded(true);
                compTimeGridAdapter = new CompTimeGridAdapter(CompetitionEntryActivity.this, slotArrayList, iSlotNo);
                gvTimeSlots.setAdapter(compTimeGridAdapter);

                //Forcefully scroll UP of screen after loading.
                svPlayerContent.post(new Runnable() {
                    public void run() {
                        svPlayerContent.fullScroll(View.FOCUS_UP);
                    }
                });
            } catch (Exception exp) {
                Log.e(LOG_TAG, "Exception " + exp.toString());
            }
        } else {
            llTimeSlotsGroup.setVisibility(View.GONE);
            llTeeTimeGroup.setVisibility(View.GONE);

            iSlotNo = 0; //iSlot will be 0 if time slots not Allowed.
        }
    }

    /**
     * Implements this method to set visibility status of players
     * UI according to iTeamSize.
     * <p>
     * For Exp: iTeamSize = 2, means set visibility of PLAYER 2 and PLAYER 3 only.
     * <p>
     * NOTE: Maximum TeamSize : 4
     */
    private void updateMemberUI() {

        //Member has ability to add another member if the below AdHocSelection is TRUE.
        if (isAllowCompEntryAdHocSelection) {

            /**
             * Loop will start from 1 because 0th is position of member itself
             * who is going to select other members.
             */
            for (int iCounter = 2; iCounter <= iTeamSize; iCounter++) {
                switch (iCounter) {
                    case 2:
                        llPlayerRow2.setVisibility(View.VISIBLE);
                        break;

                    case 3:
                        llPlayerRow3.setVisibility(View.VISIBLE);
                        break;

                    case 4:
                        llPlayerRow4.setVisibility(View.VISIBLE);
                        break;

                }
            }

            //Get instance of Member entry.
            entryInstance = getClubEventData.getEntry();

            //Not NULL Means member already entered for selected competition and want to 'MANAGE/UPDATE BOOKING'.
            if (entryInstance != null) {

                iEntryID = getClubEventData.getEntry().getEntryID();
                nameOfPlayersList.addAll(entryInstance.getPlayers());

                //UPDATE Tee Time.
                tvTeeTimeValue.setText(getClubEventData.getEntry().getReservedSlotTime());
                tvSelectHint.setText("");

                iSlotNo = entryInstance.getReservedSlotNo();

                //Pre-selected Member list for 'MANAGE YOUR BOOKING'.
                List<Player> playersList = entryInstance.getPlayers();
                for (int iCounter = 0; iCounter < playersList.size(); iCounter++) {

                    /**
                     * After remove Member, RecordID and PlayerName values will be 0 from api. If its
                     * 0 or empty then don't update UI and not need to add in local array.
                     */
                    if (playersList.get(iCounter).getRecordID() != 0) {

                        playersArrayList.add(new EligibleMember(playersList.get(iCounter).getRecordID(), new NameRecord(playersList.get(iCounter).getPlayerName())));

                        switch (iCounter) {
                            case 1:
                                tvPlayerName2.setText(playersList.get(iCounter).getPlayerName());
                                ivCrossPlayer2.setVisibility(View.VISIBLE);
                                break;

                            case 2:
                                tvPlayerName3.setText(playersList.get(iCounter).getPlayerName());
                                ivCrossPlayer3.setVisibility(View.VISIBLE);
                                break;

                            case 3:
                                tvPlayerName4.setText(playersList.get(iCounter).getPlayerName());
                                ivCrossPlayer4.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                }
            }
        }

        updatePriceUI();
    }

    /**
     * Update Tee Time Slot value.
     *
     * @param iPosition
     */
    public void updateTeeTimeValue(int iPosition) {
        tvTeeTimeValue.setText(slotArrayList.get(iPosition).getSlotStartTimeStr());
        tvSelectHint.setText("");

        iSlotNo = slotArrayList.get(iPosition).getSlotNo();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            playersArrayList.clear();
            playersArrayList = (ArrayList<EligibleMember>) data.getSerializableExtra("MEMBER_LIST");

            if (iEntryID == 0) {
                updateNoEntryIdMemberUI();
            } else {
                updateWithEntryIdMemberUI();
            }

            updatePriceUI();
        }
    }

    /**
     * Implements this method to UPDATE members name UI section when user
     * get back from MEMBER/FRIEND tabs with No Entry ID means iEntryID = 0.
     */
    private void updateNoEntryIdMemberUI() {
        for (int iCounter = 0; iCounter < playersArrayList.size(); iCounter++) {

            switch (iCounter) {

                case 0:
                    tvPlayerName2.setText(playersArrayList.get(iCounter).getNameRecord().getDisplayName());
                    ivCrossPlayer2.setVisibility(View.VISIBLE);
                    break;

                case 1:
                    tvPlayerName3.setText(playersArrayList.get(iCounter).getNameRecord().getDisplayName());
                    ivCrossPlayer3.setVisibility(View.VISIBLE);
                    break;

                case 2:
                default:
                    tvPlayerName4.setText(playersArrayList.get(iCounter).getNameRecord().getDisplayName());
                    ivCrossPlayer4.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * Implements this method to UPDATE members name UI section when
     * user get back from MEMBER/FRIEND tabs with Entry ID means
     * iEntryID = [ANY INTEGER VALUE].
     */
    private void updateWithEntryIdMemberUI() {
        for (int iCounter = 1; iCounter < playersArrayList.size(); iCounter++) {

            // Log.e(LOG_TAG, "Player " + iCounter + " : " + playersArrayList.get(iCounter).getMemberID() + " : " + playersArrayList.get(iCounter).getNameRecord().getDisplayName());

            switch (iCounter) {

                case 1:
                    tvPlayerName2.setText(playersArrayList.get(iCounter).getNameRecord().getDisplayName());
                    ivCrossPlayer2.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    tvPlayerName3.setText(playersArrayList.get(iCounter).getNameRecord().getDisplayName());
                    ivCrossPlayer3.setVisibility(View.VISIBLE);
                    break;

                case 3:
                default:
                    tvPlayerName4.setText(playersArrayList.get(iCounter).getNameRecord().getDisplayName());
                    ivCrossPlayer4.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * Implements this method to update Price after select or remove Members.
     */
    private void updatePriceUI() {
        Scanner scannerInput = new Scanner(strEventPrize).useDelimiter("[^0-9]+");
        int iPrizeVal = scannerInput.nextInt();

        if (iEntryID != 0) {
            //Will not increment playerArraylist.size() because its already have 0th member from web service.
            tvDetailPrice.setText("" + playersArrayList.size() + "x " + strEventPrize);
            tvTotalPrice.setText("£" + (playersArrayList.size() * iPrizeVal) + ".00");
        } else {
            tvDetailPrice.setText("" + (playersArrayList.size() + 1) + "x " + strEventPrize);
            tvTotalPrice.setText("£" + ((playersArrayList.size() + 1) * iPrizeVal) + ".00");
        }
    }

    /**
     * Implements this method to check Internet Connection and hit web service
     * if connection exists.
     */
    private void updateCompEntryService() {

        if (isOnline(this)) {
            callUpdateEntryService();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
            hideProgress();
        }
    }

    /**
     * Implements a method to hit update members detail service.
     */
    private void callUpdateEntryService() {

        List<Integer> selectedMemberIdList = new ArrayList<>();

        /**
         * Add Member itself if First time Entry competition. And during UPDATE, member
         * ID already added via api.
         */
        if (iEntryID == 0) {
            selectedMemberIdList.add(Integer.parseInt(getMemberId()));
        }

        for (int iCount = 0; iCount < playersArrayList.size(); iCount++) {
            selectedMemberIdList.add(playersArrayList.get(iCount).getMemberID()); //Add selected Members IDs
        }

        showPleaseWait("Please wait...");

        aJsonParamsUpdateEntry = new AJsonParamsUpdateEntry();
        aJsonParamsUpdateEntry.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsUpdateEntry.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsUpdateEntry.setMemberId(getMemberId());
        aJsonParamsUpdateEntry.setEventId(strEventId);
        aJsonParamsUpdateEntry.setPlayers(selectedMemberIdList);
        aJsonParamsUpdateEntry.setSlotNo(iSlotNo);
        aJsonParamsUpdateEntry.setZoneId(1);//Always 1 for all competition suggested by @Jaspal Singh

        if (iEntryID != 0) {
            aJsonParamsUpdateEntry.setEntryId(iEntryID);
        }

        updateCompEntryAPI = new UpdateCompEntryAPI(getClientId(), getWhichEventEntry(), aJsonParamsUpdateEntry, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.updateCompEntry(updateCompEntryAPI, new Callback<JsonObject>() {
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
     * Save Entry method calling from two sides are:
     * <br>
     * - ADD Club Event Entry [ADDCLUBEVENTENTRIES]: When user Save or Add any new Event.
     * - UPDATE Club Event Entry [UPDATECLUBEVENTENTRIES]: When user want to 'UPDATE YOUR BOOKING'
     */
    private String getWhichEventEntry() {
        if (iEntryID == 0) {
            return "ADDCLUBEVENTENTRIES";
        } else {
            return "UPDATECLUBEVENTENTRIES";
        }
    }

    /**
     * Implements a method to update SUCCESS response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<UpdateCompEntryResponse>() {
        }.getType();
        updateCompEntryResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (updateCompEntryResponse.getResult() == 1) {

                showAlertOk(updateCompEntryResponse.getData());
            } else {
                //If web service not respond in any case.
                showAlertMessage(updateCompEntryResponse.getMessage());
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
        hideProgress();
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements this method to Remove Member from ArrayList.
     *
     * @param iMemberID
     * @param tag
     */
    public void removeMemberFromList(String strMemberName) {

        int iCounter;
        for (iCounter = 0; iCounter < playersArrayList.size(); iCounter++) {

            if (strMemberName.equals(playersArrayList.get(iCounter).getNameRecord().getDisplayName())) {
                playersArrayList.remove(iCounter);
                updatePriceUI();
                break;
            }
        }
    }

    /**
     * Implement a method to display Alert Dialog message
     * after unJoin Competitions.
     */
    public void showAlertOk(String strAlertMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(strAlertMessage)
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

    /**
     * Implements this method to display {@link AlertDialog} with
     * OK button.
     */
    private void showAlertOk() {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Changes");
            builder.setMessage("Your account will be adjusted accordingly")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implements this method to display {@link AlertDialog} with
     * CANCEL and CONFIRM button.
     */
    private void showAlertConfirm() {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Your account will be charged for " + tvTotalPrice.getText().toString())
                    .setCancelable(false)
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                        }
                    })
                    .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            builder = null;
                            //do things
                            updateCompEntryService();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
