package fr.kounecorp.gamerz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Index extends AppCompatActivity {

    private Button btnJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        this.btnJouer = findViewById(R.id.btnJouer);

        this.btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                /*
                Intent alert = new Intent(this, Statistique.class);
                alert.putExtra("noms", noms);
                startActivity(alert);
                return true;
                */
            case R.id.propos:
                // Lancer page A propos
                /*
                Intent apropos = new Intent(this, APropos.class);
                apropos.putExtra("noms", noms);
                startActivity(apropos);
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
