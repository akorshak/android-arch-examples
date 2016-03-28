package com.mera.mvpweatherchecker;

import com.mera.mvpweatherchecker.interfaces.WeatherAccessor;
import com.mera.mvpweatherchecker.models.WeatherResponse;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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
    private static final long CACHE_SIZE = 10 * 1024 * 1024;

    private WeatherService mWeatherService;

    WeatherAccessorImpl(File cachePath) {
        Cache cache = new Cache(new File(cachePath, "http"), CACHE_SIZE);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CachingControlInterceptor())
                .cache(cache);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpBuilder.build())
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

    public static class CachingControlInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", String.format("max-age=%d", 3600))
                    .build();
        }
    }

    interface WeatherService {
        @GET("find?lat=55.5&lon=37.5&cnt=10&appid=6b462378109409573b90b51b8a4b79f5")
        Call<WeatherResponse> fetchWeather();
    }
}
