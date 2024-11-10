package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.databinding.IndividualArticleLayoutBinding;
import com.example.myapplication.models.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articleList;
    private Context context;

    public ArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create the ViewHolder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IndividualArticleLayoutBinding binding = IndividualArticleLayoutBinding.inflate(inflater, parent, false);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.bind(article);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, article.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    // Remove static keyword to make it a regular inner class
    class ArticleViewHolder extends RecyclerView.ViewHolder {
        IndividualArticleLayoutBinding binding;

        public ArticleViewHolder(@NonNull IndividualArticleLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article article) {
            binding.articleImageView.setImageDrawable(null);
            Glide.with(binding.articleImageView.getContext())
                    .load(article.getUrlToImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.articleImageView);
            binding.titleTextView.setText(article.getTitle());
            binding.descriptionTextView.setText(article.getDescription());
        }
    }

    public void updateArticles(List<Article> articles) {
        articleList.clear();
        articleList.addAll(articles);
        notifyDataSetChanged();
    }
}
