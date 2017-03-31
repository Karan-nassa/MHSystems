package com.mh.systems.hartsbourne.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by karan@ucreate.co.in to display Recycle
 * or List View in scroll view on 12/23/2015.
 */
public class ScrollRecycleView {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }

        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        Log.e("HEIGHT", ""+myListView.getChildCount());

        //ic_home_settings list view item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getChildCount() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.e("height of listItem:", String.valueOf(totalHeight));
    }
}
