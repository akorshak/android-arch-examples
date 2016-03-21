package com.mera.mvpweatherchecker.interfaces;

import com.mera.mvpweatherchecker.models.WeatherResponse;

import java.util.List;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVP Weather Checker
 */
public interface WeatherInteractor {
    void fetchWeatherData(OnFetchListener listener);

    interface OnFetchListener {
        void onSuccess(List<WeatherResponse.WeatherData> response);

        void onError(int errorCode);

        void onError(Throwable error);
    }
}
