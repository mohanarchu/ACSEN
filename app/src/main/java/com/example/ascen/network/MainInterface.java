package com.example.ascen.network;

import com.example.ascen.modal.CustomerModal;
import com.example.ascen.modal.IptListModal;
import com.example.ascen.modal.IptUpdatePojo;
import com.example.ascen.modal.ItpSuccessPojo;
import com.example.ascen.modal.LoginModal;
import com.example.ascen.modal.StateModal;
import com.example.ascen.modal.ToCustomerModal;
import com.example.ascen.presenter.CustomerInvModal;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainInterface {



    @GET("NewApp/IPT/ISLogin.php")
    Observable<LoginModal> loginUser(@Query("EmpCode") String empId);

    @POST("API/Deviceid")
    Observable<Response<ResponseBody>> updateDeviceId(@Body JsonObject empId);

    @POST("API/Customers")
    Observable<CustomerModal> getCustomer(@Body JsonObject jsonObject);

    @POST("API/Customerinv")
    Observable<CustomerInvModal> getCustomerInv(@Body JsonObject jsonObject);


    @POST("API/States")
    Observable<StateModal> getState(@Body JsonObject jsonObject);

    @POST("API/ToCustomers")
    Observable<ToCustomerModal> getToCustomer(@Body JsonObject jsonObject);

    @POST("API/CreateIPT")
    Observable<ItpSuccessPojo> createItp(@Body JsonObject jsonObject);

    @POST("API/IPTUpdate")
    Observable<IptUpdatePojo> updateItp(@Body JsonObject jsonObject);

    @POST("API/IPTList")
    Observable<IptListModal> getIptList(@Body JsonObject jsonObject);
}
