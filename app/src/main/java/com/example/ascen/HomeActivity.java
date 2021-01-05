package com.example.ascen;

import androidx.annotation.NonNull;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import com.example.ascen.modal.LoginModal;
import com.example.ascen.presenter.IptListPresenter;
import com.example.ascen.presenter.LoginPresenter;
import com.example.ascen.session.SessionLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements IptListPresenter.IptListView, LoginPresenter.LoginView {



    ActivityHomeBinding binding;
    IptListPresenter iptListPresenter;
    AddressAdapter iptAdapter = new AddressAdapter();
    List<IptListModal.Response> arrays;
    LoginPresenter loginPresenter;
    List<IptListModal.Response> iptUpdateList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        FirebaseApp.initializeApp(this);
        setContentView(binding.getRoot());
        binding.btnCreateIpt.setOnClickListener(v -> {
           Intent intent = new Intent(getApplicationContext(),CreateIptActivity.class);
           startActivityForResult(intent,100);
       });
        iptListPresenter = new IptListPresenter(this);
        loginPresenter = new LoginPresenter(this,getApplicationContext());
        iptListPresenter.getList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        binding.iptRecycler.setLayoutManager(linearLayoutManager);
        binding.iptRecycler.setAdapter(iptAdapter);
        binding.showFilter.setOnClickListener(v -> showAlertDialogButtonClicked());
        binding.showFilterText.setOnClickListener(v -> showAlertDialogButtonClicked());
        binding.empName.setText(SessionLogin.getUser().getResult()[0].getEmpName());
        if (SessionLogin.getUser().getResult()[0].getDcode().equals("TM") || !SessionLogin.getUser().getResult()[0].getActing().equals("Single")) {
            binding.btnCreateIpt.setVisibility(View.VISIBLE);
        } else {
            binding.btnCreateIpt.setVisibility(View.GONE);
        }
        if (SessionLogin.getIsFirstTime()) {
             fetchFCMToken();
        }
    }


    private void fetchFCMToken() {
                FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    Log.i("TAG","Toen to Send"+token);
                    // Get new FCM registration token

                    String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("EmpCode", SessionLogin.getUser().getResult()[0].getEmpCode());
                    jsonObject.addProperty("Acting",SessionLogin.getUser().getResult()[0].getActing());
                    jsonObject.addProperty("Dcode",SessionLogin.getUser().getResult()[0].getDcode());
                    jsonObject.addProperty("DeviceId",deviceId);
                    jsonObject.addProperty("Token",token);
                    jsonObject.addProperty("dataAreaId","hof");
                    SessionLogin.saveFirstTime(false);
                    loginPresenter.updateDeviceId(jsonObject,SessionLogin.getUser().getResult()[0].getEmpCode(),token);
                });
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
                    SessionLogin.saveFirstTime(true);
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
        binding.showFilterText.setText(name);
        List<IptListModal.Response> array = new ArrayList<>();
        array.clear();
        for (int i = 0; i < arrays.size(); i++) {
            if (arrays.get(i).getIPTSTATUS().toLowerCase().equals(name.toLowerCase().trim())) {
                array.add(arrays.get(i));
            }
        }
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

    @Override
    public void showMistmatchError(LoginModal loginModal, String deviceId) {

    }

    @Override
    public void showResult(LoginModal loginModal) {

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
