package com.mh.systems.demoapp.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by karan@mh.co.in to create custom
 * font style on 15-03-2016.
 */
public class BaskervilleTextFont extends TextView {

    public BaskervilleTextFont(Context context) {
        super(context);
        setFont();
    }

    public BaskervilleTextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public BaskervilleTextFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (defStyle == Typeface.ITALIC) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/baskvl.ttf");
            setTypeface(font, Typeface.ITALIC);
        }

        //setFont();
    }

    private void setFont() {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/baskvl.ttf");
        setTypeface(font, Typeface.ITALIC);
    }

}