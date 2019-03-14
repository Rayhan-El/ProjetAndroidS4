package fr.kounecorp.gamerz.game3_FastClick;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import fr.kounecorp.gamerz.DataBase;
import fr.kounecorp.gamerz.R;

public class FastClicker extends AppCompatActivity {

    private DataBase db;

    Button bLeft, bRight;
    TextView cdStartText, cdInGameText, afficheurScore;

    int nbButtonClicked;
    Boolean partieFinie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastclicker);

        this.db = new DataBase(this);

        bLeft = findViewById(R.id.buttonLeft);
        bRight = findViewById(R.id.buttonRight);
        cdStartText = findViewById(R.id.cdStartFastClicker);
        cdInGameText = findViewById(R.id.cdInGameFastClicker);
        afficheurScore = findViewById(R.id.scoreFastClicker);

        bLeft.setOnClickListener(new ecouteur(bRight));
        bRight.setOnClickListener(new ecouteur(bLeft));


        CountDownTimer cd3 = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondUntilFinished = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                cdStartText.setText(String.format(Locale.FRANCE, "%02d",secondUntilFinished));
            }

            @Override
            public void onFinish() {
                cdStartText.setText(getString(R.string.go));
                initialiser();
            }
        };
        cd3.start();


    }

    public void initialiser() {
        bLeft.setEnabled(true);
        bRight.setEnabled(true);
        partieFinie = false;
        nbButtonClicked = 0;

        CountDownTimer cd30 = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondUntilFinished = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                cdInGameText.setText(String.format(Locale.FRANCE, "%02d",secondUntilFinished));
            }

            @Override
            public void onFinish() {
                partieFinie = true;
                cdStartText.setText("");
                cdInGameText.setText(getString(R.string.fastClickEndGame));
                lunchPopUp();
            }
        };
        cd30.start();

    }

    public void lunchPopUp() {
        String pseudo = db.getLastPseudo();
        db.addScoreFastClickerToDatabaseWherePseudo(pseudo,nbButtonClicked);
        Intent scorePopUp = new Intent(this, ScorePopUpFastClicker.class);
        startActivityForResult(scorePopUp, 10);
    }

    public class ecouteur implements View.OnClickListener {

        Button other;

        public ecouteur(Button other) {
            this.other = other;
        }

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            if (!partieFinie) {
                b.setEnabled(false);
                other.setEnabled(true);
                nbButtonClicked++;
                afficheurScore.setText(String.format(Locale.FRANCE, "%02d",nbButtonClicked));
            } else {
                b.setEnabled(false);
                other.setEnabled(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Empeche l'utilisateur a faire retour, il est obligé de cliquer sur "Continuer"
        Toast.makeText(getApplicationContext(), R.string.BackOnGame, Toast.LENGTH_SHORT).show();
    }





}
