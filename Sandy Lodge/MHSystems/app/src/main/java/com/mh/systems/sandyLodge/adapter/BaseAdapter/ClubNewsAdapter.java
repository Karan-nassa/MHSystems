package com.mh.systems.sandyLodge.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.systems.sandyLodge.R;

/**
 * Created by karan@ucreate.co.in on 6/10/2016.
 */
public class ClubNewsAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    Context context;

    Typeface tfRobotoRegular;

    public ClubNewsAdapter(Activity activity) {
        context = activity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    public int getCount() {
        return 10;
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

        rowView = inflater.inflate(R.layout.list_item_club_news, parent, false);
        rowViewInstance.tvTitleOfNews = (TextView) rowView.findViewById(R.id.tvTitleOfNews);
        rowViewInstance.tvTimeOfNews = (TextView) rowView.findViewById(R.id.tvTimeOfNews);
        rowViewInstance.ivReadStatus = (ImageView) rowView.findViewById(R.id.ivReadStatus);

        if(position>1){
            rowViewInstance.ivReadStatus.setVisibility(View.INVISIBLE);
        }

        rowViewInstance.tvTitleOfNews.setTypeface(tfRobotoRegular);
        rowViewInstance.tvTimeOfNews.setTypeface(tfRobotoRegular);

        return rowView;
    }

    class RowView{
        ImageView ivReadStatus;
        TextView tvTitleOfNews, tvTimeOfNews;
    }
}
