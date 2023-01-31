package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    private Button BT_Jouer;
    private Button BT_Annuler;
    private EditText ET_SaisiJ1;
    private EditText ET_SaisiJ2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Ajout de la barre de menu
        Toolbar mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        //Récupération des composants
        BT_Jouer = findViewById(R.id.start_bt_jouer);
        BT_Annuler = findViewById(R.id.start_bt_annuler);
        ET_SaisiJ1 = findViewById(R.id.start_et_nom_j1);
        ET_SaisiJ2 = findViewById(R.id.start_et_nom_j2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BT_Jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizActivity = new Intent(StartActivity.this, QuizActivity.class);
                System.out.println(ET_SaisiJ1.getText());
                quizActivity.putExtra("nomJoueur1", ET_SaisiJ1.getText().toString());
                quizActivity.putExtra("nomJoueur2", ET_SaisiJ2.getText().toString());
                startActivity(quizActivity);
            }
        });

        BT_Annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_SaisiJ1.setText("");
                ET_SaisiJ2.setText("");
                ET_SaisiJ1.requestFocus();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
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