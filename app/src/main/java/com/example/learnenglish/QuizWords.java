package com.example.learnenglish;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuizWords extends AppCompatActivity {
//    String ques1[] = {"Two", "Here", "Witch", "Allowed", "Cell"};
//    String ques2[] = {"Too", "Hear", "Which", "Aloud", "Sell"};
//    String mean1[] = {"बहुत", "यहाँ", "चुड़ैल", "अनुमति ", "बेचना"};
//    String mean2[] = {"दो", "सुनना", "कौनसा", "जोर से बोला गया", "छोटी इकाई"};
    Button next;
    TextView quest1, means1, quest2, means2, opt3, opt4;
    int i = 0, fSol = 0;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String sc = "0";
    int ques[] = {}, q=0, qNo = 0;
    int quesS[] = {};
    ImageView resultSymbol;
    boolean ans, marked=false;
    String mean = "इसका", opt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_words);

        quest1 = findViewById(R.id.quest1);
//        quest2 = findViewById(R.id.quest2);
        means1 = findViewById(R.id.means1);
//        means2 = findViewById(R.id.means2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);
        next = findViewById(R.id.next);
        resultSymbol = findViewById(R.id.resultSymbol);
//        quest1.setText(ques1[i]);
//        quest2.setText(ques2[i]);
//        means1.setText(mean1[i]);
//        means2.setText(mean2[i]);
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









        int count = 7;
        while (count != 0) {
            int temp = (int) Math.floor(Math.random() * 9);
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



        int countS = 7;
        while (countS != 0) {
            int temp = (int) Math.floor(Math.random() * 9);
            boolean flagS = false;
            for (int i : quesS) {
                if ( i == temp || temp==0){
                    flagS = true;
                    break;
                }
            }
            if (!flagS) {
                quesS = Arrays.copyOf(quesS, quesS.length + 1);
                quesS[quesS.length - 1] = temp+10; // Assign 40 to the last element
//                Toast.makeText(QuizSent.this, String.valueOf(temp), Toast.LENGTH_SHORT).show();
                countS--;
            }
        }

        q = ques[0];
        if(q==0){
            qNo++;
            q = ques[qNo];
        }



        if(qNo<=3){
//            Toast.makeText(QuizWords.this, String.valueOf(q), Toast.LENGTH_SHORT).show();
            db.collection("Homophones").whereEqualTo("id", String.valueOf(q))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    try{
                                        opt1 = document.get("EngWord1").toString();
                                    }catch (Exception e){
                                        opt1 = "Eng Err";
                                    }

                                    if (q%2!=0){
                                        try{
                                            mean = document.get("Meaning1").toString();
//                                                        Toast.makeText(QuizWords.this, "Correct", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            opt1 = "Hindi Err";
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
            qNo++;

            q = ques[qNo];
        }
        else{
            db.collection("SilentWords").whereEqualTo("id", String.valueOf(q))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    try{
                                        opt1 = document.get("EnglishWord").toString();
                                    }catch (Exception e){
//                                        opt1 = "Eng Err";
                                        qNo--;
                                        next.performClick();
                                    }

                                    if (q%2!=0){
                                        try{
                                            mean = document.get("HindiWord").toString();
//                                                        Toast.makeText(QuizWords.this, "Correct", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
//                                            opt1 = "Hindi Err";
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
            qNo++;
            q = quesS[qNo];
        }






        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(QuizWords.this, String.valueOf(qNo), Toast.LENGTH_SHORT).show();
                resultSymbol.setVisibility(View.GONE);
                marked = false;

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

                        db.collection("User").document(currentUser).collection("Words").document("Score")
                                .update(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(QuizWords.this);

                                        builder.setMessage("आपका स्कोर: " + String.valueOf(fSol));

                                        builder.setTitle("Words");



                                        builder.setCancelable(false);

                                        builder.setPositiveButton("ठीक है", (DialogInterface.OnClickListener) (dialog, which) -> {
                                            Intent i = new Intent(QuizWords.this, Home.class);
                                            startActivity(i);
//                                            Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                                            finish();
                                        });

                                        builder.setNegativeButton("पुन: प्रयास करें", (DialogInterface.OnClickListener) (dialog, which) -> {
                                            Intent i = new Intent(QuizWords.this, QuizWords.class);
                                            startActivity(i);
                                            finish();
                                        });

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                });
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(QuizWords.this);

                        builder.setMessage("आपका स्कोर: " + String.valueOf(fSol));

                        builder.setTitle("Words");

                        builder.setCancelable(false);

                        builder.setPositiveButton("ठीक है", (DialogInterface.OnClickListener) (dialog, which) -> {
                            Intent i = new Intent(QuizWords.this, Home.class);
                            startActivity(i);
//                                            Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
                            finish();
                        });

                        builder.setNegativeButton("पुन: प्रयास करें", (DialogInterface.OnClickListener) (dialog, which) -> {
                            Intent i = new Intent(QuizWords.this, QuizWords.class);
                            startActivity(i);
                            finish();
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }



                } else {
                    if(qNo<=5){
//                        Toast.makeText(QuizWords.this, String.valueOf(q), Toast.LENGTH_SHORT).show();
                        db.collection("Homophones").whereEqualTo("id", String.valueOf(q))
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                try{
                                                    opt1 = document.get("EngWord1").toString();
                                                }catch (Exception e){
                                                    opt1 = "Eng Err";
                                                    qNo--;
                                                    next.performClick();
                                                }

                                                if (q%2!=0){
                                                    try{
                                                        mean = document.get("Meaning1").toString();
//                                                        Toast.makeText(QuizWords.this, "Correct", Toast.LENGTH_SHORT).show();
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
                        qNo++;

                        q = ques[qNo];
                    }
                    else{
                        db.collection("SilentWords").whereEqualTo("id", String.valueOf(q))
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                try{
                                                    opt1 = document.get("EnglishWord").toString();
                                                }catch (Exception e){
                                                    opt1 = "Eng Err";
                                                }

                                                if (q%2!=0){
                                                    try{
                                                        mean = document.get("HindiWord").toString();
//                                                        Toast.makeText(QuizWords.this, "Correct", Toast.LENGTH_SHORT).show();
                                                    }catch (Exception e){
                                                        opt1 = "Hindi Err";
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
                        qNo++;
                        q = quesS[qNo];
                    }
                }
            }
        });


        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultSymbol.setVisibility(View.VISIBLE);
                if (!marked) {
                    Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                    opt3.setBackground(drawable);
                    Drawable drawable3 = getResources().getDrawable(R.drawable.disabled);
                    opt4.setBackground(drawable3);
                    if (ans) {
                        fSol++;
                        resultSymbol.setImageResource(R.drawable.correct);
                    }
                    else {
                        resultSymbol.setImageResource(R.drawable.incorrect);
                    }
                    marked=true;
//                    next.performClick();
                }

            }
        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultSymbol.setVisibility(View.VISIBLE);
                if(!marked){
                    Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
                    opt4.setBackground(drawable);
                    Drawable drawable3 = getResources().getDrawable(R.drawable.disabled);
                    opt3.setBackground(drawable3);
                    if (!ans) {
                        fSol++;
                        resultSymbol.setImageResource(R.drawable.correct);
                    }
                    else {
                        resultSymbol.setImageResource(R.drawable.incorrect);
                    }
                    marked=true;
//                    next.performClick();
                }
            }
        });




















//
//
//
//        quest1.setText(ques1[i]);
//        quest2.setText(ques2[i]);
//        means1.setText(mean1[i]);
//        means2.setText(mean2[i]);
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                i++;
//                if (i<=4){
//                    Drawable drawable2 = getResources().getDrawable(R.drawable.border);
//                    opt4.setBackground(drawable2);
//                    opt3.setBackground(drawable2);
//
//                    quest1.setText(ques1[i]);
//                    quest2.setText(ques2[i]);
//                    means1.setText(mean1[i]);
//                    means2.setText(mean2[i]);
//                    if (i==4){
//                        next.setText("सबमिट");
//                    }
//                }
//                else{
////                    Toast.makeText(QuizWords.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
//                    if(fSol>=Integer.valueOf(sc)){
//                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
////                        Toast.makeText(Quiz.this, String.valueOf(fSol), Toast.LENGTH_SHORT).show();
//                        Map<String, Object> data = new HashMap<>();
//                        data.put("Score", String.valueOf(fSol));
//                        db.collection("User").document(currentUser).collection("Words").document("Score")
//                                .update(data)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Intent i = new Intent(QuizWords.this, Home.class);
//                                        startActivity(i);
//                                        Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
//                                        finish();
//                                    }
//                                });
//                    }
//                    else{
//                        Intent i = new Intent(QuizWords.this, Home.class);
//                        startActivity(i);
//                        Toast.makeText(getApplicationContext(), "Your Score: " + String.valueOf(fSol), Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                }
//            }
//        });
//        opt3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
//                opt3.setBackground(drawable);
//                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
//                opt4.setBackground(drawable2);
//                if(i==2 || i==3 || i==4){
//                    fSol++;
//                }
//            }
//        });
//        opt4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Drawable drawable = getResources().getDrawable(R.drawable.button_bg);
//                opt4.setBackground(drawable);
//                Drawable drawable2 = getResources().getDrawable(R.drawable.border);
//                opt3.setBackground(drawable2);
//                if(i==1 || i==5){
//                    fSol++;
//                }
//            }
//        });
    }
}