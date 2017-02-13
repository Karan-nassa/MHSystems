package com.mh.systems.sunningdale.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.fragments.NewsWebCamTabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubNewsWebCamActivity extends BaseActivity {


    @Bind(R.id.tbNewsWebCam)
    Toolbar tbNewsWebCam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_news_web_cam);

        //Initialize view resources.
        ButterKnife.bind(this);

        if (tbNewsWebCam != null) {
            setSupportActionBar(tbNewsWebCam);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.text_title_webcams));
        }

        updateFragment(new NewsWebCamTabFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }
}
