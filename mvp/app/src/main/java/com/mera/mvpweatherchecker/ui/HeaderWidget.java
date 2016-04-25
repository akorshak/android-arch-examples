package com.mera.mvpweatherchecker.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mera.mvpweatherchecker.R;
import com.mera.mvpweatherchecker.models.WeatherResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HeaderWidget extends LinearLayout {

    @Bind(R.id.curr_weather_temp) TextView mCurrWeatherTemp;
    @Bind(R.id.curr_weather_description) TextView mCurrWeatherDescr;
    @Bind(R.id.humidity_value) TextView mCurrHumidity;
    @Bind(R.id.pressure_value) TextView mCurrPressure;

    public HeaderWidget(Context context) {
        super(context);
        init();
    }

    public HeaderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.header_weather_layout, this);
        ButterKnife.bind(this);
    }

    public void setData(WeatherResponse.WeatherData data) {
        mCurrWeatherTemp.setText(data.getTemperature());
        mCurrWeatherDescr.setText(data.getDescription());
        mCurrHumidity.setText(data.getHumidity());
        mCurrPressure.setText(data.getPressure());
    }

}
