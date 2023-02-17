package com.renachl.speedquiz;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.renachl.speedquiz.Controllers.QuestionManager;
import com.renachl.speedquiz.Models.Question;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.Objects;

public class QuestionActivity extends AppCompatActivity {

    private ListView LI_Qst;
    private Button BT_ValiderModif;
    private EditText ED_QstInt;
    private RadioGroup RDGRP_QstEditRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Récupération des éléments
        LI_Qst = findViewById(R.id.qst_list);

        //Liste de question de la base de données
        ArrayList<String> listIntituleQst = QuestionManager.getListIntituleQst(this);

        // Rempli la liste avec les questions (avec un adapter)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listIntituleQst);
        LI_Qst.setAdapter(arrayAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        LI_Qst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showPopupEditQst(view);
//                fillValueQuestion(position);
//
//                //Valide la modification sur la base de données avec les saisies
//                BT_ValiderModif.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String reponse = ((RadioButton) findViewById(RDGRP_QstEditRep.getCheckedRadioButtonId())).getText().toString();
//                        validateEditQuestion(ED_QstInt.getText().toString(), reponse, position);
//                    }
//                });
            }
        });
    }

    /**
     * Faire appraître le popup de modification
     * @param view View de l'application
     */
    public void showPopupEditQst(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // Créer le popup
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false; // si on tappe à côté ça ne le dissimule pas
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Montre l'élement
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Récupération de la question
        Question question = QuestionManager.getOneQuestion(this, 3);

        //Affiche l'intitule de la question
        ED_QstInt = findViewById(R.id.qst_ed_initule_qst);
        ED_QstInt.setText(question.getIntitule());
    }

    public void fillValueQuestion(int position) {
        //Récupération de la question
        Question question = QuestionManager.getOneQuestion(this, position);

        //Affiche l'intitule de la question
        ED_QstInt = findViewById(R.id.qst_ed_initule_qst);
        ED_QstInt.setText(question.getIntitule());

        //Récupère la réponse et l'ajoute dans le composant raido button
        RDGRP_QstEditRep = findViewById(R.id.qst_rdGrp_rep);
        ((RadioButton) findViewById(RDGRP_QstEditRep.getCheckedRadioButtonId())).setText("1");
    }

    /**
     * Valide la modification faite sur une question
     * @param intitule Intitule de la question
     * @param reponse Reponse de la question
     * @param position Position dans la base de données de la question
     */
    private void validateEditQuestion(String intitule, String reponse, int position) {
        QuestionManager.editOneQuestion(this, position, intitule, Objects.equals(reponse, "1") ? 1 : 0);
    }
}