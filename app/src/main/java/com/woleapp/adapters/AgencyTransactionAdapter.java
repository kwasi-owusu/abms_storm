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

public class AgencyTransactionAdapter extends RecyclerView.Adapter<AgencyTransactionAdapter.MyViewHolder>{

    Context context;
    private OnItemClickListener.OnItemClickCallback onClick;
    List<Service> serviceList;

    public AgencyTransactionAdapter(Context context, List<Service> transactionCats, OnItemClickListener.OnItemClickCallback onClick) {
        this.context = context;
        this.onClick = onClick;
        this.serviceList = transactionCats;
    }

    @NonNull
    @Override
    public AgencyTransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_fragment_list, parent, false);
        return new AgencyTransactionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyTransactionAdapter.MyViewHolder holder, int position) {
        holder.transactionCardItem.setOnClickListener(new OnItemClickListener(position, onClick));
        holder.imageService.setImageResource(serviceList.get(position).getService_drawable());
        holder.txtTitle.setText(serviceList.get(position).getService_name());
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView transactionCardItem;
        ImageView imageService;
        TextView txtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageService = itemView.findViewById(R.id.imageService);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            transactionCardItem = itemView.findViewById(R.id.transactionCardItem);
        }
    }
}
