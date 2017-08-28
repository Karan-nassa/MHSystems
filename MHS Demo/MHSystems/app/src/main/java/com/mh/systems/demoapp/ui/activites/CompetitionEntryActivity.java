package com.mh.systems.demoapp.ui.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.adapter.BaseAdapter.CompTimeSlotsAdapter;
import com.mh.systems.demoapp.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.demoapp.utils.ExpandableHeightGridView;
import com.mh.systems.demoapp.utils.constants.ApplicationGlobal;
import com.mh.systems.demoapp.web.api.WebAPI;
import com.mh.systems.demoapp.web.api.WebServiceMethods;
import com.mh.systems.demoapp.web.models.competitionsentry.AJsonParamsUpdateEntry;
import com.mh.systems.demoapp.web.models.competitionsentry.EligibleMember;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Player;
import com.mh.systems.demoapp.web.models.competitionsentry.UpdateCompEntryAPI;
import com.mh.systems.demoapp.web.models.competitionsentry.UpdateCompEntryResponse;
import com.mh.systems.demoapp.web.models.competitionsentrynew.AllPlayer;
import com.mh.systems.demoapp.web.models.competitionsentrynew.NewCompEntryData;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Slot;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Team;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Zone;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.Booking;
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
public class CompetitionEntryActivity extends BaseActivity implements OnUpdatePlayers {

    private final String LOG_TAG = CompetitionEntryActivity.class.getSimpleName();

    /**
     * iMemberPosition is used to keep record of which Member position user going to update so that
     * update name of Member when get back from {@link EligiblePlayersActivity}.
     */
    int iMemberPosition;

    //It will describes whether member already entered for this competition then update values.
    int iEntryID = 0;

    String strEventId, strEventPrize, strMemberName;

    //  String strSelfMemberName = "";
    //  int iSelfMemberID;

    int iSlotNo = -1;
    //int iTeamSize;

    //  boolean isAllowCompEntryAdHocSelection, IsTeeTimeSlotsAllowed;

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

    CompTimeSlotsAdapter compTimeSlotsAdapter;

    //List<Slot> slotArrayList = new ArrayList<>();
    ArrayList<EligibleMember> playersArrayList = new ArrayList<>();
    ArrayList<Player> nameOfPlayersList = new ArrayList<>();

    UpdateCompEntryAPI updateCompEntryAPI;
    AJsonParamsUpdateEntry aJsonParamsUpdateEntry;

    UpdateCompEntryResponse updateCompEntryResponse;

//    private GetClubEventData getClubEventData;
//    private Entry entryInstance;


   /* @Bind(R.id.rvCompZone)
    RecyclerView rvCompZone;*/

    @Bind(R.id.svTimeSlotsGroup)
    ScrollView svTimeSlotsGroup;

    @Bind(R.id.tvTitleOfComp)
    TextView tvTitleOfComp;

    @Bind(R.id.tvTimeOfComp)
    TextView tvTimeOfComp;

    @Bind(R.id.flSingleZoneGroup)
    FrameLayout flSingleZoneGroup;

    @Bind(R.id.ivExpandCompZone)
    ImageView ivExpandCompZone;

    @Bind(R.id.tvZoneName)
    TextView tvZoneName;

    @Bind(R.id.tvOccupancy)
    TextView tvOccupancy;

    @Bind(R.id.btConfirmTimeBooking)
    Button btConfirmTimeBooking;

    @Bind(R.id.tvStartTimeOfEvent)
    TextView tvStartTimeOfEvent;

    @Bind(R.id.tvEndTimeOfEvent)
    TextView tvEndTimeOfEvent;

    private CompZoneExpandAdapter compZoneExpandAdapter;
    private NewCompEntryData newCompEntryData;

    ArrayList<Slot> compSlotsList;

    private int iZoneNo = 0;
    private boolean isExpandClose = false;
    private boolean slefAlreadyAdded = false;

