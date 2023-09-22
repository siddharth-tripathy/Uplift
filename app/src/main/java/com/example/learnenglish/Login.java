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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText userName, pass;
    private Button submit;
    private TextView signup;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        signup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userName.getText().toString();
                String password = pass.getText().toString();
//                Toast.makeText(getApplicationContext(), email + password, Toast.LENGTH_LONG).show();

                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter Mobile Number and Password", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog = new ProgressDialog(Login.this);

                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setTitle("Logging In...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    email += "@learnenglish.com";
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                        Intent a = new Intent(Login.this, Home.class);
                                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(a);
                                        finish();
                                    } else {

                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });



    }
}