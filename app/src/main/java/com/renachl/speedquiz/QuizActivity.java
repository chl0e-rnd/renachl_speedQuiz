package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable questionRunnable = null;
    private QuestionManager questionManager = new QuestionManager();

    // Composants
    private TextView TXT_NomJoueur1;
    private TextView TXT_NomJoueur2;
    private TextView TXT_QuestionJ1;
    private TextView TXT_QuestionJ2;
    private Button BT_J1;
    private Button BT_J2;

    //Nom des joueurs
    private String nomJoueur1;
    private String nomJoueur2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent startActivity = getIntent();
        nomJoueur1 = startActivity.getStringExtra("nomJoueur1");
        nomJoueur2 = startActivity.getStringExtra("nomJoueur2");

        //Composants
        TXT_NomJoueur1 = findViewById(R.id.quiz_txt_nom_j1);
        TXT_NomJoueur2 = findViewById(R.id.quiz_txt_nom_j2);
        TXT_QuestionJ1 = findViewById(R.id.quiz_txt_qst_j1);
        TXT_QuestionJ2 = findViewById(R.id.quiz_txt_qst_j2);
        BT_J1 = findViewById(R.id.quiz_bt_j1);
        BT_J2 = findViewById(R.id.quiz_bt_j2);

        //Rempli les champs de nom de joueurs
        TXT_NomJoueur1.setText(nomJoueur1);
        TXT_NomJoueur2.setText(nomJoueur2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        startCountDownTimer();

        BT_J1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        BT_J1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * Déclenche la recherche de question/réponse et stop le callback une fois terminé
     */
    private void startQuestionIterative() {

        handler = new Handler();
        questionRunnable = new Runnable() {
            @Override
            public void run() {
                // Si c'est la dernière question alors stop le jeu
                if (!questionManager.hasNextQuestion()) {
                    System.out.println("J'ai fini");
                } else {
                    Question questionEnCours = questionManager.nextQuestion();

                    TXT_QuestionJ1.setText(questionEnCours.getIntitule());
                    TXT_QuestionJ2.setText(questionEnCours.getIntitule());

                    handler.postDelayed(this, 2000);
                }
            }
        };
        handler.postDelayed(questionRunnable, 1000);
    }

    /**
     * Lancement d'un countdown au lancement d'une partie
     */
    private void startCountDownTimer() {

        new CountDownTimer(6000, 1000) {
            // A chaque itération change le nombre du compte à rebourd
            public void onTick(long mullisUntilFinished) {
                String temps = Long.toString(mullisUntilFinished / 1000);
                TXT_QuestionJ1.setText(temps);
                TXT_QuestionJ2.setText(temps);
            }

            // Quand le compte à rebourd à fini, lancer le jeu avec les questions
            public void onFinish() {
                startQuestionIterative();
            }
        }.start();
    }
}