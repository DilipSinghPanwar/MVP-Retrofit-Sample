package com.mvprecyclerviewrestapi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mvprecyclerviewrestapi.R;
import com.mvprecyclerviewrestapi.adapters.RecyclerviewAdapter;
import com.mvprecyclerviewrestapi.databinding.ActivityMainBinding;
import com.mvprecyclerviewrestapi.model.allposts.AllPosts;
import com.mvprecyclerviewrestapi.presenter.MainPresenterImpl;
import com.mvprecyclerviewrestapi.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private ActivityMainBinding mainBinding;
    private MainPresenterImpl mainPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initProgressBar();
        mainPresenter = new MainPresenterImpl(this, this);
        mainPresenter.getEmployeeList(this);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGetDataSuccess(AllPosts allPosts) {
        mainBinding.recyclerView.setVisibility(View.VISIBLE);
        mainBinding.tvError.setVisibility(View.GONE);
        RecyclerviewAdapter recyclerviewAdapter = new RecyclerviewAdapter(MainActivity.this, allPosts);
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerView.setAdapter(recyclerviewAdapter);
        recyclerviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetDataFailure(String message) {
        mainBinding.recyclerView.setVisibility(View.GONE);
        mainBinding.tvError.setVisibility(View.VISIBLE);
    }

    /**
     * Initializing progressbar programmatically
     * */
    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }

}