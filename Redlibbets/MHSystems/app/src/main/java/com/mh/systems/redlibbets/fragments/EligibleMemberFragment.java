package com.mh.systems.redlibbets.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mh.systems.redlibbets.R;
import com.mh.systems.redlibbets.activites.EligiblePlayersActivity;
import com.mh.systems.redlibbets.constants.ApplicationGlobal;
import com.mh.systems.redlibbets.models.competitionsEntry.AJsonParamsEligiblePlayers;
import com.mh.systems.redlibbets.models.competitionsEntry.CompEligiblePlayersAPI;
import com.mh.systems.redlibbets.models.competitionsEntry.CompEligiblePlayersResponse;
import com.mh.systems.redlibbets.models.competitionsEntry.EligibleMember;
import com.mh.systems.redlibbets.util.libAlphaIndexing.CircularContactView;
import com.mh.systems.redlibbets.util.libAlphaIndexing.async_task_thread_pool.AsyncTaskEx;
import com.mh.systems.redlibbets.util.libAlphaIndexing.async_task_thread_pool.AsyncTaskThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import lb.library.PinnedHeaderListView;
import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;

/**
 * The {@link EligibleMemberFragment} used to display the eligible players list
 * for book paid COMPETITION with {@link AlphabaticalListAdapter} indexing
 * and {@link android.support.v7.widget.SearchView}.
 * <p>
 *
 * @author karan@ucreate.co.in
 * @version 1.0
 * @since 1 May, 2016
 */
