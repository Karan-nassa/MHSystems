package com.mh.systems.sunningdale.activites;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.sunningdale.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The {@link SettingsActivity} used to RESET password for now. Rest functionality
 * implement when come.
 *
 * @author {@link karan@mh.co.in}
 * @version 1.0
 * @since 24 JUNE, 2016
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.llResetPassword)
    LinearLayout llResetPassword;

    @Bind(R.id.llContactUs)
    LinearLayout llContactUs;

    Intent intent;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/

    @Bind(R.id.tvVersionName)
    TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize all view resources.
        ButterKnife.bind(SettingsActivity.this);

        showVersionName();
    }

    @OnClick({R.id.llHomeIcon, R.id.llResetPassword, R.id.llContactUs})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.llHomeIcon:
                onBackPressed();
                break;

            case R.id.llResetPassword:
                intent = new Intent(SettingsActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.llContactUs:
                // intent = new Intent(SettingsActivity.this, ContactUsActivity.class);
                // startActivity(intent);
                break;
        }
    }

    /**
     * Implements this method to display
     * version name of App.
     */
    private void showVersionName() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tvVersionName.setText("V " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
