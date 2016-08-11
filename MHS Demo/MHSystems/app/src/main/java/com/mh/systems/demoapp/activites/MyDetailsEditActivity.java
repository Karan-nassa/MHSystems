package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.fragments.FinanceFragment;
import com.mh.systems.demoapp.models.AJsonParamsMembersDatail;
import com.mh.systems.demoapp.models.MembersDetailAPI;
import com.mh.systems.demoapp.models.MembersDetailsItems;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MyDetailsEditActivity extends BaseActivity implements View.OnClickListener {

    Toolbar tbMyDetailEdit;

    EditText etEditEmail, etEditMobile, etEditWork, etEditHome,
            etEditAddress1, etEditAddress2, etEditTown, etEditPostCode, etEditCountry;
    Typeface typeface;

    CountryPicker countryPicker;

    //Pop Menu to show Categories of Course Diary.
    PopupMenu popupMenu;

    Intent intent;

    /**
     * Declares the click event handling FIELD to set categories
     * of Your Account Finance {@link Fragment}.
     */
    public PopupMenu.OnMenuItemClickListener mCourseTypeListener =
            new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.item_toggle_mode:
                            intent = new Intent(MyDetailsEditActivity.this, EditToggleDetailActivity.class);
                            startActivity(intent);

                            break;

                        case R.id.item_edit_mode:
                            intent = new Intent(MyDetailsEditActivity.this, MyDetailsEditActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details_edit);

        tbMyDetailEdit = (Toolbar) findViewById(R.id.tbMyDetailEdit);
        if (tbMyDetailEdit != null) {
            setSupportActionBar(tbMyDetailEdit);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();

        countryPicker = CountryPicker.newInstance("Select Country");

        countryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                countryPicker.dismiss();
                etEditCountry.setText(name);
                countryPicker.getSearchEditText().setText("");
            }
        });

        etEditCountry.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_privacy, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.item_edit_mode:
                break;

            case R.id.item_toggle_mode:
                intent = new Intent(MyDetailsEditActivity.this, EditToggleDetailActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * Method to initialize views
     **/
    private void initializeViews() {
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        etEditEmail = (EditText) findViewById(R.id.etEditEmail);
        etEditMobile = (EditText) findViewById(R.id.etEditMobile);
        etEditWork = (EditText) findViewById(R.id.etEditWork);
        etEditHome = (EditText) findViewById(R.id.etEditHome);
        etEditAddress1 = (EditText) findViewById(R.id.etEditAddress1);
        etEditAddress2 = (EditText) findViewById(R.id.etEditAddress2);
        etEditTown = (EditText) findViewById(R.id.etEditTown);
        etEditPostCode = (EditText) findViewById(R.id.etEditPostCode);
        etEditCountry = (EditText) findViewById(R.id.etEditCountry);


        setTypeFace();
    }

    private void setTypeFace() {
        etEditEmail.setTypeface(typeface);
        etEditMobile.setTypeface(typeface);
        etEditWork.setTypeface(typeface);
        etEditHome.setTypeface(typeface);
        etEditAddress1.setTypeface(typeface);
        etEditAddress2.setTypeface(typeface);
        etEditTown.setTypeface(typeface);
        etEditPostCode.setTypeface(typeface);
        etEditCountry.setTypeface(typeface);
    }

    /**
     * On Click Listener
     **/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etEditCountry:
                countryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                break;
        }
    }

    /*************************** START OF WEB SERVICE IMPLEMENTATION TO GET MEMBER DETAIL AND SET IN INPUT FIELDS ***************************/


    /*************************** END OF WEB SERVICE IMPLEMENTATION TO GET MEMBER DETAIL AND SET IN INPUT FIELDS ***************************/
}
