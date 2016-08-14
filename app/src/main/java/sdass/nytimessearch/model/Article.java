package sdass.nytimessearch.model;

import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by sdass on 8/9/16.
 */
@Parcel
public class Article {
    public String webURL;
    public String mainHeadline;
    public String thumbnailImage;
    public Article(JSONObject jsonObject) throws JSONException {

        this.webURL = jsonObject.getString("web_url");
        this.mainHeadline = jsonObject.getJSONObject("headline").getString("main");
        JSONArray multimedia = jsonObject.getJSONArray("multimedia");
        if(multimedia.length() > 0){
            JSONObject mulitmediaObj = multimedia.getJSONObject(0);
            this.thumbnailImage = "http://nytimes.com/"+mulitmediaObj.getString("url");
        } else {
            this.thumbnailImage = "";
        }
    }
    public static ArrayList<Article> fromJSONArray(JSONArray array) {
        ArrayList<Article> results = new ArrayList<>();
        for (int i=0; i<array.length(); i++) {
            try {
                results.add(new Article(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
