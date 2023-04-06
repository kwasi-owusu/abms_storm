package com.woleapp.ui.fragments;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.databinding.LayoutCameraBinding;
import com.woleapp.model.AgencyUser;
import com.woleapp.util.SharedPrefManager;

public class CameraFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    AgencyUser user;
    private LayoutCameraBinding binding;
    private static final int pic_id = 123;
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.e("CashInOptionsFragment", "onReceive");
//            user = SharedPrefManager.getUser();
            user = SharedPrefManager.getAgencyUser();
        }
    };
    @Override
    public void onStart() {
        Log.e("onStart", "onStart");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver,
                new IntentFilter("UpdateUser"));
        super.onStart();
    }

    @Override
    public void onStop() {

        Log.e("onStop", "onStop");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myReceiver);
        super.onStop();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", CameraFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_camera, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        binding.cameraButton.setOnClickListener(this);
        binding.clickImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_image));
//          }
    }
    // This method will help to retrieve the image
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if(resultCode != RESULT_CANCELED) {
            if (requestCode == pic_id) {
                // BitMap is data structure of image file which store the image in memory
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Set the image in imageview for display
                binding.clickImage.setImageBitmap(photo);
            }
        }
    }
    @Override
    public void onClick(View v) {
        if(v == binding.cameraButton){
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(camera_intent, pic_id);
        }
    }
}