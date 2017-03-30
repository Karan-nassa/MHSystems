package com.mh.systems.chesterLeStreet.ui.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Karan Nassa on 03-11-2016.
 * <p>
 * Organization : ucreate.it
 * Email        : karan@ucreate.it
 */
public class RobotoLight extends TextView {

    public RobotoLight(Context context) {
        super(context);
        setFont();
    }

    public RobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (defStyle == Typeface.ITALIC) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
            setTypeface(font, Typeface.ITALIC);
        }
    }

    private void setFont() {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}