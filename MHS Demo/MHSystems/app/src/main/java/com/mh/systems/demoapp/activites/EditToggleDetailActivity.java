package com.mh.systems.demoapp.activites;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mh.systems.demoapp.R;


public class EditToggleDetailActivity extends BaseActivity implements View.OnClickListener {

    Toolbar tbEditToggleDetail;
    EditText etEditEmail, etEditMobile, etEditWork, etEditHome,
            etEditAddress1, etEditAddress2, etEditTown, etEditPostCode, etEditCountry;
    Typeface typeface;
    //CountryPicker countryPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_toggle_detail);

        initializeViews();
        //countryPicker = CountryPicker.newInstance("Select Country");

        tbEditToggleDetail = (Toolbar) findViewById(R.id.tbEditToggleDetail);
        if (tbEditToggleDetail != null) {
            setSupportActionBar(tbEditToggleDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        countryPicker.setListener(new CountryPickerListener() {
//            @Override
//            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
//                countryPicker.dismiss();
//                etEditCountry.setText(name);
//            }
//        });
    }

    /* **
     * Method to initialize views
     * **/
    private void initializeViews() {
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        etEditEmail = (EditText) findViewById(R.id.etEditEmail);
        etEditEmail.setTypeface(typeface);
        etEditMobile = (EditText) findViewById(R.id.etEditMobile);
        etEditMobile.setTypeface(typeface);
        etEditWork = (EditText) findViewById(R.id.etEditWork);
        etEditWork.setTypeface(typeface);
        etEditHome = (EditText) findViewById(R.id.etEditHome);
        etEditHome.setTypeface(typeface);
        etEditAddress1 = (EditText) findViewById(R.id.etEditAddress1);
        etEditAddress1.setTypeface(typeface);
        etEditAddress2 = (EditText) findViewById(R.id.etEditAddress2);
        etEditAddress2.setTypeface(typeface);
        etEditTown = (EditText) findViewById(R.id.etEditTown);
        etEditTown.setTypeface(typeface);
        etEditPostCode = (EditText) findViewById(R.id.etEditPostCode);
        etEditPostCode.setTypeface(typeface);
        etEditCountry = (EditText) findViewById(R.id.etEditCountry);
        etEditCountry.setTypeface(typeface);
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

            default:
                break;
        }
        return true;
    }


    /* **
     * On Click Listener
     * **/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etEditCountry:
               // countryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                break;
        }
    }
}