    // ArrayList<Booking> mBookingList = new ArrayList<>();
    private ArrayList<Slot> mFilterSlotsList = new ArrayList<>();

    private View.OnClickListener mZoneExpandListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (isExpandClose) {
                svTimeSlotsGroup.setVisibility(View.VISIBLE);
                ivExpandCompZone.setImageResource(R.mipmap.ic_expand_less);
                isExpandClose = false;
                updateTimeSlots();
            } else {
                svTimeSlotsGroup.setVisibility(View.GONE);
                isExpandClose = true;
                ivExpandCompZone.setImageResource(R.mipmap.ic_expand_more);
            }
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

//        String jsonFavorites = getIntent().getExtras().getString("RESPONSE_GET_CLUB_EVENT_DATA");
//        Gson gson = new Gson();
//        getClubEventData = gson.fromJson(jsonFavorites, GetClubEventData.class);

//        strEventPrize = getIntent().getExtras().getString("COMPETITIONS_EVENT_PRIZE");
//        strMemberName = getIntent().getExtras().getString("COMPETITIONS_MEMBER_NAME");

        //     iTeamSize = getClubEventData.getTeamSize();

        //If FALSE then user hasn't ability to add another members.
//        isAllowCompEntryAdHocSelection = getClubEventData.getAllowCompEntryAdHocSelection();
//        IsTeeTimeSlotsAllowed = getClubEventData.getIsTeeTimeSlotsAllowed();

        //      tvPlayerName1.setText(strMemberName);

        //updateMemberUI();
        //updateTimeSlots();


        /******************************* NEW COMPETITIONS ENTRY DATA *******************************/
        /*rvCompZone.setLayoutManager(new LinearLayoutManager(CompetitionEntryActivity.this));
        compZoneExpandAdapter = new CompetitionEntryActivity.CompZoneExpandAdapter(newCompEntryData.getZones());
        rvCompZone.setAdapter(compZoneExpandAdapter);*/

        String jsonNewCompEntryData = getIntent().getExtras().getString("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");
        newCompEntryData = new Gson().fromJson(jsonNewCompEntryData, NewCompEntryData.class);

        filterBookedSlotLists();

//        iSelfMemberID = Integer.parseInt(getMemberId());
//        strSelfMemberName = getMemberNameFromID(iSelfMemberID);

        strEventId = getIntent().getExtras().getString("COMPETITIONS_eventId");

        updateNewCompEntryUI();


        /*******************************************************************************************/

       /* llPlayerGroup2.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName2.setOnClickListener(mPlayerSelectionListener);

        llPlayerGroup3.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName3.setOnClickListener(mPlayerSelectionListener);

        llPlayerGroup4.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName4.setOnClickListener(mPlayerSelectionListener);*/

