package fr.kounecorp.gamerz.game2_noname;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.kounecorp.gamerz.R;

public class Game2 extends Activity {

    private CanvasView myCanvas;
    private ChronometerMillis chrono;
    private TextView infoGame;
    private TextView textScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        myCanvas = findViewById(R.id.canvasView);
        chrono = findViewById(R.id.chronometer);
        infoGame = findViewById(R.id.game2Info);
        textScore = findViewById(R.id.textScore);
        TextView valScore = findViewById(R.id.valScore);
        valScore.setTextColor(getResources().getColor(R.color.defaultGris));

        myCanvas.setGameActivity(this);
        myCanvas.setScoreValueView(valScore);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                textScore.setTextColor(getResources().getColor(R.color.defaultGris));
                myCanvas.setInfoGameView(infoGame);
                myCanvas.setChrono(chrono);
                myCanvas.initializeFormes(CanvasView.NBFORMESGAME1);
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 500);
    }

    @Override
    public void onBackPressed() {
        // Empeche l'utilisateur a faire retour, il est obligé de cliquer sur "Continuer"
        Toast.makeText(getApplicationContext(), R.string.BackOnGame, Toast.LENGTH_SHORT).show();
    }
}
