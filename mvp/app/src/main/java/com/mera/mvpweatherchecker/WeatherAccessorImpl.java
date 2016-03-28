package com.mera.mvpweatherchecker;

import com.mera.mvpweatherchecker.interfaces.WeatherAccessor;
import com.mera.mvpweatherchecker.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVP Weather Checker
 */
class WeatherAccessorImpl implements WeatherAccessor {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/";

    private WeatherService mWeatherService;

    WeatherAccessorImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mWeatherService = retrofit.create(WeatherService.class);
    }

    @Override
    public void fetchWeatherData(final OnFetchListener listener) {
        mWeatherService.fetchWeather().enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body().getWeatherData());
                } else {
                    listener.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                listener.onError(t);
            }
        });
    }

    interface WeatherService {
        @GET("find?lat=55.5&lon=37.5&cnt=10&appid=6b462378109409573b90b51b8a4b79f5")
        Call<WeatherResponse> fetchWeather();
    }
}
