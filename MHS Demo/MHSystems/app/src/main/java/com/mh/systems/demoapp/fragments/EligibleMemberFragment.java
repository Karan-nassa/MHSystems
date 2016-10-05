package com.mh.systems.demoapp.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

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
import com.mh.systems.demoapp.utils.CircularContactView;
import com.mh.systems.demoapp.utils.async_task_thread_pool.AsyncTaskEx;
import com.mh.systems.demoapp.utils.async_task_thread_pool.AsyncTaskThreadPool;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import lb.library.PinnedHeaderListView;
import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

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

    //Members list demo.
    private PinnedHeaderListView mPinnedHeaderListView;
    public static EligibleMembersAdapter mAdapter = null;
    static private LayoutInflater mInflater;

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

        mAdapter = new EligibleMembersAdapter(eligibleMemberArrayList, getActivity(), mInflater, 0);

        //int pinnedHeaderBackgroundColor = (ContextCompat.getColor(getActivity(), getResIdFromAttribute(getActivity(),  android.R.attr.colorBackground)));
        mAdapter.setPinnedHeaderBackgroundColor(Color.parseColor("#F9F8F7")/*pinnedHeaderBackgroundColor*/);
        mAdapter.setPinnedHeaderTextColor(ContextCompat.getColor(getActivity(), R.color.color9B9B9B));
        mPinnedHeaderListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mPinnedHeaderListView, false));
        mPinnedHeaderListView.setAdapter(mAdapter);
        mPinnedHeaderListView.setOnScrollListener(mAdapter);
        mPinnedHeaderListView.setEnableHeaderTransparencyChanges(false);
        Log.e(LOG_TAG, "Eligible Member mAdapter : " + mAdapter);

        setMembersListAdapter(eligibleMemberArrayList);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && iEligibleTabPos == 1) {
            ((BaseActivity) getActivity()).showPleaseWait("Loading...");
            ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());
            eligibleMemberArrayList.clear();
            eligibleMemberArrayList = ((EligiblePlayersActivity) getActivity()).getEligibleMemberList(0);
            setMembersListAdapter(eligibleMemberArrayList);
        }
    }


    /**
     * Implements a method to set Members list in Adapter.
     *
     * @param eligibleMemberArrayList
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

        /*mAdapter = new EligibleMembersAdapter(eligibleMemberArrayList, getActivity(), mInflater, 0);

        //int pinnedHeaderBackgroundColor = (ContextCompat.getColor(getActivity(), getResIdFromAttribute(getActivity(),  android.R.attr.colorBackground)));
        mAdapter.setPinnedHeaderBackgroundColor(Color.parseColor("#F9F8F7")*//*pinnedHeaderBackgroundColor*//*);
        mAdapter.setPinnedHeaderTextColor(ContextCompat.getColor(getActivity(), R.color.color9B9B9B));
        mPinnedHeaderListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mPinnedHeaderListView, false));
        mPinnedHeaderListView.setAdapter(mAdapter);
        mPinnedHeaderListView.setOnScrollListener(mAdapter);
        mPinnedHeaderListView.setEnableHeaderTransparencyChanges(false);*/

        mAdapter.notifyDataSetChanged();

        ((EligiblePlayersActivity) getActivity()).hideProgress();
    }

    public void updateSearchQuery(String strSearchText){
        mAdapter.getFilter().filter(strSearchText);
        mAdapter.setHeaderViewVisible(TextUtils.isEmpty(strSearchText));
        mAdapter.notifyDataSetChanged();
    }
}