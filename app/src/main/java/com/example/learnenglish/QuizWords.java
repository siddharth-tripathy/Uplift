package com.example.learnenglish;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QuizWords extends AppCompatActivity {
    String ques1[] = {"Two", "Here", "Witch", "Allowed", "Cell"};
    String ques2[] = {"Too", "Hear", "Which", "Aloud", "Sell"};
    String mean1[] = {"बहुत", "यहाँ", "चुड़ैल", "अनुमति ", "बेचना"};
    String mean2[] = {"दो", "सुनना", "कौनसा", "जोर से बोला गया", "छोटी इकाई"};
    Button next;
    TextView quest1, means1, quest2, means2, opt3, opt4;
    int i = 0, fSol = 0;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String sc = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_words);

        quest1 = findViewById(R.id.quest1);
        quest2 = findViewById(R.id.quest2);
        means1 = findViewById(R.id.means1);
        means2 = findViewById(R.id.means2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);
        next = findViewById(R.id.next);
        quest1.setText(ques1[i]);
        quest2.setText(ques2[i]);
        means1.setText(mean1[i]);
        means2.setText(mean2[i]);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Toast.makeText(Quiz.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
        Map<String, Object> data = new HashMap<>();
        data.put("Score", "0");
        DocumentReference docRef = db.collection("User").document(currentUser).collection("Words").document("Score");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists())
                    {
                        db.collection("User").document(currentUser).collection("Words").document("Score")
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

        quest1.setText(ques1[i]);
        quest2.setText(ques2[i]);
        means1.setText(mean1[i]);
        means2.setText(mean2[i]);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (i<=4){
                    Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                    opt4.setBackground(drawable2);
                    opt3.setBackground(drawable2);

                    quest1.setText(ques1[i]);
                    quest2.setText(ques2[i]);
                    means1.setText(mean1[i]);
                    means2.setText(mean2[i]);
                    if (i==4){
                        next.setText("सबमिट");
                    }
                }
                else{
//                    Toast.makeText(QuizWords.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
                    if(fSol>=Integer.valueOf(sc)){
                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                        Toast.makeText(Quiz.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
                        Map<String, Object> data = new HashMap<>();
                        data.put("Score", String.valueOf(fSol));
                        db.collection("User").document(currentUser).collection("Words").document("Score")
                                .update(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent i = new Intent(QuizWords.this, Home.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                    }
                    else{
                        Intent i = new Intent(QuizWords.this, Home.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                opt3.setBackground(drawable);
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt4.setBackground(drawable2);
                if(i==2 || i==3 || i==4){
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
                opt3.setBackground(drawable2);
                if(i==1 || i==5){
                    fSol++;
                }
            }
        });
    }
}