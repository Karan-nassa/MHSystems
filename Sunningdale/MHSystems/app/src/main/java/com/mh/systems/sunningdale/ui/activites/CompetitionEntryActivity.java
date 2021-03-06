package com.mh.systems.sunningdale.ui.activites;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.ui.adapter.BaseAdapter.CompTimeSlotsAdapter;
import com.mh.systems.sunningdale.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.sunningdale.utils.ExpandableHeightGridView;
import com.mh.systems.sunningdale.utils.constants.ApplicationGlobal;
import com.mh.systems.sunningdale.web.models.competitionsentry.EligibleMember;
import com.mh.systems.sunningdale.web.models.competitionsentrynew.AllPlayer;
import com.mh.systems.sunningdale.web.models.competitionsentrynew.NewCompEntryData;
import com.mh.systems.sunningdale.web.models.competitionsentrynew.Player;
import com.mh.systems.sunningdale.web.models.competitionsentrynew.Slot;
import com.mh.systems.sunningdale.web.models.competitionsentrynew.Team;
import com.mh.systems.sunningdale.web.models.competitionsentrynew.Zone;
import com.newrelic.com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create {@link CompetitionEntryActivity} to choose Players/Members and TimeSlots.
 *
 * @author karan@ucreate.co.in
 */
public class CompetitionEntryActivity extends BaseActivity implements OnUpdatePlayers {

    private final String LOG_TAG = CompetitionEntryActivity.class.getSimpleName();
    private final int RESULT_CODE_CONFIRM_BOOKING = 1221;
    private final int RESULT_CODE_ADD_MORE_MEMBERS = 1441;
    private final int RESULT_CODE_ADD_PER_TEAMSIZE_4 = 1442;

    private final int ACTION_TYPE_CONFIRM_BOOKING = 1;
    private final int ACTION_TYPE_ADD_SELF_ONLY = 2;
    private final int ACTION_TYPE_ADD_WITH_MEMBERS = 3; //Add itself along with members.
    private final int ACTION_TYPE_ADD_PER_TEAMSIZE = 4;
    public static final int ACTION_TYPE_DEFAULT = 0;

    @Bind(R.id.tbBookingDetail)
    Toolbar tbBookingDetail;

    @Bind(R.id.gvTimeSlots)
    ExpandableHeightGridView gvTimeSlots;

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

   /* @Bind(R.id.rvCompZone)
    RecyclerView rvCompZone;*/

    private float mEntryFee = 0;
    int iMaxTeamAdded;
    int iAlreadyBookSlotIdx = -1;

    /**
     * If TRUE then show Stay/Leave Alert
     * dialog.
     */
    private boolean isAnyChange = false;


    int iSlotNo = -1;

    private CompZoneExpandAdapter compZoneExpandAdapter;
    private NewCompEntryData newCompEntryData;

    ArrayList<Slot> compSlotsList;

    private int iZoneNo = 0;
    private boolean isExpandClose = false;

    Map<Integer, AllPlayer> mapAllPlayer = new HashMap<>();

    //List<Slot> slotArrayList = new ArrayList<>();
    ArrayList<EligibleMember> allEligiblePlayers = new ArrayList<>();
    ArrayList<EligibleMember> SlotsEligiblePlayers = new ArrayList<>();

    CompTimeSlotsAdapter compTimeSlotsAdapter;

    OnUpdatePlayers mOnUpdatePlayers;

    Intent intent;

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

        mOnUpdatePlayers = CompetitionEntryActivity.this;

