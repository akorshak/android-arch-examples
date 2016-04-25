package com.mera.mvpweatherchecker.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akorshak on 3/18/2016.
 * Project: MVP Weather Checker
 */

@Parcel
public class WeatherResponse {

    @SerializedName("list")
    List<WeatherData> mWeatherData = new ArrayList<>();

    public List<WeatherData> getWeatherData() {
        return mWeatherData;
    }

    @Parcel
    public static class WeatherData {

        private static final SimpleDateFormat DAY_MONTH_FORMAT = new SimpleDateFormat("dd/MM");

        @SerializedName("dt")
        long mDate;

        @Parcel
        public static class Temp {

            @SerializedName("day")
            float mDayTemp;

            @SerializedName("min")
            float mMinTemp;

            @SerializedName("max")
            float mMaxTemp;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Temp)) return false;

                Temp temp = (Temp) o;

                if (Float.compare(temp.mDayTemp, mDayTemp) != 0) return false;
                if (Float.compare(temp.mMinTemp, mMinTemp) != 0) return false;
                return Float.compare(temp.mMaxTemp, mMaxTemp) == 0;

            }

            @Override
            public int hashCode() {
                int result = (mDayTemp != +0.0f ? Float.floatToIntBits(mDayTemp) : 0);
                result = 31 * result + (mMinTemp != +0.0f ? Float.floatToIntBits(mMinTemp) : 0);
                result = 31 * result + (mMaxTemp != +0.0f ? Float.floatToIntBits(mMaxTemp) : 0);
                return result;
            }
        }

        @SerializedName("temp")
        Temp mTemp = new Temp();

        @SerializedName("pressure")
        float mPressure;

        @SerializedName("humidity")
        float mHumidity;

        @Parcel
        public static class Weather {
            @SerializedName("description")
            String mDescription;

            @SerializedName("icon")
            String mIcon;

            @SerializedName("id")
            int mIconId;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Weather)) return false;

                Weather weather = (Weather) o;

                if (mIconId != weather.mIconId) return false;
                if (mDescription != null ? !mDescription.equals(weather.mDescription) : weather.mDescription != null)
                    return false;
                return mIcon != null ? mIcon.equals(weather.mIcon) : weather.mIcon == null;

            }

            @Override
            public int hashCode() {
                int result = mDescription != null ? mDescription.hashCode() : 0;
                result = 31 * result + (mIcon != null ? mIcon.hashCode() : 0);
                result = 31 * result + mIconId;
                return result;
            }
        }

        @SerializedName("weather")
        List<Weather> mWeather = new ArrayList<>();

        private String getRoundedTemp(float temp) {
            int t = (int) temp;
            String tStr =  t >= 0 ? "+" + t : "" + t;
            return tStr + "\u00b0C";
        }

        public String getHumidity() {
            return String.format("%.1f %%", mHumidity);
        }

        public String getPressure() {
            float pressure = (mPressure / 10) * 7.50062f;
            return (int) pressure + " mmHg";
        }

        public String getTemperature() {
            return getRoundedTemp(mTemp.mDayTemp);
        }

        public String getMinTemperature() {
            return getRoundedTemp(mTemp.mMinTemp);
        }

        public String getMaxTemperature() {
            return getRoundedTemp(mTemp.mMaxTemp);
        }

        public String getIconResourceName() {
            String iconName = getIconName();
            StringBuilder resName = new StringBuilder("wi_owm_");
            if(iconName.contains("d")) {
                resName.append("day_");
            } else if(iconName.contains("n")) {
                resName.append("night_");
            }
            resName.append(getIconId());
            return resName.toString();
        }

        public String getReadableDate() {
            Date date = new Date(mDate * 1000);
            return DAY_MONTH_FORMAT.format(date);
        }

        public String getDescription() {
            if (mWeather.size() > 0) {
                return mWeather.get(0).mDescription;
            }
            return "";
        }

        public int getIconId() {
            if (mWeather.size() > 0) {
                return mWeather.get(0).mIconId;
            }
            return 0;
        }

        public String getIconName() {
            if (mWeather.size() > 0) {
                return mWeather.get(0).mIcon;
            }
            return "";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WeatherData that = (WeatherData) o;

            if (mDate != that.mDate) return false;
            if (Float.compare(that.mPressure, mPressure) != 0) return false;
            if (Float.compare(that.mHumidity, mHumidity) != 0) return false;
            if (mTemp != null ? !mTemp.equals(that.mTemp) : that.mTemp != null) return false;
            return mWeather != null ? mWeather.equals(that.mWeather) : that.mWeather == null;

        }

        @Override
        public int hashCode() {
            int result = (int) (mDate ^ (mDate >>> 32));
            result = 31 * result + (mTemp != null ? mTemp.hashCode() : 0);
            result = 31 * result + (mPressure != +0.0f ? Float.floatToIntBits(mPressure) : 0);
            result = 31 * result + (mHumidity != +0.0f ? Float.floatToIntBits(mHumidity) : 0);
            result = 31 * result + (mWeather != null ? mWeather.hashCode() : 0);
            return result;
        }
    }
}