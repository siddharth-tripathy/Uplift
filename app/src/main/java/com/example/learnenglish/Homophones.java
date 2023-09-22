package com.example.learnenglish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class Homophones extends AppCompatActivity {
    public TextToSpeech mtts;
    RecyclerView homop;
    ArrayList<ModelHomophones> modelHomophones;
    AdapterHomophones myAdapter;
    FirebaseFirestore firebaseFirestore;


    String content = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homophones);

        mtts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("Sentences", "onCreate: " + (status == TextToSpeech.SUCCESS ? "Success" : "Failure"));
            }
        });

        mtts.setLanguage(Locale.ENGLISH);

        homop = findViewById(R.id.homop);
        homop.setHasFixedSize(true);
        homop.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelHomophones = new ArrayList<>();
        myAdapter = new AdapterHomophones(this, mtts, modelHomophones);
        homop.setAdapter(myAdapter);

        firebaseFirestore.collection("Homophones").orderBy("EngWord1", Query.Direction.ASCENDING)
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.e("FireStore Error", error.getMessage());
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                DocumentSnapshot docSnap = dc.getDocument();
                                content = docSnap.getString("EngWord1");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                modelHomophones.add(dc.getDocument().toObject(ModelHomophones.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }
}