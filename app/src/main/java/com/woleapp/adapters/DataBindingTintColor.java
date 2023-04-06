package com.woleapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.transition.TransitionManager;

import com.woleapp.R;
import com.woleapp.db.LoadState;

import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;

public class DataBindingTintColor {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @BindingAdapter("android:drawableTint")
    public static void setVisibility(TextView view, String color) {


        Drawable unwrappedDrawable = AppCompatResources.getDrawable(view.getContext(), R.drawable.bubble_small);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(color));//Color.RED
        unwrappedDrawable.setBounds(0, 0, (int) (unwrappedDrawable.getIntrinsicWidth() * 0.6),
                (int) (unwrappedDrawable.getIntrinsicHeight() * 0.6));
        view.setCompoundDrawables(null, null, unwrappedDrawable, null);

    }

    @BindingAdapter("android:customDate")
    public static void setDate(LinearLayout view, String date) {
        //"yyyy-MM-dd HH:mm:ss"
        TextView tvMonth = view.findViewById(R.id.tvMonth);
        TextView tvDD = view.findViewById(R.id.tvDate);
        TextView tvYear = view.findViewById(R.id.tvYear);

        if (TextUtils.isEmpty(date)) {
            tvMonth.setText("N.A");
            tvDD.setText("N.A");
            tvYear.setText("N.A");
        } else {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            DateTime jodatime = dtf.parseDateTime(date);
            //jodatime.get(DateTimeFieldType.monthOfYear().getName())
            String month = jodatime.toString("MMM");
            String dd = jodatime.toString("dd");
            String yy = jodatime.toString("yyyy");

            tvMonth.setText(month + "");
            tvDD.setText(dd + "");
            tvYear.setText(yy + "");
            Log.e("Date", yy + " " + month + " " + dd);
        }
    }

    @BindingAdapter("android:customTextColor")
    public static void setTextColor(TextView view, String status) {
        if (TextUtils.isEmpty(status)) {
            view.setTextColor(view.getContext().getResources().getColor(R.color.red));
        } else {
            if (status.equalsIgnoreCase("pending") || status.contains("Pending")) {
                view.setTextColor(view.getContext().getResources().getColor(R.color.colorRating));
            } else if (status.equalsIgnoreCase("successful") || status.equalsIgnoreCase("success")) {
                view.setTextColor(view.getContext().getResources().getColor(R.color.green_success));
            } else {
                view.setTextColor(view.getContext().getResources().getColor(R.color.red));
            }
        }

    }

    @BindingAdapter("android:customText")
    public static void setText(TextView view, Double amount) {
        if (amount != null) {
            if (amount == 0.0d) {
                view.setText(getSpannableString(view.getContext(), "0"));//0.0
            } else {
                //amount = amount.replaceAll(",","");
                DecimalFormat myFormatter = new DecimalFormat("###,###.##");//#,##,###.00

            /*float v1 = Float.parseFloat(""+myPoints.getOGRANDTOTAL());
            String df = new DecimalFormat("0.00").format(v1);*/

                try {
                    String output = myFormatter.format(amount);
                /*DecimalFormat myFormatter = new DecimalFormat("#,##,###");
                String output = myFormatter.format(number);*/
                    view.setText(getSpannableString(view.getContext(), output));
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setText("N.A");
                }
            }
        } else {
            view.setText("N.A");
        }
    }

    public static SpannableString getSpannableString(Context context, String amount) {
        String rupee_symbol = context.getResources().getString(R.string.lbl_currency_naira);
        String amt = rupee_symbol + amount;
        SpannableString ss1 = new SpannableString(amt);
        ss1.setSpan(new RelativeSizeSpan(1.2f), 0, 1, 0); // set size
        //ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        return ss1;
    }

    @BindingAdapter("transactionRefresh")
    public static void transactionRefresh(SwipeRefreshLayout swipeRefreshLayout, LoadState loadState) {
        if (loadState == LoadState.LOADING_INITIAL)
            swipeRefreshLayout.setRefreshing(true);
        else
            swipeRefreshLayout.setRefreshing(false);
    }

    @BindingAdapter("loadingMoreProgress")
    public static void loadingMoreProgress(ProgressBar progressBar, LoadState loadState) {
        if (loadState == LoadState.LOADING_MORE)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
}
