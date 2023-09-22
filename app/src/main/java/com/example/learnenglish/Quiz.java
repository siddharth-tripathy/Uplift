package com.example.learnenglish;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    char alphabets[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B'};
    Button next;
    int sol = 0, fSol=0, i=1;
    TextView opt1, opt2, opt3, opt4;
    private FirebaseFirestore db;
    ImageButton ques;
    private FirebaseAuth mAuth;
    TextToSpeech tts;
    String q, sc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ques = findViewById(R.id.ques);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);

        next = findViewById(R.id.next);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Toast.makeText(Quiz.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
        Map<String, Object> data = new HashMap<>();
        data.put("Score", "0");
        DocumentReference docRef = db.collection("User").document(currentUser).collection("Alphabets").document("Score");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists())
                    {
                        db.collection("User").document(currentUser).collection("Alphabets").document("Score")
                                .set(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Toast.makeText(getApplicationContext(), "Start Quiz", Toast.LENGTH_LONG).show();
                                    }
                                });

                        Log.d(TAG, "Document exists!");
                    }
                    else{
                        sc = document.getString("Score");
                    }
                }
                else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });


        {
            if(i<=5){

                if(i==5){
                    next.setText("सबमिट");
                }
                i++;

                char qu = alphabets[(int)Math.floor(Math.random() * 27)];
                q = String.valueOf(qu);
//                ques.setText(q);
                int ans = (int)Math.floor(Math.random() * 5);




                if (ans==1){
                    sol=1;

                    opt1.setText(q);

                    String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans1.equals(q)){
                        ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt2.setText(String.valueOf(ans1));

                    String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans2.equals(q) && ans2.equals(ans1)){
                        ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt3.setText(String.valueOf(ans2));

                    String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                        ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt4.setText(String.valueOf(ans3));
                }
                else if (ans==2){
                    sol=2;

                    opt2.setText(q);

                    String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans1.equals(q)){
                        ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt1.setText(String.valueOf(ans1));

                    String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans2.equals(q) && ans2.equals(ans1)){
                        ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt3.setText(String.valueOf(ans2));

                    String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                        ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt4.setText(String.valueOf(ans3));
                } else if (ans==3){
                    sol=3;

                    opt3.setText(q);

                    String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans1.equals(q)){
                        ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt2.setText(String.valueOf(ans1));

                    String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans2.equals(q) && ans2.equals(ans1)){
                        ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt1.setText(String.valueOf(ans2));

                    String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                        ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt4.setText(String.valueOf(ans3));
                }
                else{
                    sol=4;

                    opt4.setText(q);

                    String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans1.equals(q)){
                        ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt2.setText(String.valueOf(ans1));

                    String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans2.equals(q) && ans2.equals(ans1)){
                        ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt3.setText(String.valueOf(ans2));

                    String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                        ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                    }
                    opt1.setText(String.valueOf(ans3));
                }
            }

        }




        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt2.setBackground(drawable2);
                opt1.setBackground(drawable2);
                opt4.setBackground(drawable2);
                opt3.setBackground(drawable2);

                if(i<=5){

                    if(i==5){
                        next.setText("सबमिट");
                    }
                    i++;

                    char qu = alphabets[(int)Math.floor(Math.random() * 27)];
                    q = String.valueOf(qu);
//                    ques.setText(q);
                    int ans = (int)Math.floor(Math.random() * 5);

                    if (ans==1){
                        sol=1;

                        opt1.setText(q);

                        String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans1.equals(q)){
                            ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt2.setText(String.valueOf(ans1));

                        String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans2.equals(q) && ans2.equals(ans1)){
                            ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt3.setText(String.valueOf(ans2));

                        String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                            ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt4.setText(String.valueOf(ans3));
                    }
                    else if (ans==2){
                        sol=2;

                        opt2.setText(q);

                        String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans1.equals(q)){
                            ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt1.setText(String.valueOf(ans1));

                        String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans2.equals(q) && ans2.equals(ans1)){
                            ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt3.setText(String.valueOf(ans2));

                        String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                            ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt4.setText(String.valueOf(ans3));
                    } else if (ans==3){
                        sol=3;

                        opt3.setText(q);

                        String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans1.equals(q)){
                            ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt2.setText(String.valueOf(ans1));

                        String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans2.equals(q) && ans2.equals(ans1)){
                            ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt1.setText(String.valueOf(ans2));

                        String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                            ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt4.setText(String.valueOf(ans3));
                    }
                    else{
                        sol=4;

                        opt4.setText(q);

                        String ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans1.equals(q)){
                            ans1 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt2.setText(String.valueOf(ans1));

                        String ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans2.equals(q) && ans2.equals(ans1)){
                            ans2 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt3.setText(String.valueOf(ans2));

                        String ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        while(ans3.equals(q) && ans3.equals(ans1) && ans3.equals(ans2) ){
                            ans3 = String.valueOf(alphabets[(int)Math.floor(Math.random() * 27)]);
                        }
                        opt1.setText(String.valueOf(ans3));
                    }
                }
                else{
                    if(fSol>=Integer.valueOf(sc)){
                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                        Toast.makeText(Quiz.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
                        Map<String, Object> data = new HashMap<>();
                        data.put("Score", String.valueOf(fSol));
                        db.collection("User").document(currentUser).collection("Alphabets").document("Score")
                                .update(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent i = new Intent(Quiz.this, Home.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                    }
                    else{
                        Intent i = new Intent(Quiz.this, Home.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            }
        });

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("Quiz", "onCreate: " + (status == TextToSpeech.SUCCESS ? "Success" : "Failure"));
            }
        });

        ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(q, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                opt1.setBackground(drawable);
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt2.setBackground(drawable2);
                opt3.setBackground(drawable2);
                opt4.setBackground(drawable2);
                if(sol==1){
                    fSol++;
                }
            }
        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                opt4.setBackground(drawable);
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt2.setBackground(drawable2);
                opt3.setBackground(drawable2);
                opt1.setBackground(drawable2);
                if(sol==4){
                    fSol++;
                }
            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                opt2.setBackground(drawable);
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt1.setBackground(drawable2);
                opt3.setBackground(drawable2);
                opt4.setBackground(drawable2);
                if(sol==2){
                    fSol++;
                }
            }
        });

        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                opt3.setBackground(drawable);
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt2.setBackground(drawable2);
                opt1.setBackground(drawable2);
                opt4.setBackground(drawable2);
                if(sol==3){
                    fSol++;
                }
            }
        });
    }
}