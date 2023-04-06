package com.woleapp.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.woleapp.R;
import com.woleapp.databinding.LayoutScanToPayBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.LocalCache;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.User;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.mobilevision.BarcodeCaptureActivity;


public class ScanToPayFragment extends BaseFragment implements View.OnClickListener
{
    Context context;
    private LayoutScanToPayBinding binding;
    String amount = "";
    User user;
    private UserViewModel mViewModel;
    float amt = 0.0f;

    final int SCAN_QR_CODE = 1001;
    int result0, result1,result2;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        /*Utilities utilities = new Utilities(context);
        utilities.getFormatedAmountString()*/
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        user = SharedPrefManager.getUser();
        ((HomeActivity)getActivity()).setTitleWithNoNavigation("Dashboard");

        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Bundle b1 = getArguments();
        if(b1.containsKey("amount"))
        {
            amount = b1.getString("amount");
            binding.tvAmount.setText(context.getResources().getString(R.string.lbl_currency_naira)+" "+amount);
        }

        binding.cardAccountTransfer.setOnClickListener(this);
        binding.cardAccountTransfer1.setOnClickListener(this);
        binding.cardQRPay.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);*/

        String textToEmbed = "";
        if(user.getBusiness_info()!=null)
        {
            textToEmbed = user.getBusiness_info().getBusiness_name();

        }
        else
        {
            String email= user.getEmail();
            String name = email.substring(0,email.indexOf('@'));
            textToEmbed = name;
        }
        binding.tvMerchantName.setText(textToEmbed);//user.getName()
        binding.btnBack.setOnClickListener(this);
        Bitmap bitmap = null;
        try {
            bitmap = textToImage(textToEmbed, 180, 180);
            binding.ivQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_scan_to_pay, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {

            /*case R.id.action_edit:
                mDataBinding.btnUpdate.setVisibility(View.VISIBLE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v)
    {

        /*if(v==binding.cardAccountTransfer)
        {
            if(user.getUser_type()==1)
            {
                updateUser(amount);
            }
            else
            {
                if(user.getIs_verified())
                {
                    addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(),PaymentSuccessFragment.class.getSimpleName());
                }
                else
                {
                    addFragmentWithoutRemove(R.id.container_main,new AccountActivationFragment(),AccountActivationFragment.class.getSimpleName());
                }

            }

            //addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(),PaymentModeFragment.class.getSimpleName());
        }
        else if(v==binding.cardAccountTransfer1)
        {
            if(user.getUser_type()==1)
            {
                updateUser(amount);
                //addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(),PaymentModeFragment.class.getSimpleName());
            }
            else
            {
                //addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(),PaymentModeFragment.class.getSimpleName());
                if(user.getIs_verified())
                {
                    addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(),PaymentSuccessFragment.class.getSimpleName());
                }
                else
                {
                    addFragmentWithoutRemove(R.id.container_main,new AccountActivationFragment(),AccountActivationFragment.class.getSimpleName());
                }
            }

        }*/
        if(v==binding.cardQRPay)
        {
            //scanQRCode();
            if(user.getUser_type()==1)
            {
                scanQRCode();
            }
            else
            {
                if(user.getQRRegistered())
                {
                    if(user.getIs_verified())
                    {
                        addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(), ScanToPayFragment.class.getSimpleName());
                        /*Bitmap bitmap = null;
                        try {
                            bitmap = textToImage("WOLE_STORM", 100, 100);
                            binding.ivQRCode.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }*/
                    }
                    else
                    {
                        addFragmentWithoutRemove(R.id.container_main,new AccountActivationFragment(),AccountActivationFragment.class.getSimpleName());
                    }
                }
                else
                {
                    addFragmentWithoutRemove(R.id.container_main,new QRRegistrationFragment(),QRRegistrationFragment.class.getSimpleName());
                }
            }

            /*if(user.getUser_type()==1)
            {
                updateUser(amount);
                //addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(),PaymentModeFragment.class.getSimpleName());
            }
            else
            {
                //addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(),PaymentModeFragment.class.getSimpleName());
            }*/
        }
        else if(v==binding.btnBack)
        {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }


    public void updateUser(String amount)
    {

        if(amount!=null)
        {
            amt = Float.parseFloat(amount)+Float.parseFloat(user.getAvailableBalance());
        }
        else
        {
            amt = Float.parseFloat(amount);
        }
        //user.setIs_verified(true);
        LocalCache.UpdateCallback callback = new LocalCache.UpdateCallback()
        {
            @Override
            public void updateFinished(int result)
            {
                if(result>0)
                {
                    user.setAvailableBalance(String.valueOf(amt));
                    SharedPrefManager.setUser(user);
                    //Toast.makeText(context,"Registration successful",Toast.LENGTH_SHORT).show();

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            addFragmentWithoutRemove(R.id.container_main,new PaymentSuccessFragment(), ScanToPayFragment.class.getSimpleName());
                            ((HomeActivity)getActivity()).setAvailableBalance(String.valueOf(amt));

                        }
                    });
                    //showFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
                    //showFragment(new OTPFragment(),OTPFragment.class.getSimpleName());
                    //addFragmentWithoutRemove(R.id.container_main,new OTPFragment(), OTPFragment.class.getSimpleName());
                }
                else
                {
                    /*getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            utilities.showAlertDialogOk("Record already exists. Please login instead.");
                        }
                    });*/

                }
            }
        };

        Log.e("UserIdToBeUpdated",user.getUser_id()+"--");
        mViewModel.updateWalletAmount(user.getUser_id(),String.valueOf(amt),callback);
    }

