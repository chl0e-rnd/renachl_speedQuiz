package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

public class QuizActivity extends AppCompatActivity {

    Handler handler;
    Runnable questionRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    /**
     * Déclenche la recherche de question/réponse et stop le callback une fois terminé
     */
//    private void startQuestionIterative() {
//        handler = new Handler();
//        questionRunnable = new Runnable() {
//            @Override
//            public void run() {
//                //Détecter si c'est la dernière question
//                if() {
//                    //Si c'est la dernière, alors il fair ce qu'il doit faire si c'est la dernière
//                } else {
//                    //Si non il continue de faire ce qu'il doit pour les questions
//                    handler.postDelayed(this, 2000);
//                }
//            }
//        };
//
//        handler.postDelayed(questionRunnable, 1000);
//
//    }
//
//    /**
//     * Lancement d'un countdown au lancement d'une partie
//     */
//    private void startCountDownTimer() {
//        new CountDownTimer(6000, 1000) {
//            //Action à chaque itération
//            public void onTick(long mullisUntilFinished) {
//                //On ajoute le temps dans le champs de question
//            }
//            //ce qu'on lance quand on finiz
//            public void onFinish() {
//                // on ajoute go au champs de question et on lance
//                startQuestionIterative();
//            }
//        }.start();
//    }
}