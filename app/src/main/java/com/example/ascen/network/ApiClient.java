package com.example.ascen.network;

import com.example.ascen.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
        public static String API_URL = "http://117.232.103.178:3000/";
        //    public static String API_URL = "http://gstapi.cbotsolution.com/api/";
        private static final ApiClient ourInstance = new ApiClient();
        public static ApiClient getInstance() {
            return ourInstance;
        }

        private Retrofit retrofit;

        private ApiClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            // Add network request response logger only in debug mode
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(getNetworkLogger());
            }

            builder.addInterceptor(new Interceptor());
            OkHttpClient okHttpClient = builder
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            Gson gson = new Gson();
            gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl( API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory
                            .createWithScheduler(Schedulers.io()))
                    .client(okHttpClient)
                    .build();
        }



        private HttpLoggingInterceptor getNetworkLogger() {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            return loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        public Retrofit getRetrofitInstance() {
            return retrofit;
        }

        public <T> T getApi(Class<T> clz) {
            return ApiClient.getInstance().getRetrofitInstance().create(clz);
        }

}
