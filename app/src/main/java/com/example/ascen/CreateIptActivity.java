package com.example.ascen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ascen.adapter.InvocieAdapter;
import com.example.ascen.databinding.ActivityCreateIptBinding;
import com.example.ascen.dropdown.CommonSelectorListener;
import com.example.ascen.dropdown.DropDownSelector;
import com.example.ascen.dropdown.SearchArray;
import com.example.ascen.modal.CustomerModal;
import com.example.ascen.modal.StateModal;
import com.example.ascen.modal.ToCustomerModal;
import com.example.ascen.presenter.CustomerInvModal;
import com.example.ascen.presenter.IptPresenter;
import com.example.ascen.session.SessionLogin;
import com.example.ascen.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateIptActivity extends AppCompatActivity implements IptPresenter.IplView {


    IptPresenter iptPresenter;
    ActivityCreateIptBinding binding;
    String customerAccountNumber = "",invId = "",stateGroupId ="";
    ArrayList<SearchArray> searchArrays = new ArrayList<>();
    InvocieAdapter invocieAdapter = new InvocieAdapter();
    ProgressDialog progressDialog;
    List<CustomerInvModal.Response> invoiceResponse;
    CustomerModal.Response selectedCustomer;
    ToCustomerModal.Response selectedToCustomer;

    String reqNumber   = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateIptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Create IPT");
        iptPresenter = new IptPresenter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        binding.invoiceRecycler.setLayoutManager(linearLayoutManager);
        binding.invoiceRecycler.setAdapter(invocieAdapter);
        binding.customerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected() || SessionLogin.getItpDetails("customer").isEmpty()) {
                    iptPresenter.getCustomer();
                } else {
                    CustomerModal customerModal = new Gson().fromJson(SessionLogin.getItpDetails("customer"),CustomerModal.class);
                    showCustomersPojo(customerModal.getResponse());
                }
            }
        });
        binding.customerInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkConnected()) {
                    showError("Check internet connection");
                    return;
                }

                if (customerAccountNumber.isEmpty()){
                    showError("Choose customer");
                    return;
                }
                iptPresenter.getCustomerInv(customerAccountNumber);
            }
        });
        binding.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( binding.customerName.getText().toString().isEmpty() || binding.customerInvoice.getText().toString().isEmpty()) {
                    showError("Please select customer name and invoice ");
                    return;
                }
                if (isNetworkConnected() || SessionLogin.getItpDetails("state").isEmpty()) {
                    iptPresenter.getState();
                } else {
                    showStatePojo(new Gson().fromJson(SessionLogin.getItpDetails("state"),StateModal.class).getResponse());
                }
            }
        });
        binding.toCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showError("Check internet connection");
                    return;
                }

                if (stateGroupId.isEmpty()){
                    showError("Select state");
                    return;
                }
                iptPresenter.getToCustomer(stateGroupId);;

            }
        });


        binding.saveIpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isNetworkConnected()) {
                    showError("Check internet connection");
                    return;
                }

                if (binding.customerName.getText().toString().isEmpty() || binding.customerInvoice.getText().toString().isEmpty() || binding.state.getText().toString().isEmpty() || binding.toCustomer.getText().toString().isEmpty()) {
                    showError("Choose all the details");
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("EmpCode",SessionLogin.getUser().getResult()[0].getEmpCode());
                iptPresenter.getRequestNumber(jsonObject);

            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(CreateIptActivity.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }


    void showCustomersPojo(CustomerModal.Response[] response) {
        if (response.length != 0) {
            ArrayList<CustomerModal.Response> responseData = new ArrayList<>(Arrays.asList(response));
            searchArrays.clear();
            for (int i = 0; i <responseData.size(); i++) {
                searchArrays.add(new SearchArray(responseData.get(i).
                        getName() + "\n" +responseData.get(i).getAccountNum(),
                        responseData.get(i).getAccountNum(), ""));
            }
            DropDownSelector dropDownSelector = new
                    DropDownSelector(CreateIptActivity.this, binding.customerName,
                    searchArrays, new CommonSelectorListener() {
                @Override
                public void selectedId(int position, String name, String selectedId, String alternateId) {
                    customerAccountNumber = selectedId;
                    binding.customerName.setText(name.split("\n")[0]);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        selectedCustomer = responseData.stream().filter(s->s.getAccountNum().equals(name.split("\n")[1])).collect(Collectors.toList()).get(0);
                    } else {
                        for (CustomerModal.Response response1 : responseData) {
                            if(response1.getAccountNum().equals(name.split("\n")[1])) {
                                selectedCustomer = response1;
                            }
                        }
                    }
                    selectedToCustomer = null;
                    binding.toCustomer.setText("");
                    stateGroupId = "";
                    binding.customerInvoice.setText("");
                    binding.state.setText("");
                    invocieAdapter.setList(null, new InvocieAdapter.IptEditedListerner() {
                        @Override
                        public void onChanged(List<CustomerInvModal.Response> responses) {

                        }
                    });
                    invocieAdapter.notifyDataSetChanged();
                }
            }, 9);
            dropDownSelector.show( binding.customerName);
        } else {
            Toast.makeText(getApplicationContext(), "Data not found ", Toast.LENGTH_SHORT).show();
        }
    }

    void showStatePojo(StateModal.Response[] response) {
        if (response.length != 0) {
            ArrayList<StateModal.Response> responseData = new ArrayList<>(Arrays.asList(response));
            searchArrays.clear();
            for (int i = 0; i <responseData.size(); i++) {
                searchArrays.add(new SearchArray(responseData.get(i).
                        getNAME() + "\n" +responseData.get(i).getCUSTGROUP(),
                        responseData.get(i).getCUSTGROUP(), ""));
            }
            DropDownSelector dropDownSelector = new DropDownSelector(CreateIptActivity.this, binding.customerName, searchArrays, (position, name, selectedId, alternateId) -> {
                stateGroupId = selectedId;
                binding.state.setText(name.split("\n")[0]);
                binding.toCustomer.setText("");
                selectedToCustomer = null;
            }, 9);
            dropDownSelector.show( binding.state);
        } else {
            Toast.makeText(getApplicationContext(), "Data not found ", Toast.LENGTH_SHORT).show();
        }
    }

    void showToCustomerPojo(ToCustomerModal.Response[] response) {
        if (response.length != 0) {
            ArrayList<ToCustomerModal.Response> responseData = new ArrayList<>(Arrays.asList(response));
            searchArrays.clear();
            for (int i = 0; i <responseData.size(); i++) {
                searchArrays.add(new SearchArray(responseData.get(i).
                        getNAME() +"\n"+responseData.get(i).
                        getACCOUNTNUM(),
                        responseData.get(i).getCUSTGROUP(), ""));
            }
            DropDownSelector dropDownSelector = new DropDownSelector(CreateIptActivity.this, binding.toCustomer,
                    searchArrays, (position, name, selectedId, alternateId) -> {

                    if (selectedCustomer.getAccountNum().equals(name.split("\n")[1].trim())) {
                        showError("From and to customer are the same. Choose diffrent one ");

                    } else {
                        binding.toCustomer.setText(name.split("\n")[0]);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            selectedToCustomer = responseData.stream().filter(s->s.getACCOUNTNUM().equals(name.split("\n")[1])).collect(Collectors.toList()).get(0);
                        }  else {
                            for (ToCustomerModal.Response response1 : responseData) {
                                if(response1.getACCOUNTNUM().equals(name.split("\n")[1])) {
                                    selectedToCustomer = response1;
                                }
                            }
                        }

                    }


                    }, 9);
            dropDownSelector.show( binding.toCustomer);
        } else {
            Toast.makeText(getApplicationContext(), "Data not found ", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void showCustomer(CustomerModal response) {
       showCustomersPojo(response.getResponse());
       SessionLogin.saveIptDeatils(new Gson().toJson(response),"customer");
    }

    private ArrayList<SearchArray> unique(List<SearchArray> list) {
        ArrayList<SearchArray> uniqueList = new ArrayList<>();
        Set<SearchArray> uniqueSet = new HashSet<>();
        for (SearchArray obj : list) {
            if (uniqueSet.add(obj)) {
                uniqueList.add(obj);
            }
        }
        return uniqueList;
    }

    @Override
    public void showCustomerInv(CustomerInvModal.Response[] response) {
        if (response.length != 0) {
            ArrayList<CustomerInvModal.Response> responseData = new ArrayList<>(Arrays.asList(response));
            searchArrays.clear();
            for (int i = 0; i <responseData.size(); i++) {
                searchArrays.add(new SearchArray(responseData.get(i).
                        getINVOICEID() + "\n" +responseData.get(i).getNAME(),
                        responseData.get(i).getINVOICEID(), ""));
            }
            List<SearchArray> allEvents = searchArrays;
             ArrayList<SearchArray> noRepeat = new ArrayList<SearchArray>();

            for (SearchArray event : allEvents) {
                boolean isFound = false;
                // check if the event name exists in noRepeat
                for (SearchArray e : noRepeat) {
                    if (e.getId().equals(event.getId()) || (e.equals(event))) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) noRepeat.add(event);
            }
            DropDownSelector dropDownSelector = new
                    DropDownSelector(CreateIptActivity.this, binding.customerName,
                    noRepeat, new CommonSelectorListener() {
                @Override
                public void selectedId(int position, String name, String selectedId, String alternateId) {
                    invId = selectedId;
                    binding.customerInvoice.setText(name.split("\n")[0]);
                    List<CustomerInvModal.Response> responses = new ArrayList<>();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        responses = new ArrayList<>(Arrays.asList(response)).stream().filter(e-> e.getINVOICEID().equals(name.split("\n")[0])).collect(Collectors.toList());
                    } else {
                        for (CustomerInvModal.Response response1 : response) {
                            if(response1.getINVOICEID().equals(name.split("\n")[1])) {
                                responses .add(response1);
                            }
                        }

                    }
                    stateGroupId = "";
                    binding.state.setText("");
                    binding.toCustomer.setText("");
                    selectedToCustomer = null;
                    invocieAdapter.setList(responses, new InvocieAdapter.IptEditedListerner() {
                        @Override
                        public void onChanged(List<CustomerInvModal.Response> responses) {
                            invoiceResponse = responses;
                        }
                    });
                    invocieAdapter.notifyDataSetChanged();
                }
            }, 9);
            dropDownSelector.show( binding.customerName);
        } else {
            Toast.makeText(getApplicationContext(), "Data not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showState(StateModal response) {
        showStatePojo(response.getResponse());
        SessionLogin.saveIptDeatils(new Gson().toJson(response),"state");
    }

    @Override
    public void showRequestCode(String code) {
        reqNumber = code;
        String formatted = "";
        JsonObject masterObject = new JsonObject();
        JsonArray jsonElements = new JsonArray();
        for (int i=0;i<invoiceResponse.size();i++) {
            Random rand = new Random();
            int n = rand.nextInt(100000);
            JsonObject jsonObject = new JsonObject();
            formatted = String.format("%05d", Integer.parseInt(reqNumber) + i + 1);
            jsonObject.addProperty("REQUESTNUM",SessionLogin.getUser().getResult()[0].getEmpCode()+"-"+formatted);
            jsonObject.addProperty("REQDATE", DateUtils.getCurrentDate());
            jsonObject.addProperty("SALESID",invoiceResponse.get(i).getSALESID());
            jsonObject.addProperty("FROMCUSTACCOUNT",selectedCustomer.getAccountNum());
            jsonObject.addProperty("TOCUSTACCOUNT",selectedToCustomer.getACCOUNTNUM());
            jsonObject.addProperty("FROMNAME",selectedCustomer.getName());
            jsonObject.addProperty("INVOICEID",invoiceResponse.get(i).getINVOICEID());
            jsonObject.addProperty("TONAME",selectedToCustomer.getNAME());
            jsonObject.addProperty("LINENUM",invoiceResponse.get(i).getLINENUM());
            jsonObject.addProperty("ITEMID",invoiceResponse.get(i).getITEMID());
            jsonObject.addProperty("ITEMNAME",invoiceResponse.get(i).getITEMNAME());
            jsonObject.addProperty("INVOICEDQTY",invoiceResponse.get(i).getSALESQTY());
            jsonObject.addProperty("TRANSFERQTY",invoiceResponse.get(i).getTRANSFERQTY() == null ||
                    invoiceResponse.get(i).getTRANSFERQTY().isEmpty() ? "0" :  invoiceResponse.get(i).getTRANSFERQTY());
            jsonObject.addProperty("FROMTMID",selectedCustomer.getTmId());
            jsonObject.addProperty("FROMTMNAME",SessionLogin.getUser().getResult()[0].getEmpName());
            jsonObject.addProperty("FROMRBMID",selectedCustomer.getRBMId());
            jsonObject.addProperty("FROMRBMNAME",selectedCustomer.getRBMNAME());
            jsonObject.addProperty("FROMDBMID",selectedCustomer.getDBMId());
            jsonObject.addProperty("FROMDBMNAME",selectedCustomer.getDBMNAME());
            jsonObject.addProperty("TOTMID",selectedToCustomer.getDBMId());
            jsonObject.addProperty("TOTMNAME",selectedToCustomer.getDBMNAME());
            jsonObject.addProperty("TORBMID",selectedToCustomer.getRBMID());
            jsonObject.addProperty("TORBMNAME",selectedToCustomer.getRBMNAME());
            jsonObject.addProperty("TODBMID",selectedToCustomer.getDBMId());
            jsonObject.addProperty("TODBMNAME", selectedToCustomer.getDBMNAME());
            jsonObject.addProperty("TODBMSTATUS","pending");
            jsonObject.addProperty("FROMDBMSTATUS","pending");
            jsonObject.addProperty("TORBMSTATUS","pending");
            jsonObject.addProperty("FROMRBMSTATUS","pending");
            jsonObject.addProperty("IPTSTATUS","pending");
            jsonObject.addProperty("DATAAREAID","hof");
            jsonObject.addProperty("RECVERSION","1");
            Random rands = new Random();
            int ns = rands.nextInt(1000000);
            jsonObject.addProperty( "RECID", ns+"");
            jsonElements.add(jsonObject);
        }
        masterObject.add("data",jsonElements);
        masterObject.addProperty("multipleInsert",true);
        iptPresenter.createItp(masterObject,formatted);
    }

    @Override
    public void showToCustomer(ToCustomerModal.Response[] response) {
          showToCustomerPojo(response);
    }

    @Override
    public void iptCreatedSucess() {
        showError("Ipt created successfully");
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    @SuppressLint("MissingPermission")
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
