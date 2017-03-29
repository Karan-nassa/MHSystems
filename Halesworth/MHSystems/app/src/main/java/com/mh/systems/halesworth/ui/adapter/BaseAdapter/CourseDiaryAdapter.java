package com.mh.systems.halesworth.ui.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.ui.activites.CustomAlertDialogActivity;
import com.mh.systems.halesworth.utils.constants.ApplicationGlobal;
import com.mh.systems.halesworth.web.models.CourseDiaryData;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by  karan@ucreate.co.in to Create adapter
 * to display COURSE DIARY EVENTS on 12/4/2015.
 */
public class CourseDiaryAdapter extends BaseAdapter implements View.OnClickListener {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    public TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    public ArrayList<CourseDiaryData> CourseDiaryData = new ArrayList<>();

    Activity context;
    LayoutInflater inflater = null;
    String strLastDate = "";

    /**
     * Declares the Typeface here for custom font style.
     */
    Typeface tfSFUIDisplayBold, tfSFUITextMedium, tfSFUIDisplayRegular;

    /**
     * COURSE DIARY events Adapter constructor to initialize all instances.
     *
     * @param context:        : To hold context.
     * @param CourseDiaryData : Used for Videos data.
     */
    public CourseDiaryAdapter(Activity context, ArrayList<CourseDiaryData> CourseDiaryData) {

        this.context = context;
        this.CourseDiaryData = CourseDiaryData;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfSFUIDisplayBold = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Bold.otf");
        tfSFUITextMedium = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Text-Medium.otf");
        tfSFUIDisplayRegular = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Regular.otf");
    }

    /**
     * Initialize the constructor.
     *
     * @param context
     */
    public CourseDiaryAdapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfSFUIDisplayBold = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Bold.otf");
        tfSFUITextMedium = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Text-Medium.otf");
        tfSFUIDisplayRegular = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Regular.otf");
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * @return Course Event array size.
     */
    @Override
    public int getCount() {
        return CourseDiaryData.size();
    }

    /**
     * @return Course Event item position
     */
    @Override
    public String getItem(int position) {
        return CourseDiaryData.get(position).getCourseEventDate();
    }

    /**
     * @return Course Event item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @return View after create Course Event row
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        View_Holder viewHolder = new View_Holder();

        int rowType = getItemViewType(position);
        switch (rowType) {
            case TYPE_SEPARATOR:
                rowView = inflater.inflate(R.layout.list_item_course_title_row, parent, false);
                viewHolder.tvCourseDateTitle = (TextView) rowView.findViewById(R.id.tvCourseDateTitle);
                viewHolder.tvCourseDateTitle.setText(CourseDiaryData.get(position).getStrCourseEventDate());

                viewHolder.tvCourseDateTitle.setTypeface(tfSFUITextMedium);
                break;

            case TYPE_ITEM: {
                if (CourseDiaryData.get(position).getSlotType() == 2) {

                    rowView = inflater.inflate(R.layout.list_item_course_diary_no_events, parent, false);

                    viewHolder = new View_Holder();
                    viewHolder.tvStartOfEvent = (TextView) rowView.findViewById(R.id.tvStartOfEvent);
                    viewHolder.tvTimeOfEvent = (TextView) rowView.findViewById(R.id.tvTimeOfEvent);
                    viewHolder.tvTitleOfEvent = (TextView) rowView.findViewById(R.id.tvTitleOfEvent);
                    viewHolder.btBookNow = (Button) rowView.findViewById(R.id.btBookNow);

                    viewHolder.llBookEventRow = (LinearLayout) rowView.findViewById(R.id.llBookEventRow);

                    viewHolder.btBookNow.setTypeface(tfSFUITextMedium);

                    viewHolder.llBookEventRow.setOnClickListener(this);
                    viewHolder.btBookNow.setOnClickListener(this);

                    /**
                     *  Book free slot of Event.
                     */
//                    viewHolder.llBookEventRow.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //Show alert dialog.
//                            Intent mIntent = new Intent(context, CustomAlertDialogActivity.class);
//                            //Pass theme green color.
//                            mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#AFD9A1");
//                            mIntent.putExtra(ApplicationGlobal.TAG_CALL_FROM, ApplicationGlobal.POSITION_COURSE_DIARY);
//                            context.startActivity(mIntent);
//                        }
//                    });

