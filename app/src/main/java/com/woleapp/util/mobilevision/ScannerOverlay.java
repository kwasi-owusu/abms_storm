package com.woleapp.util.mobilevision;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
//import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import com.woleapp.R;
//import com.checkinscansl.checkinscan.R;

//import com.kavya.checkinscan.R;

/**
 * Created by ravi on 04/05/17.
 */

public class ScannerOverlay extends ViewGroup {
    private float left, top, endY;
    private int rectWidth, rectHeight;
    private int frames;
    private boolean revAnimation;
    private int lineColor, lineWidth;
    Bitmap bitmap;
    RectF rect;

    public ScannerOverlay(Context context) {
        super(context);
    }

    public ScannerOverlay(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScannerOverlay(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ScannerOverlay,
                0, 0);
        rectWidth = a.getInteger(R.styleable.ScannerOverlay_square_width, getResources().getInteger(R.integer.scanner_rect_width));
        rectHeight = a.getInteger(R.styleable.ScannerOverlay_square_height, getResources().getInteger(R.integer.scanner_rect_height));
        lineColor = a.getColor(R.styleable.ScannerOverlay_line_color, ContextCompat.getColor(context, R.color.colorPrimary));
        lineWidth = a.getInteger(R.styleable.ScannerOverlay_line_width, getResources().getInteger(R.integer.line_width));
        frames = a.getInteger(R.styleable.ScannerOverlay_line_speed, getResources().getInteger(R.integer.line_width));
        //Drawable shape = getResources().getDrawable(R.drawable.rect1);
        //bitmap = Bitmap.createBitmap( shape.getIntrinsicWidth(), shape.getIntrinsicHeight(), Bitmap.Config.ARGB_8888 );

//        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.scan_line);
//        bitmap = bitmap.copy(ARGB_8888 ,true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        left = (w - dpToPx(rectWidth)) / 2;
        top = (h - dpToPx(rectHeight)) / 2;
        endY = top;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // draw transparent rect
        int cornerRadius = 0;
        Paint eraser = new Paint();
        eraser.setAntiAlias(true);
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//CLEAR

//        eraser.setStrokeWidth(5);
//        eraser.setColor(Color.RED);
//
//        eraser.setStyle(Paint.Style.STROKE);
        //addFrame(canvas);

        rect = new RectF(left, top, dpToPx(rectWidth) + left, dpToPx(rectHeight) + top);

        //RectF rect = new RectF(left, top, rectWidth + left, rectHeight + top);

        canvas.drawRoundRect(rect, (float) cornerRadius, (float) cornerRadius, eraser);

        drawCameraCorners(canvas);

//        bitmap.setHeight(rectHeight);
//        bitmap.setWidth(rectWidth);



//        bitmap.setHeight(dpToPx(rectHeight));
//        bitmap.setWidth(dpToPx(rectWidth));
//        canvas.drawBitmap(bitmap,left, top,eraser);

        // draw horizontal line

        Paint line = new Paint();
        line.setColor(lineColor);
        line.setStrokeWidth(Float.valueOf(lineWidth));

        // draw the line to product animation
        if (endY >= top + dpToPx(rectHeight) + frames) {
            revAnimation = true;
        } else if (endY == top + frames) {
            revAnimation = false;
        }

        // check if the line has reached to bottom
        if (revAnimation) {
            endY -= frames;
        } else {
            endY += frames;
        }

        canvas.drawLine(left, endY, left + dpToPx(rectWidth), endY, line);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(left, endY, left + dpToPx(rectWidth), endY, line);
//        }
//        else
//        {
//            canvas.drawLine(left, endY, left + dpToPx(rectWidth), endY, line);
//        }
        invalidate();
    }


    public void drawCameraCorners(Canvas canvas)
    {
        Paint line11 = new Paint();
        line11.setColor(Color.RED);
        line11.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, dpToPx(rectWidth)-100, top, line1);//dpToPx(rectWidth)/4
        canvas.drawLine(left, top, left+100, top, line11);//dpToPx(rectWidth)/4

