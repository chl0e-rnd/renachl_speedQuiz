package com.renachl.speedquiz.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.renachl.speedquiz.Models.Question;

import com.renachl.speedquiz.Models.Question;
import com.renachl.speedquiz.Models.SpeedQuizSqlite;
import com.renachl.speedquiz.R;

import java.util.ArrayList;

public class QuestionManager {

    private int indexQuestion = 0;
    private ArrayList<Question> listQuestion = new ArrayList<>();
    private int nombreQuestion = listQuestion.size();

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

        Cursor cursor = db.query(true, "quiz", new String[]{"idQuiz", "intitule", "reponse"}, null, null, null, null, "idQuiz", null);

        while (cursor.moveToNext()) {
            listQuestion.add(new Question(cursor));
        }

        cursor.close();
        db.close();
    }

    /**
     * Ajoute une nouvelle question à la base de données
     * @param context Context de l'appplication
     * @param intitule Intitule de la question à ajouter
     * @param reponse Réponse de la question à ajouter
     */
    public static void addNewQuestion(Context context, String intitule, String reponse) {
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Création des données à ajouter
        ContentValues contentValues = new ContentValues();
        contentValues.put("intitule", intitule);

        //Ajout de la réponse à la question
        String stringReponse = context.getString(R.string.config_rep_qst_vrai);
        contentValues.put("reponse", reponse.equals(stringReponse)  ? 1 : 0);

        //Ajoute les données à la base
        db.insert("quiz", null, contentValues);

        //Ferme la connexion à la base de données
        db.close();
    }
}
