package com.renachl.speedquiz.Controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.renachl.speedquiz.Models.Question;

import com.renachl.speedquiz.Models.Question;
import com.renachl.speedquiz.Models.SpeedQuizSqlite;

import java.util.ArrayList;

public class QuestionManager {

    private int indexQuestion = 0;
    private ArrayList<Question> listQuestion = new ArrayList<>();


    /**
     * Constructeur de Question manager
     * @param context Context de l'application
     */
    public QuestionManager(Context context) {
        initQuestionList(context);
    }

    /**
     * Donne une question de la liste
     * @return La question
     */
    public Question nextQuestion() {
        Question currentQuestion = listQuestion.get(indexQuestion);
        indexQuestion++;
        return currentQuestion;
    }

    /**
     * Détermine si une question en fonction de son index n'est pas la dernière de la liste
     * @return True si la question n'est pas la dernière, false sinon
     */
    public boolean hasNextQuestion() {
        return indexQuestion < listQuestion.size();
    }

    /**
     * Charge une sllite de question depuis la DB
     * @param context Le contexte de l'application pour passer la query
     */
    private void initQuestionList(Context context) {
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase(); // chercher la base de données et la rendre en lecture

        Cursor cursor = db.query(true, "quiz", new String[]{"idQuiz", "questions", "reponse"}, null, null, null, null, "idQuiz", null);

        while (cursor.moveToNext()) {
            listQuestion.add(new Question(cursor));
        }

        cursor.close();
        db.close();

    }
}