        Paint line12 = new Paint();
        line12.setColor(Color.RED);
        line12.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, left, dpToPx(rectHeight)-100, line2);//dpToPx(rectHeight)/4
        canvas.drawLine(left, top, left, top+100, line12);//dpToPx(rectHeight)/4


        Paint line21 = new Paint();
        line21.setColor(Color.RED);
        line21.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, left, dpToPx(rectHeight)-100, line2);//dpToPx(rectHeight)/4
        canvas.drawLine(dpToPx(rectWidth)+ left, top, dpToPx(rectWidth)+ left-100, top, line21);//dpToPx(rectHeight)/4

        Paint line22 = new Paint();
        line22.setColor(Color.RED);
        line22.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, left, dpToPx(rectHeight)-100, line2);//dpToPx(rectHeight)/4
        canvas.drawLine(dpToPx(rectWidth)+ left, top, dpToPx(rectWidth)+ left, top+100, line22);//dpToPx(rectHeight)/4


        Paint line31 = new Paint();
        line31.setColor(Color.RED);
        line31.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, dpToPx(rectWidth)-100, top, line1);//dpToPx(rectWidth)/4
        canvas.drawLine(left, dpToPx(rectHeight) + top, left, dpToPx(rectHeight) + top-100, line31);//dpToPx(rectWidth)/4

        Paint line32 = new Paint();
        line32.setColor(Color.RED);
        line32.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, left, dpToPx(rectHeight)-100, line2);//dpToPx(rectHeight)/4
        canvas.drawLine(left, dpToPx(rectHeight) + top, left+100, dpToPx(rectHeight) + top, line32);//dpToPx(rectHeight)/4


        Paint line41 = new Paint();
        line41.setColor(Color.RED);
        line41.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, left, dpToPx(rectHeight)-100, line2);//dpToPx(rectHeight)/4
        canvas.drawLine(dpToPx(rectWidth)+ left, dpToPx(rectHeight) + top, dpToPx(rectWidth)+ left-100, dpToPx(rectHeight) + top, line41);//dpToPx(rectHeight)/4

        Paint line42 = new Paint();
        line42.setColor(Color.RED);
        line42.setStrokeWidth(Float.valueOf(lineWidth));
        //canvas.drawLine(left, top, left, dpToPx(rectHeight)-100, line2);//dpToPx(rectHeight)/4
        canvas.drawLine(dpToPx(rectWidth)+ left, dpToPx(rectHeight) + top, dpToPx(rectWidth)+ left, dpToPx(rectHeight) + top-100, line42);//dpToPx(rectHeight)/4

    }
    public void resizeRectangle(int top, int left, int right, int bottom)
    {
        if(rect!=null)
        {
            rect.set(left,top,right,bottom);
            Log.e("RECT","Resized");

        }
        else
        {
            Log.e("RECT","Null");
        }
    }
    public void addFrame(Canvas canvas)
    {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //frameLayout.setBackground(getResources().getDrawable(R.drawable.rect1));
        //frameLayout.setBackgroundResource(R.drawable.rect1);
        View v = li.inflate(R.layout.temp7, null);
        v.setLayoutParams(new
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.addView(v);
        frameLayout.measure(200 , 200);
        frameLayout.layout((int)left, (int)top, dpToPx(rectWidth), dpToPx(rectHeight));
        frameLayout.draw(canvas);
    }

//    public void addFrame2(Canvas canvas)
//    {
//        Drawable shape = getResources().getDrawable(R.drawable.rect1);
//        Bitmap bitmap = Bitmap.createBitmap( shape.getIntrinsicWidth(), shape.getIntrinsicHeight(), Config.ARGB_8888 );
//        Canvas canvas = new Canvas( bitmap );
//        shape.setBounds( 0, 0, canvas.getWidth(), canvas.getHeight() );
//        shape.draw( canvas );
//    }


}