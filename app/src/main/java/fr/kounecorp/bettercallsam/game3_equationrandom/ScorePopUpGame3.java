package fr.kounecorp.bettercallsam.game3_equationrandom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import fr.kounecorp.bettercallsam.R;
import fr.kounecorp.bettercallsam.reflexe_game_bilan.ReflexeGameBilan;

public class ScorePopUpGame3 extends Activity {

    private TextView tmpsMoyView, tmpsTotalView, erreursView;
    private int scoreReactTime, errorsGame3;
    private int[] scoresGame2;
    private double timeGame2;
    private double[] timesGame3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_pop_up_window_game3);

        int r = getResources().getColor(R.color.RTRouge);
        int g = getResources().getColor(R.color.RTVert);
        int y = getResources().getColor(R.color.RTJaune);

        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(3);

        tmpsMoyView = findViewById(R.id.tempsMoyVal);
        tmpsTotalView = findViewById(R.id.tempsTotalVal);
        erreursView = findViewById(R.id.erreursVal);

        scoreReactTime = getIntent().getIntExtra("avg", 0);
        scoresGame2 = getIntent().getIntArrayExtra("scores");
        timeGame2 = getIntent().getDoubleExtra("time", 0);
        timesGame3 = getIntent().getDoubleArrayExtra("timesResolutions");
        errorsGame3 = getIntent().getIntExtra("errors", 0);

        int col = (errorsGame3 == 0) ? y : (errorsGame3 < 3) ? g : r;
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

        Button continuer = findViewById(R.id.btnContinuer);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reflexGameBilan = new Intent(ScorePopUpGame3.this,ReflexeGameBilan.class);
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

    @Override
    public void onBackPressed() {
        // Empeche l'utilisateur a faire retour, il est obligé de cliquer sur "Continuer"
        Toast.makeText(getApplicationContext(), R.string.BackInfo, Toast.LENGTH_SHORT).show();
    }

}
