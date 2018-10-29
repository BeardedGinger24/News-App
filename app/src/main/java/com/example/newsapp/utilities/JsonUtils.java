package com.example.newsapp.utilities;

import com.example.newsapp.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<NewsItem> parseJson(String Json){
        ArrayList<NewsItem> newsItems = new ArrayList<>();

        try {
            JSONObject results = new JSONObject(Json);
            JSONArray articles = results.getJSONArray("articles");

            for(int i = 0; i < articles.length(); i++){
                JSONObject article = articles.getJSONObject(i);

                String author = article.getString("author");
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");
                String urlImage = article.getString("urlToImage");
                String publishedAt = article.getString("publishedAt");

                newsItems.add(new NewsItem(author, title, description, url, urlImage, publishedAt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return newsItems;
    }
}
