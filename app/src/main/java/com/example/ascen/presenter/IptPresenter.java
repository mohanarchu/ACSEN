package com.example.ascen.presenter;

import com.example.ascen.modal.CustomerModal;
import com.example.ascen.modal.ItpSuccessPojo;
import com.example.ascen.modal.LoginModal;
import com.example.ascen.modal.StateModal;
import com.example.ascen.modal.ToCustomerModal;
import com.example.ascen.network.UserRepository;
import com.example.ascen.session.SessionLogin;
import com.google.gson.JsonObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IptPresenter {



    IplView iplView;

    public IptPresenter(IplView iplView){
        this.iplView = iplView;
    }


    public void getCustomer() {
        iplView.showProgress();
        UserRepository.getCustomer(SessionLogin.getUser().getResult()[0].getSite()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CustomerModal>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(CustomerModal responseBody) {
                        if (responseBody.getStatus().endsWith("true") ){
                            iplView.showCustomer(responseBody);
                        } else {
                            iplView.showError("Error in  fetching customer");
                        }
                        iplView.hideProgress();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iplView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        iplView.hideProgress();
                    }
                });
    }



    public void getCustomerInv(String accountNum) {
        iplView.showProgress();
        UserRepository.getCustomerInv(SessionLogin.getUser().getResult()[0].getSite(),accountNum).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CustomerInvModal>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(CustomerInvModal responseBody) {

                        if (responseBody.getStatus().endsWith("true") ){
                            iplView.showCustomerInv(responseBody.getResponse());
                        } else {
                            iplView.showError("Error in  fetching customer");
                        }
                        iplView.hideProgress();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iplView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        iplView.hideProgress();
                    }
                });
    }



    public void getState() {
        iplView.showProgress();
        UserRepository.getState().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateModal>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(StateModal responseBody) {

                        if (responseBody.getStatus().endsWith("true") ){
                            iplView.showState(responseBody);
                        } else {
                            iplView.showError("Error in  fetching customer");
                        }
                        iplView.hideProgress();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iplView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        iplView.hideProgress();
                    }
                });
    }



    public void getToCustomer(String groupId) {
        iplView.showProgress();
        UserRepository.getToCUstomer(groupId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ToCustomerModal>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(ToCustomerModal responseBody) {

                        if (responseBody.getStatus().endsWith("true") ){
                            iplView.showToCustomer(responseBody.getResponse());
                        } else {
                            iplView.showError("Error in  fetching customer");
                        }
                        iplView.hideProgress();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iplView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        iplView.hideProgress();
                    }
                });
    }
    public void createItp(JsonObject jsonObject) {
        iplView.showProgress();
        UserRepository.createItp(jsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItpSuccessPojo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(ItpSuccessPojo responseBody) {

                        if (responseBody.getStatus().equals("true") ){
                            iplView.iptCreatedSucess();
                        } else {
                            iplView.showError("Error in  creating IPT");
                        }
                        iplView.hideProgress();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iplView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        iplView.hideProgress();
                    }
                });
    }



    public interface IplView {
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showCustomer(CustomerModal response);
        void showCustomerInv(CustomerInvModal.Response[] response);
        void showState(StateModal response);
        void showToCustomer(ToCustomerModal.Response[] response);
        void iptCreatedSucess();
    }
}
