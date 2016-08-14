package sdass.nytimessearch.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import sdass.nytimessearch.databinding.ItemNoImageArticleBinding;
import sdass.nytimessearch.model.Article;

/**
 * Created by sdass on 8/13/16.
 */
public class ArticleViewHolderNoImage extends RecyclerView.ViewHolder{
    private final ItemNoImageArticleBinding binding;
    private EditText headline;
    private String webURL;
    private Article article;
    private Context mContext;
    public ArticleViewHolderNoImage(Context context, ItemNoImageArticleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.mContext = context;
    }

    public void bindArticle(Article article) {
        this.article = article;
        binding.tvNoImage.setText(article.mainHeadline);
        webURL = article.webURL;
    }
}
