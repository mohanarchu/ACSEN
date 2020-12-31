package com.example.ascen.dropdown;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ascen.R;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> implements Filterable {


    Context context;
    ArrayList<SearchArray> schools;
    ArrayList<SearchArray> searchSchool;
    ClassSelcterInterface classSelcterInterface;
    public SearchAdapter(Context context, ClassSelcterInterface classSelcterInterface) {
        this.context = context;


        this.classSelcterInterface = classSelcterInterface;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.search_view, parent,
                false);

        return new SearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {

        holder.setClass(searchSchool.get(position).getName());
        if(position % 2 == 1)
        {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().
                    getColor(R.color.smoke));
        } else  {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().
                    getColor(R.color.white));
        }
        holder.itemView.setOnClickListener(view ->
                classSelcterInterface.selectedClass(searchSchool.get(position).getName(),position,
                       String.valueOf(searchSchool.get(position).getId()), String.valueOf(searchSchool.get(position).getAlternareID())));
    }

    public void setItems( ArrayList<SearchArray> schools){
        this.schools = schools;
        this.searchSchool = schools;
    }

    @Override
    public int getItemCount() {
        return searchSchool != null ? searchSchool.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                if (constraint == null || constraint.length() == 0) {
                   searchSchool =schools;
//                    results.count = schools.length;
                } else {
                    ArrayList<SearchArray> filteredResult = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();
                    for (int i = 0; i < schools.size(); i++) {
                        if (schools.get(i).getName(). toLowerCase().contains(searchStr)) {
                             filteredResult.add(schools.get(i));
                        }
                    }
                    searchSchool = filteredResult;
                }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = searchSchool;
                    return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchSchool = (ArrayList<SearchArray>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class SearchHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.CommonName)
        TextView CommonName;
        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setClass(String s) {
            CommonName. setText(s);
        }
    }
}
