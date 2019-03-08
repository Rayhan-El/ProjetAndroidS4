package fr.kounecorp.bettercallsam.game2_noname;

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
import fr.kounecorp.bettercallsam.game3_equationrandom.EquationGame;

public class ScorePopUpGame2 extends Activity {

    private int[] scores;
    private int scoreReactTime;
    private double time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_pop_up_window_game2);

        int r = getResources().getColor(R.color.RTRouge);
        int g = getResources().getColor(R.color.RTVert);
        int y = getResources().getColor(R.color.RTJaune);

        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(3);

        this.scoreReactTime = getIntent().getIntExtra("avg",0);
        this.scores = getIntent().getIntArrayExtra("scores");
        this.time = getIntent().getDoubleExtra("time",0);

        TextView score1Text = findViewById(R.id.score1Text);
        TextView score2Text = findViewById(R.id.score2Text);
        TextView score3Text = findViewById(R.id.score3Text);

        score1Text.setText(getString(R.string.textScoreNGame2,1));
        score2Text.setText(getString(R.string.textScoreNGame2,2));
        score3Text.setText(getString(R.string.textScoreNGame2,3));

        TextView score1Val = findViewById(R.id.score1Val);
        TextView score2Val = findViewById(R.id.score2Val);
        TextView score3Val = findViewById(R.id.score3Val);
        TextView tempsVal = findViewById(R.id.tempsVal);

        int col = (scores[0] <= 0) ? r : ((scores[0] == 3) ? y : g);
        score1Val.setTextColor(col);
        col = (scores[1]-scores[0] <= 0) ? r : ((scores[1]-scores[0] == 4) ? y : g);
        score2Val.setTextColor(col);
        col = (scores[2]-scores[1] <= 0) ? r : ((scores[2]-scores[1] == 5) ? y : g);
        score3Val.setTextColor(col);

        score1Val.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                            Math.abs(scores[0]),
                                                            scores[0]));
        score2Val.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                    Math.abs(scores[1]-scores[0]),
                                                scores[1]-scores[0]));
        score3Val.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                    Math.abs(scores[2]-scores[1]),
                                                scores[2]-scores[1]));

        col = (this.time <= 20) ? ((this.time <= 10) ? y : g) : r;
        tempsVal.setTextColor(col);
        tempsVal.setText(getString(R.string.tempsValeurGame2, format.format(this.time)));

        Button continuer = findViewById(R.id.btnContinuer);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game3 = new Intent(ScorePopUpGame2.this,EquationGame.class);
                game3.putExtra("avg", scoreReactTime);
                game3.putExtra("scores", scores);
                game3.putExtra("time", time);
                startActivity(game3);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Empeche l'utilisateur a faire retour, il est obligé de cliquer sur "Continuer"
        Toast.makeText(getApplicationContext(), R.string.BackInfo, Toast.LENGTH_SHORT).show();
    }

}
