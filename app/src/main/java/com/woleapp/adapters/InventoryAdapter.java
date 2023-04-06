package com.woleapp.adapters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.woleapp.R;
import com.woleapp.databinding.LayoutItemListInventoryBinding;
import com.woleapp.model.Inventory;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.UtilsAndExtensionsKt;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Adapter for the list of repositories.
 *
 * @author Kaushik N Sanji
 */
public class InventoryAdapter extends PagedListAdapter<Inventory, InventoryAdapter.RepoViewHolder> {

    /**
     * DiffUtil to compare the Word data (old and new)
     * for issuing notify commands suitably to update the list
     */

    private long mLastClickTime = 0;
    OnItemClickListener.OnItemClickCallback onClick, onClickAttachment;
    private static DiffUtil.ItemCallback<Inventory> REPO_COMPARATOR
            = new DiffUtil.ItemCallback<Inventory>() {
        @Override
        public boolean areItemsTheSame(Inventory oldItem, Inventory newItem) {
            //return oldItem.getProjectName().equals(newItem.getProjectName());
            boolean result1 = oldItem.getInventory_id().intValue() == newItem.getInventory_id().intValue();
            //boolean result2 = oldItem.getUnreadMessageCount().intValue()==newItem.getUnreadMessageCount().intValue();
            return result1;//&&result2;
        }

        @Override
        public boolean areContentsTheSame(Inventory oldItem, Inventory newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setClickListeners(OnItemClickListener.OnItemClickCallback onClick/*,OnItemClickListener.OnItemClickCallback onClickAttachment*/) {
        this.onClick = onClick;
        //this.onClickAttachment = onClickAttachment;
    }

    public InventoryAdapter() {
        super(REPO_COMPARATOR);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Uses DataBinding to inflate the Item View
        LayoutItemListInventoryBinding itemBinding = LayoutItemListInventoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RepoViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClick));
        holder.bind(getItem(position), position);
    }

    /**
     * View Holder for a {@link Inventory} RecyclerView list item.
     */
    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LayoutItemListInventoryBinding mDataBinding;

        RepoViewHolder(LayoutItemListInventoryBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mDataBinding = itemBinding;

            View itemView = itemBinding.getRoot();
            itemView.setOnClickListener(this);
        }

        void bind(Inventory inventory, int position) {
            //mDataBinding.tvName.setTextSize(35.0f);
            if (inventory == null) {
                //Binding the elements in the code when the Word is null
                /*Resources resources = mDataBinding.getRoot().getContext().getResources();

                mDataBinding.txtProjectName.setText(resources.getString(R.string.project_name));
                mDataBinding.txtProjectsClientName.setText(resources.getString(R.string.client_name));
                mDataBinding.txtTime.setText(resources.getString(R.string.time));*/

            } else {
                mDataBinding.btnDetails.setOnClickListener(v -> {
//                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//                        return;
//                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
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
                String imagePath;
                if (inventory.getImageLocalPath() != null && !inventory.getImageLocalPath().isEmpty()) {
                    imagePath = inventory.getImageLocalPath();
                    Glide.with(mDataBinding.ivProduct.getContext())
                            .load(imagePath)
                            .into(mDataBinding.ivProduct);
                } else {
                    imagePath = inventory.getImage_path();
                    UtilsAndExtensionsKt.setDecodedImageToImageView(imagePath, mDataBinding.ivProduct);
                }
                mDataBinding.btnEdit.setVisibility(View.VISIBLE);
                mDataBinding.btnDelete.setVisibility(View.VISIBLE);
                mDataBinding.setOnClickAttachment(new OnItemClickListener(position, onClickAttachment));
                mDataBinding.setOnClickAttachment(new OnItemClickListener(position, onClick));
                mDataBinding.btnSell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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
                Inventory inventory = getItem(getAdapterPosition());
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
