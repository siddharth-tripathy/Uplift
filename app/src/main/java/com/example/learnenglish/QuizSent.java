package com.example.learnenglish;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuizSent extends AppCompatActivity {

    int qNo = 0, fSol = 0, q=0;
    int ques[] = {};
    FirebaseFirestore db;
    boolean ans = false;
    String mean = "कृपया उत्पादों को सही ढंग से लेबल करें। ", sc = "0", opt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_sent);


        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


//        DocumentReference docRef = ;
//        Map<String, Object> data = new HashMap<>();
//        data.put("Score", "0");
//        db.collection("User").document(currentUser).collection("Words").document("Score")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (!document.exists())
//                    {
//                        db.collection("User").document(currentUser).collection("Words").document("Score")
//                                .set(data)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
////                                        Toast.makeText(getApplicationContext(), "Start Quiz", Toast.LENGTH_LONG).show();
//                                    }
//                                });
//
//                        Log.d(TAG, "Document exists!");
//                    }
//                    else{
//                        sc = document.getString("Score");
//                    }
//                }
//                else {
//                    Log.d(TAG, "Failed with: ", task.getException());
//                }
//            }
//        });
//
//

        db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("Score", "0");
        DocumentReference docRef = db.collection("User").document(currentUser).collection("Sentences").document("Score");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists())
                    {
                        db.collection("User").document(currentUser).collection("Sentences").document("Score")
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

















        // Generating ques no.s
        // Using Random Generator
        int count = 10;
        while (count != 0) {
            int temp = (int) Math.floor(Math.random() * 25);
            boolean flag = false;
            for (int i : ques) {
                if ( i == temp || temp==0){
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ques = Arrays.copyOf(ques, ques.length + 1);
                ques[ques.length - 1] = temp; // Assign 40 to the last element
//                Toast.makeText(QuizSent.this, String.valueOf(temp), Toast.LENGTH_SHORT).show();
                count--;
            }
        }

        Button next = findViewById(R.id.next);
        TextView quest1 = findViewById(R.id.quest1);
        TextView means1 = findViewById(R.id.means1);
        TextView opt3 = findViewById(R.id.opt3);
        TextView opt4 = findViewById(R.id.opt4);

//        db = FirebaseFirestore.getInstance();

        q = ques[0];
        if(q==0){
            qNo++;
            q = ques[qNo];
        }
//        Toast.makeText(QuizSent.this, String.valueOf(q), Toast.LENGTH_SHORT).show();
        db.collection("commonsentences").whereEqualTo("id", String.valueOf(q))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                try{
                                    opt1 = document.get("EnglishSentence").toString();
                                }
                                catch (Exception e){
//                                    opt1 = "Eng error";
                                    qNo--;
                                    next.performClick();
                                }

                                if (q%2!=0){
                                    try{
                                        mean = document.get("HindiSentence").toString();
                                    }
                                    catch (Exception e){
//                                        mean = "hindi error";
                                        qNo--;
                                        next.performClick();
                                    }
                                    ans = true;
                                }
                                else {
                                    ans = false;
                                }
                                quest1.setText(opt1);
                                means1.setText(mean);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        qNo++;
        q = ques[qNo];

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt4.setBackground(drawable2);
                opt3.setBackground(drawable2);
//                Toast.makeText(QuizSent.this, String.valueOf(q), Toast.LENGTH_SHORT).show();

                if (qNo == 5) {
                    next.setText("सबमिट");
                }
                if (qNo == 6) {
//                    Toast.makeText(QuizSent.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
                    if (fSol>=Integer.parseInt(sc)){
                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                        Toast.makeText(Quiz.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
                        Map<String, Object> data = new HashMap<>();
                        data.put("Score", String.valueOf(fSol));

                        db.collection("User").document(currentUser).collection("Sentences").document("Score")
                                .update(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(QuizSent.this);

                                        builder.setMessage("आपका स्कोर: " + String.valueOf(fSol));

                                        builder.setTitle("Sentences");

                                        builder.setCancelable(false);

                                        builder.setPositiveButton("ठीक है", (DialogInterface.OnClickListener) (dialog, which) -> {
                                            Intent i = new Intent(QuizSent.this, Home.class);
                                            startActivity(i);
//                                            Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                                            finish();
                                        });

                                        builder.setNegativeButton("पुन: प्रयास करें", (DialogInterface.OnClickListener) (dialog, which) -> {
                                            Intent i = new Intent(QuizSent.this, QuizSent.class);
                                            startActivity(i);
                                            finish();
                                        });

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                });
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(QuizSent.this);

                        builder.setMessage("आपका स्कोर: " + String.valueOf(fSol));

                        builder.setTitle("Sentences");

                        builder.setCancelable(false);

                        builder.setPositiveButton("ठीक है", (DialogInterface.OnClickListener) (dialog, which) -> {
                            Intent i = new Intent(QuizSent.this, Home.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);

//                                            Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                            finish();
                        });

                        builder.setNegativeButton("पुन: प्रयास करें", (DialogInterface.OnClickListener) (dialog, which) -> {
                            Intent i = new Intent(QuizSent.this, QuizSent.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }



                } else {
                    db.collection("commonsentences").whereEqualTo("id", String.valueOf(q))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            try{
                                                opt1 = document.get("EnglishSentence").toString();
                                            }catch (Exception e){
//                                                opt1 = "Eng Err";
                                                qNo--;
                                                next.performClick();

//                                                AlertDialog.Builder builder = new AlertDialog.Builder(QuizSent.this);
//
//                                                builder.setMessage("Network Error\nRetry");
//
//                                                builder.setTitle("Sentences");
//
//                                                builder.setCancelable(false);
//
//                                                builder.setPositiveButton("ठीक है", (DialogInterface.OnClickListener) (dialog, which) -> {
//
//                                                    dialog.cancel();
////                                            Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
//                                                });
//
//
//                                                AlertDialog alertDialog = builder.create();
//                                                alertDialog.show();

                                            }

                                            if (q%2!=0){
                                                try{
                                                    mean = document.get("HindiSentence").toString();
//                                                    Toast.makeText(QuizSent.this, "Correct", Toast.LENGTH_SHORT).show();
                                                }catch (Exception e){
                                                    opt1 = "Hindi Err";
                                                    qNo--;
                                                    next.performClick();
                                                }

                                                ans = true;
                                            }
                                            else {
//                                                Toast.makeText(QuizSent.this, "In-Correct", Toast.LENGTH_SHORT).show();
                                                ans = false;
                                            }
                                            quest1.setText(opt1);
                                            means1.setText(mean);

                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
                qNo++;
                q = ques[qNo];
            }
        });


        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                opt3.setBackground(drawable);
                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
                opt4.setBackground(drawable2);
                if (ans) {
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
                if (!ans) {
                    fSol++;
                }
            }
        });


    }

//    public void genE(){
//        db = FirebaseFirestore.getInstance();
//        db.collection("commonsentences").whereEqualTo("id", String.valueOf(q))
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                try{
//                                    String opt1 = document.get("EnglishSentence").toString();
//                                }
//                                catch (Exception e){
//                                    genE();
//                                }
//
//                                if (q%2!=0){
//                                    mean = document.get("HindiSentence").toString();
//                                    ans = true;
//                                }
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//
//    }

}