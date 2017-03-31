package com.mh.systems.brokenhurst.ui.adapter.pinnedHeaderListAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mh.systems.brokenhurst.R;
import com.mh.systems.brokenhurst.ui.activites.EligiblePlayersActivity;
import com.mh.systems.brokenhurst.ui.fragments.EligibleFriendsFragment;
import com.mh.systems.brokenhurst.ui.fragments.EligibleMemberFragment;
import com.mh.systems.brokenhurst.web.models.competitionsentry.EligibleMember;
import com.mh.systems.brokenhurst.utils.libAlphaIndexing.CircularContactView;
import com.mh.systems.brokenhurst.utils.libAlphaIndexing.async_task_thread_pool.AsyncTaskEx;
import com.mh.systems.brokenhurst.utils.libAlphaIndexing.async_task_thread_pool.AsyncTaskThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;

/**
 * Created by admin on 05-10-2016.
 */
public class EligibleMembersAdapter extends SearchablePinnedHeaderListViewAdapter<EligibleMember> {
    private ArrayList<EligibleMember> mContacts;

    EligibleMembersAdapter.ViewHolder holder;
    private Context mContext;

    private LayoutInflater mInflater;
    public final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);

    int iTabPosition;

    public EligibleMembersAdapter() {
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public CharSequence getSectionTitle(int sectionIndex) {
        return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
    }

    public EligibleMembersAdapter(final ArrayList<EligibleMember> contacts, Activity activity, LayoutInflater mInflater, int iTabPosition) {
        this.mContacts = contacts;
        this.mContext = activity;
        this.mInflater = mInflater;
        this.iTabPosition = iTabPosition;
        setData(mContacts);
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
                contactNames.add(contactEntity.getNameRecord().getDisplayName());
        return contactNames.toArray(new String[contactNames.size()]);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View rootView;
        final EligibleMember contact;

        if (iTabPosition == 0) {
            ((EligiblePlayersActivity) mContext).setFragmentInstance(new EligibleMemberFragment());
        } else {
            ((EligiblePlayersActivity) mContext).setFragmentInstance(new EligibleFriendsFragment());
        }

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
        contact = mContacts.get(position);
        if (contact != null) {
            final String displayName = contact.getNameRecord().getDisplayName();
            holder.friendName.setText(displayName);

            if (iTabPosition == 1) {
                holder.tvPlayHCapStr.setText(contact.getHCapTypeStr());
            } else {
                holder.tvPlayHCapStr.setText(contact.getPlayHCapStr());
            }
            //holder.cbSelectedMember.setChecked(contact.getIsMemberSelected());
            if (((EligiblePlayersActivity) mContext).iselectedMemberList.contains(contact.getMemberID())) {
                holder.cbSelectedMember.setChecked(true);
            } else {
                holder.cbSelectedMember.setChecked(false);
            }

            holder.cbSelectedMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (EligiblePlayersActivity.iTotalAddedMembers > 0 && isChecked) {
                        buttonView.setEnabled(true);
                        ((EligiblePlayersActivity) mContext).addMemberToList(mContacts.get(position));
                        mContacts.get(position).setIsMemberSelected(isChecked);
                    } else if (EligiblePlayersActivity.iTotalAddedMembers <= EligiblePlayersActivity.iTeamSize && !isChecked) {
                        buttonView.setEnabled(true);
                        ((EligiblePlayersActivity) mContext).removeMemberFromList(mContacts.get(position));
                        mContacts.get(position).setIsMemberSelected(isChecked);
                    } else {
                        buttonView.setChecked(false);
                    }

                }
            });
            bindSectionHeader(holder.headerView, null, position);
        }

        return rootView;
    }

    @Override
    public boolean doFilter(final EligibleMember item, final CharSequence constraint) {
        if (TextUtils.isEmpty(constraint))
            return true;
        final String displayName = item.getNameRecord().getDisplayName();
        Log.e("doFilter", "displayName : " + (!TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                .contains(constraint.toString().toLowerCase(Locale.getDefault()))));
        return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                .contains(constraint.toString().toLowerCase(Locale.getDefault()));
    }

    @Override
    public ArrayList<EligibleMember> getOriginalList() {
        return mContacts;
    }

    public void setNotifyData() {
        Log.e("setNotifyData", "setNotifyData");
        notifyDataSetChanged();
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
