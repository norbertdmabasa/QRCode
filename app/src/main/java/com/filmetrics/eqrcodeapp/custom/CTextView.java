package com.filmetrics.eqrcodeapp.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Norbert Mabasa on 1/15/2018.
 */

public class CTextView extends android.support.v7.widget.AppCompatTextView {

    public CTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "verdana.ttf");
            setTypeface(tf);
        }
    }

}