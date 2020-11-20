package com.example.guessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //-----------------------------------------------------------

    boolean SetupMode;
    String name;
    int Score;
    int BestScore;
    int Attempts;
    int RandomNumber;

    //-----------------------------------------------------------

    private void RandomNumber(){
        Random rand = new Random();
        RandomNumber = rand.nextInt( 20) + 1;
    }

    private void ResetGame(){
        EditText EnterText = (EditText) findViewById(R.id.EnterText);
        TextView GuideText = (TextView) findViewById(R.id.GuideText);
        TextView ScoreText = (TextView) findViewById(R.id.ScoreText);
        TextView NameText = (TextView) findViewById(R.id.NameText);
        TextView AttemptsText = (TextView) findViewById(R.id.AttemptsText);

        Attempts = 20;                               //number of attempts. -1 attempt for every wrong guess
        SetupMode = true;                            //For entering your name
        name = "Default";
        Score = 0;
        GuideText.setText("Enter Your Name");
        ScoreText.setText("Score - 0");
        NameText.setText("NAME");
        EnterText.setHint("Name");
        AttemptsText.setText("Attempts - 20");
        RandomNumber();
    }  //resets everything but keeps the top score.


    private void UpdateScore() {
        TextView ScoreText = (TextView) findViewById(R.id.ScoreText);
        TextView TopScore = (TextView) findViewById(R.id.TopScore);

        ScoreText.setText("Score - " + Score);

        if (Score > BestScore) {
            BestScore = Score;
            TopScore.setText("Best Score - " + BestScore + "[" + name + "]");
        }
    }

    //-----------------------------------------------------------

    public void OnButtonClicked (View view) {

        EditText EnterText = (EditText) findViewById(R.id.EnterText);
        TextView GuideText = (TextView) findViewById(R.id.GuideText);
        TextView NameText = (TextView) findViewById(R.id.NameText);
        TextView AttemptsText = (TextView) findViewById(R.id.AttemptsText);

        if (SetupMode == true) {
            if (EnterText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter A number", Toast.LENGTH_SHORT).show();
            } //Check
            else {
                name = EnterText.getText().toString();
                NameText.setText(name);
                GuideText.setText("Pick Between 1 - 20");
                EnterText.setText("");
                SetupMode = false;
            }
        } //picking a name.
        else if(Attempts >= 0) {


            int UserNumber = Integer.parseInt(EnterText.getText().toString());

            if (EnterText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter A number", Toast.LENGTH_SHORT).show();
            } //EditText Empty Check
            else if (Integer.parseInt(EnterText.getText().toString()) < 1 || (Integer.parseInt(EnterText.getText().toString()) > 20)) {
                Toast.makeText(this, "Bro I told you between 1 - 20", Toast.LENGTH_SHORT).show();
            } //EditText Between 1 - 20 Check

            else{
                if (UserNumber < RandomNumber) {
                    Attempts--;
                    AttemptsText.setText("Attempts - " + Attempts);
                    GuideText.setText("Higher than " + UserNumber);
                    EnterText.setText("");
                } //If the number is lower
                else if (UserNumber > RandomNumber) {
                    Attempts--;
                    AttemptsText.setText("Attempts - " + Attempts);
                    GuideText.setText("lower than " + UserNumber);
                    EnterText.setText("");
                } //If the number is Higher
                else {
                    Score += 20;
                    UpdateScore();
                    RandomNumber();
                    GuideText.setText("Pick Between 1 - 20");
                    EnterText.setText("");
                } //if the guess was correct

                if (Attempts == 0){
                    Attempts--;
                    GuideText.setText("Bro you lost :((((");
                } //if you are out of lives
            }
        }
        else if(Attempts == -1){
            ResetGame();
        } //Reset the game
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResetGame();
        RandomNumber();
    }
}