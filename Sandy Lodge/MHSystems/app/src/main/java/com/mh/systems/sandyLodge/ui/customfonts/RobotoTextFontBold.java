package com.mh.systems.sandylodge.ui.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by karan@ucreate.co.in to create custom
 * font style of ROBOTO on 07-04-2016.
 *
 * @Ref : http://stackoverflow.com/questions/6746665/accessing-a-font-under-assets-folder-from-xml-file-in-android
 */
public class RobotoTextFontBold extends TextView {

    static Typeface font;

    public RobotoTextFontBold(Context context) {
        super(context);
        setFont();
    }

    public RobotoTextFontBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoTextFontBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (defStyle == Typeface.ITALIC) {
            font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(font, Typeface.ITALIC);
        }
    }

    private void setFont() {

        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}