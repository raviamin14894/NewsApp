package com.example.rkjc.news_app_2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView news_recyclerview;
    private NewsRecyclerViewAdapter adapter;
    private ArrayList<NewsItem> newsItems;

    String newsString = "";
    URL newsURL;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsString = NetworkUtils.buildURL(NetworkUtils.base_url, NetworkUtils.apiKey);
        try {
            newsURL = new URL(newsString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("NEWS", "onCreate newsURL :" + newsURL);

        news_recyclerview = (RecyclerView) findViewById(R.id.news_recyclerview);

        newsItems = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        news_recyclerview.setLayoutManager(mLayoutManager);
        news_recyclerview.setItemAnimator(new DefaultItemAnimator());

        new NewsQueryTask().execute();

        news_recyclerview.setAdapter(adapter);


    }

    public class NewsQueryTask extends AsyncTask<String, Void, String> {
        String response;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                response = NetworkUtils.getResponseFromHttpUrl(newsURL);
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            newsItems = JsonUtils.parseNews(s);
            if (newsItems.size() != 0 && newsItems != null) {
                adapter = new NewsRecyclerViewAdapter(MainActivity.this, newsItems);
                news_recyclerview.setAdapter(adapter);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            newsItems.clear();
            new NewsQueryTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
