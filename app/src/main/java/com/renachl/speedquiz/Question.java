package com.renachl.speedquiz;

public class Question {

    private String intitule;
    private boolean reponse;

    Question(String intitule, boolean reponse) {
        this.intitule = intitule;
        this.reponse = reponse;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public boolean isReponse() {
        return reponse;
    }

    public void setReponse(boolean reponse) {
        this.reponse = reponse;
    }
}
