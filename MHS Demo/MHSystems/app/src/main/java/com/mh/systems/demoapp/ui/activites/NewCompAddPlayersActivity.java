package com.mh.systems.demoapp.ui.activites;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.fragments.EligibleMemberFragment;
import com.mh.systems.demoapp.utils.constants.ApplicationGlobal;
import com.mh.systems.demoapp.utils.libAlphaIndexing.CircularContactView;
import com.mh.systems.demoapp.utils.libAlphaIndexing.async_task_thread_pool.AsyncTaskEx;
import com.mh.systems.demoapp.utils.libAlphaIndexing.async_task_thread_pool.AsyncTaskThreadPool;
import com.mh.systems.demoapp.web.models.competitionsentry.AJsonParamsEligiblePlayers;
import com.mh.systems.demoapp.web.models.competitionsentry.CompEligiblePlayersAPI;
import com.mh.systems.demoapp.web.models.competitionsentry.CompEligiblePlayersResponse;
import com.mh.systems.demoapp.web.models.competitionsentrynew.AllPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import lb.library.PinnedHeaderListView;
import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;

public class NewCompAddPlayersActivity extends BaseActivity {

    @Bind(R.id.tbAddPlayers)
    Toolbar tbAddPlayers;

    @Bind(R.id.lvAddPlayersList)
    PinnedHeaderListView lvAddPlayersList;

    @Bind(R.id.tvAddPlayerDesc)
    TextView tvAddPlayerDesc;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = EligibleMemberFragment.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    //Create instance of Model class MembersItems.
    CompEligiblePlayersResponse compEligiblePlayersResponse;

    //List of type books this list will store type Book which is our data model
    private CompEligiblePlayersAPI compEligiblePlayersAPI;
    AJsonParamsEligiblePlayers aJsonParamsEligiblePlayers;

    //Members list demo.
    public AlphabaticalListAdapter mAdapter = null;


    /**
     * iTeamSize is the size of Members that user can select whereas iTotalAddedMembers is the number
     * of Members that can be selected more.
     */
    public int iTotalAddedMembers, iTeamSize;

