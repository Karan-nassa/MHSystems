package com.mh.systems.demoapp.ui.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.fragments.FriendsFragment;
import com.mh.systems.demoapp.ui.fragments.MembersFragment;
import com.mh.systems.demoapp.utils.constants.ApplicationGlobal;
import com.mh.systems.demoapp.web.api.WebAPI;
import com.mh.systems.demoapp.ui.fragments.EligibleFriendsFragment;
import com.mh.systems.demoapp.ui.fragments.EligibleMemberFragment;
import com.mh.systems.demoapp.ui.fragments.EligiblePlayersTabFragment;
import com.mh.systems.demoapp.ui.fragments.MembersTabFragment;
import com.mh.systems.demoapp.web.models.competitionsentry.AJsonParamsEligiblePlayers;
import com.mh.systems.demoapp.web.models.competitionsentry.CompEligiblePlayersAPI;
import com.mh.systems.demoapp.web.models.competitionsentry.CompEligiblePlayersData;
import com.mh.systems.demoapp.web.models.competitionsentry.CompEligiblePlayersResponse;
import com.mh.systems.demoapp.web.models.competitionsentry.EligibleMember;
import com.mh.systems.demoapp.web.api.WebServiceMethods;
import com.mh.systems.demoapp.web.models.competitionsentry.NameRecord;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Create {@link EligiblePlayersActivity} is used to display tabs named MEMBERS and FRIENDS
 * for Booking/Entry an Competition called from {@link CompetitionEntryActivity}
 */
public class EligiblePlayersActivity extends BaseActivity {

    private final String LOG_TAG = EligiblePlayersActivity.class.getSimpleName();

    int strEventId;
    public static int iFreeSlotsAvail;
    boolean isSlefAlreadyAdded = false;

    int iSelfMemberID;

    /**
     * iTeamSize is the size of Members that user can select whereas iTotalAddedMembers is the number
     * of Members that can be selected more.
     */
    public static int iTeamSize;//iTotalAddedMembers;

    private int iEntryID = 0;

