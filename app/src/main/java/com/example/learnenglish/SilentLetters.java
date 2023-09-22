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

public class SilentLetters extends AppCompatActivity {

    public TextToSpeech mtts;
    RecyclerView silentLetters;
    ArrayList<ModelSilentWords> modelSilentWords;
    AdapterSilentWord myAdapter;
    FirebaseFirestore firebaseFirestore;


    String content = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent_letters);

        mtts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("Sentences", "onCreate: " + (status == TextToSpeech.SUCCESS ? "Success" : "Failure"));
            }
        });

        mtts.setLanguage(Locale.ENGLISH);

        silentLetters = findViewById(R.id.silentLetters);
        silentLetters.setHasFixedSize(true);
        silentLetters.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelSilentWords = new ArrayList<>();
        myAdapter = new AdapterSilentWord(this, modelSilentWords, mtts);
        silentLetters.setAdapter(myAdapter);





//        modelArrayList.clear();
//        myAdapter.notifyDataSetChanged();

        firebaseFirestore.collection("SilentLetters").orderBy("EnglishWord", Query.Direction.ASCENDING)
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.e("FireStore Error", error.getMessage());
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                DocumentSnapshot docSnap = dc.getDocument();
                                content = docSnap.getString("EnglishWord");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                modelSilentWords.add(dc.getDocument().toObject(ModelSilentWords.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}