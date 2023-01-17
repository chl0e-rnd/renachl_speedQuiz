package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button BT_Jouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        BT_Jouer = findViewById(R.id.start_bt_jouer);

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
    }

}