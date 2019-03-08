package fr.kounecorp.bettercallsam.reflexe_game_bilan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import fr.kounecorp.bettercallsam.Index;
import fr.kounecorp.bettercallsam.R;

public class ReflexeGameBilan extends Activity {

    private int scoreReactTime, errorsGame3;
    private int[] scoresGame2;
    private double timeGame2;
    private double[] timesGame3;
    private int etat = 0;
    private String descEtat;

    private TextView descEtatView, scoreReactTimeView,
                     scoreFormClickView, tempsFormClickView,
                     tmpsMoyView, tmpsTotalView, erreursView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflexe_game_bilan);

        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(3);

        int r = getResources().getColor(R.color.RTRouge);
        int g = getResources().getColor(R.color.RTVert);
        int y = getResources().getColor(R.color.RTJaune);

        descEtatView = findViewById(R.id.descEtat);

        scoreReactTimeView = findViewById(R.id.avgScore);

        scoreFormClickView = findViewById(R.id.scoreVal);
        tempsFormClickView = findViewById(R.id.tempsVal);

        tmpsMoyView = findViewById(R.id.tempsMoyVal);
        tmpsTotalView = findViewById(R.id.tempsTotalVal);
        erreursView = findViewById(R.id.erreursVal);


        Intent intent = getIntent();
        scoreReactTime = intent.getIntExtra("scoreReactTime", 0);
        scoresGame2 = intent.getIntArrayExtra("scoresGame2");
        timeGame2 = intent.getDoubleExtra("timeGame2",0);
        timesGame3 = intent.getDoubleArrayExtra("timesGame3");
        errorsGame3 = intent.getIntExtra("errorsGame3",0);

        etat += (scoreReactTime < 450) ? 1 : 0;
        etat += (scoresGame2[2] >= 9) ? 1 : 0;
        etat += (timeGame2 <= 20) ? 1 : 0;
        etat += (sum(timesGame3) <= 20) ? 1 : 0;
        etat += (errorsGame3 < 3) ? 1 : 0;

        // etat <= 5
        // 0 Tout raté
        // 5 Tout réussi
        if (etat >= 4)
            descEtat = getString(R.string.etatSafe);
        else if (etat >=2)
            descEtat = getString(R.string.etatAttention);
        else
            descEtat = getString(R.string.etatDangereux);

        descEtatView.setText(Html.fromHtml(descEtat));

        // Bilan game ReactTime
        int col = (scoreReactTime >= 450) ? r : ((scoreReactTime >= 250) ? g : y);
        scoreReactTimeView.setTextColor(col);
        scoreReactTimeView.setText(getString(R.string.moyenneValeur, scoreReactTime));

        // Bilan game Form Click
        col = (scoresGame2[2] == 12) ? y : ((scoresGame2[2] >= 9) ? g : r);
        scoreFormClickView.setTextColor(col);
        scoreFormClickView.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                Math.abs(scoresGame2[2]),
                scoresGame2[2]));

        col = (this.timeGame2 <= 20) ? ((this.timeGame2 <= 10) ? y : g) : r;
        tempsFormClickView.setTextColor(col);
        tempsFormClickView.setText(getString(R.string.tempsValeurGame2, format.format(this.timeGame2)));

        // Bilan game Equation
        col = (errorsGame3 == 0) ? y : (errorsGame3 < 3) ? g : r;
        erreursView.setTextColor(col);
        erreursView.setText(String.format(Locale.FRANCE,"%d",errorsGame3));

        double tempsTotal = sum(timesGame3);
        double tempsMoy = tempsTotal/timesGame3.length;

        col = (tempsMoy < 4) ? y : ((tempsMoy < 7) ? g : r);
        tmpsMoyView.setTextColor(col);
        tmpsMoyView.setText(getString(R.string.tempsValeurGame2, format.format(tempsMoy)));

        col = (tempsTotal < 12) ? y : ((tempsTotal < 20) ? g : r);
        tmpsTotalView.setTextColor(col);
        tmpsTotalView.setText(getString(R.string.tempsValeurGame2, format.format(tempsTotal)));

        // Bouton Accueil
        Button accueil = findViewById(R.id.btnAccueil);

        accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reflexGameBilan = new Intent(ReflexeGameBilan.this,Index.class);
                reflexGameBilan.putExtra("scoreReactTime", scoreReactTime);
                reflexGameBilan.putExtra("scoresGame2", scoresGame2);
                reflexGameBilan.putExtra("timeGame2", timeGame2);
                reflexGameBilan.putExtra("errorsGame3", errorsGame3);
                reflexGameBilan.putExtra("timesGame3", timesGame3);
                startActivity(reflexGameBilan);
            }
        });

    }

    private double sum(double tab[]) {
        double som = 0;
        for (double a : tab) {
            som += a;
        }
        return som;
    }

}
