package nickolai.lisberg.lundby.afragmentedworld.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nickolai.lisberg.lundby.afragmentedworld.Models.Movie;
import nickolai.lisberg.lundby.afragmentedworld.R;

public class CSVReader {
    InputStream inputStream;

    public CSVReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public ArrayList<Movie> read(){
        ArrayList<Movie> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(";");
                String[] genres = row[2].split(",");
                List<String> genreList = new ArrayList<>();
                Collections.addAll(genreList, genres);

                int myPoster = GetGenre(genreList.get(0));

                // This sets watched to true in cases where row[5] is "true", in all other cases, this value becomes "false", even if row[5] is null.
                // This behaviour is okay, but could be unwanted in other scenarios.
                Boolean watched = Boolean.valueOf(row[5].toLowerCase());

                Movie m = new Movie(row[0], row[1], genreList, Double.parseDouble(row[3]), Double.parseDouble(row[4]), watched, myPoster, "");
                resultList.add(m);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }

    private int GetGenre(String genre)
    {
        switch(genre.toUpperCase()){
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
            case "SCIFI":
                return R.drawable.scifi_50;
            case "WESTERN":
                return R.drawable.western_50;
            default:
                return R.drawable.default_50;
        }
    }
}
