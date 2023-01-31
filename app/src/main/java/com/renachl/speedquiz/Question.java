package com.renachl.speedquiz;

public class Question {

    private final String intitule;
    private final boolean reponse;

    Question(String intitule, boolean reponse) {
        this.intitule = intitule;
        this.reponse = reponse;
    }

    public String getIntitule() {
        return intitule;
    }

    public boolean getReponse() {
        return reponse;
    }

}
