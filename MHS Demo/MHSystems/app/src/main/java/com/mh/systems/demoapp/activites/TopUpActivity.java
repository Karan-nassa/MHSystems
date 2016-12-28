package com.mh.systems.demoapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mh.systems.demoapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create {@link TopUpActivity} is used to implements the FSI
 * payment Gateway. Top up balance sheet coming from AWS Server.
 *
 * @author karan@ucreate.co.in
 */
public class TopUpActivity extends AppCompatActivity {

    @Bind(R.id.tbTopUp)
    Toolbar tbTopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        ButterKnife.bind(TopUpActivity.this);

        setSupportActionBar(tbTopUp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_title_topup));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
