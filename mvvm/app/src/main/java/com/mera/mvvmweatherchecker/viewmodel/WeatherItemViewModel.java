package com.mera.mvvmweatherchecker.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mera.mvvmweatherchecker.models.WeatherResponse;

/**
 * Created by akorshak on 3/22/2016.
 * Project: MVVM Weather Checker
 */
public class WeatherItemViewModel extends BaseObservable {

    private Context mContext;
    private WeatherResponse.WeatherData mWeatherData;

    public WeatherItemViewModel(Context context, WeatherResponse.WeatherData data) {
        mContext = context;
        mWeatherData = data;
    }

    public String getCity() {
        return mWeatherData.getCity();
    }

    public String getTemperature() {
        return mWeatherData.getTemperatureInCelsius();
    }

    public String getDescription() {
        return mWeatherData.getDescription();
    }

    public String getImageUrl() {
        return mWeatherData.getIconAddress();
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

    public void setWeatherData(WeatherResponse.WeatherData data) {
        mWeatherData = data;
        notifyChange();
    }
}
