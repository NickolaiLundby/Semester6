package nickolai.lisberg.lundby.au259814movies.Models.APIModels;

import com.google.gson.Gson;

public class MovieAPIWrapper {
    public MovieAPI movieApi;

    public static MovieAPIWrapper fromJson(String s){
        return new Gson().fromJson(s, MovieAPIWrapper.class);
    }
}
