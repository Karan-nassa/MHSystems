package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.utils.pojo.CompetitionsData;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryDataCopy;

import java.util.ArrayList;

/**
 * Created by  karan@ucreate.co.in to Create adapter
 * to display SQUADS on 12/4/2015.
 */
public class CompetitionsAdapter extends BaseAdapter {
    Activity context;
    ArrayList<CompetitionsData> compititionsDatas;
    LayoutInflater inflater = null;
    String strLastDate = "";


    /**
     * VIDEOS Adapter to initialize all instances.
     *
     * @param Activity:              To hold context.
     * @param ArrayList<VideoItems>: Used for Videos data.
     */
    public CompetitionsAdapter(Activity context, ArrayList<CompetitionsData> CourseDiaryData) {
        this.context = context;
        this.compititionsDatas = CourseDiaryData;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @return Videos array size.
     */
    @Override
    public int getCount() {
        return compititionsDatas.size();
    }

    /**
     * @return Videos item position
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * @return Videos item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @return View after create vidoe row
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        View_Holder viewHolder = null;

            rowView = inflater.inflate(R.layout.list_item_competitions, parent, false);

            viewHolder = new View_Holder();
            viewHolder.tvFeeCompEvent = (TextView) rowView.findViewById(R.id.tvFeeCompEvent);
            viewHolder.tvDateCompEvent = (TextView) rowView.findViewById(R.id.tvDateCompEvent);
            viewHolder.tvCompDesc = (TextView) rowView.findViewById(R.id.tvCompDesc);
            viewHolder.tvCompTitle = (TextView) rowView.findViewById(R.id.tvCompTitle);

            rowView.setTag(viewHolder);

            viewHolder = (View_Holder) rowView.getTag();

            /**
             *  Set Course Diary events on each view.
             */
            viewHolder.tvCompTitle.setText(compititionsDatas.get(position).getTitle());
            viewHolder.tvDateCompEvent.setText(compititionsDatas.get(position).getEventDateStr() + ", " + compititionsDatas.get(position).getEventTime());
            viewHolder.tvCompDesc.setText(compititionsDatas.get(position).getDesc());

            viewHolder.tvFeeCompEvent.setText(compititionsDatas.get(position).getDayName() + context.getResources().getString(R.string.title_competitions_prize));

        return rowView;
    }

    /**
     * View_Holder to create competitions to
     * display events.
     */
    class View_Holder {
        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvFeeCompEvent, tvDateCompEvent, tvCompDesc, tvCompTitle;
    }
}