package com.woleapp.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditText extends AppCompatEditText {

    /**
     * @param context
     */
    public CustomEditText(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSelection(int start, int stop) {
        //Log.d(SkeletonAppActivity.TOSS, "setSelection");
        super.setSelection(start, stop);
    }


    @Override
    public void setSelection(int index) {
        //Log.d(SkeletonAppActivity.TOSS, "selectionChanged");
        super.setSelection(index);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        setSelection(getText().length());
        super.onDraw(canvas);
    }

}
