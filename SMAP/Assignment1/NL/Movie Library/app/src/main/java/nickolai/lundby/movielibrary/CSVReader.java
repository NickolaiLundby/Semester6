package nickolai.lundby.movielibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
                        //myPoster = R.drawable.icons8_action_50;
                        myPoster = R.drawable.icons8_crime_24;
                        break;
                    case "ADVENTURE":
                        //myPoster = R.drawable.icons8_adventure_50;
                        myPoster = R.drawable.icons8_comedy_24;
                        break;
                    case "BIOGRAPHY":
                        //myPoster = R.drawable.icons8_documentary_48;
                        myPoster = R.drawable.icons8_comedy_24;
                        break;
                    case "DRAMA":
                        //myPoster = R.drawable.icons8_popcorn_50;
                        myPoster = R.drawable.icons8_comedy_24;
                        break;
                    case "ANIMATION":
                        //myPoster = R.drawable.icons8_animation_50;
                        myPoster = R.drawable.icons8_comedy_24;
                        break;
                    default:
                        //myPoster = R.drawable.icons8_default_50;
                        myPoster = R.drawable.icons8_comedy_24;
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