    ArrayList<EligibleMember> selectedMemberList = new ArrayList<>();
    ArrayList<EligibleMember> SlotsEligiblePlayers = new ArrayList<>();
    public ArrayList<Integer> iselectedMemberList = new ArrayList<>();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.toolBarMembers)
    Toolbar toolBarMembers;

    @Bind(R.id.llBottomDesc)
    LinearLayout llBottomDesc;

    @Bind(R.id.tvAddPlayerDesc)
    TextView tvAddPlayerDesc;

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

    /**
     * Instance of {@link Fragment} used for separate result
     * of {@link MembersFragment} and {@link FriendsFragment} list data.
     */
    Fragment fragmentInstance;
    EligiblePlayersTabFragment eligiblePlayersTabFragment = null;

    Intent intent;

    //Create instance of Model class MembersItems.
    CompEligiblePlayersResponse compEligiblePlayersResponse;
    CompEligiblePlayersData compEligiblePlayersData;

    //List of type books this list will store type Book which is our data model
    private CompEligiblePlayersAPI compEligiblePlayersAPI;
    AJsonParamsEligiblePlayers aJsonParamsEligiblePlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligible_players);

        //Initialize view resources.
        ButterKnife.bind(this);

        if (toolBarMembers != null) {
            setSupportActionBar(toolBarMembers);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        eligiblePlayersTabFragment = new EligiblePlayersTabFragment();

        //Set Event Id.
        setStrEventId(getIntent().getExtras().getInt("EventID"));

        iTeamSize = getIntent().getExtras().getInt("TeamsPerSlot");
        iEntryID = getIntent().getExtras().getInt("COMPETITIONS_iEntryID");
        iFreeSlotsAvail = getIntent().getExtras().getInt("iFreeSlotsAvail");
        isSlefAlreadyAdded = getIntent().getExtras().getBoolean("isSlefAlreadyAdded");

        //Get previous Member list if already some member selected.
        selectedMemberList = (ArrayList<EligibleMember>) getIntent().getSerializableExtra("AllPlayers");
        SlotsEligiblePlayers = (ArrayList<EligibleMember>) getIntent().getSerializableExtra("SlotsEligiblePlayers");

        for (int iCount = 0; iCount < selectedMemberList.size(); iCount++) {
            iselectedMemberList.add(selectedMemberList.get(iCount).getMemberID());
        }

        //iEntryID 0 means first time Entry.
        /*if (iEntryID == 0) {
            iTotalAddedMembers = Math.abs(selectedMemberList.size() - (iTeamSize - 1));
        } else {
            iTotalAddedMembers = iTeamSize - selectedMemberList.size();
        }*/

        //iTotalAddedMembers = iTeamSize - selectedMemberList.size();

        /**
         *  If user back press on any other tab then app should
         *  open first tab by default when opening 'MEMBERS'.
         */
        MembersTabFragment.iLastTabPosition = 0;

        //Load Default fragment of Members Activity.
        if (isOnline(EligiblePlayersActivity.this)) {
            //Method to hit Members list api.
            requestEligibleMemberService();
            updateNoInternetUI(true);
        } else {
            updateNoInternetUI(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /**
         *  CANCEL all tasks.
         */
        if (EligibleMemberFragment.mAdapter != null) {
            EligibleMemberFragment.mAdapter.mAsyncTaskThreadPool.cancelAllTasks(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_members_booking, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                performSearch(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_done:
                intent = new Intent(EligiblePlayersActivity.this, CompetitionEntryActivity.class);
                intent.putExtra("slotPosition", getIntent().getExtras().getInt("slotPosition"));
                intent.putExtra("iTeamPerSlot", getIntent().getExtras().getInt("iTeamPerSlot"));
                intent.putExtra("iAddPlayerPosition", getIntent().getExtras().getInt("iAddPlayerPosition"));
                Bundle informacion = new Bundle();
                informacion.putSerializable("MEMBER_LIST", selectedMemberList);
                informacion.putSerializable("SlotsEligiblePlayers", SlotsEligiblePlayers);
                informacion.putSerializable("teams", (ArrayList<EligibleMember>) getIntent().getSerializableExtra("teams"));
                intent.putExtras(informacion);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.action_search:
                String url = null;

                if (url == null)
                    return true;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
                return true;
        }
        return false;
    }

    /**
     * Implements a method to update UI when 'No Internet connection'
     * when disconnect internet connection.
     *
     * @param isOnline : True means internet working fine.
     */
    public void updateNoInternetUI(boolean isOnline) {
        if (isOnline) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            //inc_noInternet.setVisibility(View.GONE);
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
            //inc_noInternet.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Implements a method to update UI when 'No Competitions'.
     *
     * @param hasData : True means more than 1 data.
     */
    public void updateNoDataUI(boolean hasData, int iTabPosition) {
        if (hasData) {
            showNoMemberView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true, iTabPosition);
        } else {
            showNoMemberView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false, iTabPosition);
        }
    }

    /**
     * Implements a method to Quick search bar
     * in tool bar.
     */
    public void performSearch(final String queryText) {

        if (getFragmentInstance() instanceof EligibleMemberFragment) {
            EligibleMemberFragment.mAdapter.getFilter().filter(queryText);
            EligibleMemberFragment.mAdapter.setHeaderViewVisible(TextUtils.isEmpty(queryText));
            EligibleMemberFragment.mAdapter.notifyDataSetChanged();
            // Log.e(LOG_TAG, "mAdapter : " + EligibleMemberFragment.mAdapter);

            /*FragmentManager fm = getSupportFragmentManager();

            //if you added fragment via layout xml
            EligiblePlayersTabFragment fragment = (EligiblePlayersTabFragment) fm.findFragmentById(R.id.containerView);
            fragment.fragVisible();
            Log.e(LOG_TAG, "fragment : " + fragment);*/

        } else if (getFragmentInstance() instanceof EligibleFriendsFragment) {
            EligibleFriendsFragment.mAdapter.getFilter().filter(queryText);
            EligibleFriendsFragment.mAdapter.setHeaderViewVisible(TextUtils.isEmpty(queryText));
            EligibleFriendsFragment.mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Set the current opening {@link Fragment} instance.
     *
     * @param fragmentInstance
     */
    public void setFragmentInstance(Fragment fragmentInstance) {
        this.fragmentInstance = fragmentInstance;
        Log.e(LOG_TAG, "fragmentInstance : " + fragmentInstance);
    }

    /**
     * @return fragmentInstance
     */
    public Fragment getFragmentInstance() {
        return fragmentInstance;
    }

    /**
     * @return The strEventId
     */
    public int getStrEventId() {
        return strEventId;
    }

    /**
     * @param strEventId The strEventId
     */
    public void setStrEventId(int strEventId) {
        this.strEventId = strEventId;
    }

    /**
     * @return The selectedMemberList
     */
    public ArrayList<EligibleMember> getSelectedMemberList() {
        return this.selectedMemberList;
    }

    /**
     * @param selectedMemberList The selectedMemberList
     */
    public void setSelectedMemberList(ArrayList<EligibleMember> selectedMemberList) {
        this.selectedMemberList = selectedMemberList;
    }

    /**
     * Implements this method to Add Member to ArrayList.
     *
     * @param eligibleMember
     */
    public void addMemberToList(EligibleMember eligibleMember) {
        if (/*iTotalAddedMembers*/iFreeSlotsAvail >= 0) {
            selectedMemberList.add(eligibleMember);
            SlotsEligiblePlayers.add(eligibleMember);
            iselectedMemberList.add(eligibleMember.getMemberID());
            //--iTotalAddedMembers;
            --iFreeSlotsAvail;
        }
        tvAddPlayerDesc.setText("You can add " + iFreeSlotsAvail/*iTotalAddedMembers*/ + " more players");
    }

    /**
     * Implements this method to Remove Member from ArrayList.
     *
     * @param eligibleMember
     */
    public void removeMemberFromList(EligibleMember eligibleMember) {

        int iCounter;
        for (iCounter = 0; iCounter < SlotsEligiblePlayers.size(); iCounter++) {

            int selectedMemberId = eligibleMember.getMemberID();
            int jCounteMemberID = SlotsEligiblePlayers.get(iCounter).getMemberID();

            if (selectedMemberId == jCounteMemberID) {
                selectedMemberList.remove(iCounter);
                SlotsEligiblePlayers.remove(iCounter);
                iselectedMemberList.remove(iCounter);
                // ++iTotalAddedMembers;
                ++iFreeSlotsAvail;
                break;
            }
        }
        tvAddPlayerDesc.setText("You can add " + iFreeSlotsAvail/*iTotalAddedMembers*/ + " more players");
    }


    /* ++++++++++++++++++++++++ START OF GET ELIGIBLE PLAYER WEB SERVICE ++++++++++++++++++++++++ */

    /**
     * Implement a method to hit Members web service to get response.
     */
    public void requestEligibleMemberService() {

        showPleaseWait("Loading...");

        aJsonParamsEligiblePlayers = new AJsonParamsEligiblePlayers();
        aJsonParamsEligiblePlayers.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsEligiblePlayers.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsEligiblePlayers.setMemberId(loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784"));
        aJsonParamsEligiblePlayers.setEventId(getStrEventId());

        compEligiblePlayersAPI = new CompEligiblePlayersAPI(getClientId(), "GETCOMPELIGIBLEPLAYERS", aJsonParamsEligiblePlayers, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getEligiblePlayersList(compEligiblePlayersAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateEligiblePlayerListSuccess(jsonObject);
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
     * Implements method to GET-ELIGIBLE Members and Players and pass
     * to the selected tab according to position.
     *
     * @param iPos : 0 means Members tab and 1 for friends tab
     */
    public ArrayList<EligibleMember> getEligibleMemberList(int iPos) {

        ArrayList<EligibleMember> eligibleMemberArrayList = new ArrayList<>();
        ArrayList<EligibleMember> filterEligibleMemberList = new ArrayList<>();
        eligibleMemberArrayList.clear();

        switch (iPos) {
            case 0:
                eligibleMemberArrayList.addAll(compEligiblePlayersResponse.getData().getEligibleMembers());
                break;

            case 1:
                eligibleMemberArrayList.addAll(compEligiblePlayersResponse.getData().getEligibleFriends());
                break;
        }

        for (int iCount = 0; iCount < eligibleMemberArrayList.size(); iCount++) {

            if (!iselectedMemberList.contains(
                    eligibleMemberArrayList.get(iCount).getMemberID())) {
                filterEligibleMemberList.add(eligibleMemberArrayList.get(iCount));
            }
        }

        return filterEligibleMemberList;
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateEligiblePlayerListSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "updateEligiblePlayerListSuccess : " + jsonObject.toString());

        Type type = new TypeToken<CompEligiblePlayersResponse>() {
        }.getType();
        compEligiblePlayersResponse = new Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        //eligibleMemberArrayList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (compEligiblePlayersResponse.getMessage().equalsIgnoreCase("Success")) {

                compEligiblePlayersData = compEligiblePlayersResponse.getData();
               //eligibleMemberArrayList.addAll();

                if (compEligiblePlayersData.getEligibleMembers().size() == 0) {
                    updateNoDataUI(false, 0);
                    // ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));

                } else {
                    updateNoDataUI(true, 0);

                    if (!isSlefAlreadyAdded) {
                        iFreeSlotsAvail--;
                        iSelfMemberID = getIntent().getExtras().getInt("iSelfMemberID");
                        //strSelfMemberName = getIntent().getExtras().getString("strSelfMemberName");
                        EligibleMember eligibleMember = new EligibleMember(true,
                                iSelfMemberID);

                        selectedMemberList.add(eligibleMember);
                        SlotsEligiblePlayers.add(eligibleMember);
                        iselectedMemberList.add(iSelfMemberID);
                    }
                    tvAddPlayerDesc.setText("You can add " + iFreeSlotsAvail + " more players");

                    updateFragment(eligiblePlayersTabFragment);
                }
            } else {
                updateNoDataUI(false, 0);
                hideProgress();
                //If web service not respond in any case.
                // ((BaseActivity) getActivity()).showAlertMessage(membersItems.getMessage());
            }
            //Dismiss progress dialog.
            // hideProgress();
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            reportRollBarException(EligiblePlayersActivity.class.getSimpleName(), e.toString());
        }
    }

    /**
     * Returns the Member display name.
     *
     * @param iSelfMemberID : Pass Member id.
     *//*
    private String getSelfDisplayName(int iSelfMemberID) {

        String strSelfMemberName = "N/A";

        for (int iCount = 0; iCount < eligibleMemberArrayList.size(); iCount++) {
            if (eligibleMemberArrayList.get(iCount).getMemberID() == iSelfMemberID) {
                return eligibleMemberArrayList.get(iCount).getNameRecord().getDisplayName();
            }
        }

        return strSelfMemberName;
    }

    *//**
     * Returns the HCap Value.
     *
     * @param iSelfMemberID : Pass Member id.
     *//*
    private String getSelfHCapValue(int iSelfMemberID) {

        String strSelfMemberName = "N/A";

        for (int iCount = 0; iCount < eligibleMemberArrayList.size(); iCount++) {
            if (eligibleMemberArrayList.get(iCount).getMemberID() == iSelfMemberID) {
                return eligibleMemberArrayList.get(iCount).getHCapTypeStr();
            }
        }

        return strSelfMemberName;
    }
*/
    /* ++++++++++++++++++++++++ END OF GET ELIGIBLE PLAYER WEB SERVICE ++++++++++++++++++++++++ */

}
