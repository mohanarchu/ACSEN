package com.example.ascen.network;

import com.example.ascen.modal.CustomerModal;
import com.example.ascen.modal.IptListModal;
import com.example.ascen.modal.IptUpdatePojo;
import com.example.ascen.modal.ItpSuccessPojo;
import com.example.ascen.modal.LoginModal;
import com.example.ascen.modal.StateModal;
import com.example.ascen.modal.ToCustomerModal;
import com.example.ascen.presenter.CustomerInvModal;
import com.example.ascen.session.SessionLogin;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class UserRepository {

    public static Observable<LoginModal> doLogin(String empId) {
        return UserApiClient.getInstance().getApi(MainInterface.class)
                .loginUser(empId)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<Response<ResponseBody>> updateDeviceId(JsonObject jsonObject) {
        return ApiClient.getInstance().getApi(MainInterface.class)
                .updateDeviceId(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<CustomerModal> getCustomer(String empId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TmId",empId);
        jsonObject.addProperty("dataAreaId","hof");
        return ApiClient.getInstance().getApi(MainInterface.class)
                .getCustomer(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<CustomerInvModal> getCustomerInv(String empId,String accountNum) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TmId",empId);
        jsonObject.addProperty("AccountNum",accountNum);
        jsonObject.addProperty("dataAreaId","hof");
        return ApiClient.getInstance().getApi(MainInterface.class)
                .getCustomerInv(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<StateModal> getState() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dataAreaId","hof");
        return ApiClient.getInstance().getApi(MainInterface.class)
                .getState(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<ToCustomerModal> getToCUstomer(String groupId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CUSTGROUP",groupId);
        jsonObject.addProperty("dataAreaId","hof");
        return ApiClient.getInstance().getApi(MainInterface.class)
                .getToCustomer(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<IptListModal> getIptList( ) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("EmpCode", SessionLogin.getUser().getResult()[0].getEmpCode());
        jsonObject.addProperty("Dcode", SessionLogin.getUser().getResult()[0].getDcode());
        jsonObject.addProperty("dataAreaId","hof");
        jsonObject.addProperty("STATUS","Pending");
        return ApiClient.getInstance().getApi(MainInterface.class)
                .getIptList(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<IptUpdatePojo> updateItp(JsonObject jsonObject) {
        return ApiClient.getInstance().getApi(MainInterface.class)
                .updateItp(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ItpSuccessPojo> createItp(JsonObject jsonObject) {
        return ApiClient.getInstance().getApi(MainInterface.class)
                .createItp(jsonObject)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
