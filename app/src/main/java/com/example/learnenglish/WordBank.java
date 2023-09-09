package com.example.learnenglish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class WordBank extends AppCompatActivity {

    public TextToSpeech mtts;
    RecyclerView word_bank;

//    ImageView item;

    ArrayList<ModelWordBank> modelArrayList;
    AdapterWordBank myAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView title;


    String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_bank);



        Intent i =getIntent();
        title = findViewById(R.id.title);

        String category =i.getStringExtra("Category");

        switch (category){
            case "Tools":
                title.setText("Tools");
                break;
            case "Symbols":
                title.setText("Symbol");
                break;
            case "Logistics":
                title.setText("Logistics");
                break;
            case "QualityControl":
                title.setText("Quality Control");
                break;
            case "SafetyToolsAndSign":
                title.setText("Safety Tools & Sign");
                break;
            default:
                title.setText("Title");
                break;

        }

        mtts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("WordBankActivity", "onCreate: " + (status == TextToSpeech.SUCCESS ? "Success" : "Failure"));
            }
        });

        mtts.setLanguage(Locale.ENGLISH);

        word_bank = findViewById(R.id.word_bank);
        word_bank.setHasFixedSize(true);
        word_bank.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelArrayList = new ArrayList<>();
        myAdapter = new AdapterWordBank(this, modelArrayList, mtts);
        word_bank.setAdapter(myAdapter);





//        modelArrayList.clear();
//        myAdapter.notifyDataSetChanged();

        firebaseFirestore.collection(category).orderBy("EnglishName", Query.Direction.ASCENDING)
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.e("FireStore Error", error.getMessage());
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                DocumentSnapshot docSnap = dc.getDocument();
                                content = docSnap.getString("EnglishName");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                modelArrayList.add(dc.getDocument().toObject(ModelWordBank.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });

//
//        item = findViewById(R.id.item);
//
//        Glide.with(this)
//                .load("images/alphabet-word-images-1296137_640.webp")
//                .into(item);
    }
}