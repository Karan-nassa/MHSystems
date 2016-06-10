package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return 3;
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
        View rowView = inflater.inflate(R.layout.item_list_competition_detail, parent, false);
        return rowView;
    }
}
