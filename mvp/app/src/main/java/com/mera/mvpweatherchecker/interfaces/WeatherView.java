package com.mera.mvpweatherchecker.interfaces;

import android.content.Context;

import com.mera.mvpweatherchecker.models.WeatherResponse;

import java.util.List;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVP Weather Checker
 */
public interface WeatherView {
    Context getContext();

    void showForecast(List<WeatherResponse.WeatherData> data);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String error);
}
