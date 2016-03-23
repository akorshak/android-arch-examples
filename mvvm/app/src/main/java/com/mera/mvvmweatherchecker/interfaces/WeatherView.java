package com.mera.mvvmweatherchecker.interfaces;

import android.content.Context;

import com.mera.mvvmweatherchecker.models.WeatherResponse;

import java.util.List;

/**
 * Created by akorshak on 3/22/2016.
 * Project: MVVM Weather Checker
 */
public interface WeatherView {
    Context getContext();

    void showForecast(List<WeatherResponse.WeatherData> data);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String error);
}
