package sdass.nytimessearch.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sdass.nytimessearch.model.Article;

/**
 * Created by sdass on 8/14/16.
 */
public interface ArticleApiEnpointInterface {
    @GET("articlesearch.json")
    Call<Article> getArticles(@Path("username") String username);
}
