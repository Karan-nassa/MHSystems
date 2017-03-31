package com.mh.systems.sunningdale.ui.activites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.ui.fragments.NewsWebCamTabFragment;

import java.io.InputStream;

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

    /**
     * Used this {@link AsyncTask} class to get
     * image from URL.
     */
    public class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showPleaseWait("Please wait...");
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {

            hideProgress();
            imageView.setImageBitmap(result);
        }
    }
}
