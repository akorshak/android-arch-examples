package com.mera.mvcweatherchecker.network;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherResponse that = (WeatherResponse) o;

        return !(mWeatherData != null ? !mWeatherData.equals(that.mWeatherData) : that.mWeatherData != null);

    }

    @Override
    public int hashCode() {
        return mWeatherData != null ? mWeatherData.hashCode() : 0;
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

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Weather weather = (Weather) o;

                if (mDescription != null ? !mDescription.equals(weather.mDescription) : weather.mDescription != null)
                    return false;
                return !(mIcon != null ? !mIcon.equals(weather.mIcon) : weather.mIcon != null);

            }

            @Override
            public int hashCode() {
                int result = mDescription != null ? mDescription.hashCode() : 0;
                result = 31 * result + (mIcon != null ? mIcon.hashCode() : 0);
                return result;
            }
        }

        @Parcel
        private static class Main {
            @SerializedName("temp")
            float mTemp;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Main main = (Main) o;

                return Float.compare(main.mTemp, mTemp) == 0;

            }

            @Override
            public int hashCode() {
                return (mTemp != +0.0f ? Float.floatToIntBits(mTemp) : 0);
            }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WeatherData that = (WeatherData) o;

            if (mMain != null ? !mMain.equals(that.mMain) : that.mMain != null) return false;
            if (mWeather != null ? !mWeather.equals(that.mWeather) : that.mWeather != null)
                return false;
            return !(mCity != null ? !mCity.equals(that.mCity) : that.mCity != null);

        }

        @Override
        public int hashCode() {
            int result = mMain != null ? mMain.hashCode() : 0;
            result = 31 * result + (mWeather != null ? mWeather.hashCode() : 0);
            result = 31 * result + (mCity != null ? mCity.hashCode() : 0);
            return result;
        }
    }
}
