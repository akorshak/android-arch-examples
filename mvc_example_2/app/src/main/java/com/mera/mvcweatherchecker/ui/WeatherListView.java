package com.mera.mvcweatherchecker.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Toast;

import com.mera.mvcweatherchecker.R;
import com.mera.mvcweatherchecker.components.WeatherAdapter;
import com.mera.mvcweatherchecker.network.WeatherResponse;

/**
 * Created by korshak on 4/25/2016.
 * Project: mvc_example_2
 */
public class WeatherListView extends RecyclerView implements rx.Observer<WeatherResponse> {

    public WeatherListView(Context context) {
        super(context);
    }

    public WeatherListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        Toast.makeText(getContext(), getContext().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(WeatherResponse weatherResponse) {
        WeatherAdapter adapter = (WeatherAdapter) getAdapter();
        adapter.updateDataset(weatherResponse.getWeatherData());
    }
}
