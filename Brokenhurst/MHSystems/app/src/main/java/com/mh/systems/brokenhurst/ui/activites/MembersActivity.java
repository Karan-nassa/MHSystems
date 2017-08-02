package com.mh.systems.brokenhurst.ui.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mh.systems.brokenhurst.R;
import com.mh.systems.brokenhurst.utils.constants.ApplicationGlobal;
import com.mh.systems.brokenhurst.ui.fragments.FriendsFragment;
import com.mh.systems.brokenhurst.ui.fragments.MembersFragment;
import com.mh.systems.brokenhurst.ui.fragments.MembersTabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MembersActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    String straFriendCommand = "GETLINKSTOMEMBERS";

    /**
     * This instance is used to identify and set {@link android.support.design.widget.FloatingActionButton} VISIBLE
     * if 1 [YOUR FRIENDS], selected in spinner and disable for 2 [ADDED ME]
     */
    int iWhichItem = 1;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.toolBarMembers)
    Toolbar toolBarMembers;

    @Bind(R.id.llHomeMembers)
    LinearLayout llHomeMembers;

    @Bind(R.id.llPopMenuBar)
    LinearLayout llPopMenuBar;

    @Bind(R.id.llMemberCategory)
    LinearLayout llMemberCategory;

    @Bind(R.id.tvMemberType)
    TextView tvMemberType;

     /* ++ INTERNET CONNECTION PARAMETERS ++ */

    @Bind(R.id.inc_message_view)
    RelativeLayout inc_message_view;

    @Bind(R.id.ivMessageSymbol)
    ImageView ivMessageSymbol;

    @Bind(R.id.tvMessageTitle)
    TextView tvMessageTitle;

    @Bind(R.id.tvMessageDesc)
    TextView tvMessageDesc;

    //Pop Menu to show Categories of Course Diary.
    PopupMenu popupMenu;

     /* -- INTERNET CONNECTION PARAMETERS -- */

    /**
     * Instance of {@link Fragment} used for separate result
     * of {@link MembersFragment} and {@link FriendsFragment} list data.
     */
    Fragment fragmentInstance;

    /**
     * Keep track of tab position to display
     * info or contact us icon.
     */
    private int iTabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        //Initialize view resources.
        ButterKnife.bind(this);

        if (toolBarMembers != null) {
            setSupportActionBar(toolBarMembers);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        /**
         *  If user back press on any other tab then app should
         *  open first tab by default when opening 'MEMBERS'.
         */
        MembersTabFragment.iLastTabPosition = 0;

        initializeMembersCategory();

        /**
         *  Setup Tool bar of Members screen with DROP-DOWN [SPINNER]
         *  and SEARCH bar icon.
         */
        //setupToolBar();

        //Load Default fragment of Members Activity.
        updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_ALL));

        //Set click listener events declaration.
        llHomeMembers.setOnClickListener(mHomePressListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /**
         *  CANCEL all tasks.
         */
        if (MembersFragment.mAdapter != null) {
            MembersFragment.mAdapter.mAsyncTaskThreadPool.cancelAllTasks(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.menu_friends, menu);

        switch (getiTabPosition()) {
            case 0:
                //getMenuInflater().inflate(R.menu.menu_members, menu);
                menu.getItem(1).setVisible(false);
                break;

            case 1:
                menu.getItem(1).setVisible(true);
                break;

            case 2:
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
                break;
        }

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                llPopMenuBar.setVisibility(View.INVISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //DO SOMETHING WHEN THE SEARCHVIEW IS CLOSING
                llPopMenuBar.setVisibility(View.VISIBLE);
                return true;
            }
        });

//        searchView.setBackground(ContextCompat.getDrawable(MembersActivity.this, R.drawable.ic_search_bar_pressed));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                //  Log.e("onQueryTextSubmit", query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                // Log.e("onQueryTextChange", newText);
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
            case R.id.action_search:
                String url = null;

                if (url == null)
                    return true;
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
                return true;

            case R.id.action_info:
                Intent infoIntent = new Intent(MembersActivity.this, FriendsInfoActivity.class);
                startActivity(infoIntent);
                //showAlertInfo();
                return true;

           /* case R.id.action_contact_us:
                startActivity(new Intent(MembersActivity.this, ContactUsFragment.class));
                return true;*/
        }
        return false;
    }

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


    /**
     * Declares the click event handling FIELD to set categories
     * of COURSE DIARY.
     */
    private PopupMenu.OnMenuItemClickListener mCourseTypeListener =
            new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    tvMemberType.setText(item.getTitle());
                    switch (item.getItemId()) {
                        case R.id.item_all:
                            updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_ALL));
                            break;

                        case R.id.item_ladies:
                            updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_LADIES));
                            break;

                        case R.id.item_gents:
                            updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_GENTLEMENS));
                            break;

                        case R.id.item_your_friends:
                            setStraFriendCommand("GETLINKSTOMEMBERS");
                            setiWhichSpinnerItem(1);
                            //Initially display title at position 0 of R.menu.course_menu.
