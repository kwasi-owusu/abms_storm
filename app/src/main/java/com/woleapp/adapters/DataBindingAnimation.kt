package com.woleapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.transition.TransitionManager

class DataBindingAnimation
{
    //@BindingAdapter("android:animatedVisibility")//android:animatedVisibility
    //@BindingAdapter("bind:animatedVisibility")
    fun setVisibility(target: View, isVisible: Boolean) {
        TransitionManager.beginDelayedTransition(target.rootView as ViewGroup)
        target.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    //setAnimatedVisibility
}