package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.util.pojo.CourseDiaryDataCopy;

import java.util.ArrayList;

/**
 * Created by  karan@ucreate.co.in to Create adapter
 * to display SQUADS on 12/4/2015.
 */
public class CourseDiaryAdapter extends BaseAdapter {
    Activity context;
    ArrayList<CourseDiaryDataCopy> CourseDiaryData;
    LayoutInflater inflater = null;
    String strLastDate = "";

    Typeface typeface;


    /**
     * VIDEOS Adapter to initialize all instances.
     *
     * @param context:        : To hold context.
     * @param CourseDiaryData : Used for Videos data.
     */
    public CourseDiaryAdapter(Activity context, ArrayList<CourseDiaryDataCopy> CourseDiaryData) {

        this.context = context;
        this.CourseDiaryData = CourseDiaryData;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    /**
     * @return Videos array size.
     */
    @Override
    public int getCount() {
        return CourseDiaryData.size();
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

        if (CourseDiaryData.get(position).getSlotType() == 2) {

            rowView = inflater.inflate(R.layout.list_item_course_diary_no_events, parent, false);

            viewHolder = new View_Holder();
            viewHolder.tvDateOfEvent = (TextView) rowView.findViewById(R.id.tvDateOfEvent);
            viewHolder.tvTimeOfEvent = (TextView) rowView.findViewById(R.id.tvTimeOfEvent);
            viewHolder.tvDescOfEvent = (TextView) rowView.findViewById(R.id.tvDescOfEvent);
            viewHolder.tvDayOfEvent = (TextView) rowView.findViewById(R.id.tvDayOfEvent);

            //Set Font Style Typeface
            setEventTypeFace(viewHolder);

            rowView.setTag(viewHolder);
            viewHolder = (View_Holder) rowView.getTag();

            viewHolder.tvTimeOfEvent.setText(CourseDiaryData.get(position).getStartTime() + " - " + CourseDiaryData.get(position).getEndTime());
            viewHolder.tvDateOfEvent.setText(CourseDiaryData.get(position).getCourseEventDate());
            viewHolder.tvDescOfEvent.setText(CourseDiaryData.get(position).getDesc());
            viewHolder.tvDayOfEvent.setText(CourseDiaryData.get(position).getDayName());

        } else {

            rowView = inflater.inflate(R.layout.list_item_course_diary, parent, false);

            viewHolder = new View_Holder();
            viewHolder.tvDateOfEvent = (TextView) rowView.findViewById(R.id.tvDateOfEvent);
            viewHolder.tvDayOfEvent = (TextView) rowView.findViewById(R.id.tvDayOfEvent);
            viewHolder.tvTimeOfEvent = (TextView) rowView.findViewById(R.id.tvTimeOfEvent);
            viewHolder.tvTitleOfEvent = (TextView) rowView.findViewById(R.id.tvTitleOfEvent);
            viewHolder.tvDescOfEvent = (TextView) rowView.findViewById(R.id.tvDescOfEvent);

            //Set Font Style Typeface
            setNoEventTypeFace(viewHolder);
            rowView.setTag(viewHolder);

            viewHolder = (View_Holder) rowView.getTag();

//        String strDateOfEvent = formatDateOfEvent(CourseDiaryData.get(position).getCourseEventDate());

            /**
             * Check if same date or not of Course Diary event If yes then just
             * display date and day name once otherwise skip.
             */
//        if (!strLastDate.equalsIgnoreCase(strDateOfEvent)) {
//            strLastDate = strDateOfEvent;
//
//            //Display date if existing different one.
//            viewHolder.tvDateOfEvent.setText(strDateOfEvent);
//            viewHolder.tvDayOfEvent.setText(formatDayOfEvent(CourseDiaryData.get(position).getDayName()));
//        }

            /**
             *  Set Course Diary events on each view.
             */
            viewHolder.tvTitleOfEvent.setText(CourseDiaryData.get(position).getTitle());
            viewHolder.tvTimeOfEvent.setText(CourseDiaryData.get(position).getStartTime() + " - " + CourseDiaryData.get(position).getEndTime());
            viewHolder.tvDescOfEvent.setText(CourseDiaryData.get(position).getDesc());
            //Display date if existing different one.
            viewHolder.tvDateOfEvent.setText(CourseDiaryData.get(position).getCourseEventDate());
            viewHolder.tvDayOfEvent.setText(CourseDiaryData.get(position).getDayName());
        }

        return rowView;
    }

    /**
     * Implements a method to set ROBOTO normal
     * font for EVENTS row.
     */
    public void setEventTypeFace(View_Holder viewHolder) {

        viewHolder.tvDateOfEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvDayOfEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvTimeOfEvent.setTypeface(typeface, Typeface.NORMAL);
    }

    /**
     * Implements a method to set ROBOTO normal
     * font for NO EVENTS row.
     */
    public void setNoEventTypeFace(View_Holder viewHolder) {

        viewHolder.tvDateOfEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvDayOfEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvTimeOfEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvTitleOfEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvDescOfEvent.setTypeface(typeface, Typeface.NORMAL);
    }

    /**
     * View_Holder to create item_list_squads_row to
     * display Squads.
     */
    class View_Holder {
        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvDateOfEvent, tvDayOfEvent, tvTimeOfEvent;
        TextView tvTitleOfEvent, tvDescOfEvent;

    }
}