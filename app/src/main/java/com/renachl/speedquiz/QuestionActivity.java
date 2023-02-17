package com.renachl.speedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuestionActivity extends AppCompatActivity {

    private ListView LI_Qst;
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        LI_Qst = findViewById(R.id.qst_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.activity_question, mobileArray);

        LI_Qst.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}