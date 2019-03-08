package fr.kounecorp.bettercallsam.game1_reacttime;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import fr.kounecorp.bettercallsam.R;

public class ReactTime extends AppCompatActivity {

    public final static int ROUGE = 0xFFCE2636;
    public final static int VERT  = 0xFF309C47;
    public final static int BLEU  = 0xFF2B87D1;
    public final static int JAUNE = 0xFFFFD700;

    private final static int MAXTRIES = 5;

    private Button b;
    private TextView triesText;
    private TextView avgText;
    private TextView infoText;
    private int nbClick;
    private int somTime;
    private int avg;
    private int tries;
    private long reactionTime;
    private CountDownTimer cd;
    private Boolean canClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_react_time);

        this.infoText = findViewById(R.id.info);
        this.avgText = findViewById(R.id.avg);
        this.triesText = findViewById(R.id.tries);
        this.b = findViewById(R.id.button);

        this.initialiserViews();

        this.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traiterClick();
            }
        });
    }

    private void traiterClick() {
        switch (this.nbClick % 2) {
            case 0:
                this.firstClick();
                this.countDown();
                break;
            case 1:
                if (this.canClick){
                    this.clickedOnGreen();
                } else {
                    this.clickedTooSoon();
                }
                break;
        }
        if (this.tries == ReactTime.MAXTRIES) {
            this.triesText.setTextColor(JAUNE);
            lunchPopUp();
        }
        this.nbClick++;
    }

    private void initialiserViews() {
        //Initialisation du bouton
        this.nbClick = 0;
        this.b.setBackgroundColor(BLEU);
        this.b.setText(R.string.btnInit);

        //Initialisation des textes
        this.somTime = 0;
        this.avg = 0;
        this.tries = 0;
        this.infoText.setText(R.string.infoInit);
        this.avgText.setText(getString(R.string.moyenneValeur, this.avg));
        this.triesText.setText(getString(R.string.essaisValeur, this.tries, ReactTime.MAXTRIES));
    }

    private void firstClick() {
        this.canClick = false;
        this.b.setBackgroundColor(ROUGE);
        this.b.setText(R.string.btnRed);
        this.infoText.setText(R.string.vide);
    }

    private void clickedOnGreen() {
        this.reactionTime = System.nanoTime() - reactionTime;
        this.reactionTime = TimeUnit.MILLISECONDS.convert(reactionTime, TimeUnit.NANOSECONDS);

        this.updateAvgTries();

        this.b.setBackgroundColor(BLEU);
        this.b.setText(getString(R.string.moyenneValeur,this.reactionTime));
        this.infoText.setText(R.string.infoEnd);
    }

    private void clickedTooSoon() {
        this.b.setBackgroundColor(BLEU);
        this.b.setText(R.string.btnTooSoon);
        this.infoText.setText(R.string.infoTooSoon);
        this.cd.cancel();

    }

    private void turnGreen() {
        this.b.setBackgroundColor(VERT);
        this.reactionTime = System.nanoTime();
        this.b.setText(R.string.btnGreen);
        this.infoText.setText(R.string.vide);
    }

    private void updateAvgTries() {
        this.tries++;
        this.somTime += this.reactionTime;
        this.avg = this.somTime/this.tries;
        this.avgText.setText(getString(R.string.moyenneValeur, this.avg));
        this.triesText.setText(getString(R.string.essaisValeur, this.tries, ReactTime.MAXTRIES));
    }

    private void lunchPopUp() {
        Intent scorePopUp = new Intent(ReactTime.this,ScorePopUp.class);
        scorePopUp.putExtra("avg", avg);
        startActivity(scorePopUp);
    }

    private void countDown() {
        Random rand = new Random();
        int delay = rand.nextInt(4) + 2; // delay est compris entre 2 et 6 secondes
        this.cd = new CountDownTimer(delay*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                canClick = true;
                turnGreen();
            }
        };
        this.cd.start();
    }

}
