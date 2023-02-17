package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renachl.speedquiz.Controllers.QuestionManager;
import com.renachl.speedquiz.Models.Question;

public class QuizActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable questionRunnable = null;
    private QuestionManager questionManager;

    // Création des variables de composants
    private TextView TXT_NamePlayer1;
    private TextView TXT_NamePlayer2;
    private TextView TXT_QuestionP1;
    private TextView TXT_QuestionP2;
    private TextView TXT_ScoreP1;
    private TextView TXT_ScoreP2;
    private Button BT_P1;
    private Button BT_P2;
    private Button BT_Replay;
    private Button BT_Menu;
    private ConstraintLayout CT_LAY_Bt;

    //Variable
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private boolean reponseQuestion = false;
    private int numberLaunch = 0;

    //Variable de configuration
    private final String MESSAGE_VICTOIRE = "VICTOIREEEE";
    private final String MESSAGE_DEFAITE = "Pour la prochaine fois !";
    private final String MESSAGE_EGALITE = "Egalité !!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Ajout des composants
        TXT_NamePlayer1 = findViewById(R.id.quiz_txt_nom_j1);
        TXT_NamePlayer2 = findViewById(R.id.quiz_txt_nom_j2);
        TXT_QuestionP1 = findViewById(R.id.quiz_txt_qst_j1);
        TXT_QuestionP2 = findViewById(R.id.quiz_txt_qst_j2);
        TXT_ScoreP1 = findViewById(R.id.quiz_score_j1);
        TXT_ScoreP2 = findViewById(R.id.quiz_score_j2);
        BT_P1 = findViewById(R.id.quiz_bt_j1);
        BT_P2 = findViewById(R.id.quiz_bt_j2);
        BT_Replay = findViewById(R.id.quiz_bt_rejouer);
        BT_Menu = findViewById(R.id.quiz_bt_menu);
        CT_LAY_Bt = findViewById(R.id.quiz_layout_bt);

        //Cache les boutons pendant la partie
        CT_LAY_Bt.setVisibility(View.INVISIBLE);

        //Rempli les champs de nom de joueurs
        Intent startActivity = getIntent();
        TXT_NamePlayer1.setText(startActivity.getStringExtra("namePlayer1"));
        TXT_NamePlayer2.setText(startActivity.getStringExtra("namePlayer2"));

        questionManager = new QuestionManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Désactive les boutons
        enabledButton(false);

        // Ne lance le minuteur seulement si c'est la premiere fois
        if (numberLaunch == 0) {
            startCountDownTimer();
            numberLaunch++;
        }

        BT_P1.setOnClickListener(view -> {
            //Active les boutons pour le jeu
            enabledButton(false);

            //Change le score du joueur
            scorePlayer1 += reponseQuestion ? 1 : -1;
            TXT_ScoreP1.setText(scorePlayer1);
        });

        BT_P2.setOnClickListener(view -> {
            //Active les boutons pour le jeu
            enabledButton(false);

            //Change le score du joueur
            scorePlayer2 += reponseQuestion ? 1 : -1;
            TXT_ScoreP2.setText(scorePlayer2);
        });

        BT_Replay.setOnClickListener(view -> {
            // Reset et relance le jeu
            resetScreenData();
            startCountDownTimer();
        });

        BT_Menu.setOnClickListener(view -> {
            Intent StartActivity = new Intent(QuizActivity.this, StartActivity.class);
            startActivity(StartActivity);
        });
    }

    /**
     * Reset touts les champs de l'écran ainsi que les valeurs
     */
    public void resetScreenData() {
        //Reset les scores des joueurs
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        TXT_ScoreP1.setText("0");
        TXT_ScoreP2.setText("0");

        //Nouvel instance de QuestionManager
        questionManager = new QuestionManager(this);

        //Change l'état des boutons
        enabledButton(false);

        //Change la visibilité des boutons centraux
        CT_LAY_Bt.setVisibility(View.INVISIBLE);
    }

    /**
     * Active ou désactive les boutons de jeu en fonction du paramètre
     * @param etat Etat à donner aux boutons
     */
    public void enabledButton(boolean etat) {
        BT_P1.setEnabled(etat);
        BT_P2.setEnabled(etat);
    }

    /**
     * Déclenche la recherche de question/réponse et stop le callback une fois terminé
     */
    private void startQuestionIterative() {
        SharedPreferences prefs = getSharedPreferences("com.renachl.speedquiz", MODE_PRIVATE);

        handler = new Handler();
        questionRunnable = new Runnable() {
            @Override
            public void run() {
                // Si c'est la dernière question alors stop le jeu
                if (!questionManager.hasNextQuestion()) {

                    //Affiche les boutons
                    CT_LAY_Bt.setVisibility(View.VISIBLE);

                    //Désactiver les boutons
                    enabledButton(false);

                    //Affiche les messages de fin
                    if (scorePlayer2 == scorePlayer1) {
                        TXT_QuestionP1.setText(MESSAGE_EGALITE);
                        TXT_QuestionP2.setText(MESSAGE_EGALITE);
                    } else if (scorePlayer1 > scorePlayer2) {
                        TXT_QuestionP1.setText(MESSAGE_VICTOIRE);
                        TXT_QuestionP2.setText(MESSAGE_DEFAITE);
                    } else {
                        TXT_QuestionP1.setText(MESSAGE_DEFAITE);
                        TXT_QuestionP2.setText(MESSAGE_VICTOIRE);
                    }

                } else {
                    //Active les boutons pour le jeu
                    enabledButton(true);

                    //Récupère la question avec son intitulé et sa réponse
                    Question currentQuestion = questionManager.nextQuestion();
                    reponseQuestion = currentQuestion.getReponse();
                    TXT_QuestionP1.setText(currentQuestion.getIntitule());
                    TXT_QuestionP2.setText(currentQuestion.getIntitule());

                    handler.postDelayed(this, prefs.getInt("qstDelai", ConfigActivity.QST_DELAY));
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
                String time = Long.toString(mullisUntilFinished / 1000);
                TXT_QuestionP1.setText(time);
                TXT_QuestionP2.setText(time);
            }

            // Quand le compte à rebourd à fini, lancer le jeu avec les questions
            public void onFinish() {
                startQuestionIterative();
            }
        }.start();
    }
}