package com.example.ascen.presenter;

import com.example.ascen.modal.IptListModal;
import com.example.ascen.modal.IptUpdatePojo;
import com.example.ascen.modal.ToCustomerModal;
import com.example.ascen.network.UserRepository;
import com.google.gson.JsonObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IptListPresenter {


    IptListView iptListView;
    public IptListPresenter(IptListView iptListView) {
        this.iptListView = iptListView;
    }


    public void getList() {

        UserRepository.getIptList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IptListModal>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(IptListModal responseBody) {

                        if (responseBody.getStatus().endsWith("true") ){
                            iptListView.showList(responseBody.getResponse());
                        } else {
                            iptListView.showError("Error in  fetching customer");
                        }
                        iptListView.hideProgress();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iptListView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        iptListView.hideProgress();
                    }
                });
    }
    public void updateIpt(JsonObject jsonObject) {

        UserRepository.updateItp(jsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IptUpdatePojo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(IptUpdatePojo responseBody) {
                        if (responseBody.getStatus().endsWith("true") ){
                            iptListView.updateSuccess();
                            iptListView.showError(responseBody.getMessage());
                        } else {
                            iptListView.showError("Update failed");
                        }
                        iptListView.hideProgress();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iptListView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        iptListView.hideProgress();
                    }
                });
    }
    public interface IptListView {
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showList(IptListModal.Response[] responses);
        void  updateSuccess();
    }
}
