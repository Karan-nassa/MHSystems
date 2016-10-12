package com.mh.systems.halesworth.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.activites.BaseActivity;
import com.mh.systems.halesworth.activites.HCapHistoryActivity;
import com.mh.systems.halesworth.activites.ShowCertificateWebview;
import com.mh.systems.halesworth.activites.YourAccountActivity;
import com.mh.systems.halesworth.constants.ApplicationGlobal;
import com.mh.systems.halesworth.constants.WebAPI;
import com.mh.systems.halesworth.models.AJsonParamsHandicap;
import com.mh.systems.halesworth.models.HCapRecords;
import com.mh.systems.halesworth.models.HandicapAPI;
import com.mh.systems.halesworth.models.HandicapData;
import com.mh.systems.halesworth.models.HandicapResultItems;
import com.mh.systems.halesworth.util.API.WebServiceMethods;
import com.mh.systems.halesworth.util.MyMarkerView;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by karan@mh.co.in to load and display Graph and
 * Certificate of Handicap.
 */
public class HandicapFragment extends Fragment implements OnChartValueSelectedListener {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = HandicapFragment.class.getSimpleName();
    ArrayList<HandicapData> alHandicapData = new ArrayList<>();

    private ArrayList<HCapRecords> hCapRecordsList = new ArrayList<>();
    private ArrayList<String> arrNameOfYear = new ArrayList<>();
    private ArrayList<ArrayList<HCapRecords>> jsonObjectArrayList = new ArrayList<>();

    /**
     * Used for YEAR index navigation of Graph if HANDICAP has
     * previous record.
     */
    private int mYearIndex;

    private String TAG_DATA = "Data";
    private String TAG_HCAP_RECORDS = "HCapRecords";
    private String TAG_DATE_PLAYED_STR = "DatePlayedStr";
    private String TAG_COMPETITION_OR_REASON = "CompetitionOrReason";
    private String TAG_NEW_EXACT_HCAP_ONLY_STR = "NewExactHCapOnlyStr";

    private boolean isClassVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    TextView tvHandicapExact, tvHandicapPlaying;// tvHandicapType;
    Button btShowCertificate;
    ImageView ivNextYearGraph, ivPreviousYearGraph;
    TextView tvDateOfPlayedStr, tvTitleOfPlayStr, tvTypeOfPlayStr;
    TextView tvSelectGraphYear, tvLatestGraphYear;
    LinearLayout llPreviousYearGraph, llNextYearGraph, llMonthNavigationGroup;

    LinearLayout llHandicapGroup;

    //List of type books this list will store type Book which is our data model
    private HandicapAPI handicapAPI;
    private AJsonParamsHandicap aJsonParamsHandicap;

    //Create instance of Model class CourseDiaryItems.
    HandicapResultItems handicapResultItems;

    private static LineChart mChart;
    LineDataSet set1;
    public static MyMarkerView mv;

    Button btDetailHacp;
    Intent intent;


    /**
     * Declares the Graph YEAR navigation listener to redraw
     * graph.
     */
    private View.OnClickListener mGraphNavListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.llPreviousYearGraph:
                    if (mYearIndex != 0) {
                        mYearIndex--;
                        refreshGraph();
                    }
                    break;

