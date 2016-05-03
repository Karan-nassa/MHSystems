
package com.ucreate.mhsystems.util;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.fragments.HandicapFragment;
import com.ucreate.mhsystems.util.pojo.HCapRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent, tvHandicapDate;
    private List<HCapRecord> hCapRecords;

    public MyMarkerView(Context context, int layoutResource, List<HCapRecord> hCapRecords) {
        super(context, layoutResource);

        this.hCapRecords = hCapRecords;

//        tvContent = (TextView) findViewById(R.id.tvHandicapPlayStr);
//        tvHandicapDate = (TextView) findViewById(R.id.tvHandicapDate);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {


       // HandicapFragment.mv.refreshContent(e, highlight);
       // HandicapFragment.showHandicapInfo(hCapRecords.get(e.getXIndex()).getDatePlayedStr(), hCapRecords.get(e.getXIndex()).getNewExactHCapOnlyStr());
//          tvHandicapDate.setText(hCapRecords.get(e.getXIndex()).getDatePlayedStr());

//        if (e instanceof CandleEntry) {
//
//            CandleEntry ce = (CandleEntry) e;
//            //    tvContent.setText("" + /*Utils.formatNumber(*/ce.getHigh()/*, 0, true)*/);
//        } else {
//            //   tvContent.setText("" + /*Utils.formatNumber(*/e.getVal()/*, 0, true)*/);
//        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -(getHeight() * 2);
    }
}