       /* ivCrossPlayer2.setOnClickListener(mCrossListener);
        ivCrossPlayer3.setOnClickListener(mCrossListener);
        ivCrossPlayer4.setOnClickListener(mCrossListener);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_save:

               /* if (IsTeeTimeSlotsAllowed) {
                    if (iSlotNo == -1) {
                        showAlertMessage("Please select any Tee time slot.");
                    } else {
                        showAlertConfirm();
                    }
                } else {
                    showAlertConfirm();
                }*/
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void addPlayersListener(ArrayList<Team> teams, int slotPosition
            , int iTeamPerSlot, int iAddPlayerPosition, boolean isAlertUpdate) {

        int iFreeSlotsAvail = getAvailableSlotsCount(slotPosition);
        if (iFreeSlotsAvail == 1 && !isSlefAlreadyAdded()) {

            Team mTeamInstance = new Team();
            mTeamInstance.setZoneId(teams.get(iAddPlayerPosition).getZoneId());
            mTeamInstance.setSlotIdx(teams.get(iAddPlayerPosition).getSlotIdx());
            mTeamInstance.setTeamIdx(teams.get(iAddPlayerPosition).getTeamIdx());

            /*String iMemberID = teams.get(slotPosition)
                    .getPlayers()
                    .get(0)
                    .getMemberId();*/

            mTeamInstance.setTeamName(getMemberNameFromID(Integer.parseInt(getMemberId())));
            mTeamInstance.setEntryFee(teams.get(iAddPlayerPosition).getEntryFee());

            List<Player> mPlayerList = new ArrayList<>();
            Player mPlayer = new Player();
            mPlayer.setMemberId(getMemberId());
            mPlayer.setIsGuest(false);
            mPlayerList.add(mPlayer);

            mTeamInstance.setPlayers(mPlayerList);
            //teams.get(iAddPlayerPosition).setPlayers(mPlayerList);
            teams.set(iAddPlayerPosition, mTeamInstance);
            // newCompEntryDataCopy.getZones().get(iZoneNo).getSlots().get(slotPosition).getTeams().get()
            // mBookingList.add(mBookingInstance);

            if (isAlertUpdate) {
                updateTimeSlots();
                showAlertMessage(getString(R.string.text_title_added_success));
            }
            return;
        } else {
            Intent intent = new Intent(CompetitionEntryActivity.this, EligiblePlayersActivity.class);
            intent.putExtra("NEW_COMP_EVENT_ID", newCompEntryData.getEventID());
            intent.putExtra("TeamsPerSlot", newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot());
            intent.putExtra("EventID", newCompEntryData.getEventID());
            intent.putExtra("slotPosition", slotPosition);
            intent.putExtra("iTeamPerSlot", iTeamPerSlot);
            intent.putExtra("iAddPlayerPosition", iAddPlayerPosition);
            intent.putExtra("iFreeSlotsAvail", newCompEntryData.getZones()
                    .get(iZoneNo).getSlots().get(slotPosition).getiFreeSlotsAvail());
            intent.putExtra("isSlefAlreadyAdded", isSlefAlreadyAdded());
            intent.putExtra("iSelfMemberID", Integer.parseInt(getMemberId()));
            //  intent.putExtra("strSelfMemberName", strSelfMemberName);
            Bundle informacion = new Bundle();
            informacion.putSerializable("AllPlayers", playersArrayList);
            informacion.putSerializable("teams", teams);
            intent.putExtras(informacion);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void removePlayerListener(ArrayList<Team> teams, int iSlotPosition, int iAddPlayerPosition) {

        Team mTeamInstance = new Team();
        mTeamInstance.setZoneId(teams.get(iAddPlayerPosition).getZoneId());
        mTeamInstance.setSlotIdx(teams.get(iAddPlayerPosition).getSlotIdx());
        mTeamInstance.setTeamIdx(teams.get(iAddPlayerPosition).getTeamIdx());

            /*String iMemberID = teams.get(slotPosition)
                    .getPlayers()
                    .get(0)
                    .getMemberId();*/

        mTeamInstance.setTeamName("(Free)");
        mTeamInstance.setEntryFee((double) 0);

        List<Player> mPlayerList = new ArrayList<>();

        if (teams.get(iAddPlayerPosition).getPlayers().get(0).getMemberId()
                .equalsIgnoreCase(getMemberId())) {
            setSelfAlreadyAdded(false);
        }

        mTeamInstance.setPlayers(mPlayerList);
        //teams.get(iAddPlayerPosition).setPlayers(mPlayerList);
        teams.set(iAddPlayerPosition, mTeamInstance);

        updateTimeSlots();
    }

    /**
     * Implements this method to call when user tap to Add Member.
     */
    public View.OnClickListener mPlayerSelectionListener = new View.OnClickListener() {
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
            //intent.putExtra("COMPETITIONS_TeamSize", iTeamSize);
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
    public View.OnClickListener mCrossListener = new View.OnClickListener() {
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

    /**
     * Implements this method to set visibility status of players
     * UI according to iTeamSize.
     * <p>
     * For Exp: iTeamSize = 2, means set visibility of PLAYER 2 and PLAYER 3 only.
     * <p>
     * NOTE: Maximum TeamSize : 4
     */
   /* private void updateMemberUI() {

        //Member has ability to add another member if the below AdHocSelection is TRUE.
        if (isAllowCompEntryAdHocSelection) {

            *//**
     * Loop will start from 1 because 0th is position of member itself
     * who is going to select other members.
     *//*
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

                    */

    /**
     * After remove Member, RecordID and PlayerName values will be 0 from api. If its
     * 0 or empty then don't update UI and not need to add in local array.
     *//*
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
    }*/

    /**
     * Filter Array with selected
     * members list.
     */
    private void filterBookedSlotLists() {

        List<Slot> mSlotsList = newCompEntryData.getZones().get(iZoneNo).getSlots();
        int iTeamSize = newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot();

        boolean isAddedNew;

        for (int iSlotCount = 0; iSlotCount < mSlotsList.size(); iSlotCount++) {

            ArrayList<Team> mFilterTeam = new ArrayList<>();
            isAddedNew = false;

            for (int jTeamCount = 0; jTeamCount < iTeamSize; jTeamCount++) {

                if (!mSlotsList.get(iSlotCount).getTeams()
                        .get(jTeamCount).getTeamName().equals("(Free)")) {

                    mFilterTeam.add(mSlotsList.get(iSlotCount).getTeams().get(jTeamCount));
                    isAddedNew = true;
                }
            }

            if (isAddedNew) {
                Slot mSlotInstance = new Slot();
                mSlotInstance.setTeams(mFilterTeam);
                mSlotInstance.setTeeOffTime(mSlotsList.get(iSlotCount).getTeeOffTime());
                mSlotInstance.setiFreeSlotsAvail(mSlotsList.get(iSlotCount).getiFreeSlotsAvail());
                mSlotInstance.setStatus(mSlotsList.get(iSlotCount).getStatus());
                mFilterSlotsList.add(mSlotInstance);
            }
        }
    }

    private void updateNewCompEntryUI() {

        tvZoneName.setText(newCompEntryData.getZones().get(iZoneNo).getZoneName());
        tvOccupancy.setText(("Free Spaces Available :" + newCompEntryData.getZones().get(iZoneNo).getFreePlaces()));

        tvTitleOfComp.setText(newCompEntryData.getEventName());
        tvTimeOfComp.setText(newCompEntryData.getEventStartDate().getFullDateStr());

        flSingleZoneGroup.setOnClickListener(mZoneExpandListener);
        ivExpandCompZone.setOnClickListener(mZoneExpandListener);

        updateTimeAndTitleOfZone(newCompEntryData.getZones().get(iZoneNo).getZoneName());

        btConfirmTimeBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompetitionEntryActivity.this, ConfirmBookingEntryActivity.class);
                intent.putExtra("iZoneNo", iZoneNo);
                intent.putExtra("strZoneName", tvZoneName.getText().toString());
                intent.putExtra("RESPONSE_GET_CLUBEVENT_ENTRY_DATA", new Gson().toJson(newCompEntryData));
                Bundle informacion = new Bundle();
                informacion.putSerializable("filterSlotList", mFilterSlotsList);
                intent.putExtras(informacion);
                startActivity(intent);
            }
        });

        updateTimeSlots();

        // allPlayerArrayList.addAll(newCompEntryData.getAllPlayers());
    }

