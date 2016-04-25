package com.mera.mvcweatherchecker.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by korshak on 4/25/2016.
 * Project: mvc_example_2
 */
public class WeatherRefreshView extends SwipeRefreshLayout implements rx.Observer, SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<OnRefreshListener> mListeners = new ArrayList<>();

    public WeatherRefreshView(Context context) {
        super(context);
        init();
    }

    public WeatherRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnRefreshListener(this);
    }

    public void addOnRefreshListener(OnRefreshListener listener) {
        mListeners.add(listener);
    }

    public void removeOnRefreshListener(OnRefreshListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void onCompleted() {
        setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {
        if (isRefreshing()) {
            setRefreshing(false);
        }
    }

    @Override
    public void onNext(Object o) {}

    @Override
    public void onRefresh() {
        setRefreshing(true);

        for(OnRefreshListener listener : mListeners) {
            listener.onRefresh();
        }
    }
}
