package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    private Button BT_Play;
    private Button BT_Cancel;
    private EditText ET_InputP1;
    private EditText ET_InputP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Ajout de la barre de menu
        Toolbar mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        //Récupération des composants
        BT_Play = findViewById(R.id.start_bt_jouer);
        BT_Cancel = findViewById(R.id.start_bt_annuler);
        ET_InputP1 = findViewById(R.id.start_et_nom_j1);
        ET_InputP2 = findViewById(R.id.start_et_nom_j2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Ouvre l'activité de jeu et commence de jouer
        BT_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizActivity = new Intent(StartActivity.this, QuizActivity.class);
                quizActivity.putExtra("namePlayer1", ET_InputP1.getText().toString());
                quizActivity.putExtra("namePlayer2", ET_InputP2.getText().toString());
                startActivity(quizActivity);
            }
        });

        // Reset la page
        BT_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_InputP1.setText("");
                ET_InputP2.setText("");
                ET_InputP1.requestFocus();
            }
        });
    }

    //Lie le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Gère les actions du bouton de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_config:
                //Ouverture de la page de configurations
                Intent configActivity = new Intent(StartActivity.this, ConfigActivity.class);
                startActivity(configActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}