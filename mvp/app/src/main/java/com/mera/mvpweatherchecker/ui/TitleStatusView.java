package com.mera.mvpweatherchecker.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mera.mvpweatherchecker.R;
import com.mera.mvpweatherchecker.models.WeatherResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TitleStatusView extends LinearLayout {

    @Bind(R.id.toolbar_temp) TextView mCurrWeatherTemp;
    @Bind(R.id.toolbar_hubidity) TextView mCurrHumidity;
    @Bind(R.id.toolbar_pressure) TextView mCurrPressure;

    public TitleStatusView(Context context) {
        super(context);
        init();
    }

    public TitleStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.title_status_layout, this);
        ButterKnife.bind(this);
    }

    public void setData(WeatherResponse.WeatherData data) {
        mCurrWeatherTemp.setText(data.getTemperature());
        mCurrHumidity.setText(data.getHumidity());
        mCurrPressure.setText(data.getPressure());
    }

}
