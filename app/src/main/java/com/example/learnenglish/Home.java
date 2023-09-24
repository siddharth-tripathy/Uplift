package com.example.learnenglish;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    ImageView tools, logistics, symbol, quality, safety, menu, words_arrow, alpha_arrow, sent_arrow, words_sc, alpha_sc, sent_sc;
    TextView name;
    FirebaseFirestore db;
    FirebaseUser user;
    CardView alphabets, grammar, words, sentences, quiz_word;
    PopupMenu popup;
    MenuItem logout;
    String alphaSc = "0", wordSc = "0", sentSc = "0";
    boolean vis = false, vis_alpha_options = false, quiz_alpha, quiz_words = false, quiz_sent = false;
    LinearLayout view_words, alpha_options, silent, homop;
    TextToSpeech tts;
    Button alpha_chpt, alpha_quiz;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tools = (ImageView) findViewById(R.id.tools);
        logistics = (ImageView) findViewById(R.id.logistics) ;
        symbol = (ImageView) findViewById(R.id.symbol);
        quality = (ImageView) findViewById(R.id.quality);
        safety = (ImageView) findViewById(R.id.safety);
        menu = findViewById(R.id.menu);

        alphabets = findViewById(R.id.alphabets);
//        grammar = findViewById(R.id.grammar);
        words = findViewById(R.id.words);
        view_words = findViewById(R.id.view_words);
        alpha_options = findViewById(R.id.alpha_options);
        alpha_options.setVisibility(View.GONE);
        alpha_arrow = findViewById(R.id.alpha_arrow);
        sent_arrow = findViewById(R.id.sent_arrow);
        alpha_quiz = findViewById(R.id.alpha_quiz);
        alpha_chpt = findViewById(R.id.alpha_chpt);
        words_arrow = findViewById(R.id.words_arrow);
        words_sc = findViewById(R.id.words_sc);
        alpha_sc = findViewById(R.id.alpha_sc);
        sent_sc = findViewById(R.id.sent_sc);
        sentences = findViewById(R.id.sentences);
        silent = findViewById(R.id.silent);
        quiz_word = findViewById(R.id.quiz_word);
        homop = findViewById(R.id.homop);
        name = findViewById(R.id.name);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Logout Action below
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);

                builder.setMessage("क्या आप लॉगआउट करना चाहते हैं ?");

                builder.setTitle("Alert !");

                builder.setCancelable(false);

                builder.setPositiveButton("हाँ", (DialogInterface.OnClickListener) (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(Home.this, Login.class);
                    startActivity(i);
                    finish();
                });

                builder.setNegativeButton("नहीं", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

//        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
//                Toast.makeText(Home.this, "Menu Item is Pressed", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        db.collection("User").document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                name.setText(document.getString("name"));
                            } else {
                                
                            }
                        }
                    }
                });

        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, WordBank.class);
                i.putExtra("Category", "Tools");
                startActivity(i);
            }
        });

        logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, WordBank.class);
                i.putExtra("Category", "Logistics");
                startActivity(i);
            }
        });

        symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, WordBank.class);
                i.putExtra("Category", "Symbols");
                startActivity(i);
            }
        });

        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, WordBank.class);
                i.putExtra("Category", "QualityControl");
                startActivity(i);
            }
        });

        safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, WordBank.class);
                i.putExtra("Category", "SafetyToolsAndSign");
                startActivity(i);
            }
        });

        alphabets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vis_alpha_options){
                    vis_alpha_options = false;
                    alpha_options.setVisibility(View.GONE);
                    alpha_arrow.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
                }
                else{
                    vis_alpha_options = true;
                    alpha_arrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                    alpha_options.setVisibility(View.VISIBLE);
                }
            }
        });

        alpha_chpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Alphabets.class);
                startActivity(i);
            }
        });

        alpha_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Quiz.class);
                startActivity(i);
            }
        });

        view_words.setVisibility(View.GONE);
        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference docRef = db.collection("User").document(currentUser).collection("Alphabets").document("Score");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String sc = document.getString("Score");

                                if(Integer.parseInt(sc)>=3){
                                    if (vis){
                                        view_words.setVisibility(View.GONE);
                                        words_arrow.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
                                        vis = false;
                                    }
                                    else{
                                        vis = true;
                                        words_arrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                                        view_words.setVisibility(View.VISIBLE);
                                    }
                                }
                                else{
                                    quiz_alpha = false;
                                    words_arrow.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
                                    Toast.makeText(Home.this, "पिछले अध्याय की प्रश्नोत्तरी पूरी करें", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                quiz_alpha = false;
                                words_arrow.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
                                Toast.makeText(Home.this, "पिछले अध्याय की प्रश्नोत्तरी पूरी करें", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
        silent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, SilentLetters.class);
                startActivity(i);
            }
        });

        homop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Homophones.class);
                startActivity(i);
            }
        });

        quiz_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, QuizWords.class);
                startActivity(i);
            }
        });

        sentences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference docRef = db.collection("User").document(currentUser).collection("Words").document("Score");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String sc = document.getString("Score");

                                if(Integer.parseInt(sc)>=3){
                                    Intent i = new Intent(Home.this, QuizSent.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(Home.this, "पिछले अध्याय की प्रश्नोत्तरी पूरी करें", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Home.this, "पिछले अध्याय की प्रश्नोत्तरी पूरी करें", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });








        DocumentReference docRefAlpha = db.collection("User").document(currentUser).collection("Alphabets").document("Score");
        docRefAlpha.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists())
                    {
                        alphaSc = "0";
                    }
                    else{
                        alphaSc = document.getString("Score");
//                        Toast.makeText(Home.this, alphaSc, Toast.LENGTH_SHORT).show();
                    }
                    int sc = Integer.parseInt(alphaSc);
//                    Toast.makeText(Home.this, String.valueOf(sc), Toast.LENGTH_SHORT).show();
                    if(sc>=3){
                        alpha_sc.setImageResource(R.drawable.trophy);
                    }
                    if(sc<3){
                        words_arrow.setImageResource(R.drawable.baseline_lock_24);
                    }
                }
                else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });







        DocumentReference docRefWord = db.collection("User").document(currentUser).collection("Words").document("Score");
        docRefWord.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists())
                    {
                        wordSc = "0";
                    }
                    else{
                        wordSc = document.getString("Score");
                    }
                    int sc = Integer.parseInt(wordSc);
//                    Toast.makeText(Home.this, String.valueOf(sc), Toast.LENGTH_SHORT).show();
                    if(sc>=3){
                        words_sc.setImageResource(R.drawable.trophy);
                    }
                    if(sc<3){
                        sent_arrow.setImageResource(R.drawable.baseline_lock_24);
                    }
                }
                else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });





        DocumentReference docRefSent = db.collection("User").document(currentUser).collection("Sentences").document("Score");
        docRefSent.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists())
                    {
                        sentSc = "0";
                    }
                    else{
                        sentSc = document.getString("Score");
                    }
                    int sc = Integer.parseInt(sentSc);
//                    Toast.makeText(Home.this, String.valueOf(sc), Toast.LENGTH_SHORT).show();
                    if(sc>=3){
                        sent_sc.setImageResource(R.drawable.trophy);
                    }
                }
                else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });






    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);

        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);

        builder.setMessage("क्या आप ऐप बंद करना चाहते हैं ?");

        builder.setTitle("Alert !");

        builder.setCancelable(false);

        builder.setPositiveButton("हाँ", (DialogInterface.OnClickListener) (dialog, which) -> {
            super.onBackPressed();
            return;
        });

        builder.setNegativeButton("नहीं", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}