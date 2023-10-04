package com.example.learnenglish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class Sentences extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public TextToSpeech mtts;
    RecyclerView sent;
    ArrayList<ModelSentences> modelSentences;
    ArrayList<ModelSentenceAll> modelSentencesAll;
    AdapterSentences myAdapter;
    AdapterSentenceAll MyAdapter2;
    FirebaseFirestore firebaseFirestore;
    Spinner filter;
    ProgressDialog progressDialog;
    String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);

        filter = findViewById(R.id.filter);
        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.my_spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        filter.setAdapter(adapterC);
        filter.setOnItemSelectedListener(this);


        mtts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i("Sentences", "onCreate: " + (status == TextToSpeech.SUCCESS ? "Success" : "Failure"));
            }
        });

        mtts.setLanguage(Locale.ENGLISH);

        sent = findViewById(R.id.sent);
        sent.setHasFixedSize(true);
        sent.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelSentences = new ArrayList<>();
        modelSentencesAll = new ArrayList<>();
        myAdapter = new AdapterSentences(this, modelSentences, mtts);
        MyAdapter2 = new AdapterSentenceAll(this, modelSentencesAll, mtts);

        sent.setAdapter(myAdapter);





//        modelSentences.clear();
//        myAdapter.notifyDataSetChanged();
//
//        firebaseFirestore.collection("commonsentences").orderBy("EnglishSentence", Query.Direction.ASCENDING)
//                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error!=null){
//                            Log.e("FireStore Error", error.getMessage());
//                        }
//                        for (DocumentChange dc : value.getDocumentChanges()){
//                            if (dc.getType() == DocumentChange.Type.ADDED){
//                                DocumentSnapshot docSnap = dc.getDocument();
//                                content = docSnap.getString("EnglishSentence");
////                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
//                                modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
//                            }
//                            myAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//        progressDialog.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String choice = adapterView.getItemAtPosition(i).toString();
        String c = "";

        if (choice.equals("--FILTER--")){
            sent.setAdapter(MyAdapter2);

            modelSentencesAll.clear();
            MyAdapter2.notifyDataSetChanged();

            firebaseFirestore.collection("commonsentences").orderBy("EnglishSentence", Query.Direction.ASCENDING)
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.e("FireStore Error", error.getMessage());
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                DocumentSnapshot docSnap = dc.getDocument();
                                content = docSnap.getString("EnglishSentence");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                modelSentencesAll.add(dc.getDocument().toObject(ModelSentenceAll.class));
                            }
                            MyAdapter2.notifyDataSetChanged();
                        }
                        progressDialog.dismiss();
                    }
                });


        } else if (choice.equals("Seeking for Help")) {
            sent.setAdapter(myAdapter);
            modelSentences.clear();
            myAdapter.notifyDataSetChanged();

//            firebaseFirestore.collection("CategorySentences").whereEqualTo("Category", "seeking_help").orderBy("English", Query.Direction.ASCENDING)
//                    .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if (error!=null){
//                                Log.e("FireStore Error", error.getMessage());
//                            }
//                            for (DocumentChange dc : value.getDocumentChanges()){
//                                if (dc.getType() == DocumentChange.Type.ADDED){
//                                    DocumentSnapshot docSnap = dc.getDocument();
//                                    content = docSnap.getString("English");
////                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
//                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
//                                }
//                                myAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    });

            firebaseFirestore.collection("CategorySentences").whereEqualTo("Category", "seeking_help").orderBy("English", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e("Firestore Error", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    DocumentSnapshot docSnap = dc.getDocument();
                                    content = docSnap.getString("English");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
//                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
                        }
                    });

        } else if (choice.equals("Requests")) {
            sent.setAdapter(myAdapter);
            modelSentences.clear();
            myAdapter.notifyDataSetChanged();

            firebaseFirestore.collection("CategorySentences").whereEqualTo("Category", "request").orderBy("English", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e("Firestore Error", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    DocumentSnapshot docSnap = dc.getDocument();
                                    content = docSnap.getString("English");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
//                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }else if (choice.equals("Apologies")) {
            sent.setAdapter(myAdapter);
            modelSentences.clear();
            myAdapter.notifyDataSetChanged();

            firebaseFirestore.collection("CategorySentences").whereEqualTo("Category", "apology").orderBy("English", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e("Firestore Error", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    DocumentSnapshot docSnap = dc.getDocument();
                                    content = docSnap.getString("English");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
//                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }else if (choice.equals("Expressing Gratitude")) {
            sent.setAdapter(myAdapter);
            modelSentences.clear();
            myAdapter.notifyDataSetChanged();

            firebaseFirestore.collection("CategorySentences").whereEqualTo("Category", "gratitude").orderBy("English", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e("Firestore Error", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    DocumentSnapshot docSnap = dc.getDocument();
                                    content = docSnap.getString("English");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
//                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }else if (choice.equals("Greetings")) {
            sent.setAdapter(myAdapter);

            modelSentences.clear();
            myAdapter.notifyDataSetChanged();

            firebaseFirestore.collection("CategorySentences").whereEqualTo("Category", "greeting").orderBy("English", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e("Firestore Error", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    DocumentSnapshot docSnap = dc.getDocument();
                                    content = docSnap.getString("English");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
//                                    modelSentences.add(dc.getDocument().toObject(ModelSentences.class));
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }


//        progressDialog.dismiss();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        sent.setAdapter(MyAdapter2);

        modelSentencesAll.clear();
        MyAdapter2.notifyDataSetChanged();

        firebaseFirestore.collection("commonsentences").orderBy("EnglishSentence", Query.Direction.ASCENDING)
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.e("FireStore Error", error.getMessage());
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                DocumentSnapshot docSnap = dc.getDocument();
                                content = docSnap.getString("EnglishSentence");
//                                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                                modelSentencesAll.add(dc.getDocument().toObject(ModelSentenceAll.class));
                            }
                            MyAdapter2.notifyDataSetChanged();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}