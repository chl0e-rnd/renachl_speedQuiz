package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
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

    // Création des variables de composants
    private TextView TXT_NomJoueur1;
    private TextView TXT_NomJoueur2;
    private TextView TXT_QuestionJ1;
    private TextView TXT_QuestionJ2;
    private TextView TXT_ScoreJ1;
    private TextView TXT_ScoreJ2;
    private Button BT_J1;
    private Button BT_J2;
    private Button BT_Rejouer;
    private Button BT_Menu;
    private ConstraintLayout CT_LAY_Bt;

    //Variable
    private int scoreJoueur1 = 0;
    private int scoreJoueur2 = 0;
    private boolean reponseQuestion = false;
    private int nombreLancement = 0;

    private final String MESSAGE_VICTOIRE = "VICTOIREEEE";
    private final String MESSAGE_DEFAITE = "Pour la prochaine fois !";
    private final String MESSAGE_EGALITE = "Egalité !!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Ajout des composants
        TXT_NomJoueur1 = findViewById(R.id.quiz_txt_nom_j1);
        TXT_NomJoueur2 = findViewById(R.id.quiz_txt_nom_j2);
        TXT_QuestionJ1 = findViewById(R.id.quiz_txt_qst_j1);
        TXT_QuestionJ2 = findViewById(R.id.quiz_txt_qst_j2);
        TXT_ScoreJ1 = findViewById(R.id.quiz_score_j1);
        TXT_ScoreJ2 = findViewById(R.id.quiz_score_j2);
        BT_J1 = findViewById(R.id.quiz_bt_j1);
        BT_J2 = findViewById(R.id.quiz_bt_j2);
        BT_Rejouer = findViewById(R.id.quiz_bt_rejouer);
        BT_Menu = findViewById(R.id.quiz_bt_menu);
        CT_LAY_Bt = findViewById(R.id.quiz_layout_bt);

        //Cache les boutons pendant la partie
        CT_LAY_Bt.setVisibility(View.INVISIBLE);

        //Rempli les champs de nom de joueurs
        Intent startActivity = getIntent();
        TXT_NomJoueur1.setText(startActivity.getStringExtra("nomJoueur1"));
        TXT_NomJoueur2.setText(startActivity.getStringExtra("nomJoueur2"));

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Désactive les boutons
        enabledButton(false);

        // Ne lance le minuteur seulement si c'est la premiere fois
        if (nombreLancement == 0) {
            startCountDownTimer();
            nombreLancement++;
        }

        BT_J1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                System.out.println("BT joueur 1 clique");

                //Active les boutons pour le jeu
                enabledButton(false);

                //Change le score du joueur
                scoreJoueur1 += reponseQuestion ? 1 : -1;
                TXT_ScoreJ1.setText(Integer.toString(scoreJoueur1));

            }
        });

        BT_J2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                System.out.println("BT joueur 2 cliqué");

                //Active les boutons pour le jeu
                enabledButton(false);

                //Change le score du joueur
                scoreJoueur2 += reponseQuestion ? 1 : -1;
                TXT_ScoreJ2.setText(Integer.toString(scoreJoueur2));
            }
        });

        BT_Rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset et relance le jeu
                resetScreenData();
                startCountDownTimer();
            }
        });

        BT_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StartActivity = new Intent(QuizActivity.this, StartActivity.class);
                startActivity(StartActivity);
            }
        });
    }

    /**
     * Reset touts les champs de l'écran ainsi que les valeurs
     */
    public void resetScreenData() {
        //Reset les scores des joueurs
        scoreJoueur1 = 0;
        scoreJoueur2 = 0;
        TXT_ScoreJ1.setText("0");
        TXT_ScoreJ2.setText("0");

        //Nouvel instance de QuestionManager
        questionManager = new QuestionManager();

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
        BT_J1.setEnabled(etat);
        BT_J2.setEnabled(etat);
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

                    //Affiche les boutons
                    CT_LAY_Bt.setVisibility(View.VISIBLE);

                    if (scoreJoueur2 == scoreJoueur1) {
                        TXT_QuestionJ1.setText(MESSAGE_EGALITE);
                        TXT_QuestionJ2.setText(MESSAGE_EGALITE);
                    } else if (scoreJoueur1 > scoreJoueur2) {
                        TXT_QuestionJ1.setText(MESSAGE_VICTOIRE);
                        TXT_QuestionJ2.setText(MESSAGE_DEFAITE);
                    } else {
                        TXT_QuestionJ1.setText(MESSAGE_DEFAITE);
                        TXT_QuestionJ2.setText(MESSAGE_VICTOIRE);
                    }

                } else {
                    //Active les boutons pour le jeu
                    enabledButton(true);

                    //Récupère la question avec son intitulé et sa réponse
                    Question questionEnCours = questionManager.nextQuestion();
                    reponseQuestion = questionEnCours.getReponse();
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