                case R.id.llNextYearGraph:
                    if (mYearIndex != (arrNameOfYear.size() - 1)) {
                        mYearIndex++;
                        refreshGraph();
                    }
                    break;
            }
            setNavigationIcons();
        }
    };

    /**
     * Navigate user to LATEST year of Graph.
     */
    private View.OnClickListener mLatestGraphListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (arrNameOfYear.size() > 0) {
                mYearIndex = (arrNameOfYear.size() - 1);

                setNavigationIcons();

                //setCompResultData(10, handicapData.get(0).getHCapRecordses());
                refreshGraph();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_handicap, container, false);

        //Initialize the resources.
        initializeResources();

        setHasOptionsMenu(true);

        //Check Internet connection and hit web service only on first time.
       /* isClassVisible = true;
        if (isClassVisible) {
            callHandicapWebService();
            ((YourAccountActivity) getActivity()).updateFilterIcon(8);
        }*/

        btShowCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ShowCertificateWebview.class);
                startActivity(intent);
            }
        });

        btDetailHacp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), HCapHistoryActivity.class);
                startActivity(intent);
            }
        });

        llPreviousYearGraph.setOnClickListener(mGraphNavListener);
        llNextYearGraph.setOnClickListener(mGraphNavListener);

        tvLatestGraphYear.setOnClickListener(mLatestGraphListener);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser /*&& isClassVisible*/) {
            callHandicapWebService();
            ((YourAccountActivity) getActivity()).updateFilterIcon(8);
        }
    }

    /**
     * Implements a method to call HANDICAP web service to get
     * data from server.
     */
    private void callHandicapWebService() {

        try {
            /**
             *  Check internet connection before hitting server request.
             */
            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
                requestCompetitionsEvents();
                ((YourAccountActivity) getActivity()).updateHasInternetUI(true);
                llHandicapGroup.setVisibility(View.VISIBLE);
            } else {
                ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
                llHandicapGroup.setVisibility(View.GONE);
            }
        }catch (Exception exp){
        }
    }

    /**
     * Implement a method to hit HANDICAP
     * web service to get response.
     */
    public void requestCompetitionsEvents() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsHandicap = new AJsonParamsHandicap();
        aJsonParamsHandicap.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsHandicap.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsHandicap.setMemberid(((YourAccountActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784"));

        handicapAPI = new HandicapAPI(getClientId(), "GETMEMBERHCAP", aJsonParamsHandicap, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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
                ((BaseActivity) getActivity()).showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });

    }

    /**
     * Implements a method to get CLIENT ID from {@link android.content.SharedPreferences}
     */
    private String getClientId() {
        return ((YourAccountActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
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
        alHandicapData.clear();
        jsonObjectArrayList.clear();
        arrNameOfYear.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (handicapResultItems.getMessage().equalsIgnoreCase("Success")) {

                alHandicapData.add(handicapResultItems.getData());

                if (alHandicapData.get(0).getHCapRecords().size() == 0) {
                    ((YourAccountActivity) getActivity()).updateNoCompetitionsUI(false);
                    // ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                    llMonthNavigationGroup.setVisibility(View.GONE);
                } else {
                    ((YourAccountActivity) getActivity()).updateNoCompetitionsUI(true);

                    tvHandicapExact.setText(alHandicapData.get(0).getHCapExactStr());
                    tvHandicapPlaying.setText(alHandicapData.get(0).getHCapPlayStr());
                    // tvHandicapType.setText(alHandicapData.get(0).getHCapTypeStr());

                    //JsonParser parser = new JsonParser();
                    //JsonObject responseObj = parser.parse(String.valueOf(jsonObject)).getAsJsonObject();
                    JsonObject jHCapData = jsonObject.getAsJsonObject(TAG_DATA);
                    JsonArray jHCapRecordsArr = jHCapData.getAsJsonArray(TAG_HCAP_RECORDS);

                    for (int iCounter = 0; iCounter < jHCapRecordsArr.size(); iCounter++) {

                        JsonObject jHCapRecordOfYear = jHCapRecordsArr.get(iCounter).getAsJsonObject();

                        hCapRecordsList = new ArrayList<>();

                        for (Map.Entry<String, JsonElement> entry : jHCapRecordOfYear.entrySet()) {

                            JsonArray jsonArray = jHCapRecordOfYear.getAsJsonArray(entry.getKey());

                            /**
                             *  Create list of YEARS.
                             */
                            arrNameOfYear.add(entry.getKey());

                            for (int jCounter = 0; jCounter < jsonArray.size(); jCounter++) {

                                JsonObject jsonHCapRecord = jsonArray.get(jCounter).getAsJsonObject();

                                HCapRecords hCapRecords = new HCapRecords();

                                hCapRecords.setDatePlayedStr("" + jsonHCapRecord.get(TAG_DATE_PLAYED_STR));
                                hCapRecords.setCompetitionOrReason("" + jsonHCapRecord.get(TAG_COMPETITION_OR_REASON));
                                hCapRecords.setNewExactHCapOnlyStr("" + jsonHCapRecord.get(TAG_NEW_EXACT_HCAP_ONLY_STR));

                                hCapRecordsList.add(hCapRecords);
                            }
                        }
                        Collections.reverse(hCapRecordsList);

                        jsonObjectArrayList.add(hCapRecordsList);
                    }

                    Collections.reverse(jsonObjectArrayList);
                    Collections.reverse(arrNameOfYear);

                    mYearIndex = (arrNameOfYear.size() - 1);

                    setNavigationIcons();

                    //setCompResultData(10, handicapData.get(0).getHCapRecordses());
                    refreshGraph();

                    //Log.e(LOG_TAG, "arrayListCourseData : " + hCapRecordsList.size());
                }
            } else {
                ((YourAccountActivity) getActivity()).updateNoCompetitionsUI(false);
                //If web service not respond in any case.
                // ((BaseActivity) getActivity()).showAlertMessage(handicapResultItems.getMessage());
            }

            //Dismiss progress dialog.
            ((BaseActivity) getActivity()).hideProgress();
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);

        loadDetailGraphInfo(jsonObjectArrayList.get(mYearIndex), e.getXIndex());

//        mv.refreshContent(e, h);
        //mChart.highlightValue(null, true);
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub
    }

    /**
     * Define globally method to display detail information
     * of Handicap on graph.
     *
     * @param hCapRecordses
     * @param size
     */
    public void loadDetailGraphInfo(ArrayList<HCapRecords> hCapRecordses, int size) {
        tvDateOfPlayedStr.setText(hCapRecordses.get(size).getDatePlayedStr().replace("\"", ""));
        tvTitleOfPlayStr.setText(hCapRecordses.get(size).getCompetitionOrReason().replace("\"", ""));
        tvTypeOfPlayStr.setText(hCapRecordses.get(size).getNewExactHCapOnlyStr().replace("\"", ""));
    }

    /**
     * Implements a method to initialize X-axis and Y-axis
     * to display on GRAPH.
     */
    private void set_Data(int count, List<HCapRecords> range) {

        /**
         * Reverse the Handicap graph records for annually
         * ascending order.
         */
        //Collections.reverse(range);

        loadDetailGraphInfo(jsonObjectArrayList.get(mYearIndex), jsonObjectArrayList.get(mYearIndex).size() - 1);

        tvSelectGraphYear.setText(arrNameOfYear.get(mYearIndex));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // xVals.add((i % 30) + "/" + (i % 12) + "/14");
            xVals.add(((YourAccountActivity) getActivity()).getFormateMonth(range.get(i).getDatePlayedStr().toString().replace("\"", "")));
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = Float.parseFloat((range.get(i).getNewExactHCapOnlyStr().replace("\"", "") + 1));
            yVals.add(new Entry(mult, i));
        }

//         create a dataset and give it a type
        set1 = new LineDataSet(yVals, "");

        set1.setLineWidth(2f);
        set1.setCircleRadius(5f);

        //For dot on graph.
        // set1.setCircleColorHole(ContextCompat.getColor(getActivity(), R.color.colorEF8176));

        set1.setColor(ContextCompat.getColor(getActivity(), R.color.colorEF8176));
        set1.setValueTextColor(Color.TRANSPARENT);
        set1.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorEF8176));

        set1.setDrawFilled(true);
