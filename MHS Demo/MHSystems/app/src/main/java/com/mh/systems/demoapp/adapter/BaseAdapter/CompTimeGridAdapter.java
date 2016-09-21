package com.mh.systems.demoapp.adapter.BaseAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.activites.CompetitionEntryActivity;
import com.mh.systems.demoapp.models.competitionsEntry.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan@ucreate.co.in for Time slots Grid options.
 *
 * @since 01-09-2016.
 */
public class CompTimeGridAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    private Typeface tfRobotoMedium;

    private List<Slot> slotArrayList = new ArrayList<>();
    private Button lastSelectedView = null;

    private int iLastSelectedTimeSlot;

    public CompTimeGridAdapter(CompetitionEntryActivity mainActivity, List<Slot> slotArrayList) {

        context = mainActivity;
        this.slotArrayList = slotArrayList;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tfRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    @Override
    public int getCount() {
        return slotArrayList.size();
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
     * Declares a view holder class to create view resources.
     */
    private class Holder {
        Button btTimeSlot;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        View rowView = null;
        rowView = inflater.inflate(R.layout.grid_item_time_view, null);

        holder.btTimeSlot = (Button) rowView.findViewById(R.id.btTimeSlot);
        holder.btTimeSlot.setText("" + slotArrayList.get(position).getSlotStartTimeStr());
        holder.btTimeSlot.setTypeface(tfRobotoMedium);

        holder.btTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view != lastSelectedView) {

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        holder.btTimeSlot.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_time_buttonc0995b));
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.btTimeSlot.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_time_buttonc0995b));
                    }
                    holder.btTimeSlot.setTextColor(Color.parseColor("#ffffff"));

                    //slotsArrayList.get(position).setSelected(true);

                    if (lastSelectedView != null) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            lastSelectedView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_time_buttone4e4e4));
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            lastSelectedView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_time_buttone4e4e4));
                        }
                        lastSelectedView.setTextColor(Color.parseColor("#000000"));
                        //  slotsArrayList.get(position).setSelected(true);
                    }
                    lastSelectedView = holder.btTimeSlot;
                }

                //Update Tee Time Slot value.
                ((CompetitionEntryActivity) context).updateTeeTimeValue(iLastSelectedTimeSlot);
            }
        });

        return rowView;
    }
}