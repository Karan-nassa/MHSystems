package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.models.MembersList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan@ucreate.co.in
 * for Home Grid options on 14-03-2016.
 */
public class MembersAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;

    List<MembersList> membersLists = new ArrayList<>();

    /**
     * Constructor to initialize local instances.
     *  @param context
     * @param membersLists
     */
    public MembersAdapter(Context context, List<MembersList> membersLists) {

        this.context = context;
        this.membersLists = membersLists;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return membersLists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Declares a view holder class to
     * contain all resources.
     */
    public class Holder {
        TextView tvMemberName;
        TextView tvPlayHCapStr;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.list_item_member_row, null);
        holder.tvMemberName = (TextView) rowView.findViewById(R.id.tvMemberName);
        holder.tvPlayHCapStr = (TextView) rowView.findViewById(R.id.tvPlayHCapStr);

        //Set View data according position.
        holder.tvMemberName.setText(membersLists.get(position).getDisplayName());
        holder.tvPlayHCapStr.setText(membersLists.get(position).getPlayHCapStr());

        return rowView;
    }

}