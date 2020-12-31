package com.example.ascen.dropdown;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ascen.R;

import java.util.ArrayList;

public class DropDownSelector extends PopupWindow implements ClassSelcterInterface {

    Activity context;
    View view;
    int id;
    CommonSelectorListener commonSelectorListener;
    @SuppressLint("NewApi")
    public DropDownSelector(Activity context, View view, ArrayList<SearchArray> arrays,
                            CommonSelectorListener homeworkListener, int id) {
        super(context);
        this.context = context;
        this.commonSelectorListener = homeworkListener;
        this.id = id;
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dropdown_view, null);
        setContentView(dialogView);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        final RecyclerView complaimntsRecycler;
        SearchView searchAcadamy = dialogView.findViewById(R.id.commonSearch);
        complaimntsRecycler = dialogView.findViewById(R.id.commomRecycler);
        TextView district  = dialogView.findViewById(R.id.party_district_choose);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,1);


        searchAcadamy.setQueryHint("Search");
        searchAcadamy.setActivated(true);
        searchAcadamy.onActionViewExpanded();
        searchAcadamy.setFocusable(false);
        searchAcadamy.setIconified(false);
        searchAcadamy.clearFocus();
        complaimntsRecycler.setLayoutManager(gridLayoutManager);
        complaimntsRecycler.setItemAnimator(new DefaultItemAnimator());
        searchAcadamy.setVisibility(View.VISIBLE);

        SearchAdapter searchAdapter = new SearchAdapter(context,this);
        searchAcadamy.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText.toString().toLowerCase());
                return true;
            }
        });
        setItems(arrays,complaimntsRecycler,searchAdapter);
    }
    @SuppressLint("NewApi")
    public void setItems(ArrayList<SearchArray> arrays,RecyclerView recyclerView,SearchAdapter searchAdapter) {
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.setItems(arrays);
        searchAdapter.notifyDataSetChanged();

    }

    public void show(View anchor) {

        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        this.setWidth(width-50);
        this.setHeight(height-50);
        this.setFocusable(true);
        showAtLocation(anchor, Gravity.CENTER, 0, 0);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        dimBehind(this);
    }

    @Override
    public void selectedClass(String selectedClass, int position, String id, String alternateID) {
        commonSelectorListener.selectedId(position,selectedClass,id,alternateID);
        dismiss();
    }
    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }
}
