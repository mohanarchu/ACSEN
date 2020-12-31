package com.example.ascen.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Request;

public class Interceptor  implements okhttp3.Interceptor {
    @Override
    public okhttp3.Response intercept(@NonNull okhttp3.Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }
}
