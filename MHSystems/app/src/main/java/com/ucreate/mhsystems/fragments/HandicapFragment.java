package com.ucreate.mhsystems.fragments;

/**
 * Created by karan@ucreate.co.in to load and display Graph and
 * Certificate of Handicap.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionsAdapter;
import com.ucreate.mhsystems.util.MyMarkerView;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.util.pojo.AJsonParamsHandicap;
import com.ucreate.mhsystems.util.pojo.CompetitionsAPI;
import com.ucreate.mhsystems.util.pojo.CompetitionsJsonParams;
import com.ucreate.mhsystems.util.pojo.CompetitionsResultItems;
import com.ucreate.mhsystems.util.pojo.HCapRecord;
import com.ucreate.mhsystems.util.pojo.HandicapAPI;
import com.ucreate.mhsystems.util.pojo.HandicapData;
import com.ucreate.mhsystems.util.pojo.HandicapResultItems;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class HandicapFragment extends Fragment implements OnChartValueSelectedListener {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = HandicapFragment.class.getSimpleName();
    ArrayList<HandicapData> handicapData = new ArrayList<>();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View mRootView;
    TextView tvHandicapExact, tvHandicapPlaying, tvHandicapType;

    // CompetitionsAdapter competitionsAdapter;

    //List of type books this list will store type Book which is our data model
    private HandicapAPI handicapAPI;
    private AJsonParamsHandicap aJsonParamsHandicap;

    //Create instance of Model class CourseDiaryItems.
    HandicapResultItems handicapResultItems;

    private LineChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_handicap, container, false);

        /**
         *  Initialize all resources used for Handicap graph.
         */
        tvHandicapExact = (TextView) mRootView.findViewById(R.id.tvHandicapExact);
        tvHandicapPlaying = (TextView) mRootView.findViewById(R.id.tvHandicapPlaying);
        tvHandicapType = (TextView) mRootView.findViewById(R.id.tvHandicapType);

        setHasOptionsMenu(true);

        //Initialize Line Chart
        // InitializeGraph();

        return mRootView;
    }

    /**
     * Implements a method to Initialize the Graph
     * or chart with HANDICAP values.
     */
    private void InitializeGraph() {
        mChart = (LineChart) mRootView.findViewById(R.id.linechart);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        // no description text
        mChart.setDescription("");

        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.graph_marker_view, handicapData.get(0).getHCapRecords());

        // set the marker to the chart
        mChart.setMarkerView(mv);

        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        leftAxis.setEnabled(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        // add data
        setData(handicapData.get(0).getHCapRecords().size(), handicapData.get(0).getHCapRecords());

        // // restrain the maximum scale-out factor
        // mChart.setScaleMinima(3f, 3f);
        //
        // // center the view to a specific position inside the chart
        // mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        //  l.setForm(LegendForm.LINE);

        // dont forget to refresh the drawing


        mChart.invalidate();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            callHandicapWebService();
        }
    }

    /**
     * Implements a method to call HANDICAP web service to get
     * data from server.
     */
    private void callHandicapWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            //Method to hit Squads API.
            requestCompetitionsEvents();
        } else {
            ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Implement a method to hit HANDICAP
     * web service to get response.
     */
    public void requestCompetitionsEvents() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsHandicap = new AJsonParamsHandicap();
        aJsonParamsHandicap.setCallid("1456315336575");
        aJsonParamsHandicap.setVersion(1);
        aJsonParamsHandicap.setMemberid(10784);

        handicapAPI = new HandicapAPI(44118078, "GETMEMBERHCAP", aJsonParamsHandicap, "WEBSERVICES", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getHandicap(handicapAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();

                ((BaseActivity) getActivity()).showAlertMessage("" + error);
            }
        });

    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<HandicapResultItems>() {
        }.getType();
        handicapResultItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        handicapData.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (handicapResultItems.getMessage().equalsIgnoreCase("Success")) {

                handicapData.add(handicapResultItems.getData());

                if (handicapData.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    tvHandicapExact.setText(handicapData.get(0).getHCapExactStr());
                    tvHandicapPlaying.setText(handicapData.get(0).getHCapPlayStr());
                    tvHandicapType.setText(handicapData.get(0).getHCapTypeStr());

//                    setData(10, handicapData.get(0).getHCapRecords());
                    InitializeGraph();

//                    competitionsAdapter = new CompetitionsAdapter(getActivity(), competitionsDatas/*((CourseDiaryActivity)getActivity()).filterCourseDates(arrayCourseDataBackup)*/);
//                    lvFriends.setAdapter(competitionsAdapter);

                    Log.e(LOG_TAG, "arrayListCourseData : " + handicapData.size());
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(handicapResultItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.line, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                mChart.invalidate();
                break;
            }

            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionToggleFilled: {

                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getActivity(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();

                // mChart.saveToGallery("title"+System.currentTimeMillis())
                break;
            }
        }
        return true;
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }


    /**
     * Implements a method to initialize X-axis and Y-axis
     * to display on GRAPH.
     */
    private void setData(int count, List<HCapRecord> range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
           // xVals.add((i % 30) + "/" + (i % 12) + "/14");
            xVals.add(range.get(i).getDatePlayedStr());
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = Float.parseFloat((range.get(i).getNewExactHCapOnlyStr() + 1));
           // float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(mult, i));
        }

//         create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "");

        set1.setLineWidth(4f);
        set1.setCircleRadius(8f);
        set1.setColor(Color.parseColor("#F1958C"));
        set1.setValueTextColor(Color.TRANSPARENT);
        set1.setCircleColor(Color.parseColor("#F1958C"));

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        // set data
        mChart.setData(data);
        mChart.animateX(3000);
        mChart.invalidate();
    }
}