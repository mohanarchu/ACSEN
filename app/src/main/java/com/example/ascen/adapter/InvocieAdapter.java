package com.example.ascen.adapter;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ascen.databinding.InvoiceLayoutBinding;
import com.example.ascen.presenter.CustomerInvModal;
import com.example.ascen.utils.InputFilterMinMax;

import java.util.List;

public class InvocieAdapter extends RecyclerView.Adapter<InvocieAdapter.InvoiceHolder> {

    IptEditedListerner iptEditedListerner;
    List<CustomerInvModal.Response> responses;
    @NonNull
    @Override
    public InvoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InvoiceLayoutBinding invoiceLayoutBinding = InvoiceLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new InvoiceHolder(invoiceLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceHolder holder, int position) {
        holder.binding.itemName.setText(responses.get(position).getITEMNAME());
        holder.binding.sNo.setText(position+1+"");
        holder.binding.quantity.setText(responses.get(position).getSALESQTY());
        holder.binding.tranQuantity.setFilters(new InputFilter[]{new InputFilterMinMax("0",responses.get(position).getSALESQTY())});
        holder.binding.tranQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                responses.get(position).setTRANSFERQTY(s.toString());
                iptEditedListerner.onChanged(responses);
            }
        });
    }

    @Override
    public int getItemCount() {
        return responses == null ? 0: responses.size();
    }

    public void setList( List<CustomerInvModal.Response> responses,IptEditedListerner iptEditedListerner) {
        this.iptEditedListerner = iptEditedListerner;
        this.responses = responses;
    }

    class InvoiceHolder extends RecyclerView.ViewHolder{
        InvoiceLayoutBinding binding;
        public InvoiceHolder(@NonNull InvoiceLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface IptEditedListerner {
        void  onChanged(List<CustomerInvModal.Response> responses);
    }
}
