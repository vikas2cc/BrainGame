package com.example.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int globalSum;

    CountDownTimer countDownTimer;

    boolean isTimerActive = false;

    private GameLevel gameLevel;

    private int getRandomNumberWithExclude(int start, int end, int... exclude) {
        if (start >= end) {
            return 0;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = start + 1; i <= end; i++) {
            list.add(i);
        }
        for (int ex : exclude) {
            list.remove(new Integer(ex));
            Log.i("message in exclude loop", "done" + ex);
        }
        Log.i("before shuffle list", list.toString());
        Collections.shuffle(list);
        Log.i("after shuffle list", list.toString());
        return list.get(0);
    }

    private int setAnswers(int biggerOne, int sum) {
        Random randomGenerator = new Random();
        int correctTile = randomGenerator.nextInt(4) + 1;
        Log.i("selected Tile", "" + correctTile);
        Button answerButton1 = findViewById(R.id.answerView1);
        Button answerButton2 = findViewById(R.id.answerView2);
        Button answerButton3 = findViewById(R.id.answerView3);
        Button answerButton4 = findViewById(R.id.answerView4);
        int rangeMultiplier = 2;
        if (biggerOne <= 3) {
            rangeMultiplier = 6 - biggerOne;
        }
        int random1 = getRandomNumberWithExclude(biggerOne, rangeMultiplier * biggerOne, sum);
        int random2;
        int random3;
        if (biggerOne > 20) {
            int tuner = randomGenerator.nextInt(2);
            if (tuner == 0) {
                random3 = sum + 10;
                random2 = sum - 10;
            } else {
                random3 = sum - 10;
                random2 = sum + 10;
            }
        } else {
            random2 = getRandomNumberWithExclude(biggerOne, rangeMultiplier * biggerOne, sum, random1);
            random3 = getRandomNumberWithExclude(biggerOne, rangeMultiplier * biggerOne, sum, random1, random2);
        }

        if (correctTile == 1) {
            answerButton1.setText("" + sum);
            answerButton2.setText("" + random1);
            answerButton3.setText("" + random2);
            answerButton4.setText("" + random3);
        } else if (correctTile == 2) {
            answerButton1.setText("" + random1);
            answerButton2.setText("" + sum);
            answerButton3.setText("" + random2);
            answerButton4.setText("" + random3);
        } else if (correctTile == 3) {
            answerButton1.setText("" + random1);
            answerButton2.setText("" + random2);
            answerButton3.setText("" + sum);
            answerButton4.setText("" + random3);
        } else if (correctTile == 4) {
            answerButton1.setText("" + random1);
            answerButton2.setText("" + random2);
            answerButton3.setText("" + random3);
            answerButton4.setText("" + sum);
        }
        return correctTile;
    }


    /**
     * scope of setting different colors each time of replay
     */

    private void setTilesColor() {
        Button answerButton1 = findViewById(R.id.answerView1);
        Button answerButton2 = findViewById(R.id.answerView2);
        Button answerButton3 = findViewById(R.id.answerView3);
        Button answerButton4 = findViewById(R.id.answerView4);
        answerButton1.setBackgroundColor(Color.CYAN);
        answerButton2.setBackgroundColor(Color.YELLOW);
        answerButton3.setBackgroundColor(Color.DKGRAY);
        answerButton4.setBackgroundColor(Color.MAGENTA);
        answerButton1.setTextColor(Color.BLACK);
        answerButton2.setTextColor(Color.BLACK);
        answerButton3.setTextColor(Color.BLACK);
        answerButton4.setTextColor(Color.BLACK);
    }

    private void setInputNumbers(int num1, int num2) {
        TextView numberTextView1 = findViewById(R.id.numberTextView1);
        TextView numberTextView2 = findViewById(R.id.numberTextView2);
        numberTextView1.setText(String.valueOf(num1));
        numberTextView2.setText(String.valueOf(num2));
    }

    private void startTimer() {
        final TextView timerTextView = findViewById(R.id.timerTextView);
        if (isTimerActive) {
            countDownTimer.cancel();
        }
        {
            isTimerActive = true;
            countDownTimer = new CountDownTimer(20 * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    long secondRemaining = l / 1000 + 1;
                    timerTextView.setText("Time Left : " + secondRemaining);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("Time Out!");
                    setResult(false);
                }
            };
            countDownTimer.start();
        }

    }

    private void setResult(boolean result) {
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setVisibility(View.VISIBLE);
        if (result) {
            resultTextView.setText("Huzza! You Won");
            resultTextView.setTextColor(Color.GREEN);
        } else {
            resultTextView.setText("Bazinga! You Loss");
            resultTextView.setTextColor(Color.RED);
        }
        countDownTimer.cancel();
        isTimerActive = false;
        restartGame();
    }

    public void letSetGo() {
        /**
         * set visibility
         */

    }

    public void submitAnswer(View view) {
        Button button = findViewById(view.getId());
        String clickedNumber = (String) button.getText();
        if (Integer.valueOf(clickedNumber) == globalSum) {
            setResult(true);
        } else {
            setResult(false);
        }
    }

    private void changeLevelButtonAppearance(int level){
        Button levelButton1 = findViewById(R.id.levelButtonEasy);
        Button levelButton2 = findViewById(R.id.levelButtonMedium);
        Button levelButton3 = findViewById(R.id.levelButtonHard);
        if (level == 1) {
            levelButton1.setAlpha(1);
            levelButton2.setAlpha(0.3f);
            levelButton3.setAlpha(0.3f);
        } else if (level == 2) {
            levelButton1.setAlpha(0.3f);
            levelButton2.setAlpha(1);
            levelButton3.setAlpha(0.3f);
        } else if (level == 3) {
            levelButton1.setAlpha(0.3f);
            levelButton2.setAlpha(0.3f);
            levelButton3.setAlpha(1);
        }
    }

    public void setLevel(View view) {
        Button levelButton = findViewById(view.getId());
        gameLevel.setLevel(Integer.parseInt(levelButton.getTag().toString()));
        changeLevelButtonAppearance(gameLevel.getLevel());
        restartGame();
    }

    public void restartGame() {
        startTimer();
        int firstNumber = getRandomNumberWithExclude(gameLevel.getStart(), gameLevel.getEnd());
        int secondNumber = getRandomNumberWithExclude(gameLevel.getStart(), gameLevel.getEnd(), firstNumber);
        int biggerOne = firstNumber > secondNumber ? firstNumber : secondNumber;
        globalSum = firstNumber + secondNumber;
        setInputNumbers(firstNumber, secondNumber);
        setAnswers(biggerOne, globalSum);
    }

    public void startGame(View view) {
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setVisibility(View.INVISIBLE);
        setTilesColor();
        restartGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLevel = new GameLevel(1);
        changeLevelButtonAppearance(1);
        startGame(null);
    }
}
