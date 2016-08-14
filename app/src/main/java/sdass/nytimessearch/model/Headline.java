package sdass.nytimessearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sdass on 8/14/16.
 */
public class Headline {
    @SerializedName("main")
    @Expose
    public String main;
    @SerializedName("print_headline")
    @Expose
    public String printHeadline;
}
