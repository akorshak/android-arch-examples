package com.mera.mvpweatherchecker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mera.mvpweatherchecker.components.WeatherAdapter;
import com.mera.mvpweatherchecker.interfaces.WeatherPresenter;
import com.mera.mvpweatherchecker.interfaces.WeatherView;
import com.mera.mvpweatherchecker.models.WeatherResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherListActivity extends AppCompatActivity implements WeatherView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.list_refresh) SwipeRefreshLayout mRefreshView;
    @Bind(R.id.list_content) RecyclerView mWeatherList;

    private RecyclerView.LayoutManager mLayoutManager;

    private WeatherAdapter mWeatherAdapter;
    private WeatherPresenter mWeatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mRefreshView.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mWeatherList.setLayoutManager(mLayoutManager);

        mWeatherAdapter = new WeatherAdapter(this);
        mWeatherList.setAdapter(mWeatherAdapter);

        mWeatherPresenter = new WeatherPresenterImpl(this);
        mWeatherPresenter.updateWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showForecast(List<WeatherResponse.WeatherData> data) {
        mWeatherAdapter.updateDataset(data);
    }

    @Override
    public void showProgressDialog() {
        mRefreshView.post(new Runnable() {
            @Override
            public void run() {
                mRefreshView.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgressDialog() {
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        mWeatherPresenter.updateWeather();
    }
}
