package fr.kounecorp.gamerz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class APropos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_propos);
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
}
