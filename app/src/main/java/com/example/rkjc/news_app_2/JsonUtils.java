package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<NewsItem> parseNews(String s) {
        ArrayList<NewsItem> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("status")) {
                if (jsonObject.getString("status").trim().equalsIgnoreCase("ok")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("articles");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject articles = jsonArray.getJSONObject(i);

                        NewsItem newsItem = new NewsItem(
                                articles.getString("title"),
                                articles.getString("description"),
                                articles.getString("publishedAt"),
                                articles.getString("url"));


                        list.add(newsItem);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}


