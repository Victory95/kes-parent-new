package com.fingertech.kes.Activity.Model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.fingertech.kes.R;

public class SquareFloatButton extends android.support.v7.widget.AppCompatImageButton {
    private String customAttr;

    public SquareFloatButton(Context context ) {
        this( context, null );
    }

    public SquareFloatButton(Context context, AttributeSet attrs ) {
        this( context, attrs, R.attr.imageButtonStyle );
    }

    public SquareFloatButton(Context context, AttributeSet attrs,
                               int defStyle ) {
        super( context, attrs, defStyle );

    }
}
