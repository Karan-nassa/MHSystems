package com.mh.systems.chesterLeStreet.ui.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.chesterLeStreet.R;
import com.mh.systems.chesterLeStreet.web.models.ResultEntries;

import java.util.ArrayList;

/**
 * Created by karan@ucreate.co.in to display Competitions Round result
 * on 6/10/2016.
 */
public class CompetitionDetailAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    Context context;
    ArrayList<ResultEntries> resultEntryArrayList;

    public CompetitionDetailAdapter(Activity activity, ArrayList<ResultEntries> resultEntryArrayList) {
        context = activity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resultEntryArrayList = resultEntryArrayList;
    }

    @Override
    public int getCount() {
        return resultEntryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        RowView rowViewInstance = new RowView();

        rowView = inflater.inflate(R.layout.list_item_competition_detail, parent, false);
        rowViewInstance.llRankGroupRow = (LinearLayout) rowView.findViewById(R.id.llRankGroupRow);

        rowViewInstance.tvPosOfMember = (TextView) rowView.findViewById(R.id.tvPosOfMember);
        rowViewInstance.tvNameOfMember = (TextView) rowView.findViewById(R.id.tvNameOfMember);
        rowViewInstance.tvScoreOfMember = (TextView) rowView.findViewById(R.id.tvScoreOfMember);
        rowViewInstance.tvTotalScoreOfMember = (TextView) rowView.findViewById(R.id.tvTotalScoreOfMember);

        rowViewInstance.vSpaceView = (View) rowView.findViewById(R.id.vSpaceView);

        if (position % 2 == 0) {
            rowViewInstance.llRankGroupRow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorF1F1F0));
        } else {
            rowViewInstance.llRankGroupRow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorF9F8F7));
        }

        if(position == (resultEntryArrayList.size()-1)){
            rowViewInstance.vSpaceView.setVisibility(View.VISIBLE);
        }

        rowViewInstance.tvPosOfMember.setText(resultEntryArrayList.get(position).getPlaceStr());
        rowViewInstance.tvNameOfMember.setText(resultEntryArrayList.get(position).getEntryName());
        rowViewInstance.tvScoreOfMember.setText(resultEntryArrayList.get(position).getExactHCap());
        rowViewInstance.tvTotalScoreOfMember.setText(resultEntryArrayList.get(position).getScoreSummary());

        return rowView;
    }

    class RowView {
        LinearLayout llRankGroupRow;
        TextView tvPosOfMember, tvNameOfMember, tvScoreOfMember, tvTotalScoreOfMember;
        View vSpaceView;
    }
}
