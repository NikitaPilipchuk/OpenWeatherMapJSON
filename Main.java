package com.company;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws
            IOException, MalformedURLException, JSONException
    {

        StringBuffer content = new StringBuffer();
        String urlAdress = "http://api.openweathermap.org/data/2.5/weather?id=2023469&appid=03357f665025060c9f305431aee9f946";
        URL url = new URL(urlAdress);
        URLConnection urlCon = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
        String line = bufferedReader.readLine();
        content.append(line);
        bufferedReader.close();

        JSONObject obj = new JSONObject(content.toString());

        long sunrise = obj.getJSONObject("sys").getLong("sunrise");
        Date sunriseDate = new Date(sunrise * 1000L);
        Calendar sunriseCal = Calendar.getInstance();
        sunriseCal.setTime(sunriseDate);

        long sunset = obj.getJSONObject("sys").getLong("sunset");
        Date sunsetDate = new Date(sunset * 1000L);
        Calendar sunsetCal = Calendar.getInstance();
        sunsetCal.setTime(sunsetDate);

        long diff = (sunsetCal.getTimeInMillis() - sunriseCal.getTimeInMillis())/1000;
        long hours = diff / 3600;
        long minutes = (diff - (3600 * hours)) / 60;
        long seconds = diff - ((3600 * hours)+(60*minutes));

        System.out.println("Город: " + obj.getString("name") +
                "\n\nТемпература: " + Math.round(obj.getJSONObject("main").getDouble("temp")-273) +
                "°C\nВлажность: " + Math.round(obj.getJSONObject("main").getDouble("humidity")) +
                "%\n\nВосход: " + sunriseDate +
                "\nЗакат: " + sunsetDate +
                "\nДолгота дня: " + hours + " часов " + minutes + " минута " + seconds + " секунды");
    }
}