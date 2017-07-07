package com.example.meng.android_firstapp.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.meng.android_firstapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import Modle.weather.Forecast;
import Modle.weather.HourForecast;
import Modle.weather.Weather;
import Network.weather.HttpUtil;
import Utils.Common;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private String currentWeatherId;
    private Weather currentWeather;


    public SwipeRefreshLayout swipeRefresh;
    private ScrollView scrWeatherLayout;
    private TextView tempText;
    private ImageView imgWeather;
    private TextView weatherInfoText;
    private TextView airQlty;
    private TextView pm25Text;
    private TextView airBrf;
    private TextView comfBrf;
    private TextView fluBrf;
    private TextView drsgBrf;
    private TextView aqiText;
    private TextView airTxt;
    private TextView comfortTxt;
    private TextView influenzaTxt;
    private TextView dressTxt;
    private LinearLayout hourforecastLayout;
    private LinearLayout forecastLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        currentWeatherId=getIntent().getStringExtra("weatherId");
        requestWeather(currentWeatherId);
        initView();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (currentWeatherId!=null){
                    requestWeather(currentWeatherId);
                }else{
                    Log.d("LifeCycle","swipeWeatherId is null");
                }
            }
        });
    }

    private void initView()
    {
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        scrWeatherLayout = (ScrollView)findViewById(R.id.weather_scrollView);
        tempText = (TextView) findViewById(R.id.temp);
        imgWeather= (ImageView)findViewById(R.id.img_cond);
        weatherInfoText = (TextView)findViewById(R.id.weather_info);
        hourforecastLayout= (LinearLayout)findViewById(R.id.hour_layout);
        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);
        aqiText = (TextView)findViewById(R.id.aqi_text);
        airQlty= (TextView)findViewById(R.id.airQlty);
        pm25Text = (TextView)findViewById(R.id.pm2_5);
        airBrf = (TextView)findViewById(R.id.sug_air);
        comfBrf= (TextView)findViewById(R.id.sug_comf);
        fluBrf= (TextView)findViewById(R.id.sug_flu);
        drsgBrf= (TextView)findViewById(R.id.sug_drsg);
        airTxt = (TextView)findViewById(R.id.air_txt);
        comfortTxt = (TextView)findViewById(R.id.comf_txt);
        influenzaTxt = (TextView)findViewById(R.id.flu_txt);
        dressTxt = (TextView)findViewById(R.id.drsg_txt);

    }


    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String temp = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        int imgCode = weather.now.more.code;
        //设置通知栏所需要的天气信息存储

        SharedPreferences.Editor editor = getSharedPreferences("notification", Context.MODE_PRIVATE).edit();
        editor.putString("cityName", cityName);
        editor.putString("temperature",temp);
        editor.putString("weatherInfo",weatherInfo);
        editor.apply();
        tempText.setText(temp);

        weatherInfoText.setText(weatherInfo);
        //设置天气信息的相应Icon

        if (imgCode == 100) {
            imgWeather.setImageResource(R.drawable.sun);
        }
        if (imgCode >= 101 && imgCode <= 103){
            imgWeather.setImageResource(R.drawable.cloudy);
        }
        if (imgCode==104){
            imgWeather.setImageResource(R.drawable.overcast);
        }
        if (imgCode >= 200 && imgCode <= 213){
            imgWeather.setImageResource(R.drawable.windy);
        }
        if (imgCode >= 300 && imgCode <= 313 ){
            imgWeather.setImageResource(R.drawable.rain);
        }
        if (imgCode >= 400 && imgCode <= 407){
            imgWeather.setImageResource(R.drawable.snow);
        }
        if (imgCode >= 500 && imgCode <= 501){
            imgWeather.setImageResource(R.drawable.fog);
        }
        if (imgCode == 502){
            imgWeather.setImageResource(R.drawable.haze);
        }
        if (imgCode >= 503 && imgCode <= 508){
            imgWeather.setImageResource(R.drawable.sandstorm);
        }

        hourforecastLayout.removeAllViews();
        //动态加载未来小时的天气信息

        for (HourForecast hourForecast:weather.hourForecastList){
            View viewHour = LayoutInflater.from(this).inflate(R.layout.item_hour,hourforecastLayout,false);
            TextView hourText = (TextView) viewHour.findViewById(R.id.hour_clock);
            TextView tmpText = (TextView) viewHour.findViewById(R.id.hour_temp);
            TextView humText = (TextView) viewHour.findViewById(R.id.hour_hum);
            TextView windText = (TextView) viewHour.findViewById(R.id.hour_wind);
            //去年月日保留时间
            String Hour = hourForecast.date;
            String hourWeather = Common.getHour(Hour);
            hourText.setText(hourWeather);
            tmpText.setText(hourForecast.tmp+"℃");
            humText.setText(hourForecast.hum+"%");
            windText.setText(hourForecast.wind.spd+"Km/h");
            hourforecastLayout.addView(viewHour);
        }
        forecastLayout.removeAllViews();
        //动态加载未来几天的天气信息

        for (Forecast forecast : weather.forecastsList){
            View view = LayoutInflater.from(this).inflate(R.layout.item_forecast,forecastLayout,false);
            TextView dateText= (TextView) view.findViewById(R.id.date_Text);
            TextView infoText = (TextView) view.findViewById(R.id.info_Text);
            ImageView forecastImg = (ImageView) view.findViewById(R.id.foreDayWeather);
            TextView minText= (TextView) view.findViewById(R.id.min_Text);
            TextView maxText= (TextView) view.findViewById(R.id.max_Text);
            String WeatherDate=forecast.date;
            //返回JSON数据中的日期转换成星期

            String weekDate = Common.getDate(WeatherDate);
            dateText.setText(weekDate);
            int foreCode  = forecast.more.foreCode;
            //设置天气信息的相应Icon

            if (foreCode >= 100 && foreCode <= 103  ){
                forecastImg.setImageResource(R.drawable.foredaysun);
            }
            if ((foreCode >= 104 && foreCode <= 213)||(foreCode >= 500 && foreCode <= 502)){
                forecastImg.setImageResource(R.drawable.foredaycloud);
            }
            if (foreCode >= 300 && foreCode <= 313 ){
                forecastImg.setImageResource(R.drawable.foredayrain);
            }
            if (foreCode >= 400 && foreCode <=407 ){
                forecastImg.setImageResource(R.drawable.foredaysnow);
            }
            if (foreCode >=503 && foreCode <= 508){
                forecastImg.setImageResource(R.drawable.foredaysand);
            }
            infoText.setText(forecast.more.info);
            minText.setText(forecast.temperature.min+"℃");
            maxText.setText(forecast.temperature.max+"℃");
            forecastLayout.addView(view);
        }
        if (weather.aqi !=null){
            airQlty.setText("："+weather.aqi.city.qlty);
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String airbrt = "空气指数---"+weather.suggestion.air.info;
        String comfbrf = "舒适指数---"+weather.suggestion.comfort.info;
        String flubrf = "感冒指数---"+weather.suggestion.flu.info;
        String drsgbrf = "穿衣指数---"+weather.suggestion.drsg.info;
        String airtxt = weather.suggestion.air.infotxt;
        String comforttxt =weather.suggestion.comfort.infotxt;
        String influenzatxt =weather.suggestion.flu.infotxt;
        String dresstxt=weather.suggestion.drsg.infotxt;
        airBrf.setText(airbrt);
        comfBrf.setText(comfbrf);
        fluBrf.setText(flubrf);
        drsgBrf.setText(drsgbrf);
        airTxt.setText(airtxt);
        comfortTxt.setText(comforttxt);
        influenzaTxt.setText(influenzatxt);
        dressTxt.setText(dresstxt);
        scrWeatherLayout.setVisibility(View.VISIBLE);
    }



    public void requestWeather(String weatherId)
    {
        String weatherUrl="https://api.heweather.com/v5/weather?city="
                +weatherId+"&key=342a3bf415f84fc7ba09cf90e66fcee1";
        Log.i("WeatherActivity", "weatherUrl=: "+weatherUrl);
        HttpUtil.sendHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr=response.body().string();
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(resultStr);
                    JSONArray jsonArray=jsonObject.getJSONArray("HeWeather5");
                    String weatherContent =jsonArray.getJSONObject(0).toString();
                    currentWeather=new Gson().fromJson(weatherContent,Weather.class);
                    showWeatherInfo(currentWeather);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
        });

    }
}
