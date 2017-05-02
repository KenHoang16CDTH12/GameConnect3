package app.it.hueic.gameconnect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //yellow = 0, red = 1;
    int activityPlayer = 0;
    boolean gameIsActive = true;
    //unplayed = 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    //winning position
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}
            , {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    RelativeLayout playAgainLayout;
    GridLayout gridLayout;
    TextView tvScore;
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        System.out.println(counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activityPlayer;
            if (activityPlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activityPlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activityPlayer = 0;
            }
            counter.setTranslationY(-1000f);
            counter.animate().translationYBy(1000f).rotation(360).setDuration(500);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    gameIsActive = false;
                    String winner = "Red";
                    //Someone has won
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                        tvScore.setTextColor(Color.YELLOW);
                    } else {
                        tvScore.setTextColor(Color.RED);
                    }
                    tvScore.setText(winner + " has won!");

                    playAgainLayout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) gameIsOver = false;
                    }
                    if (gameIsOver) {
                        tvScore.setText("It's a draw");
                        playAgainLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        playAgainLayout.setVisibility(View.INVISIBLE);
        activityPlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAgainLayout = (RelativeLayout) findViewById(R.id.playAgainLayout);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        tvScore = (TextView) findViewById(R.id.tvScore);
    }
}