                    rowView.setTag(viewHolder);
                    viewHolder = (View_Holder) rowView.getTag();

                    viewHolder.tvStartOfEvent.setText(CourseDiaryData.get(position).getStartTime24Format());
                    viewHolder.tvTitleOfEvent.setText(CourseDiaryData.get(position).getTitle());
                    viewHolder.tvTimeOfEvent.setText(CourseDiaryData.get(position).getDuration());

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
                    viewHolder.tvTimeOfEvent.setText(CourseDiaryData.get(position).getDuration());
                    viewHolder.tvStartOfEvent.setText(CourseDiaryData.get(position).getStartTime24Format());
                }

                /**
                 *  Stop click of Course Diary detail screen.
                 */

               /* rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        *//**
                 *  Handle NULL @Exception.
                 *//*
                        if (CourseDiaryData.get(position) != null) {

                        *//*    if (CourseDiaryData.get(position).getSlotType() == 3) {*//*

                                Intent intent = new Intent(context, CourseDiaryDetailActivity.class);
                                intent.putExtra("COURSE_TITLE", CourseDiaryData.get(position).getTitle());
                                intent.putExtra("COURSE_EVENT_IMAGE", CourseDiaryData.get(position).getLogo());
                                intent.putExtra("COURSE_EVENT_JOIN", CourseDiaryData.get(position).isJoinStatus());
                                intent.putExtra("COURSE_EVENT_DATE", CourseDiaryData.get(position).getCourseEventDate());
                                intent.putExtra("COURSE_EVENT_DAY_NAME", CourseDiaryData.get(position).getDayName());
                                intent.putExtra("COURSE_EVENT_PRIZE", "" + CourseDiaryData.get(position).getPrizePerGuest());
                                intent.putExtra("COURSE_EVENT_DESCRIPTION", CourseDiaryData.get(position).getDesc());
                                intent.putExtra("COURSE_EVENT_TIME", CourseDiaryData.get(position).getStartTime() + " - " + CourseDiaryData.get(position).getEndTime());
                                context.startActivity(intent);
                            }
                      *//*  }*//*
                    }
                });*/

                //Set Font Style Typeface
                setEventTypeFace(viewHolder);
                break;
            }
        }


        //  ((CourseDiaryActivity) context).setTitleBar(CourseDiaryData.get(position).getMonthName()/* getMonth(Integer.parseInt(String.valueOf( iMonth))) + " " +  iYear*/);

        return rowView;
    }

    /**
     * Implements a method to set typeface.
     */
    public void setEventTypeFace(View_Holder viewHolder) {
        viewHolder.tvTitleOfEvent.setTypeface(tfSFUIDisplayBold, Typeface.NORMAL);
        viewHolder.tvStartOfEvent.setTypeface(tfSFUITextMedium, Typeface.NORMAL);
        viewHolder.tvTimeOfEvent.setTypeface(tfSFUIDisplayRegular, Typeface.NORMAL);
    }

    /**
     * Implements a method to add ITEMS programmatically. Note here its the data of list
     * not heading.
     */
    public void addItem(final CourseDiaryData item) {
        CourseDiaryData.add(item);
        notifyDataSetChanged();
    }

    /**
     * Implements a method to add section heading for COURSE DIARY
     * events.
     */
    public void addSectionHeaderItem(final CourseDiaryData courseDiaryData) {
        CourseDiaryData.add(courseDiaryData);
        sectionHeader.add(CourseDiaryData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        //Show alert dialog.
        Intent mIntent = new Intent(context, CustomAlertDialogActivity.class);
        //Pass theme green color.
        mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#AFD9A1");
        mIntent.putExtra(ApplicationGlobal.TAG_CALL_FROM, ApplicationGlobal.POSITION_COURSE_DIARY);
        context.startActivity(mIntent);
    }

    /**
     * View_Holder to create COURSE DIARY row to
     * display EVENTS of COURSE DIARY.
     */
    class View_Holder {
        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvStartOfEvent, tvTitleOfEvent, tvTimeOfEvent;
        TextView tvCourseDateTitle;
        Button btBookNow;
        LinearLayout llBookEventRow;
    }
}