    ArrayList<AllPlayer> allPlayerArrayList = new ArrayList<>();
    public ArrayList<Integer> iselectedMemberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players_new_comp);

        ButterKnife.bind(this);

        if (tbAddPlayers != null) {
            setSupportActionBar(tbAddPlayers);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        iTeamSize = getIntent().getExtras().getInt("TeamsPerSlot");

        //Get previous Member list if already some member selected.
        allPlayerArrayList = (ArrayList<AllPlayer>) getIntent().getSerializableExtra("AllPlayers");

        for (int iCount = 0; iCount < allPlayerArrayList.size(); iCount++) {
            iselectedMemberList.add(allPlayerArrayList.get(iCount).getMemberId());
        }

        //iEntryID 0 means first time Entry.
        /*if (iEntryID == 0) {
            iTotalAddedMembers = Math.abs(selectedMemberList.size() - (iTeamSize - 1));
        } else {
            iTotalAddedMembers = iTeamSize - selectedMemberList.size();
        }*/

        tvAddPlayerDesc.setText("You can add " + iTotalAddedMembers + " more players");


        //eligibleMemberArrayList.clear();
        //eligibleMemberArrayList = ((EligiblePlayersActivity) getActivity()).getEligibleMemberList(0);
        setMembersListAdapter(allPlayerArrayList);
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

            default:
                break;
        }

        return true;
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to get Background theme color
     * <p>
     * <p>
     * to set same on Listview.
     */
    public static int getResIdFromAttribute(final Activity activity, final int attr) {
        if (attr == 0)
            return 0;
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId;
    }

    ////////////////////////////
    // AlphabaticalListAdapter /
    // /////////////////////////
    public class AlphabaticalListAdapter extends SearchablePinnedHeaderListViewAdapter<AllPlayer> {
        private ArrayList<AllPlayer> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        public final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);


        @Override
        public CharSequence getSectionTitle(int sectionIndex) {
            return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
        }

        public AlphabaticalListAdapter(final ArrayList<AllPlayer> contacts) {
            setData(contacts);

            PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE = getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<AllPlayer> contacts) {
            this.mContacts = contacts;
            final String[] generatedContactNames = generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
        }

        private String[] generateContactNames(final List<AllPlayer> contacts) {
            final ArrayList<String> contactNames = new ArrayList<String>();
            if (contacts != null)
                for (final AllPlayer contactEntity : contacts)
                    contactNames.add(contactEntity.getNameAndHandicap());
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            AlphabaticalListAdapter.ViewHolder holder;
            final View rootView;
            final AllPlayer contact;

            holder = new AlphabaticalListAdapter.ViewHolder();

            rootView = LayoutInflater.from(NewCompAddPlayersActivity.this).inflate(R.layout.list_item_alpha_eligible_member, parent, false);

            holder.friendProfileCircularContactView = (CircularContactView) rootView.findViewById(R.id.listview_item__friendPhotoImageView);
            holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);

            holder.friendName = (TextView) rootView.findViewById(R.id.listview_item__friendNameTextView);
            holder.tvPlayHCapStr = (TextView) rootView.findViewById(R.id.tvPlayHCapStr);
            holder.headerView = (TextView) rootView.findViewById(R.id.header_text);

            holder.cbSelectedMember = (CheckBox) rootView.findViewById(R.id.cbSelectedMember);

            rootView.setTag(holder);

            holder = (AlphabaticalListAdapter.ViewHolder) rootView.getTag();
            contact = getItem(position);
            final String displayName = contact.getNameAndHandicap();
            holder.friendName.setText(displayName);
            holder.tvPlayHCapStr.setText("");
            //holder.cbSelectedMember.setChecked(contact.getIsMemberSelected());
            if (iselectedMemberList.contains(contact.getMemberId())) {
                holder.cbSelectedMember.setChecked(true);
            } else {
                holder.cbSelectedMember.setChecked(false);
            }

            holder.cbSelectedMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (EligiblePlayersActivity.iFreeSlotsAvail/*iTotalAddedMembers*/ > 0 && isChecked) {
                        buttonView.setEnabled(true);
                        addMemberToList(allPlayerArrayList.get(position));
                        allPlayerArrayList.get(position).setIsMemberSelected(isChecked);
                    } else if (EligiblePlayersActivity.iFreeSlotsAvail/*iTotalAddedMembers*/ <= EligiblePlayersActivity.iTeamSize && !isChecked) {
                        buttonView.setEnabled(true);
                        removeMemberFromList(allPlayerArrayList.get(position));
                        allPlayerArrayList.get(position).setIsMemberSelected(isChecked);
                    } else {
                        buttonView.setChecked(false);
                    }

                }
            });

            bindSectionHeader(holder.headerView, null, position);

            return rootView;
        }

        @Override
        public boolean doFilter(final AllPlayer item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.getNameAndHandicap();
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<AllPlayer> getOriginalList() {
            return mContacts;
        }

        // /////////////
        // ViewHolder //
        // /////////////
        class ViewHolder {
            public CircularContactView friendProfileCircularContactView;
            TextView friendName, headerView, tvPlayHCapStr;
            CheckBox cbSelectedMember;
            public AsyncTaskEx<Void, Void, Bitmap> updateTask;
        }
    }

    /**
     * Implements a method to set Members list in Adapter.
     *
     * @param eligibleMemberArrayList
     */
    private void setMembersListAdapter(ArrayList<AllPlayer> eligibleMemberArrayList) {
        if (eligibleMemberArrayList.size() == 0) {
            //((EligiblePlayersActivity) getActivity()).updateNoDataUI(false, 1);
            showAlertMessage(getString(R.string.error_no_member));
        } else {
            //Members list demo.
            Collections.sort(eligibleMemberArrayList, new Comparator<AllPlayer>() {
                @Override
                public int compare(AllPlayer lhs, AllPlayer rhs) {
                    char lhsFirstLetter = TextUtils.isEmpty(lhs.getNameAndHandicap()) ? ' ' : lhs.getNameAndHandicap().charAt(0);
                    char rhsFirstLetter = TextUtils.isEmpty(rhs.getNameAndHandicap()) ? ' ' : rhs.getNameAndHandicap().charAt(0);
                    int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                    if (firstLetterComparison == 0)
                        return lhs.getNameAndHandicap().compareTo(rhs.getNameAndHandicap());
                    return firstLetterComparison;
                }
            });

            mAdapter = new AlphabaticalListAdapter(eligibleMemberArrayList);

            mAdapter.setPinnedHeaderBackgroundColor(Color.parseColor("#F9F8F7"));
            mAdapter.setPinnedHeaderTextColor(ContextCompat.getColor(NewCompAddPlayersActivity.this, R.color.color9B9B9B));
            lvAddPlayersList.setPinnedHeaderView(LayoutInflater.from(NewCompAddPlayersActivity.this)
                    .inflate(R.layout.pinned_header_listview_side_header,
                            lvAddPlayersList,
                            false));
            lvAddPlayersList.setAdapter(mAdapter);
            lvAddPlayersList.setOnScrollListener(mAdapter);
            lvAddPlayersList.setEnableHeaderTransparencyChanges(false);
        }
        hideProgress();
    }


    //////////////////////////////////////////////////////////////////////////////////////


    /**
     * Implements this method to Add Member to ArrayList.
     *
     * @param iMemberID
     */
    public void addMemberToList(AllPlayer eligibleMember) {
        if (iTotalAddedMembers >= 0) {
            allPlayerArrayList.add(eligibleMember);
            iselectedMemberList.add(eligibleMember.getMemberId());
            --iTotalAddedMembers;
        }
        tvAddPlayerDesc.setText("You can add " + iTotalAddedMembers + " more players");
    }

    /**
     * Implements this method to Remove Member from ArrayList.
     *
     * @param eligibleMember
     */
    public void removeMemberFromList(AllPlayer eligibleMember) {

        int iCounter;
        for (iCounter = 0; iCounter < allPlayerArrayList.size(); iCounter++) {

            int selectedMemberId = eligibleMember.getMemberId();
            int jCounteMemberID = allPlayerArrayList.get(iCounter).getMemberId();

            if (selectedMemberId == jCounteMemberID) {
                allPlayerArrayList.remove(iCounter);
                iselectedMemberList.remove(iCounter);
                ++iTotalAddedMembers;
                break;
            }
        }
        tvAddPlayerDesc.setText("You can add " + iTotalAddedMembers + " more players");
    }

}
