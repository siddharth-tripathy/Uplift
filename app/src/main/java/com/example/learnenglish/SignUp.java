package com.example.learnenglish;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private EditText userName, pass, cPass, name;
    private Button submit;

    private FirebaseFirestore db;

    private FirebaseUser user;

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        userName = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        cPass = findViewById(R.id.confirmPassword);
        submit = findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String email = userName.getText().toString();
                String password = pass.getText().toString();
                String cpass = cPass.getText().toString();
                String num = email;
                String nm = name.getText().toString();
                email += "@learnEnglish.com";

                if(verify(num, password, cpass, nm)){
                    Toast.makeText(getApplicationContext(), "Enter Correct Details", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog = new ProgressDialog(SignUp.this);

                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setTitle("Creating Account...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        user = mAuth.getInstance().getCurrentUser();
                                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        Map<String, Object> data = new HashMap<>();
                                        data.put("name", nm);
                                        data.put("number", num);
//                                        city.put("country", "USA");

                                        db.collection("User").document(currentUser).set(data)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getApplicationContext(), "Sign-up Successful", Toast.LENGTH_LONG).show();
                                                        Intent a = new Intent(SignUp.this, Home.class);
                                                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(a);
                                                        finish();
                                                        progressDialog.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Sign-up Un Successful", Toast.LENGTH_LONG).show();
                                                        progressDialog.dismiss();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Couldn't Sign-up", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean verify(String email, String pass, String cpass, String name){
        if(email.isEmpty())
            Toast.makeText(getApplicationContext(), "मोबाइल नंबर दर्ज करें", Toast.LENGTH_LONG).show();
        else if (pass.isEmpty())
            Toast.makeText(getApplicationContext(), "पासवर्ड दर्ज करें", Toast.LENGTH_LONG).show();
        else if (cpass.isEmpty())
            Toast.makeText(getApplicationContext(), "पासवर्ड की पुष्टि कीजिये", Toast.LENGTH_LONG).show();
        else if (name.isEmpty())
            Toast.makeText(getApplicationContext(), "नाम दर्ज करें", Toast.LENGTH_LONG).show();
        else if (email.length()!=10)
            Toast.makeText(getApplicationContext(), "ग़लत मोबाइल नंबरं", Toast.LENGTH_LONG).show();
        else if (!pass.equals(cpass))
            Toast.makeText(getApplicationContext(), "पासवर्ड मेल नहीं खा रहा. पुनः दर्ज करें", Toast.LENGTH_LONG).show();
        else if (name.isEmpty())
            Toast.makeText(getApplicationContext(), "नाम दर्ज करेंं", Toast.LENGTH_LONG).show();
        else
            return false;
        return true;
    }
}