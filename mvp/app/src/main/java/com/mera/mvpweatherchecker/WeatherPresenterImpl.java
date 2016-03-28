package com.mera.mvpweatherchecker;

import com.mera.mvpweatherchecker.interfaces.WeatherAccessor;
import com.mera.mvpweatherchecker.interfaces.WeatherPresenter;
import com.mera.mvpweatherchecker.interfaces.WeatherView;
import com.mera.mvpweatherchecker.models.WeatherResponse;

import java.util.List;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVP Weather Checker
 */
class WeatherPresenterImpl implements WeatherPresenter, WeatherAccessor.OnFetchListener {

    private WeatherView mView;
    private WeatherAccessor mInteractor;

    WeatherPresenterImpl(WeatherView view) {
        mView = view;
        mInteractor = new WeatherAccessorImpl(view.getCacheDirectory());
    }

    @Override
    public void updateWeather() {
        mView.showProgressDialog();
        mInteractor.fetchWeatherData(this);
    }

    @Override
    public void onSuccess(List<WeatherResponse.WeatherData> response) {
        mView.hideProgressDialog();
        mView.showForecast(response);
    }

    @Override
    public void onError(int errorCode) {
        //Parse error
        mView.hideProgressDialog();
        switch(errorCode) {
            default:
                mView.showError(R.string.error_message);
                break;
        }
    }

    @Override
    public void onError(Throwable error) {
        onError(-1000);
    }
}
