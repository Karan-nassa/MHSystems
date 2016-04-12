package com.ucreate.mhsystems.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.AlphabetListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class MembersActivity extends AppCompatActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/

    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    ListView lvMembersList;

    private AlphabetListAdapter adapter = new AlphabetListAdapter();
    private GestureDetector mGestureDetector;
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();


    /**
     * Declaration of Side alphabet indexing gesture.
     */
    class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

        lvMembersList = (ListView) findViewById(R.id.lvMembersList);

        List<String> countries = populateCountries();
        Collections.sort(countries);

        List<AlphabetListAdapter.Row> rows = new ArrayList<AlphabetListAdapter.Row>();
        int start = 0;
        int end = 0;
        String previousLetter = null;
        Object[] tmpIndexItem = null;
        Pattern numberPattern = Pattern.compile("[0-9]");

        for (String country : countries) {
            String firstLetter = country.substring(0, 1);

            // Group numbers together in the scroller
            if (numberPattern.matcher(firstLetter).matches()) {
                firstLetter = "#";
            }

            // If we've changed to a new letter, add the previous letter to the alphabet scroller
            if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                end = rows.size() - 1;
                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                tmpIndexItem[1] = start;
                tmpIndexItem[2] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }

            // Check if we need to add a header row
            if (!firstLetter.equals(previousLetter)) {
                rows.add(new AlphabetListAdapter.Section(firstLetter));
                sections.put(firstLetter, start);
            }

            // Add the country to the list
            rows.add(new AlphabetListAdapter.Item(country));
            previousLetter = firstLetter;
        }

        if (previousLetter != null) {
            // Save the last letter
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        adapter.setRows(rows);
        lvMembersList.setAdapter(adapter);

        updateList();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateList() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.llSideIndex);
        sideIndex.removeAllViews();
        indexListSize = alphabet.size();
        if (indexListSize < 1) {
            return;
        }

        int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
        int tmpIndexListSize = indexListSize;
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }
        double delta;
        if (tmpIndexListSize > 0) {
            delta = indexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        TextView tmpTV;
        for (double i = 1; i <= indexListSize; i = i + delta) {
            Object[] tmpIndexItem = alphabet.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();

            tmpTV = new TextView(this);
            tmpTV.setText(tmpLetter);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tmpTV.setLayoutParams(params);
            sideIndex.addView(tmpTV);
        }

        sideIndexHeight = sideIndex.getHeight();

        sideIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // now you know coordinates of touch
                sideIndexX = event.getX();
                sideIndexY = event.getY();

                // and can display a proper item it country list
                displayListItem();

                return false;
            }
        });
    }

    public void displayListItem() {
        LinearLayout llSideIndex = (LinearLayout) findViewById(R.id.llSideIndex);
        sideIndexHeight = llSideIndex.getHeight();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = sections.get(indexItem[0]);

            //ListView listView = (ListView) findViewById(android.R.id.list);
            lvMembersList.setSelection(subitemPosition);
        }
    }

    private List<String> populateCountries() {
        List<String> countries = new ArrayList<String>();
        countries.add("Abas Mr Hassan");
        countries.add("Abecasis Miss Nathalie");
        countries.add("Abecasis Mr Sebastian");
        countries.add("Bain Mr William");
        countries.add("Barry-Walsh Mr Edward");
        countries.add("Zuill Mr Stewart");
        countries.add("Zimmerman Mr Stephen");
        countries.add("Yurko Mr Allen");
        countries.add("Young Mr Robin");
        countries.add("Willian Mr John Stuart");
        countries.add("Turner Mr Roger");
        countries.add("Sturgeon Mr Benjamin");
        countries.add("Spencer Mr Callum");
        countries.add("Men");
        countries.add("Sharp Mr William");
        countries.add("Roy Mrs Lesley Ann");
        countries.add("Posford Mr Stephen");
        countries.add("Oldmeadow Mr Charles");
        countries.add("Naylor-Leyland Mrs Jane");
        countries.add("Naylor-Leyland Mr David");
        countries.add("Nash Lord John");
        countries.add("Murr Mr Michael");
        countries.add("Lynch Mr Alexander");
        countries.add("Keenan Mr Farren");
        countries.add("Keenan Miss Ellis");
        countries.add("Holland Mr Charles");
        countries.add("Holland Mr Charles");
        countries.add("Grant Mr Hugh");
        countries.add("Foster Mr Christopher");
        countries.add("Fernandez Mr Luis Javier");
        countries.add("Edgeworth Jr. Mr James");
        countries.add("Durrant Mr Brian");
        countries.add("Caldwell Mr Philip");
        countries.add("Caldwell Mr Richard");
        countries.add("JOHNBARTON");
        return countries;
    }
}
