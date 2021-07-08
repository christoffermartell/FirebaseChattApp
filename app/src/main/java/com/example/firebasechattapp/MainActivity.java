package com.example.firebasechattapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    EditText password,email;
    Button signUp,signIn;
    DatabaseReference databaseReference;


    private boolean isEmailValid(CharSequence email){
        if (email == null){
            return false;

        }else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        signUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.SignIn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter both Email & Password", Toast.LENGTH_SHORT).show();
                }else if (!isEmailValid(email.getText().toString())){
                    Toast.makeText(MainActivity.this, "Write a valid Email", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               Toast.makeText(MainActivity.this, "SignUp Success", Toast.LENGTH_SHORT).show();
                           }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error in signing upp " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter both Email & Password", Toast.LENGTH_SHORT).show();
                }else if (!isEmailValid(email.getText().toString())){
                    Toast.makeText(MainActivity.this, "Write a valid Email", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).setValue(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(MainActivity.this, "SignUp Success", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Failure in saving data" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MainActivity.this, "Error Signing in" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });
    }
}