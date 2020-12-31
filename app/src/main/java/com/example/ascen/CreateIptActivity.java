package com.example.ascen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.ascen.utils.DateUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
                iptPresenter.getCustomer();
            }
        });
        binding.customerInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                iptPresenter.getState();
            }
        });
        binding.toCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateGroupId.isEmpty()){
                    showError("Select state");
                    return;
                }
                iptPresenter.getToCustomer(stateGroupId);
            }
        });


        binding.saveIpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject masterObject = new JsonObject();
                JsonArray jsonElements = new JsonArray();
                for (int i=0;i<invoiceResponse.size();i++){
                    Random rand = new Random();
                    int n = rand.nextInt(100000);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("REQUESTNUM","HV0060-"+n);
                    jsonObject.addProperty("REQDATE", DateUtils.getCurrentDate());
                    jsonObject.addProperty("SALESID",invoiceResponse.get(i).getSALESID());
                    jsonObject.addProperty("FROMCUSTACCOUNT",selectedCustomer.getAccountNum());
                    jsonObject.addProperty("TOCUSTACCOUNT",selectedToCustomer.getACCOUNTNUM());
                    jsonObject.addProperty("FROMNAME",selectedCustomer.getName());
                    jsonObject.addProperty("TONAME",selectedToCustomer.getNAME());
                    jsonObject.addProperty("LINENUM",invoiceResponse.get(i).getLINENUM());
                    jsonObject.addProperty("ITEMID",invoiceResponse.get(i).getITEMID());
                    jsonObject.addProperty("ITEMNAME",invoiceResponse.get(i).getITEMNAME());
                    jsonObject.addProperty("INVOICEDQTY",invoiceResponse.get(i).getSALESQTY());
                    jsonObject.addProperty("TRANSFERQTY",invoiceResponse.get(i).getTRANSFERQTY() == null || invoiceResponse.get(i).getTRANSFERQTY().isEmpty() ? "0" :  invoiceResponse.get(i).getTRANSFERQTY());
                    jsonObject.addProperty("FROMTMID",selectedCustomer.getTmId());
                    jsonObject.addProperty("FROMTMNAME",selectedCustomer.getTmId());
                    jsonObject.addProperty("FROMRBMID",selectedCustomer.getRBMId());
                    jsonObject.addProperty("FROMRBMNAME",selectedCustomer.getRBMNAME());
                    jsonObject.addProperty("FROMDBMSTATUS","pending");
                    jsonObject.addProperty("FROMDBMID",selectedCustomer.getDBMId());
                    jsonObject.addProperty("FROMDBMNAME",selectedCustomer.getDBMNAME());
                    jsonObject.addProperty("FROMDBMSTATUS","pending");
                    jsonObject.addProperty("TOTMID",selectedToCustomer.getDBMId());
                    jsonObject.addProperty("TOTMNAME",selectedToCustomer.getDBMNAME());
                    jsonObject.addProperty("TORBMID",selectedToCustomer.getRBMID());
                    jsonObject.addProperty("TORBMNAME",selectedToCustomer.getRBMNAME());
                    jsonObject.addProperty("TORBMSTATUS","pending");
                    jsonObject.addProperty("TODBMID",selectedToCustomer.getDBMId());
                    jsonObject.addProperty("TODBMNAME", selectedToCustomer.getDBMNAME());
                    jsonObject.addProperty("TODBMSTATUS","pending");
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
                iptPresenter.createItp(masterObject);
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

    @Override
    public void showCustomer(CustomerModal.Response[] response) {
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
                    selectedCustomer = responseData.stream().filter(s->s.getAccountNum().equals(name.split("\n")[1])).collect(Collectors.toList()).get(0);
                }
            }, 9);
            dropDownSelector.show( binding.customerName);
        } else {
            Toast.makeText(getApplicationContext(), "Data not found ", Toast.LENGTH_SHORT).show();
        }
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
            DropDownSelector dropDownSelector = new
                    DropDownSelector(CreateIptActivity.this, binding.customerName,
                    searchArrays, new CommonSelectorListener() {
                @Override
                public void selectedId(int position, String name, String selectedId, String alternateId) {
                    invId = selectedId;
                    binding.customerInvoice.setText(name.split("\n")[0]);
                    List<CustomerInvModal.Response> responses = new ArrayList<>();
                    responses = new ArrayList<>(Arrays.asList(response)).stream().filter(e-> e.getINVOICEID().equals(name.split("\n")[0])).collect(Collectors.toList());
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
    public void showState(StateModal.Response[] response) {
        if (response.length != 0) {
            ArrayList<StateModal.Response> responseData = new ArrayList<>(Arrays.asList(response));
            searchArrays.clear();
            for (int i = 0; i <responseData.size(); i++) {
                searchArrays.add(new SearchArray(responseData.get(i).
                        getNAME() + "\n" +responseData.get(i).getCUSTGROUP(),
                        responseData.get(i).getCUSTGROUP(), ""));
            }
            DropDownSelector dropDownSelector = new
                    DropDownSelector(CreateIptActivity.this, binding.customerName,
                    searchArrays, new CommonSelectorListener() {
                @Override
                public void selectedId(int position, String name, String selectedId, String alternateId) {
                    stateGroupId = selectedId;
                    binding.state.setText(name.split("\n")[0]);
                }
            }, 9);
            dropDownSelector.show( binding.state);
        } else {
            Toast.makeText(getApplicationContext(), "Data not found ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showToCustomer(ToCustomerModal.Response[] response) {
        if (response.length != 0) {
            ArrayList<ToCustomerModal.Response> responseData = new ArrayList<>(Arrays.asList(response));
            searchArrays.clear();
            for (int i = 0; i <responseData.size(); i++) {
                searchArrays.add(new SearchArray(responseData.get(i).
                        getNAME() +"\n"+responseData.get(i).
                        getACCOUNTNUM(),
                        responseData.get(i).getCUSTGROUP(), ""));
            }
            DropDownSelector dropDownSelector = new
                    DropDownSelector(CreateIptActivity.this, binding.toCustomer,
                    searchArrays, new CommonSelectorListener() {
                @Override
                public void selectedId(int position, String name, String selectedId, String alternateId) {
                    binding.toCustomer.setText(name.split("\n")[0]);
                    selectedToCustomer = responseData.stream().filter(s->s.getACCOUNTNUM().equals(name.split("\n")[1])).collect(Collectors.toList()).get(0);
                }
            }, 9);
            dropDownSelector.show( binding.toCustomer);
        } else {
            Toast.makeText(getApplicationContext(), "Data not found ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void iptCreatedSucess() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
