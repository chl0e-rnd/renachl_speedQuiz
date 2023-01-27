package com.renachl.speedquiz;

import java.util.ArrayList;

public class QuestionManager {

    private ArrayList<Question> listeQuestion = new ArrayList<>();
    private int indexQuestion = 0;

    QuestionManager() {
        super();
        listeQuestion.add(new Question("Odin est petit fou", true));
        listeQuestion.add(new Question("Je suis une patate", true));
        listeQuestion.add(new Question("La vie est belle", true));
        listeQuestion.add(new Question("Rayan triche trop", true));
        listeQuestion.add(new Question("L'android c'est trop bien", true));
        listeQuestion.add(new Question("On est vendredi", true));
        listeQuestion.add(new Question("J'ai froid", true));
        listeQuestion.add(new Question("Oui oui baguette", true));
    }

    /**
     * Donne une question de la liste
     * @return La question
     */
    public Question nextQuestion() {
        Question currentQuestion = listeQuestion.get(indexQuestion);
        indexQuestion++;
        return currentQuestion;
    }

    /**
     * Détermine si une question en fonction de son index n'est pas la dernière de la liste
     * @return True si la question n'est pas la dernière, false sinon
     */
    public boolean hasNextQuestion() {
        return indexQuestion < listeQuestion.size();
    }
}
