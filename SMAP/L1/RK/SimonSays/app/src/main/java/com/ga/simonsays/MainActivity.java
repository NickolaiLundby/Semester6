package com.ga.simonsays;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //View elements
    //TextView txtTop;
    TextView txtColour;
    Button bntStart;
    Button bntExit;
    Button bntRed;
    Button bntBlue;
    Button bntGreen;
    Button bntYellow;

    //Constants
    private static final int RED = 0;
    private static final int BLUE = 1;
    private static final int GREEN = 2;
    private static  final int YELLOW = 3;

    //Utilities
    Random rand = new Random();
    boolean activePlay = false;
    boolean activeDisplay = false;
    Queue<Integer> coloursDisplay;
    Queue<Integer> coloursCompare;

    //Selections
    int userSelection;
    int curColour;
    String curColourString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bntStart = findViewById(R.id.bntStart);
        bntExit = findViewById(R.id.bntExit);

        txtColour = findViewById(R.id.txtColour);

        bntRed = findViewById(R.id.bntRed);
        bntBlue = findViewById(R.id.bntBlue);
        bntGreen = findViewById(R.id.bntGreen);
        bntYellow = findViewById(R.id.bntYellow);

        bntExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        bntStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeDisplay == false) {
                    activeDisplay = true;
                    GenSeq(3);
                    DisplaySeq();
                }
            }
        });


        bntRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if red is correct
                if (activePlay && coloursCompare.isEmpty() == false){
                    if(coloursCompare.remove() == RED){

                        if(coloursCompare.isEmpty()){
                            txtColour.setText("Correct - You WIN!");
                        } else {
                            txtColour.setText("Correct!");
                        }
                    } else {
                        txtColour.setText("Wrong - You lose.");
                        activePlay = false;
                    }
                }
            }
        });

        bntBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check blue right
                if (activePlay && coloursCompare.isEmpty() == false){
                    if(coloursCompare.remove() == BLUE){

                        if(coloursCompare.isEmpty()){
                            txtColour.setText("Correct - You WIN!");
                        } else {
                            txtColour.setText("Correct!");
                        }
                    } else {
                        txtColour.setText("Wrong - You lose.");
                        activePlay = false;
                    }
                }
            }
        });

        bntGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check green right
                if (activePlay && coloursCompare.isEmpty() == false){
                    if(coloursCompare.remove() == GREEN){

                        if(coloursCompare.isEmpty()){
                            txtColour.setText("Correct - You WIN!");
                        } else {
                            txtColour.setText("Correct!");
                        }
                    } else {
                        txtColour.setText("Wrong - You lose.");
                        activePlay = false;
                    }
                }
            }
        });

        bntYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check yellow right
                if (activePlay && coloursCompare.isEmpty() == false){
                    if(coloursCompare.remove() == YELLOW){

                        if(coloursCompare.isEmpty()){
                            txtColour.setText("Correct - You WIN!");
                        } else {
                            txtColour.setText("Correct!");
                        }
                    } else {
                        txtColour.setText("Wrong - You lose.");
                        activePlay = false;
                    }
                }
            }
        });
    }

    void GenSeq(int len){
        coloursDisplay = new LinkedList<>();
        coloursCompare = new LinkedList<>();
        for(int i=0; i<len; i++) {
            int next = rand.nextInt(4);
            coloursDisplay.add(next);
            coloursCompare.add(next);
        }
    }

    void ClearDisplay(){
        txtColour.setText("");
        txtColour.setBackgroundResource(0);
    }

    void DisplaySeq(){
            int next = coloursDisplay.remove();
            switch (next) {
                case RED:
                    curColourString = "RED";
                    txtColour.setText(curColourString);
                    txtColour.setTextColor(getResources().getColor(R.color.White));
                    txtColour.setBackground(this.getResources().getDrawable(R.drawable.bntred));
                    break;
                case BLUE:
                    curColourString = "BLUE";
                    txtColour.setText(curColourString);
                    txtColour.setTextColor(getResources().getColor(R.color.White));
                    txtColour.setBackground(this.getResources().getDrawable(R.drawable.bntblue));
                    break;
                case GREEN:
                    curColourString = "GREEN";
                    txtColour.setText(curColourString);
                    txtColour.setTextColor(getResources().getColor(R.color.Black));
                    txtColour.setBackground(this.getResources().getDrawable(R.drawable.bntgreen));
                    break;
                case YELLOW:
                    curColourString = "YELLOW";
                    txtColour.setText(curColourString);
                    txtColour.setTextColor(getResources().getColor(R.color.Black));
                    txtColour.setBackground(this.getResources().getDrawable(R.drawable.bntyellow));
                    break;
            }
            if (coloursDisplay.isEmpty() == false) {
                final Handler delay = new Handler();
                delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DisplaySeq();
                    }
                }, 500);
                final Handler delayClear = new Handler();
                delayClear.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ClearDisplay();
                    }
                }, 450);
            } else{

                final Handler delay = new Handler();
                delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtColour.setText("Your turn");
                        txtColour.setTextColor(getResources().getColor(R.color.Black));
                        txtColour.setBackgroundResource(0);
                        activePlay = true;
                        activeDisplay = false;
                    }
                }, 500);
            }
    };

}
