package com.example.newsapp.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.newsapp.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {
    final static String BASE_URL =
            "https://newsapi.org/v1/articles";

    // the manifest grabs the string value for the api key, in the res

    // final String temp = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=3e2f5d51c37745858195217b93a3581a";
    final static String PARAM_SOURCE = "source";
    final static String SOURCE = "the-next-web";

    final static String PARAM_SORT_BY = "sortBy";
    final static String SORT_BY = "latest";

    final static String PARAM_API_KEY = "apiKey";
    final static String API_KEY = "3e2f5d51c37745858195217b93a3581a";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, SOURCE)
                .appendQueryParameter(PARAM_SORT_BY, SORT_BY)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("IN_BUILD_URL", url.toString());
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

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
