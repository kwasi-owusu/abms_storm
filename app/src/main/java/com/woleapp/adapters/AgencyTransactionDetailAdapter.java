package com.woleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.woleapp.R;
import com.woleapp.model.AgencyTransactions;

import java.util.List;

public class AgencyTransactionDetailAdapter extends RecyclerView.Adapter<AgencyTransactionDetailAdapter.MyViewHolder> {

    Context context;
    List<AgencyTransactions> transactions;

    public AgencyTransactionDetailAdapter(Context context, List<AgencyTransactions> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public AgencyTransactionDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_transaction_detail_items, parent, false);
        return new AgencyTransactionDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyTransactionDetailAdapter.MyViewHolder holder, int position) {
        final AgencyTransactions transaction = transactions.get(position);

        holder.transID.setText(transaction.getTransID());
        holder.transCat.setText(transaction.getTransCat());
        holder.productCat.setText(transaction.getProductCat());
        holder.productName.setText(transaction.getProductName());
        holder.branchName.setText(transaction.getBranchName());
        holder.officer.setText(transaction.getOfficer());
        holder.amount.setText("Ghs: "+transaction.getTransactionAmount());
        holder.customerName.setText(transaction.getCustomerName());
        holder.accountNumber.setText(transaction.getAccountNumber());
        holder.idType.setText(transaction.getIdType());
        holder.idNumber.setText(transaction.getIdNumber());
        holder.transDate.setText(transaction.getTransDate());
        holder.depositorPayee.setText(transaction.getDepositorPayee());
        holder.agencyName.setText(transaction.getAgencyName());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transID, transCat, productCat, productName, branchName, officer, amount, customerName, accountNumber, idType, idNumber, transDate, depositorPayee, agencyName;

        public MyViewHolder(View itemView) {
            super(itemView);
            transID = itemView.findViewById(R.id.txtTransactionIdValue);
            transCat = itemView.findViewById(R.id.txtTransactionCatValue);
            productCat = itemView.findViewById(R.id.txtProductCatValue);
            productName = itemView.findViewById(R.id.txtProductNameValue);
            branchName = itemView.findViewById(R.id.txtBranchNameValue);
            officer = itemView.findViewById(R.id.txtOfficerValue);
            amount = itemView.findViewById(R.id.txtTransactionAmountValue);
            customerName = itemView.findViewById(R.id.txtCustomerNameValue);
            accountNumber = itemView.findViewById(R.id.txtAccountNumberValue);
            idType = itemView.findViewById(R.id.txtIDTypeValue);
            idNumber = itemView.findViewById(R.id.txtIDNumberValue);
            transDate = itemView.findViewById(R.id.txtTransactionDateValue);
            depositorPayee = itemView.findViewById(R.id.txtDepositorPayeeValue);
            agencyName = itemView.findViewById(R.id.txtAgencyNameValue);
        }
    }
}
