package com.mh.systems.redlibbets.ui.activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.mh.systems.redlibbets.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendsInfoActivity extends BaseActivity {

    @Bind(R.id.tbFriendInfo)
    Toolbar tbFriendInfo;

    @Bind(R.id.wvFriendsInfo)
    WebView wvFriendsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_info);

        ButterKnife.bind(FriendsInfoActivity.this);

        setSupportActionBar(tbFriendInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.tb_title_frinds_list));

        String text = "<html><body>"
                + "<p align=\"justify\">"
                + "The 'Friend' function forms part of the Members Section of the mobile App. A member can add a Friend by selecting the member from the members list and clicking on the 'Add to Friend List' Icon. This enables the member to enter competitions on behalf of the 'Friend'. The new 'friend' is then added to the friends section under the 'Your Friend' list. The member can remove a 'friend' by clicking on the member's name in the 'Your Friend' list and then clicking on the 'Remove' icon. This activates an Alert asking the member to confirm the removal by clicking on 'Remove'.<br><br>If a another member adds you as a Friend it will be displayed in the 'Added Me' list with the details of the member who added you as a friend."
                + "</p> "
                + "</body></html>";

        wvFriendsInfo.loadData(text, "text/html", "utf-8");
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
        return super.onOptionsItemSelected(item);
    }
}
