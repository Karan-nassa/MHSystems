package com.mh.systems.brokenhurst.activites;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mh.systems.brokenhurst.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubNewsDetailActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    @Bind(R.id.tbClubNewsDetail)
    Toolbar tbClubNewsDetail;

    @Bind(R.id.tvDateOfNews)
    TextView tvDateOfNews;

    @Bind(R.id.tvTimeOfNews)
    TextView tvTimeOfNews;

    @Bind(R.id.tvDescOfNews)
    TextView tvDescOfNews;

    Typeface tfSFUI_TextRegular;

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_news_detail);

        //Initialize view resources.
        ButterKnife.bind(this);

        initializeAllResources();

        if (tbClubNewsDetail != null) {
            setSupportActionBar(tbClubNewsDetail);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_club_news, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_delete:
                Log.e("Club News", "Delete item clicked...");
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * Implements a method to initialze all view resources and
     * font style.
     */
    private void initializeAllResources() {
        tfSFUI_TextRegular = Typeface.createFromAsset(getAssets(), "fonts/SF-UI-Text-Regular.otf");

        tvDateOfNews.setTypeface(tfSFUI_TextRegular);
        tvTimeOfNews.setTypeface(tfSFUI_TextRegular);
        tvDescOfNews.setTypeface(tfSFUI_TextRegular);
    }
}
