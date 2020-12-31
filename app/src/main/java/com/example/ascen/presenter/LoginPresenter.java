package com.example.ascen.presenter;

import android.content.Context;
import android.provider.Settings;

import com.example.ascen.modal.LoginModal;
import com.example.ascen.network.UserRepository;
import com.google.gson.JsonObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoginPresenter {

    LoginView userView;
    Context context;
    public LoginPresenter(LoginView loginView, Context context) {
        this.userView = loginView;
        this.context = context;
    }

    public void doLogin(String emiId) {
        userView.showProgress();
        UserRepository.doLogin(emiId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginModal>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(LoginModal responseBody) {
                String deviceId = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
                if (responseBody.getResult().length != 0 ){
                    if (responseBody.getResult()[0].getDeviceId().isEmpty() || responseBody.getResult()[0].getDeviceId() == null) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("EmpCode",responseBody.getResult()[0].getEmpCode());
                        jsonObject.addProperty("Acting",responseBody.getResult()[0].getActing());
                        jsonObject.addProperty("Dcode",responseBody.getResult()[0].getDcode());
                        jsonObject.addProperty("DeviceId",deviceId);
                        jsonObject.addProperty("Token","f_Q1E-BZf8A:APA91bHCf6JSPHtB8QKk9O1mKc5Y8HZr_-Ade5DbVVczojvdDLQcegaSPnTdAfg7dYf7-edWbtRoVlbnAusvjPN7IWcFr8DlNDGFmk-61N_FR2oXmOQ_Oz9lNFxBrz07_QwDEcRWSsA");
                        jsonObject.addProperty("dataAreaId","hof");
                        updateDeviceId(jsonObject,responseBody.getResult()[0].getEmpCode());
                    } else {
                        if (deviceId.equals(responseBody.getResult()[0].getDeviceId()))
                             userView.showResult(responseBody);
                        else
                            userView.showMistmatchError(responseBody,deviceId);
                    }

                } else {
                    userView.showError("Login failed... invalid user id");
                }
                userView.hideProgress();
            }
            @Override
            public void onError(Throwable e) {
                userView.hideProgress();
            }

            @Override
            public void onComplete() {
                userView.hideProgress();
            }
        });
    }
    public void updateDeviceId(JsonObject jsonObject,String empId) {
        userView.showProgress();
        UserRepository.updateDeviceId(jsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Response<ResponseBody> responseBody) {
                        if (responseBody.code() == 200){
                            doLogin(empId);
                            userView.hideProgress();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        userView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        userView.hideProgress();
                    }
                });
    }

    public interface LoginView {
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showMistmatchError(LoginModal loginModal,String deviceId);
        void showResult(LoginModal loginModal);

    }

}
