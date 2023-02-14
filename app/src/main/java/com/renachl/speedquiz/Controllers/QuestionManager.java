package com.renachl.speedquiz.Controllers;


import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.renachl.speedquiz.ConfigActivity;
import com.renachl.speedquiz.Models.Question;

import com.renachl.speedquiz.Models.SpeedQuizSqlite;
import com.renachl.speedquiz.R;

import java.util.ArrayList;

public class QuestionManager {

    private int indexQuestion = 0;
    private ArrayList<Question> listQuestion = new ArrayList<>();
    private int nombreQuestion = 0;

    /**
     * Constructeur de Question manager
     *
     * @param context Context de l'application
     */
    public QuestionManager(Context context) {
        initQuestionList(context);
        initNombreQuestion(context);
    }

    /**
     * Donne une question de la liste
     *
     * @return La question
     */
    public Question nextQuestion() {
        Question currentQuestion = listQuestion.get(indexQuestion);
        indexQuestion++;
        return currentQuestion;
    }

    /**
     * Détermine si une question en fonction de son index n'est pas la dernière de la liste
     *
     * @return True si la question n'est pas la dernière, false sinon
     */
    public boolean hasNextQuestion() {
        return indexQuestion < nombreQuestion - 1;
    }

    /**
     * Initialose le nombre de question choisie
     *
     * @param context Contexte de l'application
     */
    private void initNombreQuestion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.renachl.speedquiz", MODE_PRIVATE);
        nombreQuestion = prefs.getInt("nbrQst", ConfigActivity.QST_DELAI);
    }

    /**
     * Charge une sllite de question depuis la DB
     *
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
     *
     * @param context  Context de l'appplication
     * @param intitule Intitule de la question à ajouter
     * @param reponse  Réponse de la question à ajouter
     */
    public static void addNewQuestion(Context context, String intitule, String reponse) {
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Création des données à ajouter
        ContentValues contentValues = new ContentValues();
        contentValues.put("intitule", intitule);

        //Ajout de la réponse à la question
        String stringReponse = context.getString(R.string.config_rep_qst_vrai);
        contentValues.put("reponse", reponse.equals(stringReponse) ? 1 : 0);

        //Ajoute les données à la base
        db.insert("quiz", null, contentValues);

        //Ferme la connexion à la base de données
        db.close();
    }


    /**
     * Retourne le nombre de questions qui se trouve dans la table
     *
     * @param context Contexte de l'application
     * @return Nombre de question
     */
    public static long getNombreQuestion(Context context) {
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        long nombreQuestion = DatabaseUtils.queryNumEntries(db, SpeedQuizSqlite.NOM_TABLE);
        db.close();
        return nombreQuestion;
    }

    public static void supprimer(Context context) {
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase();
//        db.execSQL("DELETE FROM \"quiz\"");

        db.execSQL("INSERT INTO quiz VALUES (null, \"Odin est petit fou\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Je suis une patate\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Les hirondelles sont bleues\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"L'informatique c'est vraiment trop cool\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"J'ai froid\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Il faut que je travaille aujourd'hui\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"On est vendredi\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Petit filou\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Odin est petit fou\", 0)");
    }
}
