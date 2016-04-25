package com.mera.mvcweatherchecker;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mera.mvcweatherchecker.components.WeatherAdapter;
import com.mera.mvcweatherchecker.network.WeatherAccessor;
import com.mera.mvcweatherchecker.ui.WeatherListView;
import com.mera.mvcweatherchecker.ui.WeatherRefreshView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.list_refresh) WeatherRefreshView mRefreshView;
    @Bind(R.id.list_content) WeatherListView mWeatherList;

    private WeatherAdapter mWeatherAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private WeatherAccessor mDataAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mRefreshView.addOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mWeatherList.setLayoutManager(mLayoutManager);

        mWeatherAdapter = new WeatherAdapter(this);
        mWeatherList.setAdapter(mWeatherAdapter);

        mDataAccessor = new WeatherAccessor();
        mDataAccessor.fetchWeatherData(mWeatherList, mRefreshView);
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
        mDataAccessor.fetchWeatherData(mWeatherList, mRefreshView);
    }

    @Override
    public void onDestroy() {
        mRefreshView.removeOnRefreshListener(this);
        super.onDestroy();
    }
}
