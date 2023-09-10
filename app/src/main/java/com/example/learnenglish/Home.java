package com.example.learnenglish;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    ImageView tools, logistics, symbol, quality, safety, menu;
    TextView name;
    FirebaseFirestore db;
    FirebaseUser user;
    CardView alphabets, grammar, words;

    PopupMenu popup;
    MenuItem logout;


    boolean vis = false;

    LinearLayout view_words;
    TextToSpeech tts;

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
        grammar = findViewById(R.id.grammar);
        words = findViewById(R.id.words);
        view_words = findViewById(R.id.view_words);

        name = findViewById(R.id.name);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup = new PopupMenu(Home.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.my_menu, popup.getMenu());
                popup.show();
//                logout = findViewById(R.id.logout);
            }
        });

//        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
//                Toast.makeText(Home.this, "Menu Item is Pressed", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

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
                Intent i = new Intent(Home.this, Alphabets.class);
                startActivity(i);
            }
        });

        view_words.setVisibility(View.GONE);
        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vis){
                    view_words.setVisibility(View.GONE);
                    vis = false;
                }
                else{
                    vis = true;
                    view_words.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Handling the click events of the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Switching on the item id of the menu item
//        switch (item.getItemId()) {
//            case R.id.logout:
//                // Code to be executed when the add button is clicked
//                Toast.makeText(this, "Menu Item is Pressed", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

        int id = item.getItemId();
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        if (id ==  R.id.logout){
            Toast.makeText(this, "Menu Item is Pressed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}