        if (tbBookingDetail != null) {
            setSupportActionBar(tbBookingDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /******************************* NEW COMPETITIONS ENTRY DATA *******************************/
        /*rvCompZone.setLayoutManager(new LinearLayoutManager(CompetitionEntryActivity.this));
        compZoneExpandAdapter = new CompetitionEntryActivity.CompZoneExpandAdapter(newCompEntryData.getZones());
        rvCompZone.setAdapter(compZoneExpandAdapter);*/

        String jsonNewCompEntryData = getIntent().getExtras().getString("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");
        newCompEntryData = new Gson().fromJson(jsonNewCompEntryData, NewCompEntryData.class);

        /**
         * Initially Create dictionary of All Players
         * so we can filter it later.
         */
        for (int iMapValue = 0; iMapValue < newCompEntryData.getAllPlayers().size(); iMapValue++) {

            AllPlayer allPlayerInstance = newCompEntryData.getAllPlayers().get(iMapValue);
            mapAllPlayer.put(allPlayerInstance.getMemberId(), allPlayerInstance);
        }

        iMaxTeamAdded = newCompEntryData.getBooking().size();
        newCompEntryData.setMaxTeamAdded(iMaxTeamAdded);

        /**
         * Store SlotIdx if user already made
         * booking for any slot.
         */
        if (newCompEntryData.getBooking().size() > 0) {
            //Just get SlotIdx of any booking slot because all would be same.
            iAlreadyBookSlotIdx = newCompEntryData.getBooking().get(0).getSlotIdx();
            newCompEntryData.getZones().get(iZoneNo).setiAlreadyBookSlotIdx(iAlreadyBookSlotIdx);
        }

        mEntryFee = 0;

        updateNewCompEntryUI();

        /*******************************************************************************************/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_save:

                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {

        if (isAnyChange) {
            checkAnyUpdate();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void addPlayersListener(ArrayList<Team> teams, int slotPosition
            , int iTeamPerSlot, int iAddPlayerPosition
            , int iAlreadyBookSlotIdx, boolean isAlertUpdate) {

        //For Show Stay/Leave Alert.
        isAnyChange = true;
        newCompEntryData.getZones().get(iZoneNo).setiAlreadyBookSlotIdx(iAlreadyBookSlotIdx);

        /****************
         * Member cannot add more than the max of 'MaxTeamCount'. So
         * check How many he/she already Booked/Added.
         ****************/
        int iCanAddMore = newCompEntryData.getMaxTeamCount() - newCompEntryData.getMaxTeamAdded();

        /**
         * Exp: There are 3 Teams in 1 slot and already booked
         * 1 Team by other member.
         */
        int iFreeSlotsAvail = getAvailableSlotsCount(slotPosition);
        if (iFreeSlotsAvail < iCanAddMore) {
            iCanAddMore = iFreeSlotsAvail;
        }

        if (iCanAddMore == 1/*iCanAddMore >= 1 && iFreeSlotsAvail == 1*/ && !newCompEntryData.isSlefAlreadyAdded()) {

            Team mTeamInstance = new Team();
            mTeamInstance.setZoneId(teams.get(iAddPlayerPosition).getZoneId());
            mTeamInstance.setSlotIdx(teams.get(iAddPlayerPosition).getSlotIdx());
            mTeamInstance.setTeamIdx(teams.get(iAddPlayerPosition).getTeamIdx());

            int memberID = Integer.parseInt(getMemberId());
            mTeamInstance.setTeamName(getMemberNameFromID(memberID));
            mTeamInstance.setEntryFee((double) mapAllPlayer.get(memberID).getEntryFee());
            mTeamInstance.setAnyUpdated(true);

            List<Player> mPlayerList = new ArrayList<>();
            Player mPlayer = new Player();
            mPlayer.setMemberId(getMemberId());
            mPlayer.setIsGuest(false);
            mPlayerList.add(mPlayer);

            mTeamInstance.setPlayers(mPlayerList);
            teams.set(iAddPlayerPosition, mTeamInstance);

            mEntryFee += newCompEntryData.getEntryFee();
            newCompEntryData.setMaxTeamAdded(++iMaxTeamAdded);
            showAlertMessageOk(ACTION_TYPE_ADD_SELF_ONLY
                    , getString(R.string.text_title_added_success)
                    , getString(R.string.alert_title_congrates));

        } else {

            SlotsEligiblePlayers.clear();
            intent = new Intent(CompetitionEntryActivity.this, EligiblePlayersActivity.class);

            intent.putExtra("iFreeSlotsAvail", iCanAddMore);

            /** TRUE if
             *  Team size 1 and Teams per Tee more than 1.
             */
            intent.putExtra("isMinRequired", false);

            iAddPlayerPosition = SlotsEligiblePlayers.size();

            intent.putExtra("NEW_COMP_EVENT_ID", newCompEntryData.getEventID());
            intent.putExtra("TeamsPerSlot", newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot());
            intent.putExtra("EventID", newCompEntryData.getEventID());
            intent.putExtra("slotPosition", slotPosition);
            intent.putExtra("iTeamPerSlot", iTeamPerSlot);
            intent.putExtra("iAddPlayerPosition", iAddPlayerPosition);
            intent.putExtra("isSlefAlreadyAdded", newCompEntryData.isSlefAlreadyAdded());
            intent.putExtra("iSelfMemberID", Integer.parseInt(getMemberId()));
            Bundle informacion = new Bundle();
            informacion.putSerializable("AllPlayers", allEligiblePlayers);
            informacion.putSerializable("SlotsEligiblePlayers", SlotsEligiblePlayers);
            informacion.putSerializable("teams", teams);
            intent.putExtras(informacion);

            if (!newCompEntryData.isSlefAlreadyAdded()) {
                showAlertMessageOk(ACTION_TYPE_ADD_WITH_MEMBERS
                        , getString(R.string.text_title_added_success)
                        , getString(R.string.alert_title_congrates));
            } else {
                startActivityForResult(intent, RESULT_CODE_ADD_MORE_MEMBERS);
            }
        }
    }

    @Override
    public void removePlayerListener(ArrayList<Team> teams, int iSlotPosition,
                                     int iAddPlayerPosition) {

        //For Show Stay/Leave Alert.
        isAnyChange = true;

        int iSlotIdx = teams.get(iAddPlayerPosition).getSlotIdx();
        int iTeamIdx = teams.get(iAddPlayerPosition).getTeamIdx();

        Team mTeamInstance = new Team();
        mTeamInstance.setZoneId(teams.get(iAddPlayerPosition).getZoneId());
        mTeamInstance.setSlotIdx(iSlotIdx);
        mTeamInstance.setTeamIdx(iTeamIdx);

        mTeamInstance.setTeamName("(Free)");
        mTeamInstance.setEntryFee((double) 0);

        int iMemberID = Integer.parseInt(teams.get(iAddPlayerPosition)
                .getPlayers().get(0).getMemberId());
        removeMemberFromSelectedList(iMemberID);

        mTeamInstance.setAnyUpdated(false);

        List<Player> mPlayerList = new ArrayList<>();
        if (teams.get(iAddPlayerPosition).getPlayers().get(0).getMemberId()
                .equalsIgnoreCase(getMemberId())) {
            newCompEntryData.setSelfAlreadyAdded(false);
        }

        mTeamInstance.setPlayers(mPlayerList);
        teams.set(iAddPlayerPosition, mTeamInstance);

        mEntryFee -= newCompEntryData.getEntryFee();
        newCompEntryData.setMaxTeamAdded(--iMaxTeamAdded);

        newCompEntryData.getZones().get(iZoneNo).getSlots()
                .get(iSlotIdx).getTeams().set(iTeamIdx, mTeamInstance);

        if (newCompEntryData.getMaxTeamAdded() == 0
                && newCompEntryData.getBooking().size() == 0) {
            newCompEntryData.getZones().get(iZoneNo).setiAlreadyBookSlotIdx(-1);
        }

        updateTimeSlots();
    }

    /**
     * Team size = 2, max entry 1, team.  _2 team per slot_
     * <p>
     * Team size = 3, max entry 1, team.  _1 team per slot_
     * <p>
     * Team size = 4, max entry 1, team.  _1 team per slot_
     */
    @Override
    public void addorChangePlayerUpdateMaxTeam(List<Player> mPlayersArr, ArrayList<Team> teamArrayList
            , int slotPosition, int iTeamsPerSlot, int iTeamPosition,
                                               int iSlotIdx, int iPlayerCount, int actionCallFromRemove) {

        //allEligiblePlayers.clear();

        if (actionCallFromRemove == ApplicationGlobal.ACTION_CALL_FROM_REMOVE) {

            if (mPlayersArr.get(iPlayerCount).getMemberId().equals(getMemberId())) {
                newCompEntryData.setSelfAlreadyAdded(false);
            }

            allEligiblePlayers.remove(mPlayersArr.get(iPlayerCount).getMemberId());

            mPlayersArr.remove(iPlayerCount);

            newCompEntryData.getZones().get(iZoneNo)
                    .getSlots().get(slotPosition)
                    .getTeams().get(iTeamPosition)
                    .setPlayers(mPlayersArr);
        }

        for (int iTeamCount = 0; iTeamCount < teamArrayList.size(); iTeamCount++) {

            for (int iCount = 0; iCount < mPlayersArr.size(); iCount++) {

                int iMemberID = Integer.parseInt(mPlayersArr.get(iCount).getMemberId());
                if (mapAllPlayer.containsKey(iMemberID)) {

                    EligibleMember eligibleMember = new EligibleMember(true,
                            iMemberID);
                    allEligiblePlayers.add(eligibleMember);
                }
            }
        }

        isAnyChange = true;

        newCompEntryData.getZones().get(iZoneNo).getSlots()
                .get(slotPosition).getTeams()
                .get(iTeamPosition).setAnyUpdated(true);
        newCompEntryData.getZones().get(iZoneNo).setiAlreadyBookSlotIdx(iSlotIdx);

        /****************
         * Member cannot add more than the max of 'MaxTeamCount'. So
         * check How many he/she already Booked/Added.
         ****************/
        int iCanAddMore = newCompEntryData.getTeamSize() - mPlayersArr.size();

        SlotsEligiblePlayers.clear();
        intent = new Intent(CompetitionEntryActivity.this, EligiblePlayersActivity.class);

        /** TRUE if
         *  Team size 3 and Teams per Tee 1
         *  Team size 4 and Teams per Tee 1
         */
        intent.putExtra("isMinRequired", true);

        intent.putExtra("iFreeSlotsAvail", iCanAddMore/*newCompEntryData.getTeamSize()*/);

        iTeamsPerSlot = SlotsEligiblePlayers.size();
        intent.putExtra("NEW_COMP_EVENT_ID", newCompEntryData.getEventID());
        intent.putExtra("TeamsPerSlot", newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot());
        intent.putExtra("EventID", newCompEntryData.getEventID());
        intent.putExtra("slotPosition", slotPosition);
        intent.putExtra("iTeamSize", newCompEntryData.getTeamSize());
        intent.putExtra("iAddPlayerPosition", iTeamsPerSlot);
        intent.putExtra("iSelfMemberID", Integer.parseInt(getMemberId()));
        intent.putExtra("iTeamPosition", iTeamPosition);
        Bundle informacion = new Bundle();
        informacion.putSerializable("AllPlayers", allEligiblePlayers);
        informacion.putSerializable("SlotsEligiblePlayers", SlotsEligiblePlayers);
        informacion.putSerializable("teams", teamArrayList);
        informacion.putSerializable("playerArrayList", (ArrayList<Player>) mPlayersArr);
        intent.putExtras(informacion);

        if (!newCompEntryData.isSlefAlreadyAdded()
                && actionCallFromRemove != ApplicationGlobal.ACTION_CALL_FROM_REMOVE) {
            intent.putExtra("isSlefAlreadyAdded", newCompEntryData.isSlefAlreadyAdded());
            showAlertMessageOk(ACTION_TYPE_ADD_PER_TEAMSIZE
                    , getString(R.string.text_title_added_success)
                    , getString(R.string.alert_title_congrates));
        } else {
            //Currently as per functionality, user will not get prompt next time to add itself forcefully.
            intent.putExtra("isSlefAlreadyAdded", true);/*newCompEntryData.isSlefAlreadyAdded());*/
            startActivityForResult(intent, RESULT_CODE_ADD_PER_TEAMSIZE_4);
        }
    }

    @Override
    public void confirmRemoveTeam(final List<Player> mPlayersArr, final ArrayList<Team> teamArrayList
            , final int iSlotPos, final int iTeamsPerSlot,
                                  final int iTeamPosition, final int iSlotIdx,
                                  final int iPlayerCount, final int actionCallFromRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CompetitionEntryActivity.this);
        builder.setTitle(getString(R.string.alert_title_alert))
                .setMessage(getResources().getString(R.string.alert_title_remove_team))
                .setPositiveButton(getString(R.string.alert_text_change_player), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnUpdatePlayers.addorChangePlayerUpdateMaxTeam(mPlayersArr, teamArrayList, iSlotPos, iTeamsPerSlot,
                                iTeamPosition, iSlotIdx, iPlayerCount, actionCallFromRemove);
                    }
                })
                .setNegativeButton(getString(R.string.alert_text_remove_team), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAllPlayersOfTeam(mPlayersArr, teamArrayList, iSlotPos, iTeamsPerSlot,
                                iTeamPosition, iSlotIdx, iPlayerCount, actionCallFromRemove);
                    }
                }).show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case RESULT_CODE_CONFIRM_BOOKING: {

                    // mFilterSlotsList =  (ArrayList<Slot>) getIntent().getSerializableExtra("filterSlotList");
                    //String strZoneName = getIntent().getExtras().getString("strZoneName");
                    mEntryFee = data.getExtras().getFloat("mEntryFee");
                    iZoneNo = data.getExtras().getInt("iZoneNo");
                    newCompEntryData = (NewCompEntryData) data.getSerializableExtra("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");
                }
                break;

