package com.example.ascen.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ascen.databinding.IptDesignBinding;
import com.example.ascen.modal.IptListModal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.WINDOW_SERVICE;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.IptHolder> {



   List<IptListModal.Response> arrayList;
    private OnItemChangeListener itemChangeListerner;
    private boolean isFromView = false;
    @NonNull
    @Override
    public IptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IptDesignBinding itemTagBinding = IptDesignBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IptHolder(itemTagBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IptHolder holder, int position) {

        IptListModal.Response response = arrayList.get(position);
        holder.itemCompanyBinding.customer.setText(response.getFROMNAME());
        holder.itemCompanyBinding.toCustomer.setText(response.getTONAME());
        holder.itemCompanyBinding.reqNumber.setText(response.getREQUESTNUM());
        holder.itemCompanyBinding.regDate.setText(convertDate(response.getREQDATE()));
        holder.itemCompanyBinding.itemName.setText(response.getITEMNAME());
        holder.itemCompanyBinding.iptStatus.setText(response.getIPTSTATUS());

        if (response.getIPTSTATUS().equals("pending")) {
            holder.itemCompanyBinding.approveStatus.setVisibility(View.VISIBLE);
        } else {
            holder.itemCompanyBinding.approveStatus.setVisibility(View.GONE);
        }

        holder.itemCompanyBinding.approveStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChangeListerner.onStatusChanged(response,true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    public void setList(List<IptListModal.Response> responses,OnItemChangeListener itemChangeListener) {
        this.arrayList = responses;
        this.itemChangeListerner = itemChangeListener;
    }

    public interface OnItemChangeListener {
        void onStatusChanged( IptListModal.Response  responses,boolean isAdd);
    }


    public class IptHolder extends RecyclerView.ViewHolder {
        IptDesignBinding itemCompanyBinding;
        public IptHolder(IptDesignBinding itemCompanyBinding) {
            super(itemCompanyBinding.getRoot());
            this.itemCompanyBinding = itemCompanyBinding;
        }
    }
    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }
    public static String convertDate(String dateString) {
            DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        fromFormat.setLenient(false);
        DateFormat toFormat = new SimpleDateFormat("dd-MMM-yyyy");
        toFormat.setLenient(false);
        Date date = null;
        try {
            date = fromFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return toFormat.format(date);
    }



}
