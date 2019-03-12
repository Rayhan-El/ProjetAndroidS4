package fr.kounecorp.gamerz.game2_noname;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import fr.kounecorp.gamerz.DataBase;
import fr.kounecorp.gamerz.R;
import fr.kounecorp.gamerz.game3_equationrandom.EquationGame;

public class ScorePopUpGame2 extends Activity {

    private int scores;
    private double time;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_pop_up_window_game2);


        this.db = new DataBase(this);
        int r = getResources().getColor(R.color.RTRouge);
        int g = getResources().getColor(R.color.RTVert);
        int y = getResources().getColor(R.color.RTJaune);

        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(3);

        this.scores = this.db.getLastScore("scoreFormClick");
        this.time = this.db.getLastTime();


        TextView score1Val = findViewById(R.id.score1Val);

        TextView tempsVal = findViewById(R.id.tempsVal);

        int col = (scores <= 8) ? r : ((scores == 12) ? y : g);
        score1Val.setTextColor(col);


        score1Val.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                            Math.abs(scores),
                                                            scores));


        col = (this.time <= 20) ? ((this.time <= 10) ? y : g) : r;
        tempsVal.setTextColor(col);
        tempsVal.setText(getString(R.string.tempsValeurGame2, format.format(this.time)));

        Button continuer = findViewById(R.id.btnContinuer);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game3 = new Intent(ScorePopUpGame2.this,EquationGame.class);
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
