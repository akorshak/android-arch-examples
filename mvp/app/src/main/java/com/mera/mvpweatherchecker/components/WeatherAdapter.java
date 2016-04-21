package com.mera.mvpweatherchecker.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mera.mvpweatherchecker.models.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

import com.mera.mvpweatherchecker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVP Weather Checker
 */
public class WeatherAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<WeatherResponse.WeatherData> mWeatherData = new ArrayList<>();

    public WeatherAdapter(Context context) {
        mContext = context;
    }

    public void updateDataset(List<WeatherResponse.WeatherData> data) {
        mWeatherData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_weather_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder typedHolder = (ViewHolder) holder;

        WeatherResponse.WeatherData weatherItem = mWeatherData.get(position);
        typedHolder.mDate.setText(weatherItem.getReadableDate());
        typedHolder.mDescription.setText(weatherItem.getDescription());
        typedHolder.mTemperature.setText(weatherItem.getTemperature());

        int resId = mContext.getResources().getIdentifier(weatherItem.getIconResourceName(), "string", mContext.getPackageName());
        typedHolder.mImage.setIconResource(mContext.getString(resId));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mWeatherData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_image) WeatherIconView mImage;
        @Bind(R.id.tv_date) TextView mDate;
        @Bind(R.id.tv_desc) TextView mDescription;
        @Bind(R.id.tv_temp) TextView mTemperature;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
