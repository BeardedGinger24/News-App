package com.example.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newsapp.model.NewsItem;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {

    //Context context;
    private ArrayList<NewsItem> articles;
    final private ListItemClickListener mOnClickListener;


    public NewsAdapter(ArrayList<NewsItem> articles, ListItemClickListener mOnClickListener){
        //this.context = context;
        this.articles = articles;
        this.mOnClickListener = mOnClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_recyclerview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( NewsItemViewHolder articleViewHolder, int i) {
        articleViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView mArticleTitle;
        TextView mArticleDescription;
        TextView mArticleDate;


        public NewsItemViewHolder(View itemView) {
            super(itemView);

            mArticleTitle = (TextView) itemView.findViewById(R.id.tv_article_title);
            mArticleDescription = (TextView) itemView.findViewById(R.id.tv_article_description);
            mArticleDate = (TextView) itemView.findViewById(R.id.tv_article_date);

            itemView.setOnClickListener(this);
        }

         void bind(int position){
            mArticleTitle.setText(articles.get(position).getTitle());
            mArticleDescription.setText(articles.get(position).getDescription());
            mArticleDate.setText(articles.get(position).getPublishedAt());
         }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
