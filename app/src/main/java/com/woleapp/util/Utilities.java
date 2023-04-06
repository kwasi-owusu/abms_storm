package com.woleapp.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.woleapp.R;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.fragments.DashboardFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Utilities {
    Context context;

    public Utilities(Context context) {
        this.context = context;
        return;
    }
    /*public Utilities getInstance(Context context)
    {
        this.context = context;
        return new Utilities();
    }*/

    public void showAlertDialogOk(String message) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(message);

        alertDialog.setTitle(context.getResources().getString(R.string.title_alert));


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                //signOut();
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }

    public void showAlertDialogOk(String title, String message, HomeActivity activity, boolean success) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(message);
        alertDialog.setTitle(title);


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                //signOut();
                dialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if (success) {
                    activity.getSupportFragmentManager().popBackStack(DashboardFragment.class.getSimpleName(), 0);
                } else {
                    //activity.getSupportFragmentManager().popBackStack(DashboardFragment.class.getSimpleName(), 0);
                }

            }
        });
        // Showing Alert Message
        alertDialog.show();


    }

    public void showAlertDialogYesNo(String message) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(message);

        alertDialog.setTitle(context.getResources().getString(R.string.title_alert));


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                //signOut();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public String getFormatedAmountString(Float amt) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        //decimalFormat.setMinimumFractionDigits(2);
        String value = decimalFormat.format(amt);
//        String str = "" + value.charAt(0);
//        if (str.equals(".")) {
//            value = "0" + value;
//        }
        return value;
    }

    public String getFileSizeInMB(File file) {
        return (double) file.length() / (1024 * 1024) + " MB";
    }

    public String getFileSizeInKB(File file) {
        return (double) file.length() / (1024) + " MB";
    }

    public String getFileSizeInBytes(File file) {
        return (double) file.length() / (1024) + " Bytes";
    }

    public double getFileSizeInMBDbl(File file) {
        double res = (double) file.length() / (1024 * 1024);
        Log.e("Size", res + " MB");
        return res;
    }

    public double getFileSizeInKBDbl(File file) {
        double res = (double) file.length() / (1024);
        Log.e("Size", res + " KB");
        return (double) file.length() / (1024);
    }

    public double getFileSizeInBytesDbl(File file) {
        return (double) file.length() / (1024);
    }


    public void showDialogNoNetwork(String text, FragmentActivity activity) {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvHeader = dialog2.findViewById(R.id.tvTitle);
        TextView tvMessage = dialog2.findViewById(R.id.tvMessage);

        TextView tvYes = dialog2.findViewById(R.id.tvYes);
        TextView tvNo = dialog2.findViewById(R.id.tvNo);

        tvHeader.setText("Alert");
        tvMessage.setText(text);

//        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_message);
//        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "ROBOTO-REGULAR.TTF");
//        tvYes.setTypeface(typeface);
//        tvNo.setTypeface(typeface);
//        tvText.setTypeface(typeface);


        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);//ACTION_NETWORK_OPERATOR_SETTINGS
                /*Uri uri = Uri.fromParts("package", getActivity().getPackageName(), SignupFragment.class.getSimpleName());
                intent.setData(uri);*/
                activity.startActivityForResult(intent, 1021);

                dialog2.dismiss();
            }
        });
        dialog2.show();
    }


    public void hideKeyboard(Activity activity) {
        final InputMethodManager im = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        final View view = activity.getWindow().getDecorView();
        im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public String getFormattedAmount(long amt) {
        if (amt == 0) {
            return getSpannableString("0").toString();
        } else {
            DecimalFormat myFormatter = new DecimalFormat("#,###,###.00");//"#,##,###"
            //myFormatter.setRoundingMode(RoundingMode.UNNECESSARY);

            /*float v1 = Float.parseFloat(""+myPoints.getOGRANDTOTAL());
            String df = new DecimalFormat("0.00").format(v1);*/

            try {
                String output = myFormatter.format(amt);
                return getSpannableString(output).toString();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return "N.A.";
    }

    public String getFormattedAmount(double amt) {
        if (amt == 0.0) {
            return getSpannableString("0").toString();
        } else {
            DecimalFormat myFormatter = new DecimalFormat("#,###,###.00");//"#,##,###"
            //myFormatter.setRoundingMode(RoundingMode.UNNECESSARY);

            /*float v1 = Float.parseFloat(""+myPoints.getOGRANDTOTAL());
            String df = new DecimalFormat("0.00").format(v1);*/

            try {
                String output = myFormatter.format(amt);
                return getSpannableString(output).toString();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return "N.A.";
    }


    public String getFormattedAmount1(double amt) {
        if (amt == 0.0) {
            return getSpannableString("0").toString();
        } else {
            DecimalFormat myFormatter = new DecimalFormat("#,###.##");//"#,##,###"
            //#,###,###.00
            //myFormatter.setGroupingSize(3);

            //myFormatter.setRoundingMode(RoundingMode.UNNECESSARY);

            /*float v1 = Float.parseFloat(""+myPoints.getOGRANDTOTAL());
            String df = new DecimalFormat("0.00").format(v1);*/

            try {
                String output = myFormatter.format(amt);
                return getSpannableString(output).toString();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return "N.A.";
    }


    public String getFormattedAmount(String amt) {
        if (TextUtils.isEmpty(amt)) {
            return getSpannableString("0").toString();
        } else {
            DecimalFormat myFormatter = new DecimalFormat("#,###,###");//"#,##,###"  //#,###,##0.00
            //myFormatter.setRoundingMode(RoundingMode.UNNECESSARY);

            /*float v1 = Float.parseFloat(""+myPoints.getOGRANDTOTAL());
            String df = new DecimalFormat("0.00").format(v1);*/

            try {
                String output = myFormatter.format(amt);
                return getSpannableString(output).toString();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return "N.A.";
    }

    public String transformDate(String date, String inputFormat, String outputFormat) {

        /*Examples:- yyyy-dd-MM , "yyyy-MM-dd" , "dd MMM, yyyy" etc*/

        if (TextUtils.isEmpty(date)) {
            return "N.A.";
        } else {
            DateTimeFormatter dtf = DateTimeFormat.forPattern(inputFormat);
            DateTime jodatime = dtf.parseDateTime(date);

            DateTimeFormatter dtfOut = DateTimeFormat.forPattern(outputFormat);
            String op = dtfOut.print(jodatime);
            Log.e("Output", op + "---"); //07-03-2019
            return op;
        }


//        Date dt1 = new Date("2019-03-07");
//        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
//        String op  = format1.format(dt1);
//        Log.e("Output",op+"---");
    }

    public SpannableString getSpannableString(String amount) {
        String amt = context.getResources().getString(R.string.lbl_currency_naira) + amount;
        SpannableString ss1 = new SpannableString(amt);
        ss1.setSpan(new RelativeSizeSpan(1.2f), 0, 1, 0); // set size
        //ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        return ss1;
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
}
