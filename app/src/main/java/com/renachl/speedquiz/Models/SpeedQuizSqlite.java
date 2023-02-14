package com.renachl.speedquiz.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SpeedQuizSqlite extends SQLiteOpenHelper {

    static String DB_NAME = "SpeedQuiz.db";
    static int DB_VERSION = 1;

    public static final String NOM_TABLE = "quiz";
    public static final String COL_QST = "intitule";
    public static final String COL_REP = "reponse";

    public SpeedQuizSqlite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateDataTable = "CREATE TABLE " + NOM_TABLE + " (idQuiz INTEGER PRIMARY KEY, " + COL_QST + " TEXT, " + COL_REP + " INTEGER)";
        sqLiteDatabase.execSQL(sqlCreateDataTable);

        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"Odin est petit fou\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"Je suis une patate\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"Les hirondelles sont bleues\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"L'informatique c'est vraiment trop cool\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"J'ai froid\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"Il faut que je travaille aujourd'hui\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"On est vendredi\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"Petit filou\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"Odin est petit fou\", 0)");
        sqLiteDatabase.execSQL("INSERT INTO quiz VALUES (null, \"Odin est petit fou\", 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
