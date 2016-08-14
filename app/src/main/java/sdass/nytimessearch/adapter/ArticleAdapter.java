package sdass.nytimessearch.adapter;

import android.content.ClipData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sdass.nytimessearch.R;
import sdass.nytimessearch.databinding.ItemArticleBinding;
import sdass.nytimessearch.databinding.ItemNoImageArticleBinding;
import sdass.nytimessearch.model.Article;
import sdass.nytimessearch.view.ArticleViewHolder;
import sdass.nytimessearch.view.ArticleViewHolderNoImage;

/**
 * Created by sdass on 8/9/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Article> mArticles;
    public ArticleAdapter(Context mContext, List<Article> mArticles) {
        this.mContext = mContext;
        this.mArticles = mArticles;
    }
    private Context getContext() {
        return mContext;
    }

    public void clear() {
        mArticles.clear();
        notifyDataSetChanged();
    }
    // Add a list of items
    public void addAll(List<Article> list) {
        mArticles.addAll(list);
        notifyDataSetChanged();

    }
    @Override
    public int getItemViewType(int position) {
        Article article = mArticles.get(position);
        if(article.thumbnailImage.equals(""))
            return 0;
        else
            return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType==1) {
            ItemArticleBinding articleBinding = DataBindingUtil
                    .inflate(inflater, R.layout.item_article, parent, false);
            viewHolder = new ArticleViewHolder(getContext(), articleBinding);
        } else {
            ItemNoImageArticleBinding noImageArticleBinding = DataBindingUtil
                    .inflate(inflater, R.layout.item_no_image_article, parent, false);
            viewHolder = new ArticleViewHolderNoImage(getContext(), noImageArticleBinding);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Article article = (Article) mArticles.get(position);
        switch (viewHolder.getItemViewType()) {
            case 0:
                ArticleViewHolderNoImage vh1 = (ArticleViewHolderNoImage) viewHolder;
                if (article != null) {
                    vh1.bindArticle(article);
                }
                break;
            case 1:
                ArticleViewHolder vh2 = (ArticleViewHolder) viewHolder;
                if (article != null) {
                    vh2.bindArticle(article);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
