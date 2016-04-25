package com.mera.mvcweatherchecker.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVC Weather Checker
 */
public class WeatherAccessor {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/";

    private WeatherService mWeatherService;

    public WeatherAccessor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mWeatherService = retrofit.create(WeatherService.class);
    }

    public void fetchWeatherData(final Observer<WeatherResponse>... views) {
        Observable<WeatherResponse> observable = mWeatherService.getWeatherList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeatherResponse>() {
                    @Override
                    public void onCompleted() {
                        for (Observer<WeatherResponse> view : views) {
                            view.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        for (Observer<WeatherResponse> view : views) {
                            view.onError(e);
                        }
                    }

                    @Override
                    public void onNext(WeatherResponse weatherResponse) {
                        for (Observer<WeatherResponse> view : views) {
                            view.onNext(weatherResponse);
                        }
                    }
                });
    }

    interface WeatherService {
        @GET("find?lat=55.5&lon=37.5&cnt=10&appid=6b462378109409573b90b51b8a4b79f5")
        Observable<WeatherResponse> getWeatherList();
    }
}
