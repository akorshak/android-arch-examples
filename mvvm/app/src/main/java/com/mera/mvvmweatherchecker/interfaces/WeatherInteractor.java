package com.mera.mvvmweatherchecker.interfaces;

import com.mera.mvvmweatherchecker.models.WeatherResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by akorshak on 3/22/2016.
 * Project: MVVM Weather Checker
 */
public interface WeatherInteractor {

    String API_URL = "http://api.openweathermap.org/data/2.5/";

    @GET("find?lat=55.5&lon=37.5&cnt=10&appid=6b462378109409573b90b51b8a4b79f5")
    Observable<WeatherResponse> getWeatherList();
}
