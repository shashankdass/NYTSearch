package sdass.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sdass.nytimessearch.R;
import sdass.nytimessearch.adapter.ArticleAdapter;
import sdass.nytimessearch.collection.ArticleCollection;
import sdass.nytimessearch.databinding.ActivitySearchBinding;
import sdass.nytimessearch.fragment.SettingsFragment;
import sdass.nytimessearch.listeners.EndlessRecyclerViewScrollListener;
import sdass.nytimessearch.model.Article;
import sdass.nytimessearch.view.ItemClickSupport;

public class SearchActivity extends AppCompatActivity implements SettingsFragment.onSettingsDoneListener{
    ArticleAdapter articleAdapter;
    ArrayList<Article> articles = new ArrayList<Article>();
    private ActivitySearchBinding binding;
    Toolbar toolbar;
    RecyclerView results;
    String queryToSearch;
    boolean isNewSearch = false;
    private String begin_date_string;
    private String sortBy;
    private StringBuilder genresString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        toolbar = binding.toolbar;
        results = binding.rvArticles;

        setSupportActionBar(toolbar);
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        bindDataToAdapter(staggeredGridLayoutManager);
        ItemClickSupport.addTo(results).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Article article = articles.get(position);
                        Intent webIntent = new Intent(SearchActivity.this,WebActivity.class);
                        webIntent.putExtra("article",article.webURL);
                        startActivity(webIntent);
                    }
                }
        );
        results.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                RequestParams params = new RequestParams();
                params.put("page", page);
                params.put("fq", queryToSearch);
                isNewSearch = false;
                getArticlesAsync(params);

            }
        });
    }
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
    private void bindDataToAdapter(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        // Bind adapter to recycler view object
        articleAdapter = new ArticleAdapter(this,articles );
        results.setAdapter(articleAdapter);
        results.setLayoutManager(staggeredGridLayoutManager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.article_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                RequestParams params = new RequestParams();
                params.put("fq",query);
                queryToSearch = "\""+query+"\"";

                isNewSearch = true;
                String queryString;
                if (begin_date_string!= null && !begin_date_string.equals(""))
                    params.put("begin_date",begin_date_string);
                if (sortBy!=null && !sortBy.equals(""))
                    params.put("sort",sortBy.toLowerCase());
                if (genresString!=null && !genresString.toString().equals(""))
                    if (params.has("fq"))
                        queryToSearch = queryToSearch+genresString.toString();
                    else
                        queryToSearch = genresString.toString();
                params.put("fq",queryToSearch);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                getArticlesAsync(params);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsFragment settingsFragment = new SettingsFragment ();
            settingsFragment.show(getSupportFragmentManager(),"Settings Fragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getArticlesAsync( RequestParams params) {
        if(isNetworkAvailable() && isOnline()) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();

            AsyncHttpClient client = new AsyncHttpClient();
            params.put("api-key", "59c7eacc8c3c4bcd866fb456898cbf29");
            if (!params.has("page")) {
                params.put("page", 0);
            }
            String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
            client.get(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    JSONArray docs = null;
                    try {
                        if (isNewSearch)
                            articleAdapter.clear();
                        docs = response.getJSONObject("response").getJSONArray("docs");
                        ArrayList<Article> newArticles = Article.fromJSONArray(docs);
                        articleAdapter.addAll(newArticles);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
@Override
public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
    super.onFailure(statusCode, headers, responseString, throwable);
}
            });
        } else {
            Log.e("NNF","Network not found");
        }
    }

    @Override
    public void onSettingsDone(Calendar c, String sortBy, HashMap genres) {
        StringBuilder genresString =new StringBuilder("news_desk:(");
        Iterator it = genres.entrySet().iterator();
        ArrayList<String> genresList = new ArrayList<String>();
        while (it.hasNext()) {
            Map.Entry<String,Boolean> pair = (Map.Entry<String,Boolean>)it.next();
            if (pair.getValue()){
                genresList.add(pair.getKey());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        for (int i=0; i<genresList.size() ; i++){
            if(i == genresList.size() -1)
                genresString.append("\""+genresList.get(i)+"\"");
            else {
                genresString.append("\""+genresList.get(i)+"\"");
                genresString.append("%20");
            }

        }
        genresString.append(")");
        String year = ""+c.get(Calendar.YEAR);
        String month = String.format("%02d", (c.get(Calendar.MONTH) + 1));
        String day = String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
        this.begin_date_string = ""+year+month+day;
        this.sortBy = sortBy.toLowerCase();
        this.genresString = genresString;
    }
}
