package nickolai.lisberg.lundby.au259814movies.Utilities;

import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;

public class MovieHelperClass {
    public static int GetPosterId(String genres) {
        String[] genre = genres.split(",");
        switch(genre[0].toUpperCase()){
            case "ACTION":
                return R.drawable.action_50;
            case "ADVENTURE":
                return R.drawable.adventure_50;
            case "ANIMATION":
                return R.drawable.animation_50;
            case "ANIME":
                return R.drawable.anime_50;
            case "BIOGRAPHY":
                return R.drawable.biography_50;
            case "COMEDY":
                return R.drawable.comedy_50;
            case "DOCUMENTARY":
                return R.drawable.documentary_50;
            case "DRAMA":
                return R.drawable.drama_50;
            case "HORROR":
                return R.drawable.horror_50;
            case "MUSICAL":
                return R.drawable.musical_50;
            case "NATURE":
                return R.drawable.nature_50;
            case "ROMANCE":
                return R.drawable.romance_50;
            case "SCI-FI":
                return R.drawable.scifi_50;
            case "WESTERN":
                return R.drawable.western_50;
            default:
                return R.drawable.default_50;
        }
    }

    public static String UrlBuilder(String title){
        return "http://www.omdbapi.com/?t=" + title.replaceAll(" ", "+") + "&apikey=69750eef";
    }
}
