package nickolai.lundby.movielibrary.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nickolai.lundby.movielibrary.Models.Movie;
import nickolai.lundby.movielibrary.R;

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
                List<String> genreList = new ArrayList();
                for (String genre : genres) {
                    genreList.add(genre);
                }
                Boolean myBool;
                if(row[5].toUpperCase().equals("FALSE"))
                    myBool = false;
                else
                    myBool = true;

                int myPoster;
                switch(genreList.get(0).toUpperCase()){
                    case "ACTION":
                        myPoster = R.drawable.action_50;
                        break;
                    case "ADVENTURE":
                        myPoster = R.drawable.adventure_50;
                        break;
                    case "ANIMATION":
                        myPoster = R.drawable.animation_50;
                        break;
                    case "ANIME":
                        myPoster = R.drawable.anime_50;
                        break;
                    case "BIOGRAPHY":
                        myPoster = R.drawable.biography_50;
                        break;
                    case "COMEDY":
                        myPoster = R.drawable.comedy_50;
                        break;
                    case "DOCUMENTARY":
                        myPoster = R.drawable.documentary_50;
                        break;
                    case "DRAMA":
                        myPoster = R.drawable.drama_50;
                        break;
                    case "HORROR":
                        myPoster = R.drawable.horror_50;
                        break;
                    case "MUSICAL":
                        myPoster = R.drawable.musical_50;
                        break;
                    case "NATURE":
                        myPoster = R.drawable.nature_50;
                        break;
                    case "ROMANCE":
                        myPoster = R.drawable.romance_50;
                        break;
                    case "SCIFI":
                        myPoster = R.drawable.scifi_50;
                        break;
                    case "WESTERN":
                        myPoster = R.drawable.western_50;
                        break;
                    default:
                        myPoster = R.drawable.default_50;
                        break;
                }
                Movie m = new Movie(row[0], row[1], genreList, Double.parseDouble(row[3]), Double.parseDouble(row[4]), myBool, myPoster, row[6]);
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
}