    private void updateTimeAndTitleOfZone(String strEventName) {

        if (strEventName.contains(",")) {
            tvZoneName.setText(strEventName.substring(0, strEventName.indexOf(",")));
        }

        if (strEventName.contains("-")) {
            tvStartTimeOfEvent.setText(strEventName.substring(strEventName.indexOf(",") + 1, strEventName.indexOf("-")));
            tvEndTimeOfEvent.setText(strEventName.substring(strEventName.indexOf("-") + 1, strEventName.length()));
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
            reportRollBarException(CompetitionEntryActivity.class.getSimpleName(), e.toString());
        }
        hideProgress();
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    private String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    private String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements this method to Remove Member from ArrayList.
     *
     * @param strMemberName
     */
    private void removeMemberFromList(String strMemberName) {

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
    private void showAlertOk(String strAlertMessage) {

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

    /**
     * Implements this method to get TEE time slots for book
     * paid Competitions.
     */
    public void updateTimeSlots() {

        if (newCompEntryData.getZones().get(iZoneNo).getSlots() == null) {
            return;
        }

        try {
            compSlotsList = newCompEntryData.getZones().get(iZoneNo).getSlots();

            gvTimeSlots.setExpanded(true);
            compTimeSlotsAdapter = new CompTimeSlotsAdapter(CompetitionEntryActivity.this,
                    compSlotsList,
                    iSlotNo,
                    newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot(),
                    CompetitionEntryActivity.this);
            gvTimeSlots.setAdapter(compTimeSlotsAdapter);

            //Forcefully scroll UP of screen after loading.
            svPlayerContent.post(new Runnable() {
                public void run() {
                    svPlayerContent.fullScroll(View.FOCUS_UP);
                }
            });
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception " + e.toString());
            reportRollBarException(CompetitionEntryActivity.class.getSimpleName(), e.toString());
        }
    }

    /**
     * Update Tee Time Slot value.
     */
    /*public void updateTeeTimeValue(int iPosition) {
        tvTeeTimeValue.setText(slotArrayList.get(iPosition).getSlotStartTimeStr());
        tvSelectHint.setText("");

        iSlotNo = slotArrayList.get(iPosition).getSlotNo();
    }*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            playersArrayList.clear();
            playersArrayList = (ArrayList<EligibleMember>) data.getSerializableExtra("MEMBER_LIST");
            ArrayList<Team> mTeam = (ArrayList<Team>) data.getSerializableExtra("teams");

            int iSlotPosition = data.getExtras().getInt("slotPosition");
            int iTeamPerSlot = data.getExtras().getInt("iTeamPerSlot");
            int iAddPlayerPosition = data.getExtras().getInt("iAddPlayerPosition");

            for (int iCount = iAddPlayerPosition; iCount < playersArrayList.size(); iCount++) {
                updateMemberDetailInSlots(iCount
                        , playersArrayList
                        , mTeam
                        , iSlotPosition
                        , iTeamPerSlot
                        , iCount);
                //TODO: Update Slots value.

                //So we can add member itself in the last.
                //iAddPlayerPosition = iCount;
            }

          /*  if (!isSlefAlreadyAdded()) {
               *//* CompetitionEntryActivity.this.addPlayersListener(mTeam
                        , iSlotPosition
                        , iTeamPerSlot
                        , iAddPlayerPosition,
                        false);*//*
                addPlayerToList(mTeam
                        , iSlotPosition
                        , iTeamPerSlot
                        , iAddPlayerPosition + 1);
            }*/

            updateTimeSlots();

           /* if (iEntryID == 0) {
                updateNoEntryIdMemberUI();
            } else {
                updateWithEntryIdMemberUI();
            }*/

//            updatePriceUI();
        }
    }

    private void addPlayerToList(ArrayList<Team> teams, int iSlotPosition, int iTeamPerSlot, int iAddPlayerPosition) {
        Team mTeamInstance = new Team();
        mTeamInstance.setZoneId(teams.get(iAddPlayerPosition).getZoneId());
        mTeamInstance.setSlotIdx(teams.get(iAddPlayerPosition).getSlotIdx());
        mTeamInstance.setTeamIdx(teams.get(iAddPlayerPosition).getTeamIdx());

            /*String iMemberID = teams.get(slotPosition)
                    .getPlayers()
                    .get(0)
                    .getMemberId();*/

        mTeamInstance.setTeamName(getMemberNameFromID(Integer.parseInt(getMemberId())));
        mTeamInstance.setEntryFee(teams.get(iAddPlayerPosition).getEntryFee());

        List<Player> mPlayerList = new ArrayList<>();
        Player mPlayer = new Player();
        mPlayer.setMemberId(getMemberId());
        mPlayer.setIsGuest(false);
        mPlayerList.add(mPlayer);

        mTeamInstance.setPlayers(mPlayerList);
        //teams.get(iAddPlayerPosition).setPlayers(mPlayerList);
        teams.set(iAddPlayerPosition, mTeamInstance);

        newCompEntryData.getZones().get(iZoneNo).getSlots().get(iSlotPosition).setTeams(teams);
        // newCompEntryDataCopy.getZones().get(iZoneNo).getSlots().get(slotPosition).getTeams().get()
        // mBookingList.add(mBookingInstance);

    }

    /**
     * Get Member name along with HCap value
     * by passing Member ID.
     */
    public String getMemberNameFromID(int iMemberID) {

        if (newCompEntryData.getAllPlayers() == null) {
            return "N/A";
        }

        //Also check here if member self added in the list then set Flag.
        if (iMemberID == Integer.parseInt(getMemberId())) {
            setSelfAlreadyAdded(true);
        }

        List<AllPlayer> mAllPlayersList = newCompEntryData.getAllPlayers();

        for (int iCounter = 0; iCounter < mAllPlayersList.size(); iCounter++) {
            if (mAllPlayersList.get(iCounter).getMemberId() == iMemberID) {
                return mAllPlayersList.get(iCounter).getNameAndHandicap();
            }
        }

        return "N/A";
    }

    private int getAvailableSlotsCount(int slotPosition) {
        return newCompEntryData.getZones().get(iZoneNo).getSlots().get(slotPosition).getiFreeSlotsAvail();
    }

    /**
     * iMemberPosition : Position of Member in selected list.
     * eligibleMemberArrayList : List of selected Members.
     * iSlotPosition : In which slot member want to enter.
     * iTeamPerSlot  : Size of Team at per slot.
     * iAddPlayerPosition :
     */
    private void updateMemberDetailInSlots(int iMemberPosition, ArrayList<EligibleMember> eligibleMemberArrayList,
                                           ArrayList<Team> mTeam, int iSlotPosition, int iTeamPerSlot, int iAddPlayerPosition) {

        Team mTeamInstance = new Team();
        mTeamInstance.setZoneId(mTeam.get(iAddPlayerPosition).getZoneId());
        mTeamInstance.setSlotIdx(mTeam.get(iAddPlayerPosition).getSlotIdx());
        mTeamInstance.setTeamIdx(mTeam.get(iAddPlayerPosition).getTeamIdx());

            /*String iMemberID = teams.get(slotPosition)
                    .getPlayers()
                    .get(0)
                    .getMemberId();*/

        mTeamInstance.setTeamName(getMemberNameFromID(eligibleMemberArrayList.get(iMemberPosition).getMemberID()));
        mTeamInstance.setEntryFee(mTeam.get(iAddPlayerPosition).getEntryFee());

        List<Player> mPlayerList = new ArrayList<>();
        Player mPlayer = new Player();
        mPlayer.setMemberId("" + eligibleMemberArrayList.get(iMemberPosition).getMemberID());
        mPlayer.setIsGuest(false);
        mPlayerList.add(mPlayer);

        mTeamInstance.setPlayers(mPlayerList);
        //teams.get(iAddPlayerPosition).setPlayers(mPlayerList);
        mTeam.set(iAddPlayerPosition, mTeamInstance);
        // newCompEntryDataCopy.getZones().get(iZoneNo).getSlots().get(slotPosition).getTeams().get()
        // mBookingList.add(mBookingInstance);

        //updateTimeSlots();
        newCompEntryData.getZones().get(iZoneNo).getSlots().get(iSlotPosition).setTeams(mTeam);
    }

    public boolean isSlefAlreadyAdded() {
        return slefAlreadyAdded;
    }

    public void setSelfAlreadyAdded(boolean slefAlreadyAdded) {
        this.slefAlreadyAdded = slefAlreadyAdded;
    }

    /****************************************************************************
     *
     *         START OF EXPANDABLE ADAPTER FOR NEW COMPITIONS ENTRY FEATURE
     *
     ****************************************************************************/

    /**
     * Implements a method to update SUCCESS response of web service.
     */

    static class UserViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout flZoneGroup;
        private ImageView ivExpandCompZone;
        private TextView tvTitleOfZone, tvOccupancy;
        private ExpandableHeightGridView gvTimeSlots;

        public UserViewHolder(View itemView) {
            super(itemView);
            flZoneGroup = (FrameLayout) itemView.findViewById(R.id.flZoneGroup);

            ivExpandCompZone = (ImageView) itemView.findViewById(R.id.ivExpandCompZone);

            tvTitleOfZone = (TextView) itemView.findViewById(R.id.tvTitleOfZone);
            tvOccupancy = (TextView) itemView.findViewById(R.id.tvOccupancy);

            gvTimeSlots = (ExpandableHeightGridView) itemView.findViewById(R.id.gvTimeSlots);
        }
    }

