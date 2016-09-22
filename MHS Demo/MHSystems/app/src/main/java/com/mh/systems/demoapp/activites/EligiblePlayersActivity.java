package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.fragments.EligibleFriendsFragment;
import com.mh.systems.demoapp.fragments.EligibleMemberFragment;
import com.mh.systems.demoapp.fragments.EligiblePlayersTabFragment;
import com.mh.systems.demoapp.fragments.MembersTabFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create {@link EligiblePlayersActivity} is used to display tabs named MEMBERS and FRIENDS
 * for Booking/Entry an Competition called from {@link CompetitionEntryActivity}
 */
public class EligiblePlayersActivity extends BaseActivity {

    String strEventId;

    /**
     * iTeamSize is the size of Members that user can select whereas iTotalAddedMembers is the number
     * of Members that can be selected more.
     */
    public static int iTeamSize, iTotalAddedMembers;

    //Used this {@link ArrayList} to record of Selected Member list.
    ArrayList<Integer> selectedMemberList = new ArrayList<>();

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

    Intent intent;

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

        //Set Event Id.
        setStrEventId(getIntent().getExtras().getString("COMPETITIONS_eventId"));

        iTeamSize = getIntent().getExtras().getInt("COMPETITIONS_TeamSize");
        iTotalAddedMembers = iTeamSize;
        tvAddPlayerDesc.setText("You can add " + iTotalAddedMembers + " more players");

        /**
         *  If user back press on any other tab then app should
         *  open first tab by default when opening 'MEMBERS'.
         */
        MembersTabFragment.iLastTabPosition = 0;

        //Load Default fragment of Members Activity.
        updateFragment(new EligiblePlayersTabFragment(ApplicationGlobal.ACTION_MEMBERS_ALL));
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
                intent.putIntegerArrayListExtra("NAME_OF_MEMBER", selectedMemberList);
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
    public void updateNoDataUI(boolean hasData) {
        if (hasData) {
            showNoMemberView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
        } else {
            showNoMemberView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
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
    }

    /**
     * @return fragmentInstance
     */
    public Fragment getFragmentInstance() {
        return fragmentInstance;
    }

    /**
     * Send Selected Member name for paid Competition entry.
     */
    public void passMemberName(String strNameOfMember) {
        Intent intent = new Intent(EligiblePlayersActivity.this, CompetitionEntryActivity.class);
        intent.putExtra("NAME_OF_MEMBER", strNameOfMember);
        setResult(RESULT_OK, intent);
        finish();
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
     * Implements this method to Add Member to ArrayList.
     */
    public void addMemberToList(int iMemberID) {
        selectedMemberList.add(iMemberID);
        tvAddPlayerDesc.setText("You can add " + --iTotalAddedMembers + " more players");
    }

    /**
     * Implements this method to Remove Member from ArrayList.
     */
    public void removeMemberFromList(int iMemberID) {
        //selectedMemberList.remove(iMemberID);
        tvAddPlayerDesc.setText("You can add " + ++iTotalAddedMembers + " more players");
    }
}