//                            tvMemberType.setText("" + popupMenu.getMenu().getItem(0));
                            updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_FRIENDS_YOUR_FRIENDS));
                            break;

                        case R.id.item_added_me:
                            setStraFriendCommand("GETLINKSFROMMEMBERS");
                            setiWhichSpinnerItem(2);
                            //Initially display title at position 0 of R.menu.course_menu.
//                            tvMemberType.setText("" + popupMenu.getMenu().getItem(1));
                            updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_FRIENDS_ADDED_ME));
                            break;
                    }
                    return true;
                }
            };

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
     * @param hasData      : True means more than 1 data.
     * @param iTabPosition : Which tab Members or friends
     */
    public void updateNoDataUI(boolean hasData, int iTabPosition) {
        if (hasData) {
            showNoMemberView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true, iTabPosition);
        } else {
            showNoMemberView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false, iTabPosition);
        }
    }

    /**
     * Implements a method to define SPINNER and
     * SEARCH bar icon.
     */
    /*private void setupToolBar() {
       *//* spinner_nav = (Spinner) findViewById(R.id.spinner_nav);*//*

        refreshSpinnerItems();
    }*/

    /**
     * Implements a method to initialize Members categories in pop-up menu like
     * <b>All</b> and <b>Ladies</b> for Sunningdales golf club.
     */
    public void initializeMembersCategory() {

        if (MembersTabFragment.iLastTabPosition == 2) {

            llMemberCategory.setVisibility(View.INVISIBLE);

        } else {

            llMemberCategory.setVisibility(View.VISIBLE);

            /**
             * Step 1: Create a new instance of popup menu
             */
            popupMenu = new PopupMenu(this, tvMemberType);

            /**
             * Step 2: Inflate the menu resource. Here the menu resource is
             * defined in the res/menu project folder
             */
            switch (MembersTabFragment.iLastTabPosition) {
                case 0:
                    popupMenu.inflate(R.menu.members_menu);
                    updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_ALL));
                    break;

                case 1:
                    popupMenu.inflate(R.menu.members_friends_menu);
                    setStraFriendCommand("GETLINKSTOMEMBERS");
                    setiWhichSpinnerItem(1);
                    updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_FRIENDS_YOUR_FRIENDS));
                    break;
            }

            //Initially display title at position 0 of R.menu.course_menu.
            tvMemberType.setText(("" + popupMenu.getMenu().getItem(0)));

            llMemberCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });
            popupMenu.setOnMenuItemClickListener(mCourseTypeListener);
        }
    }

    /**
     * Implements a method to Quick search bar
     * in tool bar.
     */
    public void performSearch(final String queryText) {

        switch (getiTabPosition()) {
            case 0:
                if (MembersFragment.mAdapter != null) {
                    MembersFragment.mAdapter.getFilter().filter(queryText);
                    MembersFragment.mAdapter.setHeaderViewVisible(TextUtils.isEmpty(queryText));
                    MembersFragment.mAdapter.notifyDataSetChanged();
                }
                break;

            case 1: {
                if (FriendsFragment.mAdapter != null) {
                    FriendsFragment.mAdapter.getFilter().filter(queryText);
                    FriendsFragment.mAdapter.setHeaderViewVisible(TextUtils.isEmpty(queryText));
                    FriendsFragment.mAdapter.notifyDataSetChanged();
                }
            }
            break;
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
     * Set the current selected {@link String} FRIEND command.
     *
     * @param straFriendCommand
     */
    public void setStraFriendCommand(String straFriendCommand) {
        this.straFriendCommand = straFriendCommand;
    }

    /**
     * @return fragmentInstance
     */
    public String getStraFriendCommand() {
        return straFriendCommand;
    }

    /**
     * @return iWhichSpinnerItem
     */
    public int getiWhichSpinnerItem() {
        return iWhichItem;
    }

    /**
     * @param iWhichItem The iWhichItem
     */
    public void setiWhichSpinnerItem(int iWhichItem) {
        this.iWhichItem = iWhichItem;
    }


    /* +++++++++++++++++++++++++ HOLD TAB POSITION FOR CONTACT US & INFO POP-UP  +++++++++++++++++++++++++ */

    public int getiTabPosition() {
        return iTabPosition;
    }

    public void setiTabPosition(int iTabPosition) {
        this.iTabPosition = iTabPosition;
        invalidateOptionsMenu();
        llPopMenuBar.setVisibility(View.VISIBLE);
    }

    /* +++++++++++++++++++++++++ HOLD TAB POSITION FOR CONTACT US & INFO POP-UP  +++++++++++++++++++++++++ */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1112) {

            if (data != null) {

                /**
                 * Refresh Friend list after remove
                 * Friend.
                 */
                boolean isDeleted = data.getBooleanExtra("isDeleted", false);
                if (isDeleted) {

                    if (fragmentInstance instanceof FriendsFragment) {
                        setStraFriendCommand("GETLINKSTOMEMBERS");
                        setiWhichSpinnerItem(1);
                        updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_FRIENDS_YOUR_FRIENDS));
                    }
                }
            }
        }
    }
}
