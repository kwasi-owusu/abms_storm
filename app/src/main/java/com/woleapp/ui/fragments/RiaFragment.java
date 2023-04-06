package com.woleapp.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.juanpabloprado.countrypicker.CountryPicker;
import com.juanpabloprado.countrypicker.CountryPickerListener;
import com.woleapp.R;
import com.woleapp.databinding.LayoutRiaBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.AgencyUser;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RiaFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutRiaBinding binding;
    Utilities utilities;
    AgencyUser user;
    String currency_symbol = "";
    Drawable customErrorDrawable;
    Signature mSignature;
    int len = 0;
    Bitmap bitmap;
    LinearLayout mContent;
    private UserViewModel mViewModel;
    boolean hasSigned = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

        user = SharedPrefManager.getAgencyUser();
        Log.e("AgencyData", "user"+ user);
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        binding.btnContinue.setOnClickListener(this);
        binding.date.setText(getTodaysDate());
        binding.date.setEnabled(false);
        binding.time.setText(getTodaysTime());
        binding.time.setEnabled(false);
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        Float convenience_fee = SharedPrefManager.getTransfeeConvenienceFee();
        binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));
        addTextChangeListener();

        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("agency_id", 1);
        jsonObject.addProperty("branch_id", 1);

        setListeners();
        prepareCanvas();
        //setBank();
        //setPurposeSpinner();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_ria, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){
        if(v==binding.btnContinue){
            Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();
        }
        else if(v == binding.country){
            selectReceiverCountry();
        }
        else if (v == binding.senderCountry){
            selectSenderCountry();
        }
        else if(v == binding.sendCountry){
            selectCountryMoneySent();
        }
        else if (v == binding.linearCanvas) {
            showSignaturePad();
        } else if (v == binding.tvRetake) {
            mSignature.clear();
            hasSigned = false;
        }
    }
    public void setListeners() {
        binding.tvRetake.setOnClickListener(this);
        binding.etFee.setKeyListener(null);
        binding.btnContinue.setOnClickListener(this);
        binding.country.setOnClickListener(this);
        binding.senderCountry.setOnClickListener(this);
        binding.sendCountry.setOnClickListener(this);
    }
    public void addTextChangeListener() {

        binding.etFee.setKeyListener(null);

        RxTextView.afterTextChangeEvents(this.binding.etAmt)
                .skip(1)
                .debounce(1000, TimeUnit.MILLISECONDS)
                //.toFlowable(BackpressureStrategy.BUFFER)
                .cache()
                .filter(textViewTextChangeEvent -> this.binding.etAmt.hasFocus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewTextChangeEvent) throws Exception {
                        Log.e("tag", "text chanege");
                        Editable editable = textViewTextChangeEvent.getEditable();
                        if (editable.length() != 0 && editable.length() != len) {
                            String text = editable.toString();
                            if (text.length() > 0) {
                                text = text.replace(currency_symbol, "").replaceAll(",", "");

                                if (text.length() > 0) {
                                    boolean result = priceValidation(text);
                                    if (!result) {
                                        //binding.etPrice.setError("Invalid input");
                                        binding.etAmt.setError("Invalid input", customErrorDrawable);
                                        //binding.etPrice.requestFocus();
                                        len = text.length();
                                    } else {
                                        Double amt = Double.parseDouble(text);
                                        if (amt < 1) {
                                            binding.etAmt.setError("Minimum amount of transaction is " + currency_symbol + 1, customErrorDrawable);
                                            //binding.etPrice.requestFocus();
                                        } else if (amt > 100000) {
                                            binding.etAmt.setError("Maximum amount of transaction is " + currency_symbol + 100000, customErrorDrawable);
                                        } else {
                                            binding.etAmt.setError(null);
                                            String amt1 = utilities.getFormattedAmount(amt);
                                            binding.etAmt.setText(amt1);
                                            try {
                                                /*binding.etPrice.setSelection(amt1.length());*/
                                                binding.etAmt.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        binding.etAmt.setSelection(binding.etAmt.getText().length());
                                                    }
                                                });
                                                len = amt1.length();
                                                Log.e("Set_Selection", "Set_Selection");
                                            } catch (IndexOutOfBoundsException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    }
                });
    }
    public void selectReceiverCountry(){
        CountryPicker picker = CountryPicker.getInstance("Select Country", new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code) {
                binding.country.setText(name.toUpperCase());
                DialogFragment dialogFragment =
                        (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("CountryPicker");
                dialogFragment.dismiss();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "CountryPicker");
    }
    public void selectSenderCountry(){
        CountryPicker picker = CountryPicker.getInstance("Select Country", new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code) {
                binding.senderCountry.setText(name.toUpperCase());
                DialogFragment dialogFragment =
                        (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("CountryPicker");
                dialogFragment.dismiss();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "CountryPicker");
    }
    public void selectCountryMoneySent(){
        CountryPicker picker = CountryPicker.getInstance("Select Country", new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code) {
                binding.sendCountry.setText(name.toUpperCase());
                DialogFragment dialogFragment =
                        (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("CountryPicker");
                dialogFragment.dismiss();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "CountryPicker");
    }
    public void prepareCanvas() {
        mContent = binding.linearCanvas;
        mSignature = new Signature(context, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        hasSigned = false;
    }
    public String getTodaysDate() {

        LocalDate date = new LocalDate();
        LocalTime time = new LocalTime();
        DateTimeFormatter toDateTimeFormat
                = DateTimeFormat.forPattern("dd MMM, yyyy");

        String s = toDateTimeFormat.print(date);
        return "" + s;
    }

    public String getTodaysTime() {

        LocalTime time = new LocalTime();
        DateTimeFormatter toDateTimeFormat
                = DateTimeFormat.forPattern("HH:mm a");

        String f = toDateTimeFormat.print(time);
        return "" + f;
    }
    public boolean priceValidation(String price) {
        String regex = "[+-]?([0-9]*[.])?[0-9]+";
        Pattern numberPattern = Pattern.compile(regex);
        boolean result = numberPattern.matcher(price).matches();
        Log.e("Result: ", result + "--");
        return result;
    }
    public class Signature extends View {

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

        public void save(View v, String StoredPath) {

            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {

                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);

            }
            Canvas canvas = new Canvas(bitmap);
            try {
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
            this.getParent().requestDisallowInterceptTouchEvent(true);
            hasSigned = true;
            //binding.tvSign.setError(null);
            showOrHideSignatureAlert(false);
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
    public void showSignaturePad() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        //final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //R.anim.bottom_up_animation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        LinearLayout linearCanvas = dialog.findViewById(R.id.linearCanvas);
        Button btnRetake = dialog.findViewById(R.id.btnRetake);
        Button btnDone = dialog.findViewById(R.id.btnDone);


        btnRetake.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.2f;
        window.setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    public void showOrHideSignatureAlert(boolean isError) {
        if (isError) {
            binding.tvSign.setText(context.getResources().getString(R.string.lbl_signature_required));
            binding.tvSign.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.error_small, 0);
            binding.tvSign.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            binding.tvSign.setText(context.getResources().getString(R.string.lbl_signature));
            binding.tvSign.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.tvSign.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }

}
