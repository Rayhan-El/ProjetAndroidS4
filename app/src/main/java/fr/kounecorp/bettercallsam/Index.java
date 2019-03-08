package fr.kounecorp.bettercallsam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                        fr.kounecorp.bettercallsam.game1_reacttime.ReactTime.class);
                startActivity(ReactTime);
            }
        });
    }
}
