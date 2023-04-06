package com.woleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.woleapp.databinding.LayoutItemListInvoicePreviewBinding;
import com.woleapp.model.Inventory;

import java.util.List;

/*import com.woleapp.R;
import com.woleapp.model.Service;
import com.woleapp.util.OnItemClickListener;*/

/**
 * Created by sharad on 30/5/18.
 */

public class InvoicePreviewItemsAdapter extends RecyclerView.Adapter<InvoicePreviewItemsAdapter.RepoViewHolder> {

    Context context;

    List<Inventory> serviceList;

    public InvoicePreviewItemsAdapter(Context context, List<Inventory> categoryListDTOS)
    {
        this.context = context;
        this.serviceList = categoryListDTOS;
    }

    /*@Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dashboard, parent, false);
        return new MyViewHolder(view);

    }*/

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Uses DataBinding to inflate the Item View
        
        LayoutItemListInvoicePreviewBinding itemBinding = LayoutItemListInvoicePreviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RepoViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position)
    {
        Inventory service = serviceList.get(position);
        //holder.bind(item);
        holder.bind(service,position);
    }

    /*@Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.cardItem.setOnClickListener(new OnItemClickListener(position, onClick));

        *//*String image_url = serviceList.get(position).getEXCTYPEImageLocataion();

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
                });*//*

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

    }*/


    @Override
    public int getItemCount() {
        return serviceList.size();
    }



    /*public class MyViewHolder extends RecyclerView.ViewHolder {

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
    }*/


    /**
     * View Holder for a {@link Inventory} RecyclerView list item.
     */
    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final LayoutItemListInvoicePreviewBinding mDataBinding;

        RepoViewHolder(LayoutItemListInvoicePreviewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mDataBinding = itemBinding;

            View itemView = itemBinding.getRoot();
            itemView.setOnClickListener(this);
        }

        void bind(Inventory service, int position)
        {
            //mDataBinding.tvName.setTextSize(35.0f);
            if (service == null)
            {
                //Binding the elements in the code when the Word is null
                /*Resources resources = mDataBinding.getRoot().getContext().getResources();

                mDataBinding.txtProjectName.setText(resources.getString(R.string.project_name));
                mDataBinding.txtProjectsClientName.setText(resources.getString(R.string.client_name));
                mDataBinding.txtTime.setText(resources.getString(R.string.time));*/

            }
            else
            {

                /*mDataBinding.btnDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(mDataBinding.getIsOpen()==null)
                        {
                            mDataBinding.setIsOpen(true);
                            //mDataBinding.executePendingBindings();
                        }
                        else
                        {
                            if(mDataBinding.getIsOpen())
                            {
                                mDataBinding.setIsOpen(false);
                                //mDataBinding.executePendingBindings();
                            }
                            else
                            {
                                mDataBinding.setIsOpen(true);
                                //mDataBinding.executePendingBindings();
                            }
                        }
                        //notifyDataSetChanged();

                    }
                });*/
                //When Word is not null, data binding will be automatically done in the layout
                mDataBinding.setItem(service);
                mDataBinding.setIndex(position);
                //mDataBinding.setOnClickAttachment(new OnItemClickListener(position, onClickAttachment));

                //For Immediate Binding
                mDataBinding.executePendingBindings();
            }
        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            if (getAdapterPosition() > RecyclerView.NO_POSITION) {
                //Inventory inventory = getItem(getAdapterPosition());
                //Service service = serviceList.get(getAdapterPosition());



//                if (word != null && !TextUtils.isEmpty(word.url))
//                {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(word.url));
//                    view.getContext().startActivity(intent);
//                }
            }
        }
    }
}
