package com.runekeena.au297052movies.utils;

import com.runekeena.au297052movies.R;
import com.runekeena.au297052movies.model.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    // reads from CSV file and returns ArrayList of movie objects
    public ArrayList<Movie> readMovieData(InputStream inputStream){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try{
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                //To read all the movie shit into movie objects!
                String[] columns = csvLine.split(";");
                String genres =columns[2];

                int imgId = getImgId(genres);

                Movie m = new Movie(columns[0], columns[1], genres, Double.parseDouble(columns[3]), false, imgId);
                movieArrayList.add(m);
            }
        }catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return movieArrayList;
    }

    // adds icon resource depending on genre
    private int getImgId(String genre){
        int imgId;
        String[] g = genre.split(",");
        switch (g[0].toUpperCase()){
            case "ACTION":
                imgId = R.drawable.ic_action;
                break;
            case "ADVENTURE":
                imgId = R.drawable.ic_adventure;
                break;
            case "ANIMATION":
                imgId = R.drawable.ic_animation;
                break;
            case "BIOGRAPHY":
                imgId = R.drawable.ic_biography;
                break;
            case "COMEDY":
                imgId = R.drawable.ic_comedy;
                break;
            case "DRAMA":
                imgId = R.drawable.ic_drama;
                break;
            case "FAMILY":
                imgId = R.drawable.ic_family;
                break;
            case "FANTASY":
                imgId = R.drawable.ic_fantasy;
                break;
            case "HISTORY":
                imgId = R.drawable.ic_history;
                break;
            case "MUSIC":
                imgId = R.drawable.ic_music;
                break;
            case "ROMANCE":
                imgId = R.drawable.ic_romance;
                break;
            case "SCI-FY":
                imgId = R.drawable.ic_scify;
                break;
            case "SPORT":
                imgId = R.drawable.ic_sport;
                break;
            case "THRILLER":
                imgId = R.drawable.ic_thriller;
                break;
            default:
                imgId = R.drawable.ic_movie;
                break;
        }
        return imgId;
    }
}
