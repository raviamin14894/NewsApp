package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter   extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    Context context;
    List<NewsItem> newsItems;

    public NewsRecyclerViewAdapter(Context context, List<NewsItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new NewsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsItemViewHolder holder, int position) {
        final NewsItem newsItem = newsItems.get(position);


        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "" + newsItem.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });


        holder.title.setText("Title : " + newsItem.getTitle());
        holder.description.setText("Description : " + newsItem.getDescription());
        holder.publishedat.setText("Date  : " + newsItem.getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linear;
        ImageView imageView;
        TextView title, description, publishedat, author;

        public NewsItemViewHolder(View itemView) {
            super(itemView);


            linear = itemView.findViewById(R.id.linear);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            publishedat = itemView.findViewById(R.id.publishedat);

        }
    }
    public void setNewsData(ArrayList<NewsItem> newsdatalist) {
        this.newsItems=newsdatalist;
        notifyDataSetChanged();
    }
}
