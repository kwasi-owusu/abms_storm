package com.woleapp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteException;

import android.graphics.*;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.woleapp.R;
import com.woleapp.databinding.ActivitySignature2Binding;
import com.woleapp.util.Utilities;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import com.kavya.checkinscan.R;
//import com.kavya.checkinscan.databinding.ActivitySignature2Binding;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener
{

    File file;
    //Dialog dialog;
    LinearLayout mContent;
    View signature_view;
    Signature mSignature;
    Bitmap bitmap;
    boolean hasSigned = false;
    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/DigitSign/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";
    ActivitySignature2Binding binding;


    Context context;
    int result0;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("SignatureActivity","onResume");
        /*if (android.os.Build.VERSION.SDK_INT >= 27) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }*/
        //this.recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (android.os.Build.VERSION.SDK_INT >= 27) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("SignatureActivity","onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("SignatureActivity","onSaveInstanceState");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.e("SignatureActivity","onRestoreInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation ==
                Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.e("onConfigurationChanged","LandScape");
        } else if (newConfig.orientation ==
                Configuration.ORIENTATION_PORTRAIT){
            Log.e("onConfigurationChanged","LandScape");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SignatureActivity","onCreate");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        context = this;
        //setContentView(R.layout.activity_signature_2);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signature_2);
        binding.btnRetake.setOnClickListener(this);
        binding.btnDone.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);


        prepareCanvas();

        if(checkPermissions())
        {
            String filePath = getFilePath();
            file = new File(filePath);
        }
        else
        {
            requestPermission();
        }

    }


    public boolean checkPermissions()
    {
        try
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M)
            {
                result0 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                //result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
                //result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (result0 == 0)//result0 == 0 && result1 == 0
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return  true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  false;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0)
                {
                    //boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    //boolean phoneStateAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    //boolean networkAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if ( storageAccepted)
                    {
                        String filePath = getFilePath();
                        file = new File(filePath);//getOutputMediafile(1);
                    }
                    else
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSION_REQUEST_CODE);
                        }
                    }
                }
                break;
        }
    }

    private File getOutputMediafile(int type)
    {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "."+getResources().getString(R.string.app_name)
        );
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyHHdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".png");

        }
        else
        {
            return null;
        }

        return mediaFile;
    }
    // Function for Digital Signature
    public void prepareCanvas()
    {

        //mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mContent = binding.linearCanvas;
        mSignature = new Signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //binding.linearNext.setEnabled(false);
        hasSigned = false;
//        mClear = (Button) dialog.findViewById(R.id.clear);
//        mGetSign = (Button) dialog.findViewById(R.id.getsign);
//        mGetSign.setEnabled(false);
//        mCancel = (Button) dialog.findViewById(R.id.cancel);

//        LinearLayout temp = mContent;
//        temp.setBackgroundResource(0);
//        signature_view = temp;
//        signature_view.layout();

        signature_view = mContent; // Original

//        mClear.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.v("log_tag", "Panel Cleared");
//                mSignature.clear();
//                mGetSign.setEnabled(false);
//            }
//        });
//
//        mGetSign.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                Log.v("log_tag", "Panel Saved");
//                signature_view.setDrawingCacheEnabled(true);
//                //mSignature.save(signature_view, StoredPath);
//                Log.e("Path",file.getPath()+"");
//                mSignature.save(signature_view, file.getPath());
//
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
//                // Calling the same class
//                recreate();
//
//            }
//        });
//
//        mCancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.v("log_tag", "Panel Canceled");
//                dialog.dismiss();
//                // Calling the same class
//                recreate();
//            }
//        });
//        dialog.show();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id)
        {
            case R.id.ivBack:
                this.setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            case  R.id.btnDone:
                //this.finish();

                if(checkPermissions())
                {
                    if(hasSigned)
                    {
                        view.setDrawingCacheEnabled(true);
                        //mSignature.save(signature_view, StoredPath);
                        Log.e("Path",file.getPath()+"");
                        mSignature.save(signature_view, file.getPath());
                        Uri uri = Uri.fromFile(file);

                        final InputStream imageStream;
                        try
                        {
                            imageStream = getContentResolver().openInputStream(uri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);
                            //this.finish();
                            this.setResult(Activity.RESULT_OK);
                            this.finish();

                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Unable to locate the signature file", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(context,"Your Signature is required to proceed",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    requestPermission();
                }
                break;

            case  R.id.btnRetake:
                mSignature.clear();
                hasSigned = false;
                //binding.linearNext.setEnabled(false);
                break;

        }
    }

    public String getFilePath()
    {

        final File folder = new File(this.getExternalFilesDir(null), "/signature");
        boolean directoryExists = false;
        if (folder.exists())
        {
            directoryExists = true;

        }
        else
        {
            directoryExists = folder.mkdir();
        }

        if(directoryExists)
        {
            String path  = folder.getAbsolutePath();
            path = path + "/" + "IMG_" + System.currentTimeMillis() + ".png";// path where pdf will be stored
            return path;
        }
        return null;

    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("Base64",encImage);
        return encImage;
    }

    public class Signature extends View
    {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public Signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath)
        {

            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null)
            {

                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);

            }
            Canvas canvas = new Canvas(bitmap);
            try
            {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            //binding.linearNext.setEnabled(true);
            hasSigned = true;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
}
