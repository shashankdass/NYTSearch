package sdass.nytimessearch.collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sdass.nytimessearch.model.Article;

/**
 * Created by sdass on 8/13/16.
 */
public class ArticleCollection {
    public List<Article> getArticles() {
        return articles;
    }
//
//    public void setArticles(List<Article> articles) {
//        this.articles = articles;
//    }
    @SerializedName("docs")
    @Expose
    public List<Article> articles = new ArrayList<Article>();

    public static ArticleCollection parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        ArticleCollection articleResponse = gson.fromJson(response, ArticleCollection.class);
        return articleResponse;
    }

//    // public constructor is necessary for collections
//    public ArticleCollection() {
//        articles = new ArrayList<Article>();
//    }

//    public static ArticleCollection parseJSON(String response) {
//        Gson gson = new GsonBuilder().create();
//        ArticleCollection articleCollection = gson.fromJson(response, ArticleCollection.class);
//        return articleCollection;
//    }
}
