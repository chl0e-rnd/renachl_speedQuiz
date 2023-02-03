package com.renachl.speedquiz.Models;

import android.database.Cursor;

public class Question {

    private final String intitule;
    private final boolean reponse;

    public Question(Cursor cursor) {
        intitule = cursor.getString(cursor.getColumnIndexOrThrow("question"));
        reponse = cursor.getInt(cursor.getColumnIndexOrThrow("reponse")) == 1;
    }

    public String getIntitule() {
        return intitule;
    }

    public boolean getReponse() {
        return reponse;
    }

}
