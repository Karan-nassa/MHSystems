package com.mh.systems.corrstown.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.systems.corrstown.R;

import butterknife.Bind;
import butterknife.ButterKnife;

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