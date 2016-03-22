package com.mera.mvcweatherchecker.managers.weather;

import com.mera.mvcweatherchecker.models.WeatherResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVC Weather Checker
 */
public class WeatherInteractor {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/";

    private WeatherService mWeatherService;

    WeatherInteractor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mWeatherService = retrofit.create(WeatherService.class);
    }

    public void fetchWeatherData(Observer<WeatherResponse> view) {
        Observable<WeatherResponse> observable = mWeatherService.getWeatherList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(view);
    }

    interface WeatherService {
        @GET("find?lat=55.5&lon=37.5&cnt=10&appid=6b462378109409573b90b51b8a4b79f5")
        Observable<WeatherResponse> getWeatherList();
    }
}
