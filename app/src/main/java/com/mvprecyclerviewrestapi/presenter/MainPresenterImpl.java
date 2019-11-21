package com.mvprecyclerviewrestapi.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mvprecyclerviewrestapi.model.allposts.AllPosts;
import com.mvprecyclerviewrestapi.retroutils.RetrofitHttpRequest;
import com.mvprecyclerviewrestapi.retroutils.UrlConfig;
import com.mvprecyclerviewrestapi.view.MainView;

/**
 * Created by Dilip-MavenCluster on 20-11-2019 15:54.
 */
public class MainPresenterImpl implements MainPresenter{

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private Context mContext;
    private MainView mainView;
    private RetrofitHttpRequest retrofitHttpRequest;

    public MainPresenterImpl(MainView mainView,Context mContext) {
        this.mainView = mainView;
        this.mContext = mContext;
        retrofitHttpRequest = new RetrofitHttpRequest(mContext);
    }

    @Override
    public void getEmployeeList(Context context) {
        mainView.showProgress();
        retrofitHttpRequest.setBaseRequestListner(new RetrofitHttpRequest.HttpRequestReciver() {
            @Override
            public void onSuccess(JsonElement jsonElement) {
                Log.e(TAG, "onSuccess: >>" + jsonElement.toString());
                mainView.hideProgress();
                Gson gson = new Gson();
                AllPosts allPosts = gson.fromJson(jsonElement.toString(), AllPosts.class);
                mainView.onGetDataSuccess(allPosts);
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "onFailure: >>" + message);
                mainView.hideProgress();
                mainView.onGetDataFailure(message);
            }

            @Override
            public void onNetworkFailure(String message) {
                Log.e(TAG, "onNetworkFailure: >>" + message);
                mainView.onGetDataFailure(message);
            }
        });
        retrofitHttpRequest.getHttpRequest(UrlConfig.ALL_POST);
    }

}
