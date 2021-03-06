package fr.kounecorp.gamerz.game1_reacttime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.kounecorp.gamerz.DataBase;
import fr.kounecorp.gamerz.R;
import fr.kounecorp.gamerz.game2_noname.Game2;

public class ScorePopUp extends Activity {

    private int avg;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.score_pop_up_window);
        int r = ReactTime.ROUGE;
        int g = ReactTime.VERT;
        int y = ReactTime.JAUNE;

        this.db = new DataBase(this);
        this.avg = db.getLastScore("tempsReactTime");

        TextView avgScore = findViewById(R.id.avgScore);


        int col = (avg >= 450) ? r : ((avg >= 250) ? g : y);
        avgScore.setTextColor(col);
        avgScore.setText(getString(R.string.moyenneValeur, avg));

        Button continuer = findViewById(R.id.btnContinuer);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game2 = new Intent(ScorePopUp.this,Game2.class);
                startActivityForResult(game2, 10);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Empeche l'utilisateur a faire retour, il est obligé de cliquer sur "Continuer"
        Toast.makeText(getApplicationContext(), R.string.BackInfo, Toast.LENGTH_SHORT).show();
    }

}
