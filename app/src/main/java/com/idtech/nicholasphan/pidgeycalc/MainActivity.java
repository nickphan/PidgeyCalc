package com.idtech.nicholasphan.pidgeycalc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button pidgeyButton;
    EditText textField1;
    Button candyButton;
    EditText textField2;
    TextView errorMessege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pidgeyButton = (Button)findViewById(R.id.pidgeyButton);
        candyButton = (Button)findViewById(R.id.candyButton);
        textField1 = (EditText)findViewById(R.id.pidgeyNumber);
        textField2 = (EditText)findViewById(R.id.candyNumber);
        errorMessege = (TextView)findViewById(R.id.errorMessege);
        errorMessege.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        int pidgey = sharedPreferences.getInt("Pidgey", 0);
        int candy = sharedPreferences.getInt("Candy", 0);

        if(pidgey != 0){
            textField1.setText("" + pidgey);
        }
        if(candy != 0){
            textField2.setText("" + candy);
        }

        textField1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!isValidNumber(textField1.getText().toString())){
                    textField1.setText("");
                }
            }
        });
        textField2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!isValidNumber(textField2.getText().toString())){
                    textField2.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pidgeyCalculations(int pidgey, int candies){
        int numPidgeys = pidgey;
        int numCandy = candies;
        int numCatch = 0;
        int numTransfer = 0;

        while (numCandy != candyRequired(numPidgeys)) {
            if (numCandy < candyRequired(numPidgeys)) {
                numPidgeys--;
                numTransfer++;
                numCandy++;
            }
            else if (numCandy > candyRequired(numPidgeys)) {
                numPidgeys++;
                numCatch++;
                numCandy += 3;
            }
        }

        go(pidgey, candyRequired(pidgey), candies, pidgeyCounter(candies), numCatch, numTransfer, numPidgeys, numCandy);

    }
    public int candyRequired(int pidgey){

        return (pidgey * 10) + 2;

    }
    public int pidgeyCounter(int candy) {
        int pidgeyCandy = candy;
        int numEvos = 0;
        int numPidgeys = 0;
        int count = 0;
        while (pidgeyCandy + numEvos > 11) {
            if (pidgeyCandy > 11) {
                pidgeyCandy -= 12;
                numEvos++;
                pidgeyCandy++;
                count++;
            }
            else {
                numEvos--;
                pidgeyCandy++;
            }
        }
        return count;
	/*
	alternatively,
	if(candies % 10 > 1){
	return candies/10;
	}else{
	return (candies/10) - 1;
	}
	*/
    }

    public void onClick(View view){
        //textField1 = (EditText)findViewById(R.id.pidgeyNumber);
        //textField2 = (EditText)findViewById(R.id.candyNumber);
        String pidgeyNumber = textField1.getText().toString();
        String candyNumber = textField2.getText().toString();

        if(isValidNumber(pidgeyNumber)){
            if(isValidNumber(candyNumber)){
                int pidgey = Integer.parseInt(pidgeyNumber);
                int candy = Integer.parseInt(candyNumber);
                pidgeyCalculations(pidgey, candy);
            }else{
                int pidgey = Integer.parseInt(pidgeyNumber);
                int candyRequired = candyRequired(pidgey);
                go(candyRequired, pidgey,0);
            }
        }else if(isValidNumber(candyNumber)){

            int candy = Integer.parseInt(candyNumber);
            int numPidgeyCanEvolve = pidgeyCounter(candy);
            go(numPidgeyCanEvolve, numPidgeyCanEvolve, 1);
        }else{
            errorMessege.setVisibility(View.VISIBLE);
        }

    }

    public boolean isValidNumber(String s){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) < 48 || s.charAt(i) > 57){
                return false;
            }
        }
        return true;
    }

    public void go(int info, int pidgeys,int type){
        errorMessege.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, AnswerActivity.class);
        if(type == 0){
            intent.putExtra("candiesHave", info);
            intent.putExtra("pidgeysNeed", pidgeyCounter(info));
            save(0, info);
        }else{
            intent.putExtra("pidgeysHave", info);
            intent.putExtra("candiesNeed", candyRequired(info));
            save(info, 0);
        }
        intent.putExtra("Worth", pidgeys);
        startActivity(intent);
    }
    public void go(int pidgeyHave, int candyReq, int candyHave, int pidgeyCanEvo, int toCatch, int toTransfer, int pidgeyEvo, int candyEvo){
        errorMessege.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, AnswerActivity.class);

        intent.putExtra("pidgeysHave", pidgeyHave);
        intent.putExtra("candiesNeed", candyReq);
        intent.putExtra("candiesHave", candyHave);
        intent.putExtra("pidgeysNeed", pidgeyCanEvo);
        intent.putExtra("toCatch", toCatch);
        intent.putExtra("toTransfer", toTransfer);

        intent.putExtra("Worth", pidgeyEvo);
        save(pidgeyHave, candyHave);
        startActivity(intent);
    }

    public void save(int pidgey, int candy){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Pidgey", pidgey);
        editor.putInt("Candy", candy);
        editor.apply();
    }
}
