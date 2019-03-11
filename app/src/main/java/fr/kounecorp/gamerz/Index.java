package fr.kounecorp.gamerz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Index extends AppCompatActivity {

    private Button btnJouer;
    private EditText pseudo;

    DataBase bdd = new DataBase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        final SQLiteDatabase db = bdd.getWritableDatabase();

        this.btnJouer = findViewById(R.id.btnJouer);
        this.pseudo = findViewById(R.id.editText);

        this.btnJouer.setEnabled(false);

        pseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pseudo.getText().toString().isEmpty()) {
                    btnJouer.setEnabled(false);
                } else {
                    btnJouer.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        this.btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues joueur = new ContentValues();
                ContentValues partie = new ContentValues();

                joueur.put("Pseudo",pseudo.getText().toString());
                db.insert("Joueur",null,joueur);

                partie.put("Pseudo",pseudo.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datepartie = sdf.format(new Date());
                partie.put("DateHeure",datepartie);
                db.insert("Partie",null,partie);
                db.close();
                Intent ReactTime = new Intent(Index.this,
                        fr.kounecorp.gamerz.game1_reacttime.ReactTime.class);
                startActivity(ReactTime);

            }
        });
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
                Intent alert = new Intent(this, Statistique.class);
                startActivity(alert);
                return true;
            case R.id.propos:
                // Lancer page A propos
                Intent apropos = new Intent(this, APropos.class);
                startActivity(apropos);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
