package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.models.CourseDiaryDataCopy;

import java.util.ArrayList;

/**
 * Created by  karan@ucreate.co.in to Create adapter
 * to display COURSE DIARY EVENTS on 12/4/2015.
 */
public class CourseDiaryAdapter extends BaseAdapter {
    Activity context;
    ArrayList<CourseDiaryDataCopy> CourseDiaryData;
    LayoutInflater inflater = null;
    String strLastDate = "";

    Typeface typeface;


    /**
     * COURSE DIARY events Adapter constructor to initialize all instances.
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
            viewHolder.tvStartOfEvent = (TextView) rowView.findViewById(R.id.tvStartOfEvent);
            viewHolder.tvTimeOfEvent = (TextView) rowView.findViewById(R.id.tvTimeOfEvent);
            viewHolder.tvTitleOfEvent = (TextView) rowView.findViewById(R.id.tvTitleOfEvent);
            viewHolder.btBookNow = (Button) rowView.findViewById(R.id.btBookNow);

            //Set Font Style Typeface
            //setEventTypeFace(viewHolder);

            rowView.setTag(viewHolder);
            viewHolder = (View_Holder) rowView.getTag();

            viewHolder.tvStartOfEvent.setText(""+CourseDiaryData.get(position).getStartTime());
            viewHolder.tvTitleOfEvent.setText(CourseDiaryData.get(position).getTitle());
            viewHolder.tvTimeOfEvent.setText(""+CourseDiaryData.get(position).getDuration());

        } else {

            rowView = inflater.inflate(R.layout.list_item_course_diary, parent, false);

            viewHolder = new View_Holder();
            viewHolder.tvTimeOfEvent = (TextView) rowView.findViewById(R.id.tvTimeOfEvent);
            viewHolder.tvTitleOfEvent = (TextView) rowView.findViewById(R.id.tvTitleOfEvent);
            viewHolder.tvStartOfEvent = (TextView) rowView.findViewById(R.id.tvStartOfEvent);

            //Set Font Style Typeface
           // setNoEventTypeFace(viewHolder);
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
            viewHolder.tvTimeOfEvent.setText(""+CourseDiaryData.get(position).getDuration());
            viewHolder.tvStartOfEvent.setText(""+CourseDiaryData.get(position).getStartTime());
        }


        //  ((CourseDiaryActivity) context).setTitleBar(CourseDiaryData.get(position).getMonthName()/*CourseDiaryActivity.getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear*/);

        return rowView;
    }

    /**
     * Implements a method to set ROBOTO normal
     * font for EVENTS row.
     */
    public void setEventTypeFace(View_Holder viewHolder) {

        viewHolder.tvTimeOfEvent.setTypeface(typeface, Typeface.NORMAL);
    }

    /**
     * Implements a method to set ROBOTO normal
     * font for NO EVENTS row.
     */
    public void setNoEventTypeFace(View_Holder viewHolder) {
        viewHolder.tvTimeOfEvent.setTypeface(typeface, Typeface.NORMAL);
        viewHolder.tvTitleOfEvent.setTypeface(typeface, Typeface.NORMAL);
    }

    /**
     * View_Holder to create COURSE DIARY row to
     * display EVENTS of COURSE DIARY.
     */
    class View_Holder {
        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
//        TextView tvDateOfEvent, tvDayOfEvent, tvTimeOfEvent;
//        TextView tvTitleOfEvent, tvDescOfEvent;
        TextView tvStartOfEvent, tvTitleOfEvent, tvTimeOfEvent;
        Button btBookNow;

    }
}