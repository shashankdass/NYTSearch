package sdass.nytimessearch.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import sdass.nytimessearch.databinding.ItemArticleBinding;
import sdass.nytimessearch.model.Article;


/**
 * Created by sdass on 8/9/16.
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder{
    private final ItemArticleBinding binding;
    private ImageView thumbnail;
    private EditText headline;
    private String webURL;
    private Article article;
    private Context mContext;
    public ArticleViewHolder(Context context,ItemArticleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.mContext = context;
    }

    public void bindArticle(Article article) {
        this.article = article;
        binding.tvHeadline.setText(article.mainHeadline);
        binding.ivThumbnail.setImageResource(0);
        if (!article.thumbnailImage.equals(""))
            Glide.with(mContext).load(article.thumbnailImage).into(binding.ivThumbnail);
        webURL = article.webURL;
    }
}
