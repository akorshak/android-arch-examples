package com.mera.mvvmweatherchecker.components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mera.mvvmweatherchecker.R;
import com.mera.mvvmweatherchecker.bindings.WeatherItemBinding;
import com.mera.mvvmweatherchecker.models.WeatherResponse;
import com.mera.mvvmweatherchecker.viewmodel.WeatherItemViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVVM Weather Checker
 */
public class WeatherAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<WeatherResponse.WeatherData> mWeatherData = Collections.emptyList();

    public WeatherAdapter(Context context) {
        mContext = context;
    }

    public void updateDataset(List<WeatherResponse.WeatherData> data) {
        mWeatherData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WeatherItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.content_weather_item,
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder typedHolder = (ViewHolder) holder;
        typedHolder.bind(mWeatherData.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mWeatherData.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private WeatherItemBinding mBinding;

        public ViewHolder(WeatherItemBinding binding) {
            super(binding.itemLayout);
            mBinding = binding;
        }

        void bind(WeatherResponse.WeatherData weatherData) {
            if (mBinding.getWeatherItemViewModel() == null) {
                mBinding.setWeatherItemViewModel(new WeatherItemViewModel(itemView.getContext(), weatherData));
            } else {
                mBinding.getWeatherItemViewModel().setWeatherData(weatherData);
            }
        }
    }
}
