package nickolai.lisberg.lundby.au259814movies.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;

// Inspiration for CSVReader class came from this stackoverflow:
// https://stackoverflow.com/questions/43055661/reading-csv-file-in-android-app
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

                int myPoster = MovieHelperClass.GetPosterId(row[2]);

                // This sets watched to true in cases where row[5] is "true", in all other cases, this value becomes "false", even if row[5] is null.
                // This behaviour is okay, but could be unwanted in other scenarios.
                Boolean watched = Boolean.valueOf(row[5].toLowerCase());

                Movie m = new Movie(row[0], row[1], row[2], Double.parseDouble(row[3]), Double.parseDouble(row[4]), watched, myPoster, "");
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

