package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.newsapp.model.NewsItem;
import com.example.newsapp.utilities.JsonUtils;
import com.example.newsapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.ListItemClickListener {

    //TextView mNewsJsonResults;
    NewsAdapter mNewsAdapter;
    RecyclerView mNewsArticles;
    ArrayList<NewsItem> articles;
    //Toast mToast;
    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mNewsJsonResults = (TextView) findViewById(R.id.tv_json_news_result);
        mNewsArticles = (RecyclerView) findViewById(R.id.rv_news_articles);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNewsArticles.setLayoutManager(layoutManager);

        mNewsArticles.setHasFixedSize(true);
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
            articles = JsonUtils.parseJson(newsSearchResults);

            mNewsAdapter = new NewsAdapter( articles, MainActivity.this);
            mNewsArticles.setAdapter(mNewsAdapter);
            //String test = articles.get(0).getAuthor();
            //mNewsJsonResults.setText(test);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
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

    @Override
    public void onListItemClick(int clickedItemIndex) {
//        mToast = Toast.makeText(this, "Hurray", Toast.LENGTH_LONG);
//        mToast.show();

        Uri webpage = Uri.parse(articles.get(clickedItemIndex).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}
