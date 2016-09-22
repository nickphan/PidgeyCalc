package com.idtech.nicholasphan.pidgeycalc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nicholas on 8/23/2016.
 */
public class AnswerActivity extends android.app.Activity {

    TextView pidgeysHaveView;
    TextView candiesNeedView;
    TextView candiesHaveView;
    TextView pidgeysNeedView;
    TextView toCatchView;
    TextView toTransferView;

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent answerIntent = getIntent();

        pidgeysHaveView = (TextView)findViewById(R.id.pidgeysHave);
        candiesNeedView = (TextView)findViewById(R.id.candiesNeed);
        candiesHaveView = (TextView)findViewById(R.id.candiesHave);
        pidgeysNeedView = (TextView)findViewById(R.id.pidgeysNeed);
        toCatchView = (TextView)findViewById(R.id.toCatch);
        toTransferView = (TextView)findViewById(R.id.toTransfer);
        /*
        * pidgeysHave
        * candiesNeed
        * candiesHave
        * pidgeysNeed
        * toCatch
        * toTransfer
        * */

        int pidgeysHave = answerIntent.getIntExtra("pidgeysHave", 0);
        int candiesNeed = answerIntent.getIntExtra("candiesNeed", 0);
        int candiesHave = answerIntent.getIntExtra("candiesHave", 0);
        int pidgeysNeed = answerIntent.getIntExtra("pidgeysNeed", 0);
        int toCatch = answerIntent.getIntExtra("toCatch", 0);
        int toTransfer = answerIntent.getIntExtra("toTransfer", 0);

        if(pidgeysHave != 0 && candiesHave != 0){   // both
            setTexts(pidgeysHave, candiesNeed, candiesHave, pidgeysNeed, toCatch, toTransfer);
        }else if(pidgeysHave != 0){                 // just pidgeys
            setTexts(pidgeysHave, candiesNeed, 1);
        }else{                                      // just candies
            setTexts(candiesHave, pidgeysNeed, 0);
        }

        int worth = answerIntent.getIntExtra("Worth", 0);
        setImage(worth);


    }
    //FIXED
    public void setTexts(int valueHave, int valueNeed, int type){
        if(type == 0){                              // 0 is for candies
            candiesHaveView.setText("" + valueHave);
            pidgeysNeedView.setText("" + valueNeed);

            text = (TextView)findViewById(R.id.message1);
            text.setText(null);
            text = (TextView)findViewById(R.id.message2);
            text.setText(null);
            text = (TextView)findViewById(R.id.message5);
            text.setText(null);
            text = (TextView)findViewById(R.id.message6);
            text.setText(null);
            text = (TextView)findViewById(R.id.message7);
            text.setText(null);

        }else{                                      // 1 is for pidgeys
            pidgeysHaveView.setText("" + valueHave);
            candiesNeedView.setText("" + valueNeed);

            text = (TextView)findViewById(R.id.message3);
            text.setText(null);
            text = (TextView)findViewById(R.id.message4);
            text.setText(null);
            text = (TextView)findViewById(R.id.message5);
            text.setText(null);
            text = (TextView)findViewById(R.id.message6);
            text.setText(null);
            text = (TextView)findViewById(R.id.message7);
            text.setText(null);

        }
    }

    public void setTexts(int pidgeysHave, int candiesNeed, int candiesHave, int pidgeysNeed, int toCatch, int toTransfer){

        pidgeysHaveView.setText("" + pidgeysHave);
        candiesNeedView.setText("" + candiesNeed);
        candiesHaveView.setText("" + candiesHave);
        pidgeysNeedView.setText("" + pidgeysNeed);
        toCatchView.setText("" + toCatch);
        toTransferView.setText("" + toTransfer);

        setColors(pidgeysHave, candiesNeed, candiesHave, pidgeysNeed, toCatch, toTransfer);
    }

    public void setColors(int pidgeysHave, int candiesNeed, int candiesHave, int pidgeysNeed, int toCatch, int toTransfer){
        pidgeysHaveView.setTextColor(Color.GREEN);
        candiesHaveView.setTextColor(Color.GREEN);

        if(pidgeysHave >= pidgeysNeed){
            pidgeysNeedView.setTextColor(Color.GREEN);
        }else if(pidgeysHave + 20 >= pidgeysNeed){
            pidgeysNeedView.setTextColor(Color.YELLOW);
        }else{
            pidgeysNeedView.setTextColor(Color.RED);
        }

        if(candiesHave >= candiesNeed){
            candiesNeedView.setTextColor(Color.GREEN);
        }else if(candiesHave + 20 >= candiesNeed){
            candiesNeedView.setTextColor(Color.YELLOW);
        }else{
            candiesNeedView.setTextColor(Color.RED);
        }

        if(toCatch == 0){
            toCatchView.setTextColor(Color.GREEN);
        }else if(toCatch < 10){
            toCatchView.setTextColor(Color.YELLOW);
        }else{
            toCatchView.setTextColor(Color.RED);
        }

        toTransferView.setTextColor(Color.GREEN);
    }

    public void setImage(int pidgeys){
        ImageView image = (ImageView)findViewById(R.id.image);
        if(pidgeys < 25){
            image.setImageResource(R.drawable.pidgey);
        }else if(pidgeys < 75){
            image.setImageResource(R.drawable.pidgeotto);
        }else{
            image.setImageResource(R.drawable.pidgeot);
        }
    }
}
