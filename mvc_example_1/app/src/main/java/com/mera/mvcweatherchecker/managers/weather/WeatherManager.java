package com.mera.mvcweatherchecker.managers.weather;

import com.mera.mvcweatherchecker.models.WeatherResponse;

/**
 * Created by akorshak on 3/22/2016.
 * Project: MVC Weather Checker
 */
public class WeatherManager {

    private WeatherInteractor mInteractor;

    public WeatherManager() {
        mInteractor = new WeatherInteractor();
    }

    public void requestWeather(rx.Observer<WeatherResponse> view) {
        mInteractor.fetchWeatherData(view);
    }
}
