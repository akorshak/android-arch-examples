package com.mera.mvcweatherchecker;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mera.mvcweatherchecker.components.WeatherAdapter;
import com.mera.mvcweatherchecker.managers.weather.WeatherManager;
import com.mera.mvcweatherchecker.models.WeatherResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherListActivity extends AppCompatActivity implements rx.Observer<WeatherResponse>, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.list_refresh) SwipeRefreshLayout mRefreshView;
    @Bind(R.id.list_content) RecyclerView mWeatherList;

    private RecyclerView.LayoutManager mLayoutManager;

    private WeatherAdapter mWeatherAdapter;
    private WeatherManager mController;

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

        mController = new WeatherManager();
        mController.requestWeather(this);
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
    public void onRefresh() {
        mRefreshView.setRefreshing(true);
        mController.requestWeather(this);
    }

    @Override
    public void onCompleted() {
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {
        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
        }
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(WeatherResponse response) {
        mWeatherAdapter.updateDataset(response.getWeatherData());
    }
}
