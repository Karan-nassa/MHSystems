package com.mh.systems.littlehampton.ui.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by karan@ucreate.co.in to create custom
 * font style on 15-03-2016.
 */
public class ButlerMediumTextView extends TextView {

    public ButlerMediumTextView(Context context) {
        super(context);
        setFont();
    }

    public ButlerMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public ButlerMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (defStyle == Typeface.ITALIC) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Butler_Medium.otf");
            setTypeface(font, Typeface.ITALIC);
        }

        //setFont();
    }

    private void setFont() {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Butler_Medium.otf");
       // setTypeface(font, Typeface.ITALIC);
    }

}