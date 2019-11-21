package com.mvprecyclerviewrestapi.retroutils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static String HEADER_CONTENT_TYPE = "Content-Type";
    private static String HEADER_CONTENT_TYPE_VALUE = "application/json";

    private static String HEADER_KRY = "key";
    private static String HEADER_KRY_VALUE = "value";

    private static OkHttpClient okHttpClient = null;
    private static Retrofit retrofit = null;

    private static int TIMEOUT = 2 * 60 * 1000;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConfig.BASE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static OkHttpClient getRequestHeader() {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder ongoing = chain.request().newBuilder();
//                            ongoing.addHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE);
//                            ongoing.addHeader(HEADER_KRY, HEADER_KRY_VALUE);
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}