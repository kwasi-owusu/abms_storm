package com.woleapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.woleapp.databinding.LayoutItemListTransactionsBinding;
import com.woleapp.model.Transactions;
import com.woleapp.util.OnItemClickListener;


public class TransactionAdapter extends PagedListAdapter<Transactions, TransactionAdapter.RepoViewHolder> {

    private OnItemClickListener.OnItemClickCallback onClick;
    private static final DiffUtil.ItemCallback<Transactions> itemCallback = new DiffUtil.ItemCallback<Transactions>() {
        @Override
        public boolean areItemsTheSame(@NonNull Transactions oldItem, @NonNull Transactions newItem) {
            return oldItem.getTId() == newItem.getTId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transactions oldItem, @NonNull Transactions newItem) {
            return oldItem.getTId() == newItem.getTId();
        }
    };


    public TransactionAdapter(OnItemClickListener.OnItemClickCallback onClick) {
        super(itemCallback);
        this.onClick = onClick;
    }

    /*public void setClickListeners(OnItemClickListener.OnItemClickCallback onClick/*,OnItemClickListener.OnItemClickCallback onClickAttachment)
    {
        this.onClick = onClick;
        //this.onClickAttachment = onClickAttachment;
    }*/


    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Uses DataBinding to inflate the Item View
        LayoutItemListTransactionsBinding itemBinding = LayoutItemListTransactionsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RepoViewHolder(itemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClick));
        Transactions service = getItem(position);
        holder.bind(service);
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LayoutItemListTransactionsBinding mDataBinding;

        RepoViewHolder(LayoutItemListTransactionsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mDataBinding = itemBinding;

            View itemView = itemBinding.getRoot();
            itemView.setOnClickListener(this);
        }

        void bind(Transactions inventory) {
            //mDataBinding.tvName.setTextSize(35.0f);
            if (inventory == null) {
                Log.e("TAG", "Inventory is null");
                //Binding the elements in the code when the Word is null
                /*Resources resources = mDataBinding.getRoot().getContext().getResources();

                mDataBinding.txtProjectName.setText(resources.getString(R.string.project_name));
                mDataBinding.txtProjectsClientName.setText(resources.getString(R.string.client_name));
                mDataBinding.txtTime.setText(resources.getString(R.string.time));*/
            } else {
                mDataBinding.btnDetails.setOnClickListener(v -> {
                    if (mDataBinding.getIsOpen() == null) {
                        mDataBinding.setIsOpen(true);
                        //mDataBinding.executePendingBindings();
                    } else {
                        if (mDataBinding.getIsOpen()) {
                            mDataBinding.setIsOpen(false);
                            //mDataBinding.executePendingBindings();
                        } else {
                            mDataBinding.setIsOpen(true);
                            //mDataBinding.executePendingBindings();
                        }
                    }
                    //notifyDataSetChanged();

                });
                //When Word is not null, data binding will be automatically done in the layout
                mDataBinding.setInventory(inventory);
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
                Log.e("TAG", "NOTHING");
                //Inventory inventory = getItem(getAdapterPosition());
//                if (word != null && !TextUtils.isEmpty(word.url))
//                {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(word.url));
//                    view.getContext().startActivity(intent);
//                }
            }
        }
    }

    /*public int getJobId(int position)
    {
        ProjectDTO word = getItem(position);
        return word.getId();

    }

    public int getClientId(int position)
    {
        ProjectDTO word = getItem(position);
        return word.getClientId();

    }

    public String getJobTitle(int position)
    {
        ProjectDTO word = getItem(position);
        return word.getProjectName()+"";

    }

    public String getClientName(int position)
    {
        ProjectDTO word = getItem(position);
        return word.getClientName()+"";

    }*/
}
