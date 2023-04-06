package com.woleapp.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.appcompat.widget.AppCompatEditText;

/**
 *
 */

// For disabling enter on soft keyboard

public class EditTextNoEnter extends AppCompatEditText
{
    public EditTextNoEnter(Context context){
        super(context);
    }
    public EditTextNoEnter(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public EditTextNoEnter(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        //initVideoView();
    }

//    public void initVideoView()
//    {
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
//                "fonts/SF-UI-Display-Thin.otf");
//
//        setTypeface(tf);
//    }
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }
}
