package com.mera.mvvmweatherchecker;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mera.mvvmweatherchecker.bindings.WeatherListBinding;
import com.mera.mvvmweatherchecker.components.WeatherAdapter;
import com.mera.mvvmweatherchecker.interfaces.WeatherView;
import com.mera.mvvmweatherchecker.interfaces.WeatherViewModel;
import com.mera.mvvmweatherchecker.models.WeatherResponse;
import com.mera.mvvmweatherchecker.viewmodel.WeatherListViewModel;

import java.util.List;

public class WeatherListActivity extends AppCompatActivity implements WeatherView {

    private WeatherAdapter mWeatherAdapter;
    private LinearLayoutManager mLayoutManager;

    private WeatherViewModel mViewModel;
    private WeatherListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather_list);
        mViewModel = new WeatherListViewModel(this);
        mBinding.setWeatherListViewModel(mViewModel);
        mBinding.listRefresh.setOnRefreshListener((WeatherListViewModel) mViewModel);

        setSupportActionBar(mBinding.toolbar);

        mLayoutManager = new LinearLayoutManager(this);
        mBinding.listContent.setLayoutManager(mLayoutManager);

        mWeatherAdapter = new WeatherAdapter(this);
        mWeatherAdapter.setHasStableIds(true);
        mBinding.listContent.setAdapter(mWeatherAdapter);

        mViewModel.updataWeatherData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        mBinding.listRefresh.post(new Runnable() {
            @Override
            public void run() {
                mBinding.listRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgressDialog() {
        mBinding.listRefresh.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
