package com.example.learnenglish;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    ImageView tools, logistics, symbol;
    TextView name;

    FirebaseFirestore db;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tools = (ImageView) findViewById(R.id.tools);
        logistics = (ImageView) findViewById(R.id.logistics) ;
        symbol = (ImageView) findViewById(R.id.symbol);
        name = findViewById(R.id.name);
        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, WordBank.class);
                i.putExtra("Category", "Tools");
                startActivity(i);
            }
        });

        db = FirebaseFirestore.getInstance();

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

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


    }
}