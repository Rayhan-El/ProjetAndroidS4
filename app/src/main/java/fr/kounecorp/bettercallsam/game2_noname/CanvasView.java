package fr.kounecorp.bettercallsam.game2_noname;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.text.Html;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.kounecorp.bettercallsam.R;


public class CanvasView extends View {

    private static final int NBPARTIESMAX = 3;
    public static final int NBFORMESGAME1 = 3;

    private int scoreReactTime;

    private int width;
    private int height;

    private int w05;
    private int w80;

    private int h05;
    private int h80;

    private List<Forme> formes;
    private Random random;
    private int theChoosen;
    private int[] scores;
    private int numPartie;
    private TextView scoreValue;
    private TextView infoGame;
    private ChronometerMillis chrono;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        this.formes = new ArrayList<>();
        this.random = new Random();
        this.scores = new int[CanvasView.NBPARTIESMAX];
        this.numPartie = 0;
        this.theChoosen = -1;
        this.width = 0;
        this.height = 0;
    }

    public void initializeFormes(int nbAll) {
        this.initializeFormes(nbAll,nbAll,nbAll);
    }

    public void initializeFormes(int nbRects, int nbCarres, int nbCercles) {
        int tmp = this.theChoosen;
        while (this.theChoosen == tmp) {
            this.theChoosen = this.random.nextInt(3);
        }
        this.printInfoGameOnView();
        this.printScoreNOnView(this.numPartie);
        this.addNRect(nbRects, (this.theChoosen == 0));
        this.addNCarre(nbCarres, (this.theChoosen == 1));
        this.addNCercle(nbCercles,(this.theChoosen == 2));
        invalidate();
    }

    private void addNRect(int nb, boolean choosen) {
        int nbRects = 0;
        int diffMin = 40;

        while (nbRects < nb) {
            int x = this.random.nextInt((this.w80-this.w05*2))+this.w05; // 5% à 70% width
            int y = this.random.nextInt((this.h80-this.h05))+this.h05; // 5% à 75% height
            int L = this.random.nextInt((this.w05*4))+this.w05*2; // 10% à 20% width
            int l = this.random.nextInt((this.w05*4))+this.w05*2; // 10% à 20% width
            Rectangle newR = new Rectangle(x,y,L,l);
            newR.setChoosen(choosen);
            if (this.formes.isEmpty() && !(L-diffMin < l && l < L+diffMin)) {
                this.formes.add(newR);
                nbRects++;
            } else if (!(L-diffMin < l && l < L+diffMin)){
                boolean drawable = true;
                for (Forme forme : this.formes) {
                    if (forme.intersects(newR)) {
                        drawable = false;
                    }
                }
                if (drawable) {
                    this.formes.add(newR);
                    nbRects++;
                }
            }
        }
    }

    private void addNCarre(int nb, boolean choosen) {
        int nbCarres = 0;
        while (nbCarres < nb) {
            int x = this.random.nextInt((this.w80-this.w05*2))+this.w05; // 5% à 70% width
            int y = this.random.nextInt((this.h80-this.h05))+this.h05; // 5% à 75% height
            int c = this.random.nextInt((this.w05*4))+this.w05*2;  // 10% à 20% width
            Carre newC = new Carre(x,y,c);
            newC.setChoosen(choosen);
            if (this.formes.isEmpty()) {
                this.formes.add(newC);
                nbCarres++;
            } else {
                boolean drawable = true;
                for (Forme forme : this.formes) {
                    if (forme.intersects(newC)) {
                        drawable = false;
                    }
                }
                if (drawable) {
                    this.formes.add(newC);
                    nbCarres++;
                }
            }
        }
    }

    private void addNCercle(int nb, boolean choosen) {
        int nbCercles = 0;
        while (nbCercles < nb) {
            int x = this.random.nextInt((this.w80-this.w05*2))+this.w05; // 5% à 70% width
            int y = this.random.nextInt((this.h80-this.h05))+this.h05; // 5% à 75% height
            int d = this.random.nextInt((this.w05*4))+this.w05*2;  // 10% à 20% width
            Cercle newC = new Cercle(x,y,d);
            newC.setChoosen(choosen);
            if (this.formes.isEmpty()) {
                this.formes.add(newC);
                nbCercles++;
            } else {
                boolean drawable = true;
                for (Forme forme : this.formes) {
                    if (forme.intersects(newC)) {
                        drawable = false;
                    }
                }
                if (drawable) {
                    this.formes.add(newC);
                    nbCercles++;
                }
            }
        }
    }

    public void setChrono(ChronometerMillis chrono) {
        this.chrono = chrono;
        this.chrono.setTextColor(getResources().getColor(android.R.color.black));
    }

    public void setScoreReactTime(int score) {
        this.scoreReactTime = score;
    }

    public void setInfoGameView(TextView t) {
        this.infoGame = t;
        this.infoGame.setTextColor(getResources().getColor(android.R.color.black));
    }

    public String getChoosenToString() {
        switch (this.theChoosen) {
            case 0:
                return "Rectangles";
            case 1:
                return "Carrés";
            default:
                return "Cercles";
        }
    }

    public void printInfoGameOnView() {
        String info = this.getContext().getString(R.string.infoGame2,this.getChoosenToString());
        infoGame.setText(Html.fromHtml(info));
    }

    public void setScoreValueView(TextView t) {
        this.scoreValue = t;
    }

    private void printScoreNOnView(int n) {
        String score = getResources().getQuantityString(R.plurals.scoreValeurGame2,
                                                        Math.abs(scores[n]),
                                                        scores[n]);
        this.scoreValue.setText(score);
    }


    private void lunchPopUp() {
        Intent scorePopUp = new Intent(this.getContext(), ScorePopUpGame2.class);
        scorePopUp.putExtra("avg", this.scoreReactTime);
        scorePopUp.putExtra("scores", this.scores);
        scorePopUp.putExtra("time", this.chrono.getTimeElapsedInSeconds());
        this.getContext().startActivity(scorePopUp);
    }

    private void updateScoreN(int n) {
        int nbFormeChoosenTouched = 0;
        if (n > 0) {
            scores[n] = scores[n-1];
        } else {
            scores[n] = 0;
        }
        for (Forme forme : this.formes) {
            if (forme.isTouched() && forme.isChoosen()) {
                scores[n]++;
                nbFormeChoosenTouched++;
            } else if (forme.isTouched()) {
                scores[n]--;
            }
        }
        printScoreNOnView(n);
        nextGame(nbFormeChoosenTouched);

    }

    private void nextGame(int nbFormeChoosenTouched) {
        if (nbFormeChoosenTouched == this.numPartie+NBFORMESGAME1) {
            if (this.numPartie+1 < CanvasView.NBPARTIESMAX) {
                this.clearCanvas();
                this.initializeFormes(this.numPartie+NBFORMESGAME1+1);
                this.numPartie++;
            } else {
                chrono.stop();
                lunchPopUp();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();

        this.w05 = (int) (this.width*0.05);
        this.w80 = width - w05*4;

        this.h05 = (int) (this.height*0.05);
        this.h80 = height - h05*4;

        if (this.numPartie < CanvasView.NBPARTIESMAX) {
            for (Forme forme : this.formes) {
                forme.display(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP && this.numPartie < CanvasView.NBPARTIESMAX) {
            for (Forme forme : this.formes) {
                forme.testClick(x,y);
                invalidate();
            }
            updateScoreN(this.numPartie);
        }
        return true;
    }

    public void clearCanvas() {
        this.formes.clear();
        invalidate();
    }
}
