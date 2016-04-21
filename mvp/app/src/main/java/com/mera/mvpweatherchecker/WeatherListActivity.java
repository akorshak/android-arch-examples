package com.mera.mvpweatherchecker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mera.mvpweatherchecker.components.WeatherAdapter;
import com.mera.mvpweatherchecker.interfaces.WeatherPresenter;
import com.mera.mvpweatherchecker.interfaces.WeatherView;
import com.mera.mvpweatherchecker.models.WeatherResponse;
import com.mera.mvpweatherchecker.ui.AnimationConstants;
import com.mera.mvpweatherchecker.ui.HeaderWeatherView;
import com.mera.mvpweatherchecker.ui.TitleStatusView;

import org.parceler.Parcels;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherListActivity extends AppCompatActivity implements WeatherView, SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener {

    private static final String CURR_WEATHER_EXTRA = "curr_weather_extra";

    @Bind(R.id.title) TitleStatusView mTitle;
    @Bind(R.id.curr_weather_icon) WeatherIconView mCurrWeatherIcon;
    @Bind(R.id.header_weather) HeaderWeatherView mHeaderWeatherView;
    @Bind(R.id.app_bar) AppBarLayout mAppBar;
    @Bind(R.id.list_refresh) SwipeRefreshLayout mRefreshView;
    @Bind(R.id.list_content) RecyclerView mWeatherList;

    private List<WeatherResponse.WeatherData> mData;

    private boolean mIsTheTitleVisible = false;

    private RecyclerView.LayoutManager mLayoutManager;

    private WeatherAdapter mWeatherAdapter;
    private WeatherPresenter mWeatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        ButterKnife.bind(this);

        mWeatherAdapter = new WeatherAdapter(this);
        mWeatherList.setAdapter(mWeatherAdapter);

        if(savedInstanceState != null) {
            mData = Parcels.unwrap(savedInstanceState.getParcelable(CURR_WEATHER_EXTRA));
            showForecast(mData);
        }

        mRefreshView.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mWeatherList.setLayoutManager(mLayoutManager);

        mWeatherAdapter = new WeatherAdapter(this);
        mWeatherList.setAdapter(mWeatherAdapter);

        mWeatherPresenter = new WeatherPresenterImpl(this);
        mWeatherPresenter.updateWeather();

        mAppBar.addOnOffsetChangedListener(this);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
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
    public void showForecast(List<WeatherResponse.WeatherData> data) {
        mData = data;

        int resId = this.getResources().getIdentifier(data.get(0).getIconResourceName(), "string", this.getPackageName());
        mCurrWeatherIcon.setIconResource(this.getString(resId));

        mHeaderWeatherView.setData(data.get(0));
        mTitle.setData(data.get(0));

        //without today's weather
        mWeatherAdapter.updateDataset(data.subList(1, data.size()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURR_WEATHER_EXTRA, Parcels.wrap(mData));
    }

    @Override
    public void showProgressDialog() {
        mRefreshView.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void showError(int stringId) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_LONG).show();
    }

    @Override
    public File getCacheDirectory() {
        return getCacheDir();
    }

    @Override
    public void onRefresh() {
        mWeatherPresenter.updateWeather();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= AnimationConstants.PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, AnimationConstants.ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, AnimationConstants.ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