    class CompZoneExpandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        String strSecondPosition = "";
        List<Zone> compZonesList;
        LinearLayoutManager layoutInflater;

        public CompZoneExpandAdapter(List<Zone> compZonesList) {
            this.compZonesList = compZonesList;
            //  layoutInflater = (LinearLayoutManager) rvCompZone.getLayoutManager();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemLayout = LayoutInflater.from(CompetitionEntryActivity.this).inflate(R.layout.list_item_comp_zone_title, null);
            return new CompetitionEntryActivity.UserViewHolder(itemLayout);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof UserViewHolder) {
                final Zone zoneInstance = compZonesList.get(position);
                final UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.tvTitleOfZone.setText(zoneInstance.getZoneName());
                userViewHolder.tvOccupancy.setText(("Total Free Spaces Available :" + zoneInstance.getFreePlaces()));

                /*
                * if type = 0 then collapse the view
                *
                * if type = 1 then expande the view
                * */
                if (zoneInstance.isExpand()) {
                    userViewHolder.gvTimeSlots.setVisibility(View.GONE);
                    userViewHolder.ivExpandCompZone.setImageResource(R.mipmap.ic_expand_less);
                } else {
                    userViewHolder.gvTimeSlots.setVisibility(View.VISIBLE);
                    userViewHolder.ivExpandCompZone.setImageResource(R.mipmap.ic_expand_more);
                }
                userViewHolder.flZoneGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        * For expande and collapse the view
                        * */

                        if (zoneInstance.isExpand()) {
                            zoneInstance.setExpand(false);
                        } else {
                            zoneInstance.setExpand(true);
                        }


                        /*
                        * This condition is used for to collapse the last expand view when expand
                        * another one
                        * */
                        if (!strSecondPosition.equals("") && !strSecondPosition.equals("" + position)) {
                            Zone zoneInstanceTemp = compZonesList.get(Integer.parseInt(strSecondPosition));
                            zoneInstanceTemp.setZoneName(zoneInstanceTemp.getZoneName());
                            zoneInstanceTemp.setFreePlaces("Total Free Spaces Available :" + zoneInstanceTemp.getFreePlaces());
                            zoneInstanceTemp.setExpand(false);
                            compZonesList.set(Integer.parseInt(strSecondPosition), zoneInstanceTemp);

                        }
                  /*
                   * @param strSecondPosition is used to store clicked item position
                   * */
                        strSecondPosition = "" + position;


                        compZoneExpandAdapter.notifyDataSetChanged();
                    }
                });

                /*
                * This condition is used to show loader in the bottom of recycle view
                * */
            }
        }

        @Override
        public int getItemCount() {
            return compZonesList == null ? 0 : compZonesList.size();
        }
    }

    /****************************************************************************
     *
     *         END OF EXPANDABLE ADAPTER FOR NEW COMPITIONS ENTRY FEATURE
     *
     ****************************************************************************/
}