//    new Handler().postDelayed(new Runnable() {
//    @Override
//    public void run() {
//        Intent intentUpdate = new Intent();
//        intentUpdate.setAction(action);
//        intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
//        intentUpdate.putExtra("resId", resId);
//        intentUpdate.putExtra("actionable", actionable);
//        BroadcastService.sendBroadcast(context, intentUpdate);
//    }
//}, delay);

    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            if(user.getUser_type()==1)
            {
                if(position==0)
                {
                    addFragmentWithoutRemove(R.id.container_main,new FundWalletFragment(),FundWalletFragment.class.getSimpleName());
                }
            }
            else
            {
                if(position==0)
                {
                    addFragmentWithoutRemove(R.id.container_main,new QuickTransactionFragment(),QuickTransactionFragment.class.getSimpleName());
                }
            }

        }
    };


    public void scanQRCode()
    {
        try
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M)
            {
                result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
//                result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
//                result2 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
                //result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
                //result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
                if (result0 == 0)// && result1 == 0 && result2 == 0)//result0 == 0 && result1 == 0
                {
//                            Intent i1 = new Intent(ScanQRActivity.this, FullScannerActivity.class);
//                            startActivityForResult(i1,SCAN_QR_CODE);


//                            Intent i1 = new Intent(ScanQRActivity.this, CaptureActivity.class);
//                            startActivityForResult(i1,SCAN_QR_CODE);

                    Intent i1 = new Intent(getActivity(), BarcodeCaptureActivity.class);
                    startActivityForResult(i1,SCAN_QR_CODE);
                }
                else
                {
                    requestPermission();
                }
            }
            else
            {
//                        Intent i1 = new Intent(ScanQRActivity.this, FullScannerActivity.class);
//                        startActivityForResult(i1,SCAN_QR_CODE);


//                        Intent i1 = new Intent(ScanQRActivity.this, CaptureActivity.class);
//                        startActivityForResult(i1,SCAN_QR_CODE);

                Intent i1 = new Intent(getActivity(), BarcodeCaptureActivity.class);
                startActivityForResult(i1,SCAN_QR_CODE);

                //startScan();

//                        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//                        qrScan.setCameraId(0);  // Use a specific camera of the device
//                        qrScan.setBeepEnabled(false);
//                        qrScan.setBarcodeImageEnabled(true);
//                        qrScan.initiateScan();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        //,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_NETWORK_STATE
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean phoneStateAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean networkAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    //boolean networkAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if ( cameraAccepted && phoneStateAccepted && networkAccepted)
                    {
//                        Intent i1 = new Intent(ScanQRActivity.this, FullScannerActivity.class);
//                        startActivityForResult(i1,SCAN_QR_CODE);
                        Intent i1 = new Intent(getActivity(), BarcodeCaptureActivity.class);
                        startActivityForResult(i1,SCAN_QR_CODE);
//                        Intent i1 = new Intent(ScanQRActivity.this, CaptureActivity.class);
//                        startActivityForResult(i1,SCAN_QR_CODE);
                    }
                    else
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_NETWORK_STATE},
                                    PERMISSION_REQUEST_CODE);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        Log.e("PaymentModeFragment","onActivityResult: "+requestCode+" , "+resultCode);
        if (requestCode == SCAN_QR_CODE)
        {
            if (resultCode == CommonStatusCodes.SUCCESS)
            {
                if (data != null)
                {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    String result = barcode.displayValue;
                    Log.e("QR", "Barcode read: " + result);//                try
                    /*try
                    {
                        JSONObject jobj = new JSONObject(result);
                        String propertyId = jobj.optString("propertyId","");
                        getPropertyDetails(propertyId);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        showDialog("");
                    }*/

                }
                else
                {
                    Log.e("QR", "No barcode captured, intent data is null");
                    //showDialog("");
                }
            }
            else
            {
//                statusMessage.setText(String.format(getString(R.string.barcode_error),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
