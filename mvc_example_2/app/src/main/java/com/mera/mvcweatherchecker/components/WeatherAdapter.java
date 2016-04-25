package com.mera.mvcweatherchecker.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mera.mvcweatherchecker.R;
import com.mera.mvcweatherchecker.network.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVC Weather Checker
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
        typedHolder.mCity.setText(weatherItem.getCity());
        typedHolder.mDescription.setText(weatherItem.getDescription());
        typedHolder.mTemperature.setText(weatherItem.getTemperatureInCelsius());

        Glide.with(mContext)
                .load(weatherItem.getIconAddress())
                .fitCenter()
                .crossFade()
                .into(typedHolder.mImage);
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

        @Bind(R.id.iv_image) ImageView mImage;
        @Bind(R.id.tv_city) TextView mCity;
        @Bind(R.id.tv_desc) TextView mDescription;
        @Bind(R.id.tv_temp) TextView mTemperature;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
