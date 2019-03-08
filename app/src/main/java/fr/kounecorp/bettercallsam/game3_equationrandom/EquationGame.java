package fr.kounecorp.bettercallsam.game3_equationrandom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

import fr.kounecorp.bettercallsam.R;
import fr.kounecorp.bettercallsam.game2_noname.ChronometerMillis;

public class EquationGame extends Activity {

    private final int bound = 10;

    private int rouge;
    private int vert;
    private Runnable nivSupp;
    private Runnable endGame;
    private Handler h = new Handler();
    private Random r = new Random();
    private TextView equationText;
    private TextView equationGuess;
    private TextView notifValider;
    private TextView notifTime;
    private ChronometerMillis chrono;
    private String guess;

    private int currentNiveau;
    private ExpressionArithmetique currentEquation;
    private double times[];
    private int errors;

    private NumberFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_random);

        format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(3);

        rouge = getResources().getColor(R.color.RTRouge);
        vert = getResources().getColor(R.color.RTVert);

        nivSupp = new Runnable() {
            @Override
            public void run() {
                generateEquation();
                displayEquation();
                notifValider.setText(R.string.vide);
                guess = "";
                equationGuess.setText("???");
                notifTime.setText("");
            }
        };

        endGame = new Runnable() {
            @Override
            public void run() {
                Intent scorePopUp = new Intent(EquationGame.this, ScorePopUpGame3.class);
                scorePopUp.putExtra("avg", getIntent().getIntExtra("avg",0));
                scorePopUp.putExtra("scores", getIntent().getIntArrayExtra("scores"));
                scorePopUp.putExtra("time", getIntent().getDoubleExtra("time",0));
                scorePopUp.putExtra("errors", errors);
                scorePopUp.putExtra("timesResolutions", times);
                startActivity(scorePopUp);
            }
        };

        times = new double[3];
        errors = 0;
        currentNiveau = 0;
        guess = "";

        equationText = findViewById(R.id.equationText);
        equationGuess = findViewById(R.id.equationGuess);
        notifValider = findViewById(R.id.notifValidation);
        notifTime = findViewById(R.id.notifTimeVal);
        chrono = findViewById(R.id.chronoEquation);

        equationGuess.setText("???");

        this.generateEquation();
        this.displayEquation();

    }

    private void generateEquation() {
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        currentNiveau++;
        switch (currentNiveau) {
            case 1:
                generateEquationNiv1();
                break;
            case 2:
                generateEquationNiv2();
                break;
            case 3:
                generateEquationNiv3();
                break;
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(currentEquation.evaluate());
        }
    }

    private void generateEquationNiv1() {
        // a -+ b
        currentEquation = generateAddOrSubWithNumbers();
    }

    private void generateEquationNiv2() {
        // a*/b -+ c -+ d
        currentEquation = generateAddOrSub();
        currentEquation.add(generateMultOrDivWithNumbers());
        currentEquation.add(generateAddOrSubWithNumbers());
    }

    private void generateEquationNiv3() {
        // (a*/b -+ c /* d) -+ (e -+ f)
        // Génération de (a*/b -+ c /* d)
        ExpressionArithmetique m1 = generateAddOrSub();
        ExpressionArithmetique tmp = generateMultOrDivWithNumbers();
        m1.add(tmp);
        if (tmp instanceof ExpressionDiv) {
            tmp = new ExpressionMult();
            tmp.add(generateNumbersForAddSubOrMult());
        } else {
            tmp = new ExpressionDiv();
            tmp.add(generateNumbersForDiv());
        }
        m1.add(tmp);

        currentEquation = generateAddOrSub();
        currentEquation.add(m1);
        currentEquation.add(generateAddOrSubWithNumbers());
    }

    private ExpressionArithmetique generateAddOrSub() {
        ExpressionArithmetique ret;
        if (r.nextBoolean())
            ret = new ExpressionPlus();
        else
            ret = new ExpressionMoins();
        return ret;
    }

    private ExpressionArithmetique generateAddOrSubWithNumbers() {
        ExpressionArithmetique ret = generateAddOrSub();
        ret.add(generateNumbersForAddSubOrMult());
        return ret;
    }

    private ExpressionArithmetique generateMultOrDiv() {
        ExpressionArithmetique ret;
        if (r.nextBoolean()) {
            ret = new ExpressionMult();
        }else {
            ret = new ExpressionDiv();
        }
        return ret;
    }

    private ExpressionArithmetique generateMultOrDivWithNumbers() {
        ExpressionArithmetique ret = generateMultOrDiv();
        if (ret instanceof ExpressionDiv)
            ret.add(generateNumbersForDiv());
        else
            ret.add(generateNumbersForAddSubOrMult());
        return ret;
    }

    private Entier[] generateNumbersForAddSubOrMult() {
        Entier a = new Entier(r.nextInt(bound));
        Entier b = new Entier(r.nextInt(bound));
        Entier[] ret = {a,b};
        return ret;
    }

    private Entier[] generateNumbersForDiv() {
        int a;
        a = r.nextInt(bound*5) + 1;
        int b = r.nextInt(a) + 1;
        int i = 0;
        while ((b == 1 || b == a || a % b != 0) && i < 100) {
            b = r.nextInt(a) + 1;
            i++;
        }
        if (i == 100) {
            if (r.nextBoolean()) {
                b = a;
            } else {
                b = 1;
            }
        }
        Entier A = new Entier(a);
        Entier B = new Entier(b);
        Entier[] ret = {A, B};
        return ret;
    }

    private void displayEquation() {
        if (currentEquation instanceof ExpressionPlus || currentNiveau == 1)
            equationText.setText(getString(R.string.equation, currentEquation.toString()));
        else {
            String tmp = currentEquation.toString();
            String equa = tmp.substring(0,tmp.length()-5) + "(" + tmp.substring(tmp.length()-5) + ")";
            equationText.setText(getString(R.string.equation, equa));
        }
    }

    public void updateGuess(View v) {
        Button b = (Button) v;
        // Traitement de '-'
        if (b.getText().equals("-")) {
            if (guess.isEmpty())
                guess = b.getText().toString();
            else if (!guess.equals("0") && guess.charAt(0) != '-')
                guess = b.getText().toString() + guess;
            else if (guess.charAt(0) == '-')
                guess = guess.substring(1, guess.length());
        }
        // Traitement de '0'
        if (b.getText().equals("0") && guess.length() < 4) {
            if (!guess.equals("0") && !guess.equals("-")) {
                guess += b.getText().toString();
            } else if (guess.equals("-")) {
                guess = b.getText().toString();
            }
        }
        // Traitement des chiffres de 1 à 9
        if (guess.length() < 4 && !b.getText().equals("-") && !b.getText().equals("0")) {
            if (guess.equals("0"))
                guess = b.getText().toString();
            else
                guess += b.getText().toString();
        }
        equationGuess.setText(guess);
    }

    public void supprLast(View v) {
        if (!guess.isEmpty()) {
            guess = guess.substring(0, guess.length() - 1);
            equationGuess.setText(guess);
        }
    }

    public void clearGuess(View v) {
        guess = "";
        equationGuess.setText(guess);
    }

    public void valider(View v) {
        if (!guess.equals("-") && !guess.isEmpty()) {
            int guessInNumber = Integer.parseInt(String.valueOf(guess));
            if (currentEquation.evaluate() == guessInNumber) {
                chrono.stop();
                times[currentNiveau-1] = chrono.getTimeElapsedInSeconds();
                notifTime.setTextColor(colorForNotifTime());
                notifTime.setText(getString(R.string.tempsValeurGame2, format.format(times[currentNiveau-1])));
                notifValider.setTextColor(vert);
                notifValider.setText("Correct !");
                if (currentNiveau < 3)
                    h.postDelayed(nivSupp, 500);
                else
                    h.postDelayed(endGame, 500);
            } else {
                errors++;
                notifValider.setTextColor(rouge);
                notifValider.setText("Dommage...");
            }
        } else {
            notifValider.setTextColor(rouge);
            notifValider.setText("Veuillez rentrer un chiffre...");
        }
    }

    private int colorForNotifTime() {
        int col;
        int tempsAccorde = 5;
        switch (currentNiveau) {
            case 1:
                col = (times[currentNiveau-1] < tempsAccorde) ? vert : rouge;
                break;
            case 2:
                col = (times[currentNiveau-1] < tempsAccorde+10) ? vert : rouge;
                break;
            default:
                col = (times[currentNiveau-1] < tempsAccorde+25) ? vert : rouge;
                break;
        }
        return col;
    }

}
