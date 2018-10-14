package com.example.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.newsapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView mNewsJsonResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsJsonResults = (TextView) findViewById(R.id.tv_json_news_result);

        makeNewsSearchQuery();
    }

    private void makeNewsSearchQuery(){
        URL newsSearchUrl = NetworkUtils.buildUrl();
        new NewsQueryTask().execute(newsSearchUrl);
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String newsSearchResults) {
            super.onPostExecute(newsSearchResults);
            mNewsJsonResults.setText(newsSearchResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search_menu) {
            makeNewsSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