                case RESULT_CODE_ADD_PER_TEAMSIZE_4: {
                    //For Show Stay/Leave Alert.
                    isAnyChange = true;

                    if (newCompEntryData.getTeamSize() == 3 || newCompEntryData.getTeamSize() == 4) {
                        allEligiblePlayers.clear();
                    }

                    allEligiblePlayers.addAll((ArrayList<EligibleMember>) data.getSerializableExtra("MEMBER_LIST"));

                    SlotsEligiblePlayers.clear();
                    SlotsEligiblePlayers = (ArrayList<EligibleMember>) data.getSerializableExtra("SlotsEligiblePlayers");
                    ArrayList<Team> mTeam = (ArrayList<Team>) data.getSerializableExtra("teams");
                    List<Player> playerArrayList = (ArrayList<Player>) data.getSerializableExtra("playerArrayList");

                    int iSlotPosition = data.getExtras().getInt("slotPosition");
                    int iTeamPerSlot = data.getExtras().getInt("iTeamPerSlot");
                    int iAddPlayerPosition = data.getExtras().getInt("iAddPlayerPosition");
                    int iTeamPos = data.getExtras().getInt("iTeamPosition");

                    for (int iSelectedEliglble = 0; iSelectedEliglble < SlotsEligiblePlayers.size(); iSelectedEliglble++) {

                        Player mPlayerInstance = new Player();
                        mPlayerInstance.setMemberId("" + SlotsEligiblePlayers.get(iSelectedEliglble).getMemberID());
                        mPlayerInstance.setIsGuest(false);
                        playerArrayList.add(mPlayerInstance);

                        if (mPlayerInstance.getMemberId().equals(getMemberId())) {
                            newCompEntryData.setSelfAlreadyAdded(true);
                        }
                    }

                    double iMaxPlayerFee = 0;
                    for (int iPlayer = 0; iPlayer < playerArrayList.size(); iPlayer++) {
                        double currentPlayerFee = mapAllPlayer.
                                get(Integer.parseInt(playerArrayList.get(iPlayer).getMemberId())).getEntryFee();
                        if (iMaxPlayerFee < currentPlayerFee) {
                            iMaxPlayerFee = currentPlayerFee;
                        }
                        mTeam.get(iTeamPos).setEntryFee(iMaxPlayerFee);
                        mEntryFee = (float) iMaxPlayerFee;
                    }

                    mTeam.get(iTeamPos).setAnyUpdated(true);
                    mTeam.get(iTeamPos).setPlayers(playerArrayList);
                    newCompEntryData.getZones().get(iZoneNo).getSlots().get(iSlotPosition).setTeams(mTeam);
                }
                break;

