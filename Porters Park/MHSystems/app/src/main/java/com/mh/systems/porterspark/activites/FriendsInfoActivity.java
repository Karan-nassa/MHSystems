package com.mh.systems.porterspark.activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mh.systems.porterspark.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendsInfoActivity extends BaseActivity {

    @Bind(R.id.tbFriendInfo)
    Toolbar tbFriendInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_info);

        ButterKnife.bind(FriendsInfoActivity.this);

        setSupportActionBar(tbFriendInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.tb_title_frinds_list));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
