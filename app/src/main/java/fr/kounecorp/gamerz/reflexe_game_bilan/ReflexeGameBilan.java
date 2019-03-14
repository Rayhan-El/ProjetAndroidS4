package fr.kounecorp.gamerz.reflexe_game_bilan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import fr.kounecorp.gamerz.APropos;
import fr.kounecorp.gamerz.DataBase;
import fr.kounecorp.gamerz.Index;
import fr.kounecorp.gamerz.R;
import fr.kounecorp.gamerz.Statistique;

public class ReflexeGameBilan extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflexe_game_bilan);

        DataBase db = new DataBase(this);

        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(3);

        int r = getResources().getColor(R.color.RTRouge);
        int g = getResources().getColor(R.color.RTVert);
        int y = getResources().getColor(R.color.RTJaune);

        TextView scoreReactTimeView = findViewById(R.id.avgScore);

        TextView scoreFormClickView = findViewById(R.id.scoreVal);
        TextView tempsFormClickView = findViewById(R.id.tempsVal);

        TextView scoreFCView = findViewById(R.id.scoreFCVal);

        ////////////////  RECUPERATION DES SCORES PAR DES REQUETES DB  \\\\\\\\\\\\\\\\\\
        int scoreReactTime = db.getLastScore("tempsReactTime");
        int scoreFormClick = db.getLastScore("scoreFormClick");
        double timeFormClick = db.getLastTime();
        int scoreFastClicker = db.getLastScore("scoreFastClicker");
        /////////////////////////////////////////////////////////////////////////////////

        // Bilan game ReactTime
        int col = (scoreReactTime >= 450) ? r : ((scoreReactTime >= 250) ? g : y);
        scoreReactTimeView.setTextColor(col);
        scoreReactTimeView.setText(getString(R.string.moyenneValeur, scoreReactTime));

        // Bilan game Form Click
        col = (scoreFormClick <= 8) ? r : ((scoreFormClick == 12) ? y : g);
        scoreFormClickView.setTextColor(col);
        scoreFormClickView.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                Math.abs(scoreFormClick),
                scoreFormClick));

        col = (timeFormClick <= 20) ? ((timeFormClick <= 10) ? y : g) : r;
        tempsFormClickView.setTextColor(col);
        tempsFormClickView.setText(getString(R.string.tempsValeurGame2, format.format(timeFormClick)));

        // Bilan game FastClicker
        col = (scoreFastClicker <= 15) ? r : ((scoreFastClicker <= 35) ? g : y);
        scoreFCView.setTextColor(col);
        scoreFCView.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                            Math.abs(scoreFastClicker),
                                                            scoreFastClicker));

        // Bouton Accueil
        Button accueil = findViewById(R.id.btnAccueil);

        accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent index = new Intent(ReflexeGameBilan.this,Index.class);
                startActivity(index);
                finishActivity(10);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent index = new Intent(ReflexeGameBilan.this,Index.class);
        startActivity(index);
        finishActivity(10);
    }
}
