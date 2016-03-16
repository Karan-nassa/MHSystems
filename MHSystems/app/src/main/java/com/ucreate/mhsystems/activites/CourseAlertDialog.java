package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ucreate.mhsystems.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by karan@ucreate.co.in to display
 * alert dialog of COURSE DIARY on 16-03-2016.
 */
public class CourseAlertDialog extends AppCompatActivity {

    @Bind(R.id.tvCloseDialog)
    TextView tvCloseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(CourseAlertDialog.this); //Initialize facebook Fresco for round profile pic.
        setContentView(R.layout.activity_alert_dialog);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(CourseAlertDialog.this);
    }

    public void onClose(View view){

        onBackPressed();
    }
}