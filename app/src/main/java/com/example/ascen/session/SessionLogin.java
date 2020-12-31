package com.example.ascen.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ascen.MainApplication;
import com.example.ascen.modal.LoginModal;
import com.google.gson.Gson;

public class SessionLogin {

    /**
     * Login Session, User Session
     **/
    public static SharedPreferences sessionLogin = MainApplication.getInstance().getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

    //To save login session
    public static void saveLoginSession() {
        sessionLogin.edit().putBoolean("isValid", true).apply();
    }

    //To clear login session
    public static void clearLoginSession() {
        sessionLogin.edit().putBoolean("isValid", false).apply();
    }

    //To Get login session
    public static boolean getLoginSession() {
        return sessionLogin.getBoolean("isValid", false);
    }


    /**
     * First Time Login
     **/
    static SharedPreferences pref2 = MainApplication.getInstance().getSharedPreferences("WelcomeSessionLogin", Context.MODE_PRIVATE);

    // Save if user opened the app first time
    public static void saveFirstTime(boolean value) {
        pref2.edit().putBoolean("isFirstTime", value).apply();
    }

    // Get if user opened the app first time
    public static boolean getIsFirstTime() {
        return pref2.getBoolean("isFirstTime", false);
    }
    public static void saveToken(String token) {
        pref2.edit().putString("token", token).apply();
    }
    public static String getToken() {
        return pref2.getString("token", "");
    }
    public static void saveCompanyProfile(String token) {
        pref2.edit().putString("company_profile", token).apply();
    }
    public static String getCOmpanyProfile() {
        return pref2.getString("company_profile", "");
    }

    //To save user in session
    public static void saveUser(LoginModal user) {
        Gson gson = new Gson();
        String string = gson.toJson(user);
        sessionLogin.edit().putString("user", string).apply();
    }


    //To get user in session
    public static LoginModal getUser() {
        String string = sessionLogin.getString("user", "");
        Gson gson = new Gson();
        return gson.fromJson(string, LoginModal.class);
    }

    public static void saveDecimals(String demcimal) {
        pref2.edit().putString("decimal", demcimal).apply();
    }
    public static String getDecimal() {
        return pref2.getString("decimal", "##.##");
    }
    public static void saveRate1(String rate) {
        pref2.edit().putString("rate1", rate).apply();
    }
    public static String getRate1() {
        return pref2.getString("rate1", "3");
    }
    public static void saveRate2(String rate) {
        pref2.edit().putString("rate2", rate).apply();
    }
    public static String getRate2() {
        return pref2.getString("rate2", "5");
    }
    public static void saveRate3(String rate) {
        pref2.edit().putString("rate3", rate).apply();
    }
    public static String getRate3() {
        return pref2.getString("rate3", "12");
    }
    public static void saveRate4(String rate) {
        pref2.edit().putString("rate4", rate).apply();
    }
    public static String getRate4() {
        return pref2.getString("rate4", "18");
    }
    public static void saveRate5(String rate) {
        pref2.edit().putString("rate5", rate).apply();
    }
    public static String getRate5() {
        return pref2.getString("rate5", "28");
    }
}
