package com.mh.systems.brokenhurst.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.brokenhurst.R;
import com.mh.systems.brokenhurst.activites.BaseActivity;
import com.mh.systems.brokenhurst.activites.YourAccountActivity;
import com.mh.systems.brokenhurst.constants.ApplicationGlobal;
import com.mh.systems.brokenhurst.constants.WebAPI;
import com.mh.systems.brokenhurst.models.AJsonParamsMembersDatail;
import com.mh.systems.brokenhurst.models.MembersDetailAPI;
import com.mh.systems.brokenhurst.models.MembersDetailsItems;
import com.mh.systems.brokenhurst.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static com.mh.systems.brokenhurst.R.id.ivMessageSymbol;
import static com.mh.systems.brokenhurst.R.id.llUsernameOfPerson;

/**
 * The {@link NoInternetFragment} used to display the
 * sign of No Internet Connection found message.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 * @since 3rd Jan, 2017
 */
public class NoInternetFragment extends Fragment {

    @Bind(R.id.ivMessageSymbol) ImageView ivMessageSymbol;
    @Bind(R.id.tvMessageTitle) TextView tvMessageTitle;
    @Bind(R.id.tvMessageDesc)  TextView tvMessageDesc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootFragment = inflater.inflate(R.layout.include_display_message, container, false);

        //Initialize butter knife to initialize views.
        ButterKnife.bind(this, mRootFragment);

        ivMessageSymbol.setImageResource(R.mipmap.ic_no_internet);
        tvMessageTitle.setText(getString(R.string.error_no_internet));
        tvMessageDesc.setText(getString(R.string.error_try_again));

        return mRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}