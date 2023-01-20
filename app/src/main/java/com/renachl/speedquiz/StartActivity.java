package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    private Button BT_Jouer;
    private Button BT_Annuler;
    private EditText ET_SaisiJ1;
    private EditText ET_SaisiJ2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
                startActivity(quizActivity);
            }
        });

        BT_Annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_SaisiJ1.setText("");
                ET_SaisiJ2.setText("");
            }
        });

    }

}