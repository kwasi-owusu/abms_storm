package com.woleapp.ui.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.woleapp.R;
import com.woleapp.adapters.DashboardAdapter;
import com.woleapp.adapters.InvoicePreviewItemsAdapter;
import com.woleapp.databinding.LayoutInvoicePreviewBinding;
import com.woleapp.model.Inventory;
import com.woleapp.model.Service;
import com.woleapp.model.User;
import com.woleapp.util.Constants;
import com.woleapp.util.DividerItemDecoration;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.PermissionsUtils;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.StormApplication;
import com.woleapp.util.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InvoicePreviewFragment extends BaseFragment implements View.OnClickListener, Constants {
    Context context;
    Utilities utilities;
    private LayoutInvoicePreviewBinding binding;
    DashboardAdapter adapter;
    List<Service> serviceList;
    User user;
    Drawable customErrorDrawable;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    String prefixMerchant = "Merchant:";
    String prefixAddress = "Address:";
    String prefixPhone = "Contact:";
    String prefixName = "Name:";
    ArrayList<Inventory> itemList;
    InvoicePreviewItemsAdapter invoiceItemsadapter;

    boolean hasSigned = false;
    View signature_view;
    LinearLayout mContent;
    Signature mSignature;
    Bitmap bitmap;
    int result0;
    //File file;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean wasSettingsIntent = false;
    private static final int PERMISSION_REQUEST_CODE = 200;
    File generatedFile;
    ProgressDialog mProgressDialog;
    String inv_no = "";

    @Override
    public void onResume() {
        super.onResume();
        Log.e("InvoicePreviewFragment", "InvoicePreviewFragment");
        Log.e("wasSettingsIntent", wasSettingsIntent + "");
        if (wasSettingsIntent) {
            wasSettingsIntent = false;
            /*if(!TextUtils.isEmpty(invoice_number))
            {
                launchPreview(invoice_number);
                //launchPreviewTask(invoice_number);
            }*/
        }
        /*else
        {
            try {
                setPayerAndPayeeInfo();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        user = SharedPrefManager.getUser();
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        //((HomeActivity)getActivity()).setTitleWithNoNavigation("Dashboard");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addTextWatchers();
        binding.tvRetake.setOnClickListener(this);
        binding.btnShare.setOnClickListener(this);
        User user = SharedPrefManager.getUser();
        binding.tvContact.append(user.getPhone());
        Long tsLong = System.currentTimeMillis() / 1000;
        inv_no = "INV_" + SharedPrefManager.getUser().getUser_id() + tsLong.toString() + "";
        binding.tvInvoiceNumber.setText(inv_no);
        binding.tvInvoiceDate.setText("Date: " + utilities.getTodaysDate());
        Inventory inventory = StormApplication.getInstance().getSelectedInventory();
        if (inventory != null) {
            String currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

            Double amt = inventory.getQuantity() * Double.parseDouble(inventory.getPrice());
            binding.tvTotal.setText(utilities.getFormattedAmount(amt));
            binding.tvTotalAmt.setText(utilities.getFormattedAmount(amt));
            itemList = new ArrayList<>();
            itemList.add(inventory);
            invoiceItemsadapter = new InvoicePreviewItemsAdapter(context, itemList);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(context,
                    R.color.bg_gray);
            binding.rvItems.addItemDecoration(mDividerItemDecoration);
            binding.rvItems.setAdapter(invoiceItemsadapter);
        }
        prepareCanvas();
        /*if(checkPermissions())
        {
            String filePath = getFilePath();
            file = new File(filePath);
        }
        else
        {
            requestPermission();
        }*/
    }

    public boolean checkPermissions() {
        try {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                //result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
                //result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (result0 == 0)//result0 == 0 && result1 == 0
                {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 1010) {
            if (PermissionsUtils.verifyPermissions(grantResults)) {
                launchPreview(inv_no);
                //launchPreviewTask(invoice_number);
            } else {

                showSettingsDialog(context, "Permission to access storage is required to generate and share receipts. Grant permission ?");
            }

        }
        /*else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }*/
    }

    public void showSettingsDialog(final Context context, String message) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Permission required");
        builder.setMessage(message);
        builder.setPositiveButton("Open App Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(context);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        wasSettingsIntent = true;
        startActivity(intent);
    }

    public String getFilePath() {

        final File folder = new File(getActivity().getExternalFilesDir(null), "/signature");
        boolean directoryExists = false;
        if (folder.exists()) {
            directoryExists = true;

        } else {
            directoryExists = folder.mkdir();
        }

        if (directoryExists) {
            String path = folder.getAbsolutePath();
            path = path + "/" + "IMG_" + System.currentTimeMillis() + ".png";// path where pdf will be stored
            return path;
        }
        return null;

    }

    public void prepareCanvas() {

        //mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mContent = binding.linearCanvas;
        mSignature = new Signature(context, null);
        mSignature.setBackgroundColor(Color.WHITE);
        //mSignature.getParent().requestDisallowInterceptTouchEvent(true);

        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //binding.linearNext.setEnabled(false);
        hasSigned = false;
        signature_view = mContent; // Original

    }

    public void addTextWatchers() {
        binding.tvSenderName.setText(prefixMerchant);
        binding.tvSenderName.setSelection(prefixMerchant.length());

        binding.tvSenderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() < prefixMerchant.length()) {
                    binding.tvSenderName.setText(prefixMerchant);
                    binding.tvSenderName.setSelection(prefixMerchant.length());
                } else {
                    /*binding.tvSenderName.setText(prefixMerchant);
                    binding.tvSenderName.append(s.toString());*/
                }
            }
        });

        binding.tvContact.setText(prefixPhone);
        binding.tvContact.setSelection(prefixPhone.length());

        binding.tvCustContact.setText(prefixPhone);
        binding.tvCustContact.setSelection(prefixPhone.length());

        binding.tvCustomerName.setText(prefixName);
        binding.tvCustomerName.setSelection(prefixName.length());

        binding.tvAddress.setText(prefixAddress);
        binding.tvAddress.setSelection(prefixAddress.length());

        binding.tvCustomerAddress.setText(prefixAddress);
        binding.tvCustomerAddress.setSelection(prefixAddress.length());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_invoice_preview, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }


    public boolean priceValidation(String price) {
        //^[1-9][0-9]{12,16}$ (old regular expression)
//		String regex = "^[+][0-9]{12,16}$";
        String regex = "[+-]?([0-9]*[.])?[0-9]+";//"[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
        //String regex = "^[+][0-9]{10,13}$";
        Pattern numberPattern = Pattern.compile(regex);
        boolean result = numberPattern.matcher(price).matches();
        Log.e("Result: ", result + "--");
        return result;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();

//        menu.findItem(R.id.action_edit).setVisible(true);
//        menu.findItem(R.id.action_search).setVisible(false);
        //menu.removeItem(R.id.action_search);//clear();

        /*if(showDeleteMenu)
        {
            menu.findItem(R.id.action_search).setVisible(false);
            menu.removeItem(R.id.action_search);//clear();
        }
        else
        {
            menu.clear();

//            menu.removeItem(R.id.action_search);//clear();
//            menu.removeItem(R.id.action_search);//clear();
//
//            menu.findItem(R.id.action_search).setVisible(false);
//
//            menu.findItem(R.id.action_search).setVisible(false);
        }*/

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_fragment, menu);
//        MenuItem searchMenu = menu.findItem(R.id.action_edit);
//        searchMenu.setVisible(true);
        //MenuItem delete = menu.findItem(R.id.action_delete);