public class EligibleMemberFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = EligibleMemberFragment.class.getSimpleName();
    ArrayList<EligibleMember> eligibleMemberArrayList = new ArrayList<>();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    //Create instance of Model class MembersItems.
    CompEligiblePlayersResponse compEligiblePlayersResponse;

    //List of type books this list will store type Book which is our data model
    private CompEligiblePlayersAPI compEligiblePlayersAPI;
    AJsonParamsEligiblePlayers aJsonParamsEligiblePlayers;

    //Members list demo.
    private PinnedHeaderListView mPinnedHeaderListView;
    public static AlphabaticalListAdapter mAdapter = null;
    public LayoutInflater mInflater;

    private int iEligibleTabPos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());

        viewRootFragment = inflater.inflate(R.layout.fragment_members, container, false);

        mPinnedHeaderListView = (PinnedHeaderListView) viewRootFragment.findViewById(R.id.lvMembersList);
        ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());

        iEligibleTabPos = 1;

        ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());
        eligibleMemberArrayList.clear();
        eligibleMemberArrayList = ((EligiblePlayersActivity) getActivity()).getEligibleMemberList(0);
        setMembersListAdapter(eligibleMemberArrayList);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && iEligibleTabPos == 1) {
            ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());
            eligibleMemberArrayList.clear();
            eligibleMemberArrayList = ((EligiblePlayersActivity) getActivity()).getEligibleMemberList(0);
            setMembersListAdapter(eligibleMemberArrayList);
        }
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return ((EligiblePlayersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }


    /**
     * Implements a method to set Members list in Adapter.
     *
     * @param eligibleMemberArrayList
     */
    private void setMembersListAdapter(ArrayList<EligibleMember> eligibleMemberArrayList) {
        if (eligibleMemberArrayList.size() == 0) {
            //((EligiblePlayersActivity) getActivity()).updateNoDataUI(false, 1);
            ((EligiblePlayersActivity) getActivity()).showAlertMessage(getString(R.string.error_no_member));
        } else {
            //Members list demo.
            Collections.sort(eligibleMemberArrayList, new Comparator<EligibleMember>() {
                @Override
                public int compare(EligibleMember lhs, EligibleMember rhs) {
                    char lhsFirstLetter = TextUtils.isEmpty(lhs.getNameRecord().getDisplayName()) ? ' ' : lhs.getNameRecord().getDisplayName().charAt(0);
                    char rhsFirstLetter = TextUtils.isEmpty(rhs.getNameRecord().getDisplayName()) ? ' ' : rhs.getNameRecord().getDisplayName().charAt(0);
                    int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                    if (firstLetterComparison == 0)
                        return lhs.getNameRecord().getDisplayName().compareTo(rhs.getNameRecord().getDisplayName());
                    return firstLetterComparison;
                }
            });

            mAdapter = new AlphabaticalListAdapter(eligibleMemberArrayList);

            //int pinnedHeaderBackgroundColor = (ContextCompat.getColor(getActivity(), getResIdFromAttribute(getActivity(),  android.R.attr.colorBackground)));
            mAdapter.setPinnedHeaderBackgroundColor(Color.parseColor("#F9F8F7")/*pinnedHeaderBackgroundColor*/);
            mAdapter.setPinnedHeaderTextColor(ContextCompat.getColor(getActivity(), R.color.color9B9B9B));
            mPinnedHeaderListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mPinnedHeaderListView, false));
            mPinnedHeaderListView.setAdapter(mAdapter);
            mPinnedHeaderListView.setOnScrollListener(mAdapter);
            mPinnedHeaderListView.setEnableHeaderTransparencyChanges(false);
        }
        ((EligiblePlayersActivity) getActivity()).hideProgress();
    }


    //////////////////////////////////////////////////////////////////////////////////////

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
    public class AlphabaticalListAdapter extends SearchablePinnedHeaderListViewAdapter<EligibleMember> {
        private ArrayList<EligibleMember> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        public final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);


        @Override
        public CharSequence getSectionTitle(int sectionIndex) {
            return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
        }

        public AlphabaticalListAdapter(final ArrayList<EligibleMember> contacts) {
            setData(contacts);

            PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE = getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<EligibleMember> contacts) {
            this.mContacts = contacts;
            final String[] generatedContactNames = generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
        }

        private String[] generateContactNames(final List<EligibleMember> contacts) {
            final ArrayList<String> contactNames = new ArrayList<String>();
            if (contacts != null)
                for (final EligibleMember contactEntity : contacts)
                    contactNames.add(contactEntity.getNameRecord().getDisplayName()/*getFullName()*/);
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            ViewHolder holder;
            final View rootView;
            final EligibleMember contact;

            ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());

            holder = new ViewHolder();

            rootView = mInflater.inflate(R.layout.list_item_alpha_eligible_member, parent, false);

            holder.friendProfileCircularContactView = (CircularContactView) rootView.findViewById(R.id.listview_item__friendPhotoImageView);
            holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);

            holder.friendName = (TextView) rootView.findViewById(R.id.listview_item__friendNameTextView);
            holder.tvPlayHCapStr = (TextView) rootView.findViewById(R.id.tvPlayHCapStr);
            holder.headerView = (TextView) rootView.findViewById(R.id.header_text);

            holder.cbSelectedMember = (CheckBox) rootView.findViewById(R.id.cbSelectedMember);

            rootView.setTag(holder);

            holder = (ViewHolder) rootView.getTag();
            contact = getItem(position);
            final String displayName = contact.getNameRecord().getDisplayName();
            holder.friendName.setText(displayName);
            holder.tvPlayHCapStr.setText(contact.getPlayHCapStr());
            //holder.cbSelectedMember.setChecked(contact.getIsMemberSelected());
            if (((EligiblePlayersActivity) getActivity()).iselectedMemberList.contains(contact.getMemberID())) {
                holder.cbSelectedMember.setChecked(true);
            } else {
                holder.cbSelectedMember.setChecked(false);
            }

            holder.cbSelectedMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (EligiblePlayersActivity.iTotalAddedMembers > 0 && isChecked) {
                        buttonView.setEnabled(true);
                        ((EligiblePlayersActivity) getActivity()).addMemberToList(eligibleMemberArrayList.get(position));
                        eligibleMemberArrayList.get(position).setIsMemberSelected(isChecked);
                    } else if (EligiblePlayersActivity.iTotalAddedMembers <= EligiblePlayersActivity.iTeamSize && !isChecked) {
                        buttonView.setEnabled(true);
                        ((EligiblePlayersActivity) getActivity()).removeMemberFromList(eligibleMemberArrayList.get(position));
                        eligibleMemberArrayList.get(position).setIsMemberSelected(isChecked);
                    } else {
                        buttonView.setChecked(false);
                    }

                }
            });

            bindSectionHeader(holder.headerView, null, position);

            return rootView;
        }

        @Override
        public boolean doFilter(final EligibleMember item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.getNameRecord().getDisplayName();
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<EligibleMember> getOriginalList() {
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
}