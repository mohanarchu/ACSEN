package com.example.ascen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.example.ascen.databinding.ActivityMainBinding;
import com.example.ascen.modal.LoginModal;
import com.example.ascen.presenter.LoginPresenter;
import com.example.ascen.session.SessionLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoginPresenter.LoginView {




    LoginPresenter loginPresenter;
    ProgressDialog  progressDialog;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginPresenter = new LoginPresenter(this,getApplicationContext());
        progressDialog = new ProgressDialog(MainActivity.this);
        binding.password.setTransformationMethod(new PasswordTransformationMethod());
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.userId.getText().toString().isEmpty()) {
                    showError("Please enter userid");
                    return;
                }
                if (binding.password.getText().toString().isEmpty()) {
                    showError("Please enter password");
                    return;
                }
                loginPresenter.doLogin(binding.userId.getText().toString().toLowerCase(),fetchFCMToken(), binding.password.getText().toString().toLowerCase().trim());
            }
        });
        if (SessionLogin.getLoginSession()) {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
        binding.showPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.password.setTransformationMethod(null);
            } else {
                binding.password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
    }
    private String fetchFCMToken() {
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                          token[0] = task.getResult();
                    }
                });
        return token[0];
    }


    @Override
    public void showProgress() {
    progressDialog.setMessage("Please wait");
    progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMistmatchError(LoginModal loginModal,String deviceId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("User already logged in another device. Logout from other devices and login here.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("EmpCode",loginModal.getResult()[0].getEmpCode());
                jsonObject.addProperty("Acting",loginModal.getResult()[0].getActing());
                jsonObject.addProperty("Dcode",loginModal.getResult()[0].getDcode());
                jsonObject.addProperty("DeviceId",deviceId);
                jsonObject.addProperty("Token",fetchFCMToken());
                jsonObject.addProperty("dataAreaId","hof");
                loginPresenter.updateDeviceId(jsonObject,loginModal.getResult()[0].getEmpCode().toLowerCase(),fetchFCMToken(),binding.password.getText().toString().toLowerCase().trim());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    @Override
    public void showResult(LoginModal loginModal) {
        if (loginModal.getResult()[0].getPassword().toLowerCase().equals(binding.password.getText().toString().toLowerCase().trim())) {
            SessionLogin.saveLoginSession();
            SessionLogin.saveUser(loginModal);
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            showError("Entered invalid password...Try again");
        }

    }
}