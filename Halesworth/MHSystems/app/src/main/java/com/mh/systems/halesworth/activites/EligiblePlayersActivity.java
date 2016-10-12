package com.mh.systems.halesworth.activites;

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
import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.constants.ApplicationGlobal;
import com.mh.systems.halesworth.constants.WebAPI;
import com.mh.systems.halesworth.fragments.EligibleFriendsFragment;
import com.mh.systems.halesworth.fragments.EligibleMemberFragment;
import com.mh.systems.halesworth.fragments.EligiblePlayersTabFragment;
import com.mh.systems.halesworth.fragments.MembersTabFragment;
import com.mh.systems.halesworth.models.competitionsEntry.AJsonParamsEligiblePlayers;
import com.mh.systems.halesworth.models.competitionsEntry.CompEligiblePlayersAPI;
import com.mh.systems.halesworth.models.competitionsEntry.CompEligiblePlayersResponse;
import com.mh.systems.halesworth.models.competitionsEntry.EligibleMember;
import com.mh.systems.halesworth.util.API.WebServiceMethods;
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

    String strEventId;

    /**
     * iTeamSize is the size of Members that user can select whereas iTotalAddedMembers is the number
     * of Members that can be selected more.
     */
    public static int iTeamSize, iTotalAddedMembers;

    private int iEntryID = 0;

    ArrayList<EligibleMember> selectedMemberList = new ArrayList<>();
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

    //List of type books this list will store type Book which is our data model
    private CompEligiblePlayersAPI compEligiblePlayersAPI;
    AJsonParamsEligiblePlayers aJsonParamsEligiblePlayers;

    ArrayList<EligibleMember> eligibleMemberArrayList = new ArrayList<>();

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
        setStrEventId(getIntent().getExtras().getString("COMPETITIONS_eventId"));

        iTeamSize = getIntent().getExtras().getInt("COMPETITIONS_TeamSize");
        iEntryID = getIntent().getExtras().getInt("COMPETITIONS_iEntryID");

        //Get previous Member list if already some member selected.
        selectedMemberList = (ArrayList<EligibleMember>) getIntent().getSerializableExtra("MEMBER_LIST");

        for (int iCount = 0; iCount < selectedMemberList.size(); iCount++) {
            iselectedMemberList.add(selectedMemberList.get(iCount).getMemberID());
        }

        //iEntryID 0 means first time Entry.
        if (iEntryID == 0) {
            iTotalAddedMembers = Math.abs(selectedMemberList.size() - (iTeamSize - 1));
        } else {
            iTotalAddedMembers = iTeamSize - selectedMemberList.size();
        }

        tvAddPlayerDesc.setText("You can add " + iTotalAddedMembers + " more players");

        /**
         *  If user back press on any other tab then app should
         *  open first tab by default when opening 'MEMBERS'.
         */
        MembersTabFragment.iLastTabPosition = 0;

        //Load Default fragment of Members Activity.
        if (isOnline(EligiblePlayersActivity.this)) {
            //Method to hit Members list API.
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
                Bundle informacion = new Bundle();
                informacion.putSerializable("MEMBER_LIST", selectedMemberList);
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
    public String getStrEventId() {
        return strEventId;
    }

    /**
     * @param strEventId The strEventId
     */
    public void setStrEventId(String strEventId) {
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
     * @param iMemberID
     */
    public void addMemberToList(EligibleMember eligibleMember) {
        if (iTotalAddedMembers >= 0) {
            selectedMemberList.add(eligibleMember);
            iselectedMemberList.add(eligibleMember.getMemberID());
            --iTotalAddedMembers;
        }
        tvAddPlayerDesc.setText("You can add " + iTotalAddedMembers + " more players");
    }

    /**
     * Implements this method to Remove Member from ArrayList.
     *
     * @param eligibleMember
     */
    public void removeMemberFromList(EligibleMember eligibleMember) {

        int iCounter;
        for (iCounter = 0; iCounter < selectedMemberList.size(); iCounter++) {

            int selectedMemberId = eligibleMember.getMemberID();
            int jCounteMemberID = selectedMemberList.get(iCounter).getMemberID();

            if (selectedMemberId == jCounteMemberID) {
                selectedMemberList.remove(iCounter);
                iselectedMemberList.remove(iCounter);
                ++iTotalAddedMembers;
                break;
            }
        }
        tvAddPlayerDesc.setText("You can add " + iTotalAddedMembers + " more players");
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
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<CompEligiblePlayersResponse>() {
        }.getType();
        compEligiblePlayersResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        eligibleMemberArrayList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (compEligiblePlayersResponse.getMessage().equalsIgnoreCase("Success")) {
                eligibleMemberArrayList.addAll(compEligiblePlayersResponse.getData().getEligibleMembers());

                if (eligibleMemberArrayList.size() == 0) {
                    updateNoDataUI(false, 0);
                    // ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {
                    updateNoDataUI(true, 0);

                    updateFragment(eligiblePlayersTabFragment);
                }
            } else {
                updateNoDataUI(false, 0);
                //If web service not respond in any case.
                // ((BaseActivity) getActivity()).showAlertMessage(membersItems.getMessage());
            }
            //Dismiss progress dialog.
            // hideProgress();
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /* ++++++++++++++++++++++++ END OF GET ELIGIBLE PLAYER WEB SERVICE ++++++++++++++++++++++++ */

    /**
     * Implements method to GET-ELIGIBLE Members and Players and pass
     * to the selected tab according to position.
     *
     * @param iPos : 0 means Members tab and 1 for Friends tab
     */
    public ArrayList<EligibleMember> getEligibleMemberList(int iPos) {

        ArrayList<EligibleMember> eligibleMemberArrayList = new ArrayList<>();
        eligibleMemberArrayList.clear();

        switch (iPos) {
            case 0:
                eligibleMemberArrayList.addAll(compEligiblePlayersResponse.getData().getEligibleMembers());
                break;

            case 1:
                eligibleMemberArrayList.addAll(compEligiblePlayersResponse.getData().getEligibleFriends());
                break;
        }
        return eligibleMemberArrayList;
    }
}
