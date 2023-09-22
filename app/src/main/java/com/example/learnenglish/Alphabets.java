package com.example.learnenglish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Alphabets extends AppCompatActivity {

    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<String> alpha_pro_hindi = new ArrayList<>();

    TextToSpeech tts;
    Button next;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabets);

        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        alphabet.add("D");
        alphabet.add("E");
        alphabet.add("F");
        alphabet.add("G");
        alphabet.add("H");
        alphabet.add("I");
        alphabet.add("J");
        alphabet.add("K");
        alphabet.add("L");
        alphabet.add("M");
        alphabet.add("N");
        alphabet.add("O");
        alphabet.add("P");
        alphabet.add("Q");
        alphabet.add("R");
        alphabet.add("S");
        alphabet.add("T");
        alphabet.add("U");
        alphabet.add("V");
        alphabet.add("W");
        alphabet.add("X");
        alphabet.add("Y");
        alphabet.add("Z");

        alpha_pro_hindi.add("ए");
        alpha_pro_hindi.add("बी");
        alpha_pro_hindi.add("सी");
        alpha_pro_hindi.add("डी");
        alpha_pro_hindi.add("इ");
        alpha_pro_hindi.add("एफ");
        alpha_pro_hindi.add("जी");
        alpha_pro_hindi.add("एच");
        alpha_pro_hindi.add("आई");
        alpha_pro_hindi.add("जे");
        alpha_pro_hindi.add("के");
        alpha_pro_hindi.add("एल");
        alpha_pro_hindi.add("एम");
        alpha_pro_hindi.add("एन");
        alpha_pro_hindi.add("ओ");
        alpha_pro_hindi.add("पी");
        alpha_pro_hindi.add("क्यू");
        alpha_pro_hindi.add("आर");
        alpha_pro_hindi.add("एस");
        alpha_pro_hindi.add("टी");
        alpha_pro_hindi.add("यू");
        alpha_pro_hindi.add("भी");
        alpha_pro_hindi.add("डब्ल्लू");
        alpha_pro_hindi.add("एक्स");
        alpha_pro_hindi.add("वाय");
        alpha_pro_hindi.add("जेड");

        next = (Button) findViewById(R.id.next);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                Log.i("HomeActivity", "onCreate: " + (i == TextToSpeech.SUCCESS ? "Success" : "Failure"));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.alpha);
        recyclerView.setLayoutManager(layoutManager);
        AdapterAlphabets adapterAlphabets = new AdapterAlphabets(this, alphabet, alpha_pro_hindi, tts);
        recyclerView.setAdapter(adapterAlphabets);

        int total = adapterAlphabets.getItemCount() - 1;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i<=total){
                    i++;
                    recyclerView.smoothScrollToPosition(i);
                }
            }
        });
    }
}