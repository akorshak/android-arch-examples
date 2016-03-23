package com.mera.mvvmweatherchecker.viewmodel;

import android.support.v4.widget.SwipeRefreshLayout;

import com.mera.mvvmweatherchecker.R;
import com.mera.mvvmweatherchecker.interfaces.WeatherInteractor;
import com.mera.mvvmweatherchecker.interfaces.WeatherView;
import com.mera.mvvmweatherchecker.interfaces.WeatherViewModel;
import com.mera.mvvmweatherchecker.models.WeatherResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by akorshak on 3/22/2016.
 * Project: MVVM Weather Checker
 */
public class WeatherListViewModel implements WeatherViewModel, Observer<WeatherResponse>, SwipeRefreshLayout.OnRefreshListener {

    private WeatherView mView;
    private WeatherInteractor mInteractor;

    public WeatherListViewModel(WeatherView view) {
        mView = view;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherInteractor.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mInteractor = retrofit.create(WeatherInteractor.class);
    }

    @Override
    public void updataWeatherData() {
        mView.showProgressDialog();

        Observable<WeatherResponse> observable = mInteractor.getWeatherList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        mView.hideProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        mView.hideProgressDialog();
        mView.showError(mView.getContext().getString(R.string.error_message));
    }

    @Override
    public void onNext(WeatherResponse weatherResponse) {
        mView.showForecast(weatherResponse.getWeatherData());
    }

    @Override
    public void onRefresh() {
        updataWeatherData();
    }
}
