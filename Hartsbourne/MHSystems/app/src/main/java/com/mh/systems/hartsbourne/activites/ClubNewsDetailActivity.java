package com.mh.systems.hartsbourne.activites;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mh.systems.hartsbourne.R;

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

        switch (getIntent().getExtras().getInt("NEWS_POS")){
            case 0:
                tvDescOfNews.setText("These offers are only available when you book your table online, for free, through Skiddle.");
                break;

            case 1:
                tvDescOfNews.setText("Whether you’re a long-time lover of the genre or a total newbie, London’s got something to keep all jazz fans entertained. With top-notch nights taking place.");
                break;

            case 2:
                tvDescOfNews.setText("Many people have asked why golf courses have eighteen holes and this is now the universal format played today. The early golf courses all had different numbers of holes and were not always played in a defined order, as evidenced at Earlsferry. The order of play on Aberdeen Links is known to have been laid out in August 1780, but the layouts below were probably established much earlier");
                break;
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
