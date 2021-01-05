package com.example.ascen.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.ascen.MainActivity;
import com.example.ascen.R;
import com.example.ascen.modal.LoginModal;
import com.example.ascen.presenter.LoginPresenter;
import com.example.ascen.session.SessionLogin;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;

import java.util.Map;

public class FireBaseMessageService extends FirebaseMessagingService implements LoginPresenter.LoginView {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Bundle  extras = new Bundle();  
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        LoginPresenter loginPresenter = new LoginPresenter(this,getApplicationContext());
        if (SessionLogin.getLoginSession()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("EmpCode", SessionLogin.getUser().getResult()[0].getEmpCode());
            jsonObject.addProperty("Acting",SessionLogin.getUser().getResult()[0].getActing());
            jsonObject.addProperty("Dcode",SessionLogin.getUser().getResult()[0].getDcode());
            jsonObject.addProperty("DeviceId",deviceId);
            jsonObject.addProperty("Token",s);
            jsonObject.addProperty("dataAreaId","hof");
            loginPresenter.updateDeviceId(jsonObject,SessionLogin.getUser().getResult()[0].getEmpCode(),s);
        }
        super.onNewToken(s);
    }

    void showNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(message)
                .setContentText(title).setAutoCancel(true).setContentIntent(pendingIntent);;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }
    @SuppressLint("NewApi")
    public String createNotificationChannel(String channelId, String channalName){
        @SuppressLint("InlinedApi") NotificationChannel chan = new  NotificationChannel(channelId, channalName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor( Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(chan);
        return channelId;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showMistmatchError(LoginModal loginModal, String deviceId) {

    }

    @Override
    public void showResult(LoginModal loginModal) {

    }
}
