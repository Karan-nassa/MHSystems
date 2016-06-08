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
import com.ucreate.mhsystems.models.CompetitionsData;

import java.util.ArrayList;

/**
 * Created by  karan@ucreate.co.in to Create adapter
 * to display COMPETITIONS on 12/4/2015.
 */
public class CompetitionsAdapter extends BaseAdapter {

    Activity context;
    public ArrayList<CompetitionsData> compititionsDatas;
    LayoutInflater inflater = null;
    String strLastDate = "";
    boolean isJoinVisible;

    Typeface typeface, tpRobotoMedium;

    /**
     * COMPETITIONS Adapter to initialize all instances.
     *
     * @param context:        To hold context.
     * @param CourseDiaryData : Used for Videos data.
     * @param isJoinVisible   : JOIN will be visible only for {@link UpcomingFragment}
     */
    public CompetitionsAdapter(Activity context, ArrayList<CompetitionsData> CourseDiaryData, boolean isJoinVisible) {

        this.context = context;
        this.compititionsDatas = CourseDiaryData;
        this.isJoinVisible = isJoinVisible;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        tpRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    /**
     * COMPETITIONS Adapter to initialize all instances.
     *
     * @param context:        To hold context.
     */
    public CompetitionsAdapter(Activity context) {

        this.context = context;
        this.isJoinVisible = isJoinVisible;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        tpRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    /**
     * @return COMPETITIONS array size.
     */
    @Override
    public int getCount() {
        return compititionsDatas.size();
    }

    /**
     * @return COMPETITIONS item position
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * @return COMPETITIONS item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @return View after create COMPETITIONS row
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
        viewHolder.tvTimeOfCompEvent = (TextView) rowView.findViewById(R.id.tvTimeOfCompEvent);

        viewHolder.llCompetitionGroup = (LinearLayout) rowView.findViewById(R.id.llCompetitionGroup);

        //viewHolder.llShowDetails = (LinearLayout) rowView.findViewById(R.id.llShowDetails);

        //Set Font Style Typeface
        setTypeFace(viewHolder);
        rowView.setTag(viewHolder);

        viewHolder = (View_Holder) rowView.getTag();

        /**
         *  Set Course Diary events on each view.
         */
        viewHolder.tvCompTitle.setText(compititionsDatas.get(position).getTitle());
        viewHolder.tvDateCompEvent.setText(compititionsDatas.get(position).getEventDateStr());
        viewHolder.tvTimeOfCompEvent.setText(compititionsDatas.get(position).getEventTime());
        viewHolder.tvCompDesc.setText(compititionsDatas.get(position).getDesc());
        viewHolder.tvEventStatusStr.setText(compititionsDatas.get(position).getEventStatusStr());
        viewHolder.tvFeeCompEvent.setText("£" + compititionsDatas.get(position).getPricePerGuest() + " " + context.getResources().getString(R.string.title_competitions_prize));

        /**
         *  Show detail page on tap of 'Show Details & Join'.
         */
        viewHolder.llCompetitionGroup.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("COMPETITIONS_JOIN_STATE", isJoinVisible);
                intent.putExtra("COMPETITIONS_IsMemberJoined", compititionsDatas.get(position).getIsMemberJoined());
                intent.putExtra("COMPETITIONS_eventId", compititionsDatas.get(position).getEventId());
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

        viewHolder.tvCompTitle.setTypeface(tpRobotoMedium, Typeface.NORMAL);
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
        TextView tvTimeOfCompEvent;
        //Linear Layout instances declaration.
        //LinearLayout llShowDetails;
        LinearLayout llCompetitionGroup;
    }
}