package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryData;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by  karan@ucreate.co.in to Create adapter
 * to display SQUADS on 12/4/2015.
 */
public class CourseDiaryAdapter extends BaseAdapter {
    Activity context;
    ArrayList<CourseDiaryData> CourseDiaryData;
    LayoutInflater inflater = null;
    String strLastDate = "";

    /**
     * VIDEOS Adapter to initialize all instances.
     *
     * @param Activity:              To hold context.
     * @param ArrayList<VideoItems>: Used for Videos data.
     */
    public CourseDiaryAdapter(Activity context, ArrayList<CourseDiaryData> CourseDiaryData) {
        this.context = context;
        this.CourseDiaryData = CourseDiaryData;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        rowView = inflater.inflate(R.layout.list_item_course_diary, parent, false);

        viewHolder = new View_Holder();
        viewHolder.tvDateOfEvent = (TextView) rowView.findViewById(R.id.tvDateOfEvent);
        viewHolder.tvDayOfEvent = (TextView) rowView.findViewById(R.id.tvDayOfEvent);
        viewHolder.tvTimeOfEvent = (TextView) rowView.findViewById(R.id.tvTimeOfEvent);
        viewHolder.tvCategoryOfEvent = (TextView) rowView.findViewById(R.id.tvCategoryOfEvent);
        viewHolder.tvTitleOfEvent = (TextView) rowView.findViewById(R.id.tvTitleOfEvent);
        viewHolder.tvDescOfEvent = (TextView) rowView.findViewById(R.id.tvDescOfEvent);
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
        viewHolder.tvCategoryOfEvent.setText(CourseDiaryData.get(position).getCategory());
        viewHolder.tvTimeOfEvent.setText(CourseDiaryData.get(position).getStartTime() + " - " + CourseDiaryData.get(position).getEndTime());
        viewHolder.tvDescOfEvent.setText(CourseDiaryData.get(position).getDesc());
        //Display date if existing different one.
        viewHolder.tvDateOfEvent.setText(CourseDiaryData.get(position).getCourseEventDate());
        viewHolder.tvDayOfEvent.setText(CourseDiaryData.get(position).getDayName());

        return rowView;
    }

    /**
     * @param strCourseEventDate <br>
     *                           Implements a method to return the format the day of
     *                           event.
     *                           <p/>
     *                           Exapmle: 2016-03-04T00:00:00
     * @Return : 04
     */
    private String formatDateOfEvent(String strCourseEventDate) {

        String strEventDate = strCourseEventDate.substring(strCourseEventDate.lastIndexOf("-") + 1, strCourseEventDate.lastIndexOf("T"));

        return strEventDate;
    }

    /**
     * @param strDayName <br>
     *                   Implements a method to return the format the day of
     *                   event.
     *                   <p/>
     *                   Exapmle: NAME OF DAY : Friday
     * @Return : Fri
     */
    private String formatDayOfEvent(String strDayName) {
        return (strDayName.substring(0, 3));
    }

    /**
     * Get date and return in dd-MMMM-yyyy format.
     * Example: 2015-12-1
     *
     * @return 1-DECEMBER-2015
     */
    public String getDateFormat(String strDate) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");

        String strNewDate = strDate.substring(0, strDate.indexOf("T"));

        try {
            Date date = inputFormat.parse(strNewDate);
            strNewDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }

        return strNewDate.toUpperCase();
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
        TextView tvCategoryOfEvent, tvTitleOfEvent, tvDescOfEvent;

    }
}