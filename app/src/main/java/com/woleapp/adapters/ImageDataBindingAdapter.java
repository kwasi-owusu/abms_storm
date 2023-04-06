package com.woleapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;
import com.github.piasy.biv.view.ImageViewFactory;
import com.woleapp.R;
import com.woleapp.network.APIServiceClient;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;

import java.io.File;

public class ImageDataBindingAdapter
{

    /*@BindingAdapter("android:src")
    public static void setImageUri(ImageView view, String imageUri)
    {
        if (imageUri == null) {
            view.setImageURI(null);
        } else {
            view.setImageURI(Uri.parse(imageUri));
        }
    }*/

    @BindingAdapter("android:src")
    //@BindingAdapter({"android:src","onClickAttachment"})
    public static void setImagePath(ImageView view, String path)//, OnItemClickListener onClick
    {
        showImage( path, view, true);
    }


    @BindingAdapter("android:onClickAttachment")
    //@BindingAdapter({"android:src","onClickAttachment"})
    public static void setClickListener(View view, OnItemClickListener onClick)//, OnItemClickListener onClick
    {
        view.setOnClickListener(onClick);
    }



    public static void showImage(String path, ImageView view,boolean clickable)
    {
        /*Check if the path is a Uri or URL:- */
        if(path.contains(view.getContext().getPackageName()) || path.contains("Android"))
        {
            Uri dat;
            File file = new File(path);
            if (file.exists())
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    Log.e("Package:-",view.getContext().getPackageName()+"");
                    dat = FileProvider.getUriForFile(view.getContext(), view.getContext().getPackageName()+".provider", file);// + "activity.getApplicationContext().getPackageName().provider"
                } else {
                    dat = Uri.fromFile(file);
                }
                view.setImageURI(dat);
            }
            else
            {
                view.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.no_image));//no_image
            }

        }
        else
        {
            Glide.with(view.getContext())
                    .load(APIServiceClient.BASE_URL+path)
                    .placeholder(R.drawable.no_image)
                    .centerInside()//.fitCenter()
                    .listener(new RequestListener<Drawable>()
                    {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("GlideImageLoading","Failed "+e.getMessage());
                            view.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.no_image));
                            e.printStackTrace();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
                        {
                            Log.e("GlideImageLoading","Success");
                            return false;
                        }

                                /*@Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                    return false;
                                }*/
                    })
                    .into(view);
        }

        if(clickable)
        {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogImage(v.getContext(),path);
                }
            });
        }
        //view.setOnClickListener(onClick);

    }

    public static void showImage2(String path, BigImageView view,boolean clickable)
    {
        ImageViewFactory viewFactory = new GlideImageViewFactory();
        view.setProgressIndicator(new ProgressPieIndicator());
        view.setTapToRetry(true);
        view.setImageViewFactory(viewFactory);
        /*Check if the path is a Uri or URL:- */
        if(path.contains(view.getContext().getPackageName()) || path.contains("Android"))
        {
            Uri dat;
            File file = new File(path);
            if (file.exists())
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    Log.e("Package:-",view.getContext().getPackageName()+"");
                    dat = FileProvider.getUriForFile(view.getContext(), view.getContext().getPackageName()+".provider", file);// + "activity.getApplicationContext().getPackageName().provider"
                } else {
                    dat = Uri.fromFile(file);
                }
                view.showImage(dat);
            }
            else
            {
                //view.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.no_image));//no_image
                view.setFailureImage(view.getContext().getResources().getDrawable(R.drawable.no_image));//no_image
            }

        }
        else
        {
            Uri uri = Uri.parse(APIServiceClient.BASE_URL+path);
            //String str = uri.getPath();
            //Log.e("URL",imageUrl+" ##");
            view.showImage(uri);

            /*Glide.with(view.getContext())
                    .load(APIServiceClient.BASE_URL+path)
                    .placeholder(R.drawable.no_image)
                    .centerInside()//.fitCenter()
                    .listener(new RequestListener<Drawable>()
                    {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("GlideImageLoading","Failed "+e.getMessage());
                            //view.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.no_image));
                            view.setFailureImage(view.getContext().getResources().getDrawable(R.drawable.no_image));//no_image
                            e.printStackTrace();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
                        {
                            Log.e("GlideImageLoading","Success");
                            return false;
                        }

                                *//*@Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                    return false;
                                }*//*
                    })
                    .into(view);*/
        }

        if(clickable)
        {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogImage(v.getContext(),path);
                }
            });
        }
        //view.setOnClickListener(onClick);

    }
    public static void showDialogImage(Context context,String path)
    {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_transparent);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.semi_transparent_gray)));

        BigImageView imageView =  dialog2.findViewById(R.id.imageView);
        ImageView ivClose =  dialog2.findViewById(R.id.ivClose);
        showImage2( path, imageView, false);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog2.show();
    }

    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, Uri imageUri) {
        view.setImageURI(imageUri);
    }

    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }
}