//         set1.setFillColor(ContextCompat.getColor(getActivity(), R.color.colorEF8176));
//         set1.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorEF8176));
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_graph_shade);
        set1.setFillDrawable(drawable);

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        // set data
        mChart.setData(data);
        mChart.animateX(1500);
        mChart.invalidate();
    }

    /**
     * Implements a method to Initialize the Graph
     * or chart with HANDICAP values.
     */
    private void refreshGraph() {

        if (jsonObjectArrayList.size() != 0) {

            mChart = (LineChart) viewRootFragment.findViewById(R.id.linechart);
            mChart.setOnChartValueSelectedListener(this);
            mChart.setDrawGridBackground(false);
            // no description text
            mChart.setDescription("");

            // enable touch gestures
            mChart.setTouchEnabled(true);
            // enable scaling and dragging
            mChart.setDragEnabled(false);
            mChart.setScaleEnabled(false); //Zoom functioanlity
            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(false);

            // set an alternative background color
            // mChart.setBackgroundColor(Color.GRAY);

            // create a custom MarkerView (extend MarkerView) and specify the layout
            // to use for it
            mv = new MyMarkerView(getActivity(), R.layout.graph_marker_view, jsonObjectArrayList.get(mYearIndex));

            // set the marker to the chart
            mChart.setMarkerView(mv);

            //Disable shows data set labels and colors of Graph.
            mChart.getLegend().setEnabled(false);

            XAxis xl = mChart.getXAxis();
            xl.setAvoidFirstLastClipping(true);
            xl.setDrawGridLines(false);
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setInverted(false);
            leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
            leftAxis.setEnabled(true);
            leftAxis.setDrawGridLines(false);
            // leftAxis.setAxisMaxValue(30);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setEnabled(false);

            // add data
            set_Data(jsonObjectArrayList.get(mYearIndex).size(), jsonObjectArrayList.get(mYearIndex));

            // // restrain the maximum scale-out factor
            // mChart.setScaleMinima(3f, 3f);
            //
            // // center the view to a specific position inside the chart
            // mChart.centerViewPort(10, 50);

            // get the legend (only possible after ic_home_settings data)
            Legend l = mChart.getLegend();

            // modify the legend ...
//         l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
//          l.setForm(Legend.LegendForm.LINE);

            // dont forget to refresh the drawing
            mChart.invalidate();

            mChart.setOnChartValueSelectedListener(this);
        }
    }

    /**
     * Implements a method to update navigation icons
     * for GRAPH.
     */
    private void setNavigationIcons() {

        if (mYearIndex == 0) {
            /*ivPreviousYearGraph.setImageResource(R.mipmap.ic_arrow_left_blur);*/
            ivPreviousYearGraph.setAlpha((float) 0.3);
        } else if (mYearIndex == (arrNameOfYear.size() - 1)) {
           /* ivNextYearGraph.setImageResource(R.mipmap.ic_arrow_right_blur);*/
            ivNextYearGraph.setAlpha((float) 0.3);
            ivPreviousYearGraph.setAlpha((float) 1.0); //ENABLE previous button when mYearIndex
        } else {
            ivPreviousYearGraph.setAlpha((float) 1.0);
            ivNextYearGraph.setAlpha((float) 1.0);
            ivNextYearGraph.setImageResource(R.mipmap.ic_date_nextmonth);
            ivPreviousYearGraph.setImageResource(R.mipmap.ic_date_prevmonth);
        }
    }

    /**
     * Implements a method to initialize all resources
     * used for Handicap graph.
     */
    private void initializeResources() {
        tvHandicapExact = (TextView) viewRootFragment.findViewById(R.id.tvHandicapExact);
        tvHandicapPlaying = (TextView) viewRootFragment.findViewById(R.id.tvHandicapPlaying);
        //  tvHandicapType = (TextView) viewRootFragment.findViewById(R.id.tvHandicapType);
        tvDateOfPlayedStr = (TextView) viewRootFragment.findViewById(R.id.tvDateOfPlayedStr);
        tvTitleOfPlayStr = (TextView) viewRootFragment.findViewById(R.id.tvTitleOfPlayStr);
        tvTypeOfPlayStr = (TextView) viewRootFragment.findViewById(R.id.tvTypeOfPlayStr);
        tvSelectGraphYear = (TextView) viewRootFragment.findViewById(R.id.tvSelectGraphYear);

        tvLatestGraphYear = (TextView) viewRootFragment.findViewById(R.id.tvLatestGraphYear);

        btShowCertificate = (Button) viewRootFragment.findViewById(R.id.btShowCertificate);
        btDetailHacp = (Button) viewRootFragment.findViewById(R.id.btDetailHacp);

        llPreviousYearGraph = (LinearLayout) viewRootFragment.findViewById(R.id.llPreviousYearGraph);
        llNextYearGraph = (LinearLayout) viewRootFragment.findViewById(R.id.llNextYearGraph);
        llMonthNavigationGroup = (LinearLayout) viewRootFragment.findViewById(R.id.llMonthNavigationGroup);

        llHandicapGroup = (LinearLayout) viewRootFragment.findViewById(R.id.llHandicapGroup);

        ivPreviousYearGraph = (ImageView) viewRootFragment.findViewById(R.id.ivPreviousYearGraph);
        ivNextYearGraph = (ImageView) viewRootFragment.findViewById(R.id.ivNextYearGraph);
    }

}