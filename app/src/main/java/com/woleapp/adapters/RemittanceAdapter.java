package com.woleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.woleapp.R;
import com.woleapp.model.Service;
import com.woleapp.util.OnItemClickListener;

import java.util.List;

public class RemittanceAdapter extends RecyclerView.Adapter<RemittanceAdapter.MyViewHolder>{
    Context context;
    private OnItemClickListener.OnItemClickCallback onClick;

    List<Service> serviceList;
    boolean isPayService = false;

    public RemittanceAdapter(Context context, List<Service> remittanceList, OnItemClickListener.OnItemClickCallback onClick)
    {
        this.context = context;
        this.onClick = onClick;
        this.serviceList = remittanceList;
       // this.isPayService = isPayService;
    }
    @Override
    public RemittanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dashboard, parent, false);
        return new RemittanceAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RemittanceAdapter.MyViewHolder holder, int position) {
        holder.cardItem.setOnClickListener(new OnItemClickListener(position, onClick));

        holder.ivService.setImageResource(serviceList.get(position).getService_drawable());
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
