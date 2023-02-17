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

    private ArrayList<Question> listQuestion = new ArrayList<>();
    private int nbrQstChoose = 0;
    private int nbrQstAsk = 0;

    /**
     * Constructeur de QuestionManager
     *
     * @param context Context de l'application
     */
    public QuestionManager(Context context) {
        initQuestionList(context);
        initNbrQuestion(context);
    }

    /**
     * Donne une question de la liste
     *
     * @return La question
     */
    public Question nextQuestion() {
        Question currentQuestion = listQuestion.get(getRandomNumber(0, listQuestion.size() - 1));
        nbrQstAsk++;
        listQuestion.remove(currentQuestion);
        return currentQuestion;
    }

    /**
     * Détermine si une question en fonction de son index n'est pas la dernière de la liste
     * @return True si la question n'est pas la dernière, false sinon
     */
    public boolean hasNextQuestion() {
        return nbrQstAsk < nbrQstChoose;
    }

    /**
     * Initialose le nombre de question choisie
     * @param context Contexte de l'application
     */
    private void initNbrQuestion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.renachl.speedquiz", MODE_PRIVATE);
        nbrQstChoose = prefs.getInt("nbrQst", ConfigActivity.QST_DELAY);
    }

    /**
     * Charge une liste de question depuis la DB
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

    /**
     * Obtient un nombre aléatoire entre deux bornes
     * @param min Borne min
     * @param max Borne max
     * @return un nombre aléatoire
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Retourne une liste de tous les intitulés de toutes les questions de la liste
     *
     * @param context Contexte de l'application
     * @return la liste de l'intitule de question
     */
    public static ArrayList<String> getListIntituleQst(Context context) {
        ArrayList<String> listIntituleQuestion = new ArrayList<>();

        //Création du lien avec la base de données
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase(); // chercher la base de données et la rendre en lecture

        //Récupération des données depuis la base et ajout dans la liste
        Cursor cursor = db.query(true, "quiz", new String[]{"idQuiz", "intitule", "reponse"}, null, null, null, null, "idQuiz", null);
        while (cursor.moveToNext()) {
            listIntituleQuestion.add(new Question(cursor).getIntitule());
        }

        //Fermeture de la base
        cursor.close();
        db.close();

        return listIntituleQuestion;
    }

    /**
     * Modifie une données de la base de données
     *
     * @param position Position de la donnée à modifier
     * @param intitule Nouvel intitulé de la question
     * @param reponse  Nouvelle réponse de la question
     */
    public static void editOneQuestion(Context context, int position, String intitule, int reponse) {
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sqlCreateDataTable = "UPDATE " + SpeedQuizSqlite.NOM_TABLE +
                " SET " + SpeedQuizSqlite.COL_QST + " = '" + intitule + "', "
                + SpeedQuizSqlite.COL_REP + "= " + reponse +
                " WHERE " + SpeedQuizSqlite.COL_ID + "= " + position + ";";
        db.execSQL(sqlCreateDataTable);
    }

    /**
     * Retourne une question depuis la base de données
     * @param context Contetxe de l'application
     * @param position Position
     * @return question en fonction de la position
     */
    public static Question getOneQuestion(Context context, int position) {

        //Création du lien avec la base de données
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase(); // chercher la base de données et la rendre en lecture

        //Récupération des données depuis la base et ajout dans la liste
        Cursor cursor = db.query(true, "quiz", new String[]{"idQuiz", "intitule", "reponse"}, null, null, null, null, "idQuiz", null);

        //Obtient la question à l'index voulu
        cursor.moveToPosition(position);
        Question question = new Question(cursor);

        //Fermeture de la base
        cursor.close();
        db.close();

        return question;
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
