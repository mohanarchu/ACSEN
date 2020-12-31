package com.example.ascen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ascen.adapter.AddressAdapter;
import com.example.ascen.databinding.ActivityHomeBinding;
import com.example.ascen.modal.IptListModal;
import com.example.ascen.presenter.IptListPresenter;
import com.example.ascen.session.SessionLogin;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements IptListPresenter.IptListView {



    ActivityHomeBinding binding;
    IptListPresenter iptListPresenter;
    AddressAdapter iptAdapter = new AddressAdapter();
    List<IptListModal.Response> arrays;
    List<IptListModal.Response> iptUpdateList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnCreateIpt.setOnClickListener(v -> {
           Intent intent = new Intent(getApplicationContext(),CreateIptActivity.class);
           startActivityForResult(intent,100);
       });
        iptListPresenter = new IptListPresenter(this);
        iptListPresenter.getList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        binding.iptRecycler.setLayoutManager(linearLayoutManager);
        binding.iptRecycler.setAdapter(iptAdapter);
        binding.showFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked();
            }
        });
        if (SessionLogin.getUser().getResult()[0].getDcode().equals("TM")) {
            binding.btnCreateIpt.setVisibility(View.VISIBLE);
        } else{
            binding.btnCreateIpt.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Are your sure you want to logout ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SessionLogin.clearLoginSession();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialogButtonClicked() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("  Select");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setTextColor(Color.WHITE);
        String[] animals = {"Waiting for approval", "Approved","Rejected","All"};
        builder.setCustomTitle(textView).setItems(animals, (dialog, which) -> {
            switch (which) {
                case 0:
                   filter("Waiting for approval","Pending");
                   dialog.dismiss();
                    break;
                case 1:
                    filter("Approved","Approved");
                    dialog.dismiss();
                    break;
                case 2:
                    filter("Rejected","Rejected");
                    dialog.dismiss();
                    break;
                case 3:
                    filter("All","All");
                    dialog.dismiss();
                    break;
            }
        });
        AlertDialog alertDialogObject = builder.create();
        ListView listView=alertDialogObject.getListView();
        listView.setDivider(new ColorDrawable(Color.GRAY)); // set color
        listView.setDividerHeight(1);
        listView.setFooterDividersEnabled(false);
        listView.addFooterView(new View(getApplicationContext()));
        alertDialogObject.show();
    }

    void filter(String type,String name) {
        binding.showFilter.setText(name);
        List<IptListModal.Response> array;
        array = arrays.stream().filter(e -> e.getIPTSTATUS().toLowerCase().equals(name.toLowerCase().trim())).collect(Collectors.toList());
        if (name.equals("All")) {
            setData(arrays);
        } else {
            setData(array);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            iptListPresenter.getList();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    void setData(  List<IptListModal.Response> arrays) {
        if (arrays.size() != 0) {
            iptAdapter.setList(arrays, new AddressAdapter.OnItemChangeListener() {
                @Override
                public void onStatusChanged(IptListModal.Response  responses,boolean isAdd) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("REQUESTNUM",responses.getREQUESTNUM());
                    jsonObject.addProperty("DCODE",SessionLogin.getUser().getResult()[0].getDcode());
                    jsonObject.addProperty("EmpCode",SessionLogin.getUser().getResult()[0].getEmpCode());
                    jsonObject.addProperty("TYPE",SessionLogin.getUser().getResult()[0].getEmpCode().equals(responses.getFROMDBMID()) ? "FROM" :
                            SessionLogin.getUser().getResult()[0].getEmpCode().equals(responses.getTORBMID()) ?  "TO" : "BOTH" );
                    jsonObject.addProperty("DBM",responses.getTODBMID().equals(responses.getFROMDBMID()) ? "SAME" : "DIFF");
                    jsonObject.addProperty("RBM",responses.getFROMRBMID().equals(responses.getTORBMID()) ? "SAME" : "DIFF");
                    jsonObject.addProperty("STATUS","Approved");
                    iptListPresenter.updateIpt(jsonObject);
                }
            });
            iptAdapter.notifyDataSetChanged();
            binding.notFound.setVisibility(View.GONE);
            binding.iptRecycler.setVisibility(View.VISIBLE);
        } else {
            binding.notFound.setVisibility(View.VISIBLE);
            binding.iptRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void showList(IptListModal.Response[] responses) {
        arrays = new ArrayList<>(Arrays.asList(responses));
       setData(arrays);
    }

    @Override
    public void updateSuccess() {
        iptListPresenter.getList();
    }

    @Override
    public void onBackPressed() {

    }
}
