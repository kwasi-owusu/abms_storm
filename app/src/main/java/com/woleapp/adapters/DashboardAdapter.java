package com.woleapp.adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.woleapp.R;
import com.woleapp.model.Service;
import com.woleapp.util.OnItemClickListener;


import java.util.List;

/**
 * Created by sharad on 30/5/18.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    Context context;
    private OnItemClickListener.OnItemClickCallback onClick;

    List<Service> serviceList;
    boolean isPayService = false;

    public DashboardAdapter(Context context, List<Service> categoryListDTOS, OnItemClickListener.OnItemClickCallback onClick,boolean isPayService)
    {
        this.context = context;
        this.onClick = onClick;
        this.serviceList = categoryListDTOS;
        this.isPayService = isPayService;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dashboard, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.cardItem.setOnClickListener(new OnItemClickListener(position, onClick));

        /*String image_url = serviceList.get(position).getEXCTYPEImageLocataion();

        Picasso.with(context).load(image_url).into(holder.ivType, new Callback() {
                    @Override
                    public void onSuccess()
                    {
                        Log.e("onSuccess", "onSuccess");
                    }

                    @Override
                    public void onError() {
                        Log.e("onError", "onError");

                        Picasso.with(context).load(R.drawable.demo).error(R.drawable.demo).
                                placeholder(R.drawable.logo).into(holder.ivType);

                    }
                });*/

       // holder.ivService.setImageResource(serviceList.get(position).getService_drawable());
        Glide.with(context)
                .load(serviceList.get(position).getService_drawable())
                .into(holder.ivService);

        holder.tvTitle.setText(serviceList.get(position).getService_name());
        if(isPayService)
        {
            holder.tvServiceProviders.setVisibility(View.VISIBLE);
            holder.tvServiceProviders.setText(serviceList.get(position).getService_providers());
        }
        else
        {
            holder.tvServiceProviders.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivService;
        TextView tvTitle,tvServiceProviders;
        CardView cardItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivService =  itemView.findViewById(R.id.ivService);
            tvTitle =  itemView.findViewById(R.id.tvTitle);
            tvServiceProviders =  itemView.findViewById(R.id.tvServiceProviders);
            cardItem =  itemView.findViewById(R.id.cardItem);
        }
    }
}
