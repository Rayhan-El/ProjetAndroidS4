package fr.kounecorp.gamerz.game3_FastClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.kounecorp.gamerz.DataBase;
import fr.kounecorp.gamerz.R;
import fr.kounecorp.gamerz.game1_reacttime.ReactTime;
import fr.kounecorp.gamerz.game2_noname.Game2;
import fr.kounecorp.gamerz.reflexe_game_bilan.ReflexeGameBilan;

public class ScorePopUpFastClicker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.score_pop_up_window);
        int r = ReactTime.ROUGE;
        int g = ReactTime.VERT;
        int y = ReactTime.JAUNE;

        DataBase db = new DataBase(this);
        int score = db.getLastScore("scoreFastClicker");

        TextView avgScore = findViewById(R.id.avgScore);


        int col = (score <= 15) ? r : ((score <= 35) ? g : y);
        avgScore.setTextColor(col);
        avgScore.setText(getResources().getQuantityString(R.plurals.scoreValeurGame2,
                Math.abs(score),
                score));

        Button continuer = findViewById(R.id.btnContinuer);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bilan = new Intent(ScorePopUpFastClicker.this,ReflexeGameBilan.class);
                startActivityForResult(bilan, 10);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Empeche l'utilisateur a faire retour, il est obligé de cliquer sur "Continuer"
        Toast.makeText(getApplicationContext(), R.string.BackInfo, Toast.LENGTH_SHORT).show();
    }

}