//        if(showDeleteMenu)
//        {
//            delete.setVisible(true);
//        }
//        else
//        {
//            delete.setVisible(false);
//        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    public void scrollToTop() {
        binding.scroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    binding.scroll.smoothScrollTo(0, 0);//smoothScrollToPosition
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1200);
    }

    public boolean validateAndProceed() {
        String name = binding.tvSenderName.getText().toString().trim().replaceAll(prefixMerchant, "");
        String cust_name = binding.tvCustomerName.getText().toString().trim().replaceAll(prefixName, "");
        String phone = binding.tvContact.getText().toString().trim().replaceAll(prefixPhone, "");
        String phone_cust = binding.tvContact.getText().toString().trim().replaceAll(prefixPhone, "");
        String address = binding.tvAddress.getText().toString().trim().replaceAll(prefixAddress, "");
        String cust_address = binding.tvCustomerAddress.getText().toString().trim().replaceAll(prefixAddress, "");

        if (TextUtils.isEmpty(name)) {
            binding.tvSenderName.setError("Merchant name required", customErrorDrawable);
            binding.tvSenderName.requestFocus();
            scrollToTop();
        } else if (TextUtils.isEmpty(phone)) {
            binding.tvContact.setError("Merchant contact no. required", customErrorDrawable);
            binding.tvContact.requestFocus();
            scrollToTop();
        } else if (phone.length() < 9) {
            binding.tvContact.setError("Invalid contact no.", customErrorDrawable);
            binding.tvContact.requestFocus();
            scrollToTop();
        } else if (TextUtils.isEmpty(address)) {
            binding.tvAddress.setError("Address required", customErrorDrawable);
            binding.tvAddress.requestFocus();
            scrollToTop();
        } else if (TextUtils.isEmpty(cust_name)) {
            binding.tvCustomerName.setError("Customer name required", customErrorDrawable);
            binding.tvCustomerName.requestFocus();
            scrollToTop();

        } else if (TextUtils.isEmpty(phone_cust)) {
            binding.tvCustContact.setError("Customer contact no. required", customErrorDrawable);
            binding.tvCustContact.requestFocus();
            scrollToTop();
        } else if (phone_cust.length() < 9) {
            binding.tvCustContact.setError("Invalid contact no.", customErrorDrawable);
            binding.tvCustContact.requestFocus();
            scrollToTop();
        } else if (TextUtils.isEmpty(cust_address)) {
            binding.tvCustomerAddress.setError("Address required", customErrorDrawable);
            binding.tvCustomerAddress.requestFocus();
        } else {
            return true;
            /*if (ConnectivityReceiver.isConnected())
            {

            }
            else
            {
                utilities.showDialogNoNetwork("You need an active internet connection to proceed. Would you like to enable it ?",getActivity());
            }*/
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            /*case R.id.action_edit:
                mDataBinding.btnUpdate.setVisibility(View.VISIBLE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == binding.tvRetake) {
            mSignature.clear();
            hasSigned = false;
        } else if (v == binding.btnShare) {
            if (validateAndProceed()) {
                downloadAndShare(inv_no);
            }
        }
        /*if(v==binding.btnSend)
        {
            if(isValid())
            {
                showFragment(new DashboardFragment(),DashboardFragment.class.getSimpleName());
            }

        }
        else if(v==binding.btnBack)
        {
            showFragment(new DashboardFragment(),DashboardFragment.class.getSimpleName());
        }
        else if(v==binding.linearNo)
        {
            showFragment(new DashboardFragment(),DashboardFragment.class.getSimpleName());
        }*/
    }

    public boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /*public boolean isValid()
    {
        String email = binding.etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            binding.etEmail.setError("Email is required",customErrorDrawable);
            binding.etEmail.requestFocus();
        }
        else if(!isValidEmail(email))
        {
            binding.etEmail.setError("Invalid Email",customErrorDrawable);
            binding.etEmail.requestFocus();
        }
        else
        {
            return true;
        }
        return false;
    }*/

    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {


        }
    };


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

        /*public void save(View v, String StoredPath)
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

        }*/

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

            //showOrHideSignatureAlert(false);
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

    public void downloadAndShare(String inv_no) {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(PERMISSIONS_STORAGE, 1010);
            //Toast.makeText(getActivity(),"Permission to access the storage is required",Toast.LENGTH_LONG).show();
//                ActivityCompat
//                        .requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            launchPreview(inv_no);
        }
    }

    public void launchPreview(String inv_no) {
        binding.linearOptions.setVisibility(View.GONE);
        binding.tvRetake.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                launchPreviewTask(inv_no);
            }
        }, 400);
    }

    public void launchPreviewTask(String invoice_number) {
        new AsyncTask<Void, Void, File>() {
            private ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                binding.linearOptions.setVisibility(View.GONE);
                showProgressBar();
            }

            @Override
            protected File doInBackground(Void... a) {
                File file = printConversationToPDF_Set(invoice_number);//printConversationToPDF();
//                        Bitmap bmp;
//                        bmp = utilities.getPicture(url);
                return file;
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);

                //file = pdfBox(file);
                generatedFile = file;
                dismissProgressBar();
                if (file == null) {
                    Toast.makeText(getActivity(), "Failed to export the file", Toast.LENGTH_LONG).show();
                    binding.linearOptions.setVisibility(View.VISIBLE);
                    binding.tvRetake.setVisibility(View.VISIBLE);

                } else {
                    showDialogSuccess(file);
                    /*if(SMEApplication.getInstance().isPrintIntent())
                    {
                        SMEApplication.getInstance().setPrintIntent(false);
                        launchPDFViewer();
                    }
                    else
                    {
                        showDialogSuccess(file);
                    }*/
                    binding.linearOptions.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showDialogSuccess(final File file) {
        boolean intentLaunched = false;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //android.R.string.ok
        builder.setTitle("Success")
                .setCancelable(false)
                .setMessage("File exported successfully.")
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()//android.R.string.ok
                {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //openMailIntent(file);
                    }

                })
                .setPositiveButton("Share", (dialog, whichButton) -> {
                    User user = SharedPrefManager.getUser();
                    openMailIntent(file);
                    //openFile(file);

                }).show();
    }

    public void openMailIntent(File pdfFile) {
//        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//
//        /* Fill it with Data */
//        emailIntent.setType("plain/text");
//        //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Chat backup");
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
//
//        /* Send it off to the Activity-Chooser */
//        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));


//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.setType("application/pdf");
//        sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Chat backup");
//        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
//        //sendIntent.setData(Uri.parse("testuurmi@gmail.com"));
//        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//        //sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "testuurmi@gmail.com" });
//        //sendIntent.putExtra(Intent.EXTRA_SUBJECT, "testPDF");
//        //sendIntent.putExtra(Intent.EXTRA_TEXT, "this is a PDF ");
//
//        File pdfFile=null;
//        try {
//            pdfFile = new File(attachmentFileName);
//            FileOutputStream fos = new FileOutputStream(pdfFile);
//            fos.write(bytes);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Uri fileuri = Uri.fromFile(pdfFile);
//        sendIntent.putExtra(Intent.EXTRA_STREAM, fileuri);
//        //start the gmailapp intent
//        startActivity(sendIntent);


        try {
            Uri dat;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Log.e("Package:-", getActivity().getApplicationContext().getPackageName() + "");
                dat = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", pdfFile);// + "activity.getApplicationContext().getPackageName().provider"
            } else {
                dat = Uri.fromFile(pdfFile);
            }

            //Uri dat = Uri.fromFile(path);

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/html");
            //sendIntent.setDataAndType(uri, "application/pdf");//Uri.fromFile(file)
            sendIntent.setType("application/pdf");//Uri.fromFile(file)
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            sendIntent.putExtra(Intent.EXTRA_EMAIL, "");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Receipt");
            sendIntent.putExtra(Intent.EXTRA_STREAM, dat);
            startActivity(Intent.createChooser(sendIntent, "Share receipt via..."));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void launchPDFViewer() {
        if (generatedFile != null) {
            Uri path;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                path = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", generatedFile);
            } else {
                path = Uri.fromFile(generatedFile);
            }

            try {
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                /*intent.setDataAndType(path, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);*/

                //  startActivity(intent);


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, path);
                browserIntent.setDataAndType(path, "application/pdf");
                browserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                browserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                //startActivity(browserIntent);


//                    File outputFile = new File(Environment.getExternalStoragePublicDirectory
//                            (Environment.DIRECTORY_DOWNLOADS), "ref Number from Quotation.pdf");
//                    Uri uri = Uri.fromFile(outputFile);

                /*Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, path);*/

                //share.setPackage("com.whatsapp");


                /*Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                browserIntent.setType("application/pdf");*/
                browserIntent.putExtra(Intent.EXTRA_STREAM, path);

                getActivity().startActivity(browserIntent);


            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                // tv_loading.setError("PDF Reader application is not installed in your device");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Failed to open the file", Toast.LENGTH_LONG).show();
        }
    }

    public String getFilePath(String invoice_number) {
        //final File folder = new File(getActivity().getExternalFilesDir(null), "print123.pdf");
        final File folder = new File(getActivity().getExternalFilesDir(null), "/invoices");
        boolean directoryExists = false;
        if (folder.exists()) {
            directoryExists = true;

        } else {
            directoryExists = folder.mkdir();
        }

        if (directoryExists) {
            String path = folder.getAbsolutePath();
            path = path + "/" + /*"INV_" +*/ invoice_number + ".pdf";// path where pdf will be stored
            return path;
        }
        return null;
    }

    public void showProgressBar() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(context, null, null);
            mProgressDialog.setContentView(R.layout.dialog_progress);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
            mProgressDialog.setCancelable(false);

        /*if (mProgressDialog!=null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }*/
        } else {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    public void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public static Bitmap loadBitmapFromView(CardView v) {
        int height = v.getChildAt(0).getHeight();
        int width = v.getWidth();
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    private File printConversationToPDF_Set(String invoice_number) {


        // LinkedHashSet<View>
        //Set<View>


        //final File file = new File(getStorageDir("PDF"), "print.pdf");
        //final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "print007.pdf");

        //final File file = new File(getActivity().getExternalFilesDir(null), "print123.pdf");

        try {
            /*View view = binding.cardItem;
            view.buildDrawingCache(); //Adding the content to the document
            Bitmap bmp = view.getDrawingCache();*/
            Bitmap bmp = loadBitmapFromView(binding.cardItem);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(stream.toByteArray());

            Document document = new Document(image);//A4
            /*Phrase phrase = new Phrase();
            phrase.add("The founders of iText are nominated for a ");
            Chunk chunk = new Chunk("European Business Award!");
            chunk.setAnchor("http://itextpdf.com/blog/european-business-award-kick-ceremony");
            phrase.add(chunk);
            document.add(chunk);//table.addCell(phrase);*/

            final File file = new File(getFilePath(invoice_number));
            PdfWriter writer = null;
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                writer = PdfWriter.getInstance(document, fos);
                /*PdfWriter.getInstance(document, new FileOutputStream(file));*/
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }

            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
            image.scalePercent(scaler);
            image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER | com.itextpdf.text.Image.ALIGN_TOP);

//                image.scalePercent(70);
//                image.setAlignment(Image.MIDDLE);
            if (!document.isOpen()) {
                document.open();
            }

            document.add(image);


            if (document.isOpen()) {
                document.close();
            }
            return file;
        } catch (Exception ex) {
            Log.e("TAG-ORDER PRINT ERROR", ex.getMessage());
        }

        return null;
    }
}
