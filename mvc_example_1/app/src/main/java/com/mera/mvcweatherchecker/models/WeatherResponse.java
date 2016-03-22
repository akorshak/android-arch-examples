package com.mera.mvcweatherchecker.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVC Weather Checker
 */

@Parcel
public class WeatherResponse {

    @SerializedName("list")
    private List<WeatherData> mWeatherData = new ArrayList<>();

    public List<WeatherData> getWeatherData() {
        return mWeatherData;
    }

    @Parcel
    public static class WeatherData {

        private final static String ICON_ADDR = "http://openweathermap.org/img/w/";

        @Parcel
        private static class Weather {
            @SerializedName("description")
            String mDescription;

            @SerializedName("icon")
            String mIcon;
        }

        @Parcel
        private static class Main {
            @SerializedName("temp")
            float mTemp;
        }

        @SerializedName("main")
        private Main mMain = new Main();

        @SerializedName("weather")
        private List<Weather> mWeather = new ArrayList<>();

        @SerializedName("name")
        private String mCity = "";

        private WeatherData() {
        }

        public String getCity() {
            return mCity;
        }

        public String getTemperatureInCelsius() {
            float temp = mMain.mTemp - 273.15f;
            return String.format("%.2f \u00b0C", temp);
        }

        public String getIconAddress() {
            return ICON_ADDR + mWeather.get(0).mIcon + ".png";
        }

        public String getDescription() {
            if (mWeather.size() > 0) {
                return mWeather.get(0).mDescription;
            }
            return "";
        }
    }
}
