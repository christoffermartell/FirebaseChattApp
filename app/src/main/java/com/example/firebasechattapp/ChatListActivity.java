package com.example.firebasechattapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {


    ListView userlistView;
    Button signOut;

    ArrayAdapter arrayAdapter;
    ArrayList<String>   users = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userlistView = findViewById(R.id.userListView);
        signOut = findViewById(R.id.signOut);



        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String email = dataSnapshot.child("email").getValue().toString();
                        if (!email.equals(mAuth.getCurrentUser().getEmail())){
                            users.add(email);
                        }
                    }
                    arrayAdapter = new ArrayAdapter(ChatListActivity.this, android.R.layout.simple_list_item_1,users);
                    userlistView.setAdapter(arrayAdapter);
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ChatListActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ChatListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        userlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ChatListActivity.this,ChatActivity.class);
                intent.putExtra("email",users.get(position));
                startActivity(intent);
            }
        });

    }
}