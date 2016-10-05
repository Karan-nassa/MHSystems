package com.mh.systems.demoapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.activites.BaseActivity;
import com.mh.systems.demoapp.activites.EligiblePlayersActivity;
import com.mh.systems.demoapp.adapter.pinnedHeaderListAdapter.EligibleMembersAdapter;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.competitionsEntry.AJsonParamsEligiblePlayers;
import com.mh.systems.demoapp.models.competitionsEntry.CompEligiblePlayersAPI;
import com.mh.systems.demoapp.models.competitionsEntry.CompEligiblePlayersResponse;
import com.mh.systems.demoapp.models.competitionsEntry.EligibleMember;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lb.library.PinnedHeaderListView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * The {@link EligibleFriendsFragment} used to display the Member list
 * with {@link AlphabaticalListAdapter} indexing and {@link android.support.v7.widget.SearchView}
 * <p/>
 *
 * @author karan@ucreate.co.in
 * @version 1.0
 * @since 12 May, 2016
 */
public class EligibleFriendsFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public final String LOG_TAG = EligibleFriendsFragment.class.getSimpleName();

    ArrayList<EligibleMember> eligibleMemberArrayList = new ArrayList<>();

    private int isTabVisibile = 0;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    //Members list demo.
    private PinnedHeaderListView mPinnedHeaderListView;
    static private LayoutInflater mInflater;

    public static EligibleMembersAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());

        viewRootFragment = inflater.inflate(R.layout.fragment_friends, container, false);

        mPinnedHeaderListView = (PinnedHeaderListView) viewRootFragment.findViewById(R.id.phlvFriends);
        ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleFriendsFragment());

        mAdapter = new EligibleMembersAdapter();
        Log.e(LOG_TAG, "Eligible Member mAdapter : " + mAdapter);

        isTabVisibile = 1;

//        ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());
//        eligibleMemberArrayList.clear();
//        eligibleMemberArrayList = ((EligiblePlayersActivity) getActivity()).getEligibleMemberList(1);
//        setMembersListAdapter(eligibleMemberArrayList);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isTabVisibile == 1) {
            ((BaseActivity) getActivity()).showPleaseWait("Loading...");
            ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleFriendsFragment());
            eligibleMemberArrayList.clear();
            eligibleMemberArrayList = ((EligiblePlayersActivity) getActivity()).getEligibleMemberList(1);
            setMembersListAdapter(eligibleMemberArrayList);
        }
    }

    /**
     * Implements a method to set Members list in Adapter.
     *
     * @param eligibleMembers
     */
    private void setMembersListAdapter(ArrayList<EligibleMember> eligibleMemberArrayList) {

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

        mAdapter = new EligibleMembersAdapter(eligibleMemberArrayList, getActivity(), mInflater, 1);

        //int pinnedHeaderBackgroundColor = (ContextCompat.getColor(getActivity(), getResIdFromAttribute(getActivity(), android.R.attr.colorBackground)));
        mAdapter.setPinnedHeaderBackgroundColor(Color.parseColor("#F9F8F7")/*pinnedHeaderBackgroundColor*/);
        mAdapter.setPinnedHeaderTextColor(ContextCompat.getColor(getActivity(), R.color.color9B9B9B));
        mPinnedHeaderListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mPinnedHeaderListView, false));
        mPinnedHeaderListView.setAdapter(mAdapter);
        mPinnedHeaderListView.setOnScrollListener(mAdapter);
        mPinnedHeaderListView.setEnableHeaderTransparencyChanges(false);
        mAdapter.notifyDataSetChanged();

        ((EligiblePlayersActivity) getActivity()).hideProgress();
    }
}