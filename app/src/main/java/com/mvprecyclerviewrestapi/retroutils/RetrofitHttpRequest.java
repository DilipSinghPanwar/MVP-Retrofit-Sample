package com.mvprecyclerviewrestapi.retroutils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitHttpRequest {

    private static final String TAG = RetrofitHttpRequest.class.getSimpleName();

    private ApiInterface apiService;
    private Context mContext;
    private HttpRequestReciver httpRequestReciver;

    public RetrofitHttpRequest(Context mContext) {
        this.mContext = mContext;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void setBaseRequestListner(HttpRequestReciver httpRequestReciver) {
        this.httpRequestReciver = httpRequestReciver;
    }

    public Callback<JsonElement> responseCallback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            if (response.code() == 200) {
                httpRequestReciver.onSuccess(response.body());
            } else {
                httpRequestReciver.onFailure(response.toString());
            }
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            String message = null;
            if (t instanceof NetworkErrorException) {
                message = "Please check your internet connection";
            } else if (t instanceof ParseException) {
                message = "Parsing error! Please try again after some time!!";
            } else if (t instanceof TimeoutException) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else if (t instanceof UnknownHostException) {
                message = "Please check your internet connection and try later";
            } else if (t instanceof Exception) {
                message = t.getMessage();
            }
            httpRequestReciver.onFailure(message);
        }
    };

    public void postHttpRequest(String apiName, JsonObject params) {
        if (isConnected()) {
            Call<JsonElement> call = apiService.postHttpRequest(apiName, params);
            call.enqueue(responseCallback);
        } else {
            httpRequestReciver.onFailure("No Internet Connection.");
        }

    }

    public void postHttpRequest1(String apiName, Map<String, String> params) {
        if (isConnected()) {
            Call<JsonElement> call = apiService.postDataGET(apiName, params);
            call.enqueue(responseCallback);
        } else {
            httpRequestReciver.onFailure("No Internet Connection.");
        }
    }

    public void getHttpRequest(String apiName) {
        if (isConnected()) {
            Call<JsonElement> call = apiService.getHttpRequest(apiName);
            call.enqueue(responseCallback);
        } else {
            httpRequestReciver.onFailure("No Internet Connection.");
        }
    }

    public interface HttpRequestReciver {
        void onSuccess(JsonElement jsonElement);
        void onFailure(String message);
        void onNetworkFailure(String message);
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}