                case RESULT_CODE_ADD_MORE_MEMBERS:

                    //For Show Stay/Leave Alert.
                    isAnyChange = true;

                    allEligiblePlayers.clear();
                    SlotsEligiblePlayers.clear();
                    SlotsEligiblePlayers = (ArrayList<EligibleMember>) data.getSerializableExtra("SlotsEligiblePlayers");
                    allEligiblePlayers = (ArrayList<EligibleMember>) data.getSerializableExtra("MEMBER_LIST");
                    ArrayList<Team> mTeam = (ArrayList<Team>) data.getSerializableExtra("teams");

                    int iSlotPosition = data.getExtras().getInt("slotPosition");
                    int iTeamPerSlot = data.getExtras().getInt("iTeamPerSlot");
                    int iAddPlayerPosition = data.getExtras().getInt("iAddPlayerPosition");

                    for (int iSelectedEliglble = 0; iSelectedEliglble < SlotsEligiblePlayers.size(); iSelectedEliglble++) {

                        boolean isSpaceFound = false;
                        List<Team> teamArrayList = newCompEntryData.getZones()
                                .get(iZoneNo).getSlots().get(iSlotPosition).getTeams();

                        for (int iTeamPos = 0;
                             (iTeamPos < teamArrayList.size() && !isSpaceFound); iTeamPos++) {

                            List<Player> mPlayerArr = teamArrayList.get(iTeamPos).getPlayers();

                            if (mPlayerArr.size() == 0) {

                                updateMemberDetailInSlots(
                                        SlotsEligiblePlayers.get(iSelectedEliglble)
                                        , mTeam
                                        , iSlotPosition
                                        , iTeamPerSlot
                                        , iTeamPos);

                                isSpaceFound = true;
                            }
                        }
                    }
            }
            updateTimeSlots();
        }
    }

    private void removeAllPlayersOfTeam(final List<Player> mPlayersArr, final ArrayList<Team> teamArrayList
            , final int slotPosition, final int iTeamsPerSlot
            , final int iTeamPosition, final int iSlotIdx
            , final int iPlayerCount, final int actionCallFromRemove) {

        Team mTeamInstance = new Team();
        mTeamInstance.setZoneId(teamArrayList.get(iTeamPosition).getZoneId());
        mTeamInstance.setSlotIdx(iSlotIdx);
        mTeamInstance.setTeamIdx(iTeamPosition);

        mTeamInstance.setTeamName("(Free)");
        mTeamInstance.setEntryFee((double) 0);

        for (int iPlayer = 0; iPlayer < mPlayersArr.size(); iPlayer++) {
            if (teamArrayList.get(iTeamPosition).getPlayers().get(iPlayer).getMemberId()
                    .equalsIgnoreCase(getMemberId())) {
                newCompEntryData.setSelfAlreadyAdded(false);
            }
        }

        List<Player> mPlayerList = new ArrayList<>();
        mTeamInstance.setPlayers(mPlayerList);
        //teams.get(iAddPlayerPosition).setPlayers(mPlayerList);
        teamArrayList.set(iTeamPosition, mTeamInstance);

        mEntryFee -= newCompEntryData.getEntryFee();
        newCompEntryData.setMaxTeamAdded((newCompEntryData.getMaxTeamAdded() - 1));

        newCompEntryData.getZones().get(iZoneNo).getSlots()
                .get(iSlotIdx).getTeams().set(iTeamPosition, mTeamInstance);

        newCompEntryData.getZones().get(iZoneNo).setiAlreadyBookSlotIdx(-1);

        updateTimeSlots();
    }

    private void updateNewCompEntryUI() {

        tvZoneName.setText(newCompEntryData.getZones().get(iZoneNo).getZoneName());
        tvOccupancy.setText(("Free Spaces Available : " + newCompEntryData.getZones().get(iZoneNo).getFreePlaces()));

        tvTitleOfComp.setText(newCompEntryData.getEventName());
        tvTimeOfComp.setText(newCompEntryData.getEventStartDate().getFullDateStr());

        flSingleZoneGroup.setOnClickListener(mZoneExpandListener);
        ivExpandCompZone.setOnClickListener(mZoneExpandListener);

        updateTimeAndTitleOfZone(newCompEntryData.getZones().get(iZoneNo).getZoneName());

        btConfirmTimeBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isAnyChange) {
                    Intent intent = new Intent(CompetitionEntryActivity.this, ConfirmBookingEntryActivity.class);
                    intent.putExtra("mEntryFee", mEntryFee);
                    intent.putExtra("iZoneNo", iZoneNo);
                    intent.putExtra("strZoneName", tvZoneName.getText().toString());
                    intent.putExtra("RESPONSE_GET_CLUBEVENT_ENTRY_DATA", newCompEntryData/*new Gson().toJson(newCompEntryData)*/);
                    Bundle informacion = new Bundle();
                    informacion.putSerializable("mapAllPlayer", (Serializable) mapAllPlayer);
                    intent.putExtras(informacion);
                    startActivityForResult(intent, RESULT_CODE_CONFIRM_BOOKING);
                } else {
                    showAlertMessageOk(ACTION_TYPE_CONFIRM_BOOKING
                            , getString(R.string.error_no_entry_changes)
                            , getString(R.string.alert_title_alert));
                }
            }
        });

        updateTimeSlots();
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
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    private String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements this method to get TEE time slots for book
     * paid Competitions.
     */
    private void updateTimeSlots() {

        if (newCompEntryData.getZones().get(iZoneNo).getSlots() == null) {
            return;
        }

        compSlotsList = newCompEntryData.getZones().get(iZoneNo).getSlots();

        allEligiblePlayers.clear();

        for (int iSlotCount = 0; iSlotCount < compSlotsList.size(); iSlotCount++) {

            int teamSize = newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot();

            for (int jTeamCount = 0; jTeamCount < teamSize; jTeamCount++) {

                List<Player> mPlayerArr = compSlotsList.get(iSlotCount)
                        .getTeams().get(jTeamCount).getPlayers();

                for (int iPlayerCount = 0; iPlayerCount < mPlayerArr.size(); iPlayerCount++) {

                    Integer iMemberID = Integer.parseInt(mPlayerArr.get(iPlayerCount).getMemberId());

                    if (mapAllPlayer.containsKey(iMemberID)) {

                        EligibleMember eligibleMember = new EligibleMember(true,
                                iMemberID);
                        allEligiblePlayers.add(eligibleMember);

                        compSlotsList.get(iSlotCount)
                                .getTeams().get(jTeamCount).setAlreadyBooked(true);

                        /**
                         * Also check EntryStatus equal to
                         * 0, If not booked
                         * 1, if booked by someone else
                         * 2, If booked by itself
                         */
                        compSlotsList.get(iSlotCount).getTeams()
                                .get(jTeamCount).setEntryStatus(mapAllPlayer.get(iMemberID)
                                .getEntryStatus());

                    }
                }
            }
        }

        try {
            gvTimeSlots.setExpanded(true);
            compTimeSlotsAdapter = new CompTimeSlotsAdapter(CompetitionEntryActivity.this,
                    compSlotsList,
                    iSlotNo,
                    newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot(),
                    newCompEntryData.getMaxTeamAdded(),
                    newCompEntryData.getMaxTeamCount(),
                    newCompEntryData.getZones().get(iZoneNo).getiAlreadyBookSlotIdx(),
                    newCompEntryData.getTeamSize(),
                    CompetitionEntryActivity.this);
            gvTimeSlots.setAdapter(compTimeSlotsAdapter);

        } catch (
                Exception e)

        {
            Log.e(LOG_TAG, "Exception " + e.toString());
            reportRollBarException(CompetitionEntryActivity.class.getSimpleName(), e.toString());
        }
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
            newCompEntryData.setSelfAlreadyAdded(true);
        }

        if (mapAllPlayer.containsKey(iMemberID)) {
            return mapAllPlayer.get(iMemberID).getNameAndHandicap();
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
    private void updateMemberDetailInSlots(/*int iMemberPosition, */EligibleMember eligibleMemberInstance,
                                           ArrayList<Team> mTeam, int iSlotPosition, int iTeamPerSlot, int iAddPlayerPosition) {

        if (iAddPlayerPosition <= mTeam.size()) {
            Team mTeamInstance = new Team();
            mTeamInstance.setZoneId(mTeam.get(iAddPlayerPosition).getZoneId());
            mTeamInstance.setSlotIdx(mTeam.get(iAddPlayerPosition).getSlotIdx());
            mTeamInstance.setTeamIdx(mTeam.get(iAddPlayerPosition).getTeamIdx());

            int memberID = eligibleMemberInstance/*.get(iMemberPosition)*/.getMemberID();
            mTeamInstance.setTeamName(getMemberNameFromID(memberID));
            mTeamInstance.setEntryFee((double) mapAllPlayer.get(memberID).getEntryFee());
            // mTeamInstance.setEntryFee((double) newCompEntryData.getEntryFee());

            List<Player> mPlayerList = new ArrayList<>();
            Player mPlayer = new Player();
            mPlayer.setMemberId("" + eligibleMemberInstance/*.get(iMemberPosition)*/.getMemberID());
            mPlayer.setIsGuest(false);
            mPlayerList.add(mPlayer);

            mTeamInstance.setAnyUpdated(true);

            mTeamInstance.setPlayers(mPlayerList);
            mTeam.set(iAddPlayerPosition, mTeamInstance);

            mEntryFee += newCompEntryData.getEntryFee();
            newCompEntryData.setMaxTeamAdded(++iMaxTeamAdded);
            newCompEntryData.getZones().get(iZoneNo).getSlots().get(iSlotPosition).setTeams(mTeam);
        }
    }

    /**
     * Implements this method to Remove Member from ArrayList.
     *
     * @param iMemberID
     */
    public void removeMemberFromSelectedList(int iMemberID) {

        int iCounter;
        for (iCounter = 0; iCounter < allEligiblePlayers.size(); iCounter++) {

            int jCounteMemberID = allEligiblePlayers.get(iCounter).getMemberID();

            if (iMemberID == jCounteMemberID) {
                allEligiblePlayers.remove(iCounter);
                break;
            }
        }
    }

    /**
     * Alert user if he/she doesn't make any
     * change and tap on Cofirm Entry.
     */
    public void showAlertMessageOk(final int actionType, String strMessage, String strTitle) {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle(strTitle);
            builder.setCancelable(false);
            builder.setMessage(strMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            switch (actionType) {
                                case ACTION_TYPE_CONFIRM_BOOKING:

                                    break;

                                case ACTION_TYPE_ADD_SELF_ONLY:
                                    updateTimeSlots();
                                    break;

                                case ACTION_TYPE_ADD_WITH_MEMBERS:

                                    startActivityForResult(intent, RESULT_CODE_ADD_MORE_MEMBERS);
                                    break;

                                case ACTION_TYPE_ADD_PER_TEAMSIZE:

                                    startActivityForResult(intent, RESULT_CODE_ADD_PER_TEAMSIZE_4);
                                    break;
                            }

                            builder = null;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Handle Alert dialog STAY/LEAVE
     * here.
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    isAnyChange = false;
                    onBackPressed();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //Cancel button clicked
                    break;
            }
        }
    };

    /**
     * If @isAnyChange TRUE, show Stay/Cancel Entry
     * Alert message.
     */
    private void checkAnyUpdate() {

        if (isAnyChange) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CompetitionEntryActivity.this);
            builder.setTitle("Alert")
                    .setMessage(getResources().getString(R.string.text_alert_cancel_entry))
                    .setPositiveButton("Cancel entry", dialogClickListener)
                    .setNegativeButton("Stay", dialogClickListener)
                    .setCancelable(false).show();
        }
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
