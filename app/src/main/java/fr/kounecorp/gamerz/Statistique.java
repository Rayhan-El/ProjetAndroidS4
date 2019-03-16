package fr.kounecorp.gamerz;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Statistique extends AppCompatActivity {

    private TextView statReact, statFormClick, statFastClicker;
    private Spinner spinnerPseudo;
    private RadioGroup radioGroup;

    private DataBase db;

    private NumberFormat format;
    private String currentPseudo = "";
    private boolean highScore = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(3);

        db = new DataBase(this);

        spinnerPseudo = findViewById(R.id.spinnerPseudo);
        List<String> pseudos = db.getAllPseudos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.pseudos_spinnerview, R.id.pseudoInSpinner, pseudos);
        spinnerPseudo.setAdapter(adapter);

        radioGroup = findViewById(R.id.radioGroupStat);

        statReact = findViewById(R.id.statReactTime);
        statFormClick = findViewById(R.id.statFormClick);
        statFastClicker = findViewById(R.id.statFastClicker);

    }

    @Override
    protected void onStart() {
        super.onStart();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                highScore = (checkedId == R.id.radioButtonHighScore);
                updateView(currentPseudo, highScore);
            }
        });

        spinnerPseudo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPseudo = (String) parent.getItemAtPosition(position);
                updateView(currentPseudo, highScore);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateView(String pseudo, boolean highScore) {
        String reactime, formClick, fastClicker;
        if (highScore) {
            reactime = getString(R.string.reactTimeHS, db.getBestReactTime(pseudo));
            formClick = getString(R.string.formClickHS, db.getBestScore("scoreFormClick", pseudo), format.format(db.getBestTime(pseudo)));
            fastClicker = getString(R.string.fastClickerHS, db.getBestScore("scoreFastClicker",pseudo));
        } else {
            reactime = getString(R.string.reactTimeMOY, db.getScoreMoy("tempsReactTime",pseudo));
            formClick = getString(R.string.formClickMOY, db.getScoreMoy("scoreFormClick", pseudo), format.format(db.getTempsMoy(pseudo)));
            fastClicker = getString(R.string.fastClickerMOY, db.getScoreMoy("scoreFastClicker",pseudo));
        }
        statReact.setText(Html.fromHtml(reactime));
        statFormClick.setText(Html.fromHtml(formClick));
        statFastClicker.setText(Html.fromHtml(fastClicker));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statMenu:
                // Lancer page des statistiques
                Intent stat = new Intent(this, Statistique.class);
                startActivity(stat);
                finish();
                return true;
            case R.id.propos:
                // Lancer page A propos
                Intent apropos = new Intent(this, APropos.class);
                startActivity(apropos);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        Intent index = new Intent(this, Index.class);
        startActivity(index);
        finish();
    }
}
