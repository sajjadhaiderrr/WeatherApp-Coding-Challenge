package com.example.weatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView cityTextView;
    private TextView dateTextView;
    private TextView temperatureTextView;
    private TextView mainTextView;
    private TextView windSpeedTextView;
    private TextView humidityTextView;
    private TextView highLoTextView;
    private ImageView weatherImageView;

    private String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityTextView = (TextView) findViewById(R.id.cityTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        temperatureTextView =  (TextView) findViewById(R.id.temperatureTextView);
        mainTextView = (TextView) findViewById(R.id.mainTextView);
        windSpeedTextView = (TextView) findViewById(R.id.windSpeedTextView);
        humidityTextView = (TextView) findViewById(R.id.humidityTextView);
        highLoTextView = (TextView) findViewById(R.id.highLoTextView);
        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        city = spinner.getSelectedItem().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
                new JSONWeatherTask().execute(city);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        new JSONWeatherTask().execute(city);


    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

            @Override
            protected Weather doInBackground(String... params) {

                JSONObject data = null;
                Weather weather = new Weather();

                try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&APPID=beb65f86b94b467d1d65ed3fdc51da69");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());
                    JSONArray jArr = data.getJSONArray("weather");
                    JSONObject JSONWeather = jArr.getJSONObject(0);
                    String main = JSONWeather.getString("main");
                    String mainId = JSONWeather.getString("icon");


                    JSONObject mainObj = data.getJSONObject("main");
                    String temperature = mainObj.getString("temp");
                    String humidity = mainObj.getString("humidity");
                    String high = mainObj.getString("temp_max");
                    String low = mainObj.getString("temp_min");

                    JSONObject windObj = data.getJSONObject("wind");
                    String speed = windObj.getString("speed");

                    String city = data.getString("name");

                    weather.setCity(city);
                    weather.setTemperature(temperature);
                    weather.setMain(main);
                    weather.setSpeed(speed);
                    weather.setHumidity(humidity);
                    weather.setHigh(high);
                    weather.setLow(low);
                    weather.setImgId(mainId);


                } catch (Exception e) {
                }
                return weather;
            }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            cityTextView.setText(weather.getCity());

            String imgURL = "http://openweathermap.org/img/w/";

            new ImageTask().execute(imgURL+weather.getImgId()+".png");


            Date currDate = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDate = new SimpleDateFormat("EEEE hh:mm a");
            String currDateString = simpleDate.format(currDate);
            dateTextView.setText(currDateString);

            temperatureTextView.setText(weather.getTemperature()+ " \u2103");
            mainTextView.setText(weather.getMain());
            windSpeedTextView.setText(weather.getSpeed() + " m/s");
            humidityTextView.setText(weather.getHumidity() + "%");
            highLoTextView.setText(weather.getHigh() + "°C\n" + weather.getLow() + "°C");

        }

    }

    private class ImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            weatherImageView.setImageBitmap(result);
        }
    }
}
