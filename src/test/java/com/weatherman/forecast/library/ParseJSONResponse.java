package com.weatherman.forecast.library;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.*;
import com.google.common.collect.Multiset;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParseJSONResponse {
    public LinkedHashMap<String,String> currentMainDataMap = new LinkedHashMap<String,String>();
    public LinkedHashMap<String,String> currentWeatherDataMap= new LinkedHashMap<String,String>();
    public LinkedHashMap<String,String> pastMainDataMap = new LinkedHashMap<String,String>();
    public LinkedHashMap<String,String> pastWeatherDataMap= new LinkedHashMap<String,String>();
    HashMap<String,String> tmpDataMap;
    ArrayList<HashMap<String,String>> tmpWeatherDataMap;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date currentdate = new Date();
    Date jsonDate;
    public void parseMainDataMap(HashMap<String,Object> json)
    {
        String date=json.get("dt_txt").toString();
        try {
            jsonDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }
        if(jsonDate.compareTo(currentdate)==0)
        {
            tmpDataMap = (HashMap<String,String>) json.get("main");
            currentMainDataMap.put(tmpDataMap.get("temp"),jsonDate.toString());
        }
        else
        {
            tmpDataMap = (HashMap<String,String>) json.get("main");
            pastMainDataMap.put(tmpDataMap.get("temp"),jsonDate.toString());
        }
    }
    public void parseWeatherDataMap(HashMap<String,Object> json)
    {
        String date=json.get("dt_txt").toString();
        try {
            jsonDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }
        if(jsonDate.compareTo(currentdate)==0)
        {
            tmpWeatherDataMap = (ArrayList<HashMap<String, String>>) json.get("weather");
            currentWeatherDataMap.put(tmpWeatherDataMap.get(0).get("description"),jsonDate.toString());
        }
        else
        {
            tmpWeatherDataMap = (ArrayList<HashMap<String,String>>) json.get("weather");
            pastWeatherDataMap.put(tmpWeatherDataMap.get(0).get("description"),jsonDate.toString());
        }
    }
    public void analyseTempData()
    {
        if(currentMainDataMap.isEmpty())
        {
            System.out.println("**********NO WEATHER FORECAST FOR CURRENT DATE ANALYSING THE PAST DATA**********");
            Multimap<String, String> multiMap = HashMultimap.create();
            for (Map.Entry<String, String> entry : pastMainDataMap.entrySet()) {
                multiMap.put(entry.getValue(), entry.getKey());
            }
            System.out.println();
            float[][] a=new float[multiMap.size()][pastMainDataMap.size()+1];
            int n=0;
            for (Map.Entry<String, Collection<String>> entry : multiMap.asMap().entrySet()) {
                System.out.println("Original value: " + entry.getKey() + " was mapped to keys: "
                        + entry.getValue());
                int l=0;
                for (Object o : entry.getValue().toArray()) {
                    a[n][l]= Float.parseFloat(o.toString());
                    l++;
                }
                n++;
            }

            for(int i=0;i<multiMap.size();i++)
            {
                boolean increase=false,decrease=false,same=false;
                for(int j=0;j<pastMainDataMap.size();j++)
                {
                    if(a[i][j]<a[i][j+1])
                        increase=true;
                    else if(a[i][j]>a[i][j+1])
                        decrease=true;
                    else
                        same=true;
                }
                if(increase==true && decrease==true && same==true)
                    System.out.println("Temperature Fluctuation is Present");
                else if (increase==true && decrease==false && same==true)
                    System.out.println("Temperature Fluctuation is Present");
                else if (increase==false && decrease==true && same==true)
                    System.out.println("Temperature Fluctuation is Present");
                else if (increase==true && decrease==true && same==false)
                    System.out.println("Temperature Fluctuation is Present");
                else if(increase==false && decrease==false && same==true)
                    System.out.println("Temperature is SAME");
                else if(increase==false && decrease==true && same==false)
                    System.out.println("Temperature is DOWN");
                else if(increase==true && decrease==false && same==false)
                    System.out.println("Temperature is UP");
            }
        }
    }
    public void analyseWeatherData() {
        if (currentWeatherDataMap.isEmpty()) {
            System.out.println("**********NO WEATHER FORECAST FOR CURRENT DATE ANALYSING THE PAST DATA**********");
            Multimap<String, String> multiMap = HashMultimap.create();
            for (Map.Entry<String, String> entry : pastWeatherDataMap.entrySet()) {
                multiMap.put(entry.getValue(), entry.getKey());
            }
            System.out.println();
            String[][] a = new String[multiMap.size()][pastWeatherDataMap.size() + 1];
            int n = 0;
            for (Map.Entry<String, Collection<String>> entry : multiMap.asMap().entrySet()) {
                System.out.println("Original value: " + entry.getKey() + " was mapped to keys: "
                        + entry.getValue());
                int l = 0;
                for (Object o : entry.getValue().toArray()) {
                    a[n][l] = o.toString();
                    l++;
                }
                n++;
            }

            for (int i = 0; i < multiMap.size(); i++) {
                boolean clear = false,  rainy=false, cloudy = false, snow = false;
                for (int j = 0; j < pastWeatherDataMap.size(); j++) {
                    if (a[i][j] != null) {
                        switch (a[i][j].toLowerCase()) {
                            case "scattered clouds":
                            case "overcast clouds":
                            case "broken clouds":
                                cloudy = true;
                                break;
                            case "clear sky":
                            case "few clouds":
                                clear = true;
                                break;
                            case "light rain":
                            case "moderate rain":
                                rainy = true;
                                break;
                            case "snow":
                                snow = true;
                                break;
                        }
                    }
                }
                    if (cloudy == true && rainy == true && clear == false && snow == false)
                        System.out.println("RAINY");
                    else if (cloudy == true && clear == true && rainy == false && snow == false)
                        System.out.println("CLOUDY");
                    else if (clear == true && cloudy == false && rainy == false && snow == false)
                        System.out.println("CLEAR");
                    else if (snow == true && cloudy == false && rainy == false && clear == false)
                        System.out.println("SNOWY");
            }
        }
    }
}
