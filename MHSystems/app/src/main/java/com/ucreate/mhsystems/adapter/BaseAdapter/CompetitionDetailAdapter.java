package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;

/**
 * Created by karan@ucreate.co.in on 6/10/2016.
 */
public class CompetitionDetailAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    Context context;

    public CompetitionDetailAdapter(Activity activity) {
        context = activity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 9;
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

        rowView = inflater.inflate(R.layout.item_list_competition_detail, parent, false);
        rowViewInstance.llRankGroupRow = (LinearLayout) rowView.findViewById(R.id.llRankGroupRow);

        if(position%2 == 0){
            rowViewInstance.llRankGroupRow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorF1F1F0));
        }else{
            rowViewInstance.llRankGroupRow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorF9F8F7));
        }

        return rowView;
    }

    class RowView{
        LinearLayout llRankGroupRow;
    }
}
