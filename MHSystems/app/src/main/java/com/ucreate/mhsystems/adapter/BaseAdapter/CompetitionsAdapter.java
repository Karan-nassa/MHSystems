package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.CompetitionsDetailActivity;
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

    Typeface typeface, typefaceMedium;

    /**
     * VIDEOS Adapter to initialize all instances.
     *
     * @param context:              To hold context.
     * @param CourseDiaryData    : Used for Videos data.
     */
    public CompetitionsAdapter(Activity context, ArrayList<CompetitionsData> CourseDiaryData) {
        this.context = context;
        this.compititionsDatas = CourseDiaryData;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        typefaceMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
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
        viewHolder.tvEventStatusStr = (TextView) rowView.findViewById(R.id.tvEventStatusStr);

        viewHolder.llShowDetails = (LinearLayout) rowView.findViewById(R.id.llShowDetails);

        //Set Font Style Typeface
        setTypeFace(viewHolder);
        rowView.setTag(viewHolder);

        viewHolder = (View_Holder) rowView.getTag();

        /**
         *  Set Course Diary events on each view.
         */
        viewHolder.tvCompTitle.setText(compititionsDatas.get(position).getTitle());
        viewHolder.tvDateCompEvent.setText(compititionsDatas.get(position).getEventDateStr() + ", " + compititionsDatas.get(position).getEventTime());
        viewHolder.tvCompDesc.setText(compititionsDatas.get(position).getDesc());
        viewHolder.tvEventStatusStr.setText(compititionsDatas.get(position).getEventStatusStr());
        viewHolder.tvFeeCompEvent.setText("£" + compititionsDatas.get(position).getPricePerGuest() + " " + context.getResources().getString(R.string.title_competitions_prize));

        /**
         *  Show detail page on tap of 'Show Details & Join'.
         */
        viewHolder.llShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CompetitionsDetailActivity.class);
                intent.putExtra("COMPETITIONS_TITLE", compititionsDatas.get(position).getTitle());
                intent.putExtra("COMPETITIONS_EVENT_IMAGE", compititionsDatas.get(position).getLogo());
                intent.putExtra("COMPETITIONS_EVENT_JOIN", compititionsDatas.get(position).getJoinStatus());
                intent.putExtra("COMPETITIONS_EVENT_DATE", compititionsDatas.get(position).getEventDateStr());
                intent.putExtra("COMPETITIONS_EVENT_TIME", compititionsDatas.get(position).getEventTime());
                intent.putExtra("COMPETITIONS_EVENT_PRIZE", "" + compititionsDatas.get(position).getPricePerGuest());
                intent.putExtra("COMPETITIONS_EVENT_DESCRIPTION", compititionsDatas.get(position).getDesc());
                context.startActivity(intent);
            }

        });

        return rowView;
    }


    /**
     * Implements a method to set ROBOTO normal
     * font for NO EVENTS row.
     */
    public void setTypeFace(View_Holder viewHolder) {

        viewHolder.tvFeeCompEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvDateCompEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvCompDesc.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvEventStatusStr.setTypeface(typeface, Typeface.NORMAL);

        viewHolder.tvCompTitle.setTypeface(typefaceMedium, Typeface.NORMAL);
    }

    /**
     * View_Holder to create competitions to
     * display events.
     */
    class View_Holder {
        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvFeeCompEvent, tvDateCompEvent, tvCompDesc, tvCompTitle, tvEventStatusStr;
        //Linear Layout instances declaration.
        LinearLayout llShowDetails;
    }
}