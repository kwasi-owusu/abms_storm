package com.woleapp.adapters;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import androidx.transition.TransitionManager;

public class DataBindingAnimation1
{
    @BindingAdapter("android:animatedVisibility")
    public static void setVisibility(View view, Boolean isVisible)
    {
        TransitionManager.beginDelayedTransition((ViewGroup) view.getRootView());
        view.setVisibility(isVisible==null?View.GONE:isVisible?View.VISIBLE:View.GONE);

        /*if (imageUri == null) {
            view.setImageURI(null);
        } else {
            view.setImageURI(Uri.parse(imageUri));
        }*/
    }
}
