package com.mera.mvpweatherchecker.interfaces;

import com.mera.mvpweatherchecker.models.WeatherResponse;

import java.io.File;
import java.util.List;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVP Weather Checker
 */
public interface WeatherView {
    void showForecast(List<WeatherResponse.WeatherData> data);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(int stringId);

    File getCacheDirectory();
}
