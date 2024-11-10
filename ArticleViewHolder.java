package com.example.myapplication;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.databinding.IndividualArticleLayoutBinding;
import com.example.myapplication.models.Article;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    private final IndividualArticleLayoutBinding binding;

    public ArticleViewHolder(@NonNull IndividualArticleLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Article article) {
        binding.articleImageView.setImageDrawable(null); // Clear image before loading
        Glide.with(binding.articleImageView.getContext())
                .load(article.getUrlToImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.articleImageView);
        binding.titleTextView.setText(article.getTitle());
        binding.descriptionTextView.setText(article.getDescription());
    }

}
