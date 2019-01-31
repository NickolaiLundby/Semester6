package com.nickolailisberglundby.rockpaperscissor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Widgets
    TextView txtUserScore;
    TextView txtAndroidScore;
    TextView txtUserOutput;
    Button btnRock;
    Button btnPaper;
    Button btnScissor;

    //Constants
    private static final int ROCK = 0;
    private static final int PAPER = 1;
    private static final int SCISSOR = 2;

    //Selections
    int userWins;
    int androidWins;
    int userSelection;
    int androidSelection;
    String userSelectionString;
    String androidSelectionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Bind widgets
        txtUserScore.findViewById(R.id.txtUserScore);
        txtAndroidScore.findViewById(R.id.txtAndroidScore);
        txtUserOutput.findViewById(R.id.txtUserOutput);
        btnRock.findViewById(R.id.btnRock);
        btnPaper.findViewById(R.id.btnPaper);
        btnScissor.findViewById(R.id.btnScissor);

        //Setup listeners
        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelection = ROCK;
                userSelectionString = "ROCK";
            }
        });

        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelection = PAPER;
                userSelectionString = "PAPER";
            }
        });

        btnScissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelection = SCISSOR;
                userSelectionString = "SCISSOR";
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    public void evaluateGame(int selection) {
        Random rn = new Random();
        androidSelection = rn.nextInt(3);
        switch(androidSelection){
            case ROCK:
                androidSelectionString = "ROCK";
            case PAPER:
                androidSelectionString = "PAPER";
            case SCISSOR:
                androidSelectionString = "SCISSOR";
        }
        if(userSelection == androidSelection)
        {
            txtUserOutput.setText("The game was a TIE! User selected " + userSelectionString + " and Android selected " + androidSelectionString);
        }
        else if(userSelection == ROCK && androidSelection == SCISSOR)
        {
            txtUserOutput.setText("The USER won! User selected " + userSelectionString + " and Android selected " + androidSelectionString);;
            userWins++;
            txtUserScore.setText(""+userWins);
        }
        else if(userSelection == SCISSOR && androidSelection == PAPER)
        {
            txtUserOutput.setText("The USER won! User selected " + userSelectionString + " and Android selected " + androidSelectionString);;
            userWins++;
            txtUserScore.setText(""+userWins);
        }
        else if(userSelection == PAPER && androidSelection == ROCK)
        {
            txtUserOutput.setText("The USER won! User selected " + userSelectionString + " and Android selected " + androidSelectionString);;
            userWins++;
            txtUserScore.setText(""+userWins);
        }
        else
        {
            txtUserOutput.setText("The ANDROID won! User selected " + userSelectionString + " and Android selected " + androidSelectionString);;
            androidWins++;
            txtUserScore.setText(""+androidWins);
        }
    }
}
