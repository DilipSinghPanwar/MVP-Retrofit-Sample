package com.mvprecyclerviewrestapi.view;
import com.mvprecyclerviewrestapi.model.allposts.AllPosts;

/**
 * Created by Dilip-MavenCluster on 20-11-2019 15:52.
 */
public interface MainView {
    void showProgress();
    void hideProgress();
    void onGetDataSuccess(AllPosts allPosts);
    void onGetDataFailure(String message);
}
