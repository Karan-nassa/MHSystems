package com.mh.systems.redlibbets.ui.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by karan@ucreate.co.in to create custom
 * font style of ROBOTO on 07-04-2016.
 */
public class RobotoMediumTextView extends TextView {

    public RobotoMediumTextView(Context context) {
        super(context);
        setFont();
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (defStyle == Typeface.ITALIC) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
            setTypeface(font, Typeface.ITALIC);
        }
    }

    private void setFont() {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}