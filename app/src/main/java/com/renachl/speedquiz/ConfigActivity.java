package com.renachl.speedquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.renachl.speedquiz.Controllers.QuestionManager;
import com.renachl.speedquiz.Models.Question;


public class ConfigActivity extends AppCompatActivity {

    private Slider SL_Delais;
    private Button BT_TestDelais;
    private Button BT_ValiderNvQst;
    private EditText ED_IntituleQst;
    private RadioGroup RDGRP_RepQst;

    private Handler handler;
    private Runnable questionRunnable = null;

    public static final int QST_DELAI = 2000;
    public static int nombreQuestion = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config);

        SL_Delais = findViewById(R.id.config_slide_delais);
        BT_TestDelais = findViewById(R.id.config_bt_tst);

        ED_IntituleQst = findViewById(R.id.config_ed_initule_qst);
        RDGRP_RepQst = findViewById(R.id.config_rdGrp_rep);
        BT_ValiderNvQst = findViewById(R.id.config_bt_qst_valider);

        //Change la valeur du slider pour la mettre à celle actuelle
        SharedPreferences prefs = getSharedPreferences("com.renachl.speedquiz", MODE_PRIVATE);
        float valueDelais = (float) (prefs.getInt("qstDelai", QST_DELAI) / 1000.0);
        SL_Delais.setValue(valueDelais);
        displaySliderValue(valueDelais);
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("com.renachl.speedquiz", MODE_PRIVATE);
        handler = new Handler();

        questionRunnable = new Runnable() {
            int isVisible = 0;
            @Override
            public void run() {
                BT_TestDelais.setVisibility(isVisible);
                isVisible = isVisible == 4 ? 0 : 4;
                handler.postDelayed(this, prefs.getInt("qstDelai", ConfigActivity.QST_DELAI));
            }
        };
        handler.postDelayed(questionRunnable,1000);

        //Change la valeur du temps dans les config de l'application au mouvement du slider
        SL_Delais.addOnChangeListener((slider, value, fromUser) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("qstDelai", (int) (SL_Delais.getValue() * 1000.0));
            editor.apply();
        });


        ED_IntituleQst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BT_ValiderNvQst.setEnabled(!ED_IntituleQst.getText().toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Ajoute la nouvelle question à la base de données, vide le champs désactive le bt
        BT_ValiderNvQst.setOnClickListener(view -> {
            String reponse = ((RadioButton) findViewById(RDGRP_RepQst.getCheckedRadioButtonId())).getText().toString();
            addQstPerso(ED_IntituleQst.getText().toString(), reponse);
            ED_IntituleQst.setText("");
            ED_IntituleQst.requestFocus();
        });

    }

    /**
     * Affiche la valeur du slider dans la text view
     *
     * @param value Valeur à afficher
     */
    private void displaySliderValue(float value) {
        String txtDelais = SL_Delais.getValue() + " s";
    }

    /**
     * Ajoute une question à la base de données
     *
     * @param intitule Intitulé de la nouvelle question
     * @param reponse  Réponse de la nouvellle question
     */
    private void addQstPerso(String intitule, String reponse) {
        QuestionManager.addNewQuestion(this, intitule, reponse);
    }
}