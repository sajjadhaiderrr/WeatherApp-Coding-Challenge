package com.example.weatherapp;

/**
 * Created by Sajjad on 2018-02-21.
 */

public class Weather {

    private String mCity;

    private String mTemperature;

    // type of day such as "clear","cloudy",etc
    private String mMain;

    private String mSpeed;

    private String mHumidity;

    private String mHigh;

    private String mLow;

    private String mImgId;

    public Weather() {
    }

    public Weather(String city, String temperature, String main, String speed, String humidity, String high, String low) {
        mCity = city;
        mTemperature = temperature;
        mMain = main;
        mSpeed = speed;
        mHumidity = humidity;
        mHigh = high;
        mLow = low;
    }

    public String getCity() {
        return mCity;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public String getMain() {
        return mMain;
    }

    public String getSpeed() { return mSpeed; }

    public String getHumidity() { return mHumidity; }

    public String getHigh() {
        return mHigh;
    }

    public String getLow() { return mLow; }

    public String getImgId() { return mImgId;}

    public void setCity(String city) {
        mCity = city;
    }

    public void setTemperature(String temperature) {
        mTemperature = temperature;
    }

    public void setMain(String main) {
        mMain = main;
    }

    public void setSpeed(String speed) {
        mSpeed = speed;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    public void setHigh(String high) {
        mHigh = high;
    }

    public void setLow(String low) {
        mLow = low;
    }

    public void setImgId(String imgId) { mImgId = imgId; }

}
