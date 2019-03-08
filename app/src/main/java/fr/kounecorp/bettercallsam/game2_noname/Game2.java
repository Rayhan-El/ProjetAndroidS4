package fr.kounecorp.bettercallsam.game2_noname;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import fr.kounecorp.bettercallsam.R;

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

        myCanvas.setScoreValueView(valScore);
        myCanvas.setScoreReactTime(getIntent().getIntExtra("avg",0));

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

    public void clearCanvas(View v) {
        myCanvas.clearCanvas();
    }

    public void newFormes(View v) {
        myCanvas.initializeFormes(1);
        myCanvas.invalidate();
